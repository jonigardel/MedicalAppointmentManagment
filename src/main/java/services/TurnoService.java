package services;

import controller.TurnoDao;
import entidades.*;
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
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class TurnoService {

    private List<Turno> turnos = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private MedicoService serviceMedico = new MedicoService();

    public void crearTurno() {
        try {
            //se solicita primero el dni del paciente, al no tener sesion
            System.out.println("Ingrese el dni del paciente:");
            int dni = scanner.nextInt();
            Paciente paciente = PacienteService.buscarPacientePorDNI(dni);
            if (paciente != null) {
                System.out.println("Seleccione la especialidad medica para el turno:");
                List<String> listaEspecialidades = serviceMedico.listarEspecialidades();
                String especialidad;

                for (int i = 0; i < listaEspecialidades.size(); i++) {
                    System.out.println((i + 1) + ". " + listaEspecialidades.get(i));
                }
                int eleccion = scanner.nextInt();
                especialidad = listaEspecialidades.get(eleccion - 1);

                List<Medico> especialistas = serviceMedico.medicosPorEspecialidad(especialidad);
                Medico medico = null;
                if (especialistas.isEmpty()) {
                    System.out.println("No se encontro medico especialista disponible");
                } else {
                    System.out.println("Seleccione la especialidad medica para el turno:");
                    for (int i = 0; i < especialistas.size(); i++) {
                        System.out.println((i + 1) + ". " + especialistas.get(i).getNombre() + " " + especialistas.get(i).getApellido());
                    }
                    int eleccionMedico = scanner.nextInt();
                    medico = especialistas.get(eleccionMedico - 1);
                }

                if (medico != null) {
                    List<Turno> turnosTomados = this.listarTurnosPorMedico(medico.getId());
                    System.out.println("A continuacion se muestran los turnos disponibles por semana");

                    listarTurnosPorSemana(medico.getHorarios(), turnosTomados);
                    Date fechaTurno = null;
                    String horaSeleccionada = null;
                    boolean disponible = false;
                    while (!disponible) {

                        System.out.println("Ingrese fecha seleccionada(yyyy-mm-dd)");
                        //todo se omite un scan porque en la primera iteracion no toma la nueva linea
                        String scannResidual = scanner.nextLine();
                        String fechaSeleccionada = scanner.nextLine();

                        fechaTurno = dateFormat.parse(fechaSeleccionada);

                        System.out.println("Ingrese hora seleccionada(HH:mm)");

                        horaSeleccionada = scanner.nextLine();

                        disponible = this.validarTurnoDisponible(medico.getHorarios(),fechaTurno, horaSeleccionada, turnosTomados);
                    }
                    Turno nuevoTurno = new Turno(UUID.randomUUID(), medico.getId(), paciente.getId(), fechaTurno, horaSeleccionada, "pendiente");
                    TurnoDao.insert(nuevoTurno);
                    System.out.println("Turno creado Correctamente");
                    TurnoDao.mostrarTurno(nuevoTurno);
                }
            } else {
                System.out.println("No se encontro al paciente, registrelo previamente");
            }
        } catch (ParseException e) {
            System.out.println(e);
            System.out.println("No se pudo agendar el turno");
        }

    }

    public void editarTurno() {
        try {
            System.out.println("Ingrese el dni del paciente:");
            int dni = scanner.nextInt();
            Paciente paciente = PacienteService.buscarPacientePorDNI(dni);
            if (paciente != null) {
                List<Map<String, Object>> turnos = TurnoDao.listTurnosPorPaciente(paciente.getId().toString());

                List<Map<String, Object>> turnosPendientes = new ArrayList<>();

                for (Map<String, Object> turno : turnos) {
                    if (turno.get("estado") instanceof String && "pendiente".equals(turno.get("estado"))) {
                        turnosPendientes.add(turno);
                    }
                }
                if (!turnosPendientes.isEmpty()) {
                    for (int i = 0; i < turnosPendientes.size(); i++) {
                        Map<String, Object> turno = turnos.get(i);
                        String fecha = (String) turno.get("fecha");
                        String hora = (String) turno.get("hora");
                        String nombreMedico = (String) turno.get("nombre_medico");
                        String apellidoMedico = (String) turno.get("apellido_medico");
                        System.out.println((i + 1) + ". " + fecha + " " + hora + " " + nombreMedico + " " + apellidoMedico + ".");
                    }

                    int eleccion = scanner.nextInt();
                    Map<String, Object> turnoSeleccionado = turnos.get(eleccion - 1);
                    String idTurno = (String) turnoSeleccionado.get("id");

                    Turno turno = TurnoDao.buscarPorId(idTurno);

                    System.out.println("Seleccione la especialidad medica para el turno:");
                    List<String> listaEspecialidades = serviceMedico.listarEspecialidades();
                    String especialidad;

                    for (int i = 0; i < listaEspecialidades.size(); i++) {
                        System.out.println((i + 1) + ". " + listaEspecialidades.get(i));
                    }
                    int eleccionEspecialidad = scanner.nextInt();
                    especialidad = listaEspecialidades.get(eleccionEspecialidad - 1);

                    List<Medico> especialistas = serviceMedico.medicosPorEspecialidad(especialidad);
                    Medico medico = null;
                    if (especialistas.isEmpty()) {
                        System.out.println("No se encontro medico especialista disponible");
                    } else {
                        System.out.println("Seleccione la especialidad medica para el turno:");
                        for (int i = 0; i < especialistas.size(); i++) {
                            System.out.println((i + 1) + ". " + especialistas.get(i).getNombre() + " " + especialistas.get(i).getApellido());
                        }
                        int eleccionMedico = scanner.nextInt();
                        medico = especialistas.get(eleccionMedico - 1);
                    }

                    if (medico != null) {
                        List<Turno> turnosTomados = this.listarTurnosPorMedico(medico.getId());
                        System.out.println("A continuacion se muestran los turnos disponibles por semana");

                        listarTurnosPorSemana(medico.getHorarios(), turnosTomados);
                        Date fechaTurno = null;
                        String horaSeleccionada = null;
                        boolean disponible = false;
                        while (!disponible) {

                            System.out.println("Ingrese fecha seleccionada(yyyy-mm-dd)");
                            //todo se omite un scan porque en la primera iteracion no toma la nueva linea
                            String scannResidual = scanner.nextLine();
                            String fechaSeleccionada = scanner.nextLine();

                            fechaTurno = dateFormat.parse(fechaSeleccionada);

                            System.out.println("Ingrese hora seleccionada(HH:mm)");

                            horaSeleccionada = scanner.nextLine();

                            disponible = this.validarTurnoDisponible(medico.getHorarios(),fechaTurno, horaSeleccionada, turnosTomados);
                        }

                        turno.setIdMedico(medico.getId());
                        turno.setFecha(fechaTurno);
                        turno.setHora(horaSeleccionada);
                        TurnoDao.update(turno);
                        System.out.println("Turno creado Correctamente");
                        TurnoDao.mostrarTurno(turno);
                    }
                } else {
                    System.out.println("No hay turnos disponibles para editar");
                }

            } else {
                System.out.println("No se encontro al turno,");
            }
        } catch (ParseException e) {
            System.out.println(e);
            System.out.println("No se pudo agendar el turno");
        }
    }

    public void cancelarAusenteTurno(String estado) {
        System.out.println("Ingrese el dni del paciente:");
        int dni = scanner.nextInt();
        Paciente paciente = PacienteService.buscarPacientePorDNI(dni);
        if(paciente != null){
            List<Map<String, Object>> turnos = TurnoDao.listTurnosPorPaciente(paciente.getId().toString());
            
             List<Map<String, Object>> turnosPendientes = new ArrayList<>();
             //filtra los turnos que puedan cambiar de estado
            for (Map<String, Object> turno : turnos) {
                if (turno.get("estado") instanceof String && "pendiente".equals(turno.get("estado"))) {
                    turnosPendientes.add(turno);
                }
            }
            
            for (int i = 0; i < turnosPendientes.size(); i++) {
                Map<String, Object> turno = turnos.get(i);
                String fecha = (String) turno.get("fecha");
                String hora = (String) turno.get("hora");
                String nombreMedico = (String) turno.get("nombre_medico");
                String apellidoMedico = (String) turno.get("apellido_medico");
                System.out.println((i + 1) + ". " + fecha + " " + hora + " " + nombreMedico+ " " + apellidoMedico +".");
            }
            int eleccion = scanner.nextInt();
            Map<String, Object> turnoSeleccionado = turnos.get(eleccion - 1);
            String idTurno = (String) turnoSeleccionado.get("id");
            
            TurnoDao.cambiarEstado(idTurno, estado);
            System.out.println("Turno"+ estado +"correctamente");
        }
    }

    public void listarTurnos() {
        ArrayList<Turno> turnos = TurnoDao.listTurnos();
        if (turnos.isEmpty()) {
            System.out.println("No hay turnos registrados.");
        } else {
            for (Turno turno : turnos) {
                mostrarTurno(turno);
            }
        }
    }

    public void listarTurnosPorPaciente(){
         System.out.println("Ingrese el dni del paciente:");
        int dni = scanner.nextInt();
        Paciente paciente = PacienteService.buscarPacientePorDNI(dni);
        if(paciente != null){
            ArrayList<Turno> turnos = TurnoDao.listTurnos();
            if (!turnos.isEmpty()) {
                for (Turno turno : turnos) {
                    if (turno.getIdPaciente().toString().equals(paciente.getId().toString())) {
                        TurnoDao.mostrarTurno(turno);
                    }
                }
            } else {
                System.out.println("No hay turnos registrados.");
            }
        }
    }
    
    public void listarTurnosEstado(String estado){
        List<Turno> turnos = TurnoDao.listarTurnosEstado(estado);
        if (!turnos.isEmpty()) {
            for (Turno turno : turnos) {
                mostrarTurno(turno);
            }
        }
    }
    
    
    public List<Turno> listarTurnosPorMedico(UUID idMedico) {
        List<Turno> turnosTomados;
        turnosTomados = new ArrayList<>();
        ArrayList<Turno> turnos = TurnoDao.listTurnos();
        if (!turnos.isEmpty()) {
            for (Turno turno : turnos) {
                if (turno.getIdMedico().toString().equals(idMedico.toString())) {
                    if (turno.getEstado().equals("pendiente")) {
                        turnosTomados.add(turno);
                    }
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
        //se lista los 7 dias siguientes a la fecha
        for (int i = 0; i < 7; i++) {
            LocalDate proximoDia = hoy.plusDays(i + 1);
            semana.add(proximoDia.format(formatter));
        }
        return semana;
    }

    public void listarTurnosPorSemana(Map<String, String> disponibilidad, List<Turno> turnosTomados) {
        List<String> semana = this.listarSemana();
        //se listan los turnos por cada dia de la semana
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
            
            // Convertir horas ocupadas al formato correcto HH
            for (int i = 0; i < horasOcupadas.size(); i++) {
                horasOcupadas.set(i, hourFormat.format(hourFormat.parse(horasOcupadas.get(i))));
            }
            
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

    private boolean validarTurnoDisponible(Map<String, String> disponibilidad, Date fecha, String hora, List<Turno> turnosTomados) {
        try {
            SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
            //validamos que el turno no este dentro de la lista de turnos ya tomados
            for (int i = 0; i < turnosTomados.size(); i++) {
                Turno turno = turnosTomados.get(i);
                if (compararFechas(fecha, turno.getFecha())) {
                    String horaTurno = hourFormat.format(hourFormat.parse(turno.getHora()));
                    if (horaTurno.equals(hora)) {
                        System.out.println("El turno ya esta tomado en esa hora y fecha, por favor elija otro.");
                        return false;
                    }
                }
            }
            SimpleDateFormat formatoDia = new SimpleDateFormat("EEEE", new Locale("es", "ES"));
            String dia = formatoDia.format(fecha);
            //validamo que el dia este dentro de los disponibles
            if(disponibilidad.containsKey(dia.toLowerCase())){
                String[] horasDisponibles = disponibilidad.get(dia).split("/");
                String inicio = horasDisponibles[0];
                String fin = horasDisponibles[1];
                System.out.println("horainicio " + inicio+ "   "  + fin );
                Date horaDate = hourFormat.parse(hora);
                Date inicioDate = hourFormat.parse(inicio);
                Date finDate = hourFormat.parse(fin);
                //se valida que el horario este dentro del disponible por el medico
                if((horaDate.equals(inicioDate) || horaDate.after(inicioDate)) && horaDate.before(finDate)){
                    return true;
                } else {
                    System.out.println("El turno no puede ser asignado para esa hora, por favor elija otra.");
                }
            } else {
                System.out.println("EL turno no puede ser asignado para ese dia, por favor elija otro.");
            }
            
            return false;
        } catch (ParseException e) {
            return false;
        }
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
    
    public static boolean compararFechas(Date fecha1, Date fecha2) {
     //las fechas estan en diferente formato, para poder compararlas se pasa a tipo Calendar y se valida individualmente cada componente
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(fecha1);
        cal2.setTime(fecha2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
               cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }
    
}
