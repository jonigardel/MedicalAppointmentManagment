package services;

import entidades.*;
import services.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class TurnoService {

    private List<Turno> turnos = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private MedicoService serviceMedico = new MedicoService();

    public void crearTurno() {
        try {
            System.out.println("Seleccione la especialidad medica para el turno:");
            List<String> listaEspecialidades = serviceMedico.listarEspecialidades();
            String especialidad;
            if (listaEspecialidades.isEmpty()) {
                System.out.println("No se encontro especialidad disponible");
                //se crea una especialidad para el tp3 ya que sino no se puede continuar con la carga
                System.out.println("Se agrega la especialidad guardia");
                especialidad = "guardia";
                //throw new Exception();
            } else {
                for (int i = 0; i < listaEspecialidades.size(); i++) {
                    System.out.println(i + ". " + listaEspecialidades.get(i));
                }
                int eleccion = scanner.nextInt();
                especialidad = listaEspecialidades.get(eleccion);
            }

            List<Medico> especialistas = serviceMedico.medicosPorEspecialidad(especialidad);
            Medico medico;
            if (especialistas.isEmpty()) {
                System.out.println("No se encontro medico especialista disponible");
                System.out.println("Se deriva medico de guardia");
                Map<String, String> horarios = new HashMap<>();
                horarios.put("lunes", "08:30/17:30");
                //se crea un medico para el tp3 ya que sino no se puede continuar con la carga
                medico = new Medico(UUID.randomUUID(), "Juan", "Perez", 78956321, "juan.perez@gmail.com", "calle 123", dateFormat.parse("1980-02-20"), "Argentino", "Argentino", "35369", horarios);
                //throw new Exception();
            } else {
                for (int i = 0; i < especialistas.size(); i++) {
                    System.out.println(i + ". " + especialistas.get(i));
                }
                int eleccionMedico = scanner.nextInt();
                medico = especialistas.get(eleccionMedico);
            }

            List<Turno> turnosTomados = this.listarTurnosPorMedico(medico.getId());
            System.out.println("A continuacion se muestran los turnos disponibles por semana");

            listarTurnosPorSemana(medico.getHorarios(), turnosTomados);
            Date fechaTurno = null;
            String horaSeleccionada = null;
            boolean disponible = false;
            while (!disponible) {

                System.out.println("Ingrese fecha seleccionada(yyyy-mm-dd)");

                String fechaSeleccionada = scanner.nextLine();

                fechaTurno = dateFormat.parse(fechaSeleccionada);

                System.out.println("Ingrese hora seleccionada(HH:mm)");

                horaSeleccionada = scanner.nextLine();

                disponible = this.validarTurnoDisponible(fechaTurno, horaSeleccionada, turnosTomados);
            }
            
            Turno nuevoTurno = new Turno(UUID.randomUUID(), medico.getId(), UUID.randomUUID(), fechaTurno, horaSeleccionada, "enEspera");
            nuevoTurno.toString();
        } catch (ParseException e) {
            System.out.println(e);
            System.out.println("No se pudo agendar el turno");
        }

    }

    public void editarTurno() {
        System.out.println("Ingrese el ID del turno a editar:");
        String idTurno = scanner.nextLine();
        scanner.nextLine();  // Consume newline

        Turno turno = buscarTurnoPorId(idTurno);
        if (turno != null) {
            System.out.println("Ingrese el nuevo estado del turno:");
            String estado = scanner.nextLine();

            turno.setEstado(estado);

            System.out.println("Turno actualizado:");
            mostrarTurno(turno);
        } else {
            System.out.println("Turno no encontrado.");
        }
    }

    public void eliminarTurno() {
        System.out.println("Ingrese el ID del turno a eliminar:");
        String idTurno = scanner.nextLine();
        scanner.nextLine();

        Turno turno = buscarTurnoPorId(idTurno);
        if (turno != null) {
            turnos.remove(turno);
            System.out.println("Turno eliminado.");
        } else {
            System.out.println("Turno no encontrado.");
        }
    }

    public void listarTurnos() {
        if (turnos.isEmpty()) {
            System.out.println("No hay turnos registrados.");
        } else {
            for (Turno turno : turnos) {
                mostrarTurno(turno);
            }
        }
    }

    public List<Turno> listarTurnosPorMedico(UUID idMedico) {
        List<Turno> turnosTomados;
        turnosTomados = new ArrayList<>();
        if (turnos.isEmpty()) {
            System.out.println("No hay turnos registrados.");
            turnosTomados = turnos;
        } else {
            for (Turno turno : turnos) {
                if (turno.getIdMedico() == idMedico && turno.getEstado().equals("enEspera")) {
                    turnosTomados.add(turno);
                }
            }
        }
        return turnosTomados;
    }

    public List<String> listarSemana() {
        List<String> semana = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        semana.add(hoy.format(formatter));

        for (int i = 0; i < 7; i++) {
            LocalDate proximoDia = hoy.plusDays(i + 1);
            semana.add(proximoDia.format(formatter));
        }
        return semana;
    }

    public void listarTurnosPorSemana(Map<String, String> disponibilidad, List<Turno> turnosTomados) {
        List<String> semana = this.listarSemana();
        for (int i = 0; i < semana.size(); i++) {
            DayOfWeek dia = LocalDate.parse(semana.get(i)).getDayOfWeek();
            String diaSemana = dia.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
            this.listarTurnosPorDia(disponibilidad, turnosTomados, diaSemana, semana.get(i));
        }
    }

    public void listarTurnosPorDia(Map<String, String> disponibilidad, List<Turno> turnosTomados, String dia, String fecha) {
        //validamos si el medico tiene atencion el dia solicitado, en caso contrario no se continua.
        if (!disponibilidad.containsKey(dia.toLowerCase())) {
            System.out.println("No hay disponibilidad para " + dia + " " + fecha);
            return;
        }
        System.out.println(dia + " " + fecha);

        String[] horasDisponibles = disponibilidad.get(dia).split("/");
        String inicio = horasDisponibles[0];
        String fin = horasDisponibles[1];

        List<String> horasOcupadas = new ArrayList<>();
        for (Turno turno : turnosTomados) {
            if (fecha.equalsIgnoreCase(dateFormat.format(turno.getFecha()))) {
                //se agregan a la lista las horas que estan ocupadas del dia.
                horasOcupadas.add(turno.getHora());
            }
        }

        listarHorasDisponibles(inicio, fin, horasOcupadas);
    }

    public static void listarHorasDisponibles(String inicio, String fin, List<String> horasOcupadas) {
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
        try {
            Date inicioDate = hourFormat.parse(inicio);
            Date finDate = hourFormat.parse(fin);
            Calendar cal = Calendar.getInstance();
            cal.setTime(inicioDate);
            //listar horarios disponibles entre la fecha de inicio y fin de jornada del medico
            while (cal.getTime().before(finDate)) {
                String horaActual = hourFormat.format(cal.getTime());
                if (!horasOcupadas.contains(horaActual)) {
                    //solo se logea en el caso que la hora no este ocupada con otro turno.
                    System.out.println(horaActual);
                }
                cal.add(Calendar.MINUTE, 30); // los turnos se dan cada 30 minutos
            }
        } catch (ParseException e) {
        }
    }

    private boolean validarTurnoDisponible(Date fecha, String hora, List<Turno> turnosTomados) {
        for (int i = 0; i < turnosTomados.size(); i++) {
            Turno turno = turnosTomados.get(i);
            if (turno.getFecha().equals(fecha) && turno.getHora().equals(hora)) {
                return false;
            }
        }
        return true;
    }

    private Turno buscarTurnoPorId(String idTurno) {
        UUID uuidTurno = UUID.fromString(idTurno);
        for (Turno turno : turnos) {
            if (turno.getId() == uuidTurno) {
                return turno;
            }
        }
        return null;
    }

    private void mostrarTurno(Turno turno) {
        System.out.println("Datos del turno:");
        System.out.println("ID Turno: " + turno.getId());
        System.out.println("ID Administrativo: " + turno.getIdAdministrativo());
        System.out.println("ID Medico: " + turno.getIdMedico());
        System.out.println("ID Paciente: " + turno.getIdPaciente());
        System.out.println("Fecha: " + dateFormat.format(turno.getFecha()));
        System.out.println("Estado: " + turno.getEstado());
    }
}
