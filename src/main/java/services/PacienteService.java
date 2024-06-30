package services;

import controller.HistoriaClinicaDao;
import entidades.HistoriaClinica;
import entidades.Paciente;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import controller.PacienteDao;
import controller.TurnoDao;
import entidades.Turno;
import java.util.Map;

public class PacienteService {

    private static List<Paciente> pacientes = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void registrarPaciente() {
        System.out.println("Ingrese el nombre del paciente:");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese el apellido del paciente:");
        String apellido = scanner.nextLine();

        System.out.println("Ingrese el DNI del paciente:");
        int dni = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Ingrese el email del paciente:");
        String email = scanner.nextLine();

        System.out.println("Ingrese la direccion del paciente:");
        String direccion = scanner.nextLine();

        System.out.println("Ingrese la fecha de nacimiento del paciente (yyyy-MM-dd):");
        String fechaNacimientoStr = scanner.nextLine();

        Date fechaNacimiento = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fechaNacimiento = dateFormat.parse(fechaNacimientoStr);
        } catch (ParseException e) {
            System.out.println("Fecha de nacimiento no valida. Usando fecha por defecto.");
            fechaNacimiento = new Date();
        }

        System.out.println("Ingrese la nacionalidad del paciente:");
        String nacionalidad = scanner.nextLine();

        System.out.println("Ingrese la obra social del paciente:");
        String obraSocial = scanner.nextLine();

        System.out.println("Ingrese la credencial de la obra social:");
        int credencial = scanner.nextInt();

        HistoriaClinica historia = new HistoriaClinica(UUID.randomUUID(), "", "", "");
        HistoriaClinicaDao.insert(historia);
        Paciente paciente = new Paciente(UUID.randomUUID(), nombre, apellido, dni, email, direccion, fechaNacimiento, nacionalidad, obraSocial, credencial, historia.getId());
        PacienteDao.insert(paciente);
        System.out.println("---------------------------------");
        System.out.println("Paciente Registrado correctamente");

        paciente.mostrarInformacion();
        System.out.println("Generada historia clinica id:" + historia.getId());
    }

    public void editarPaciente() {
        System.out.println("Ingrese el DNI del paciente a editar:");
        int dni = scanner.nextInt();
        scanner.nextLine();

        Paciente paciente = buscarPacientePorDNI(dni);
        if (paciente != null) {
            System.out.println("Para no editar un campo, deje vacio y presione enter.");
            System.out.println("Ingrese el nombre del paciente:");
            String nombre = scanner.nextLine();
            if (!nombre.isEmpty()) {
                paciente.setNombre(nombre);
            }

            System.out.println("Ingrese el apellido del paciente:");
            String apellido = scanner.nextLine();
            if (!apellido.isEmpty()) {
                paciente.setApellido(apellido);
            }

            System.out.println("Ingrese el email del paciente:");
            String email = scanner.nextLine();
            if (!email.isEmpty()) {
                paciente.setEmail(email);
            }

            System.out.println("Ingrese la direccion del paciente:");
            String direccion = scanner.nextLine();
            if (!direccion.isEmpty()) {
                paciente.setDireccion(direccion);
            }

            System.out.println("Ingrese la fecha de nacimiento del paciente (yyyy-MM-dd):");
            String fechaNacimientoStr = scanner.nextLine();
            if (!fechaNacimientoStr.isEmpty()) {
                Date fechaNacimiento = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    fechaNacimiento = dateFormat.parse(fechaNacimientoStr);
                    paciente.setFechaDeNacimiento(fechaNacimiento);
                } catch (ParseException e) {
                    System.out.println("Fecha de nacimiento no valida. No se modificara.");
                }
            }

            System.out.println("Ingrese la nacionalidad del paciente:");
            String nacionalidad = scanner.nextLine();
            if (!nacionalidad.isEmpty()) {
                paciente.setNacionalidad(nacionalidad);
            }

            System.out.println("Ingrese la obra social del paciente:");
            String obraSocial = scanner.nextLine();
            if (!obraSocial.isEmpty()) {
                paciente.setObraSocial(obraSocial);
            }

            System.out.println("Ingrese la credencial de la obra social:");
            String credencial = scanner.nextLine();
            if (!credencial.isEmpty()) {
                paciente.setNumeroCredencial(Integer.parseInt(credencial));
            }
            PacienteDao.update(paciente);

            System.out.println("Paciente actualizado:");
            paciente.mostrarInformacion();
        } else {
            System.out.println("Paciente no encontrado.");
        }
    }

    public void eliminarPaciente() {
        System.out.println("Ingrese el DNI del paciente a eliminar:");
        int dni = scanner.nextInt();
        scanner.nextLine();

        Paciente paciente = buscarPacientePorDNI(dni);
        if (paciente != null) {

            //Al estar referenciados por fk, se necesita primero eliminar todas los turnos del Paciente.
            //Ademas se elimina la historia clinica
            List<Map<String, Object>> turnos = TurnoDao.listTurnosPorPaciente(paciente.getId().toString());

            for (Map<String, Object> turno : turnos) {
                String id = (String) turno.get("id");
                TurnoDao.delete(id);
            }
            HistoriaClinicaDao.delete(paciente.getIdHistoriaCLinica().toString());
            PacienteDao.delete(paciente.getId().toString());
            System.out.println("Paciente eliminado con éxito.");
        } else {
            System.out.println("Paciente no encontrado.");
        }
    }

    public void listarPacientes() {
        pacientes = PacienteDao.listPacientes();
        for (Paciente paciente : pacientes) {
            paciente.mostrarInformacion();
        }
    }

    public void buscarPorDni() {
        System.out.println("Ingrese el DNI del paciente a buscar:");
        int dni = scanner.nextInt();
        scanner.nextLine();
        Paciente p = buscarPacientePorDNI(dni);
        if (p != null) {
            p.mostrarInformacion();

        } else {
            System.out.println("no se encotrno paciente con el dni solicitado");
        }

    }

    public static Paciente buscarPacientePorDNI(int dni) {
        Paciente p = PacienteDao.getPacienteByDni(dni);
        return p;
    }

}
