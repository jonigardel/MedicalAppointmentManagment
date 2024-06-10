package services;

import entidades.HistoriaClinica;
import entidades.Paciente;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class PacienteService {
    private static List<Paciente> pacientes = new ArrayList<>();

    public static List<Paciente> getPacientes() {
        return pacientes;
    }
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

        UUID pacienteId =  UUID.randomUUID();
        Paciente paciente = new Paciente(pacienteId, nombre, apellido, dni, email, direccion, fechaNacimiento, nacionalidad, obraSocial, credencial);
        HistoriaClinica historia = new HistoriaClinica(UUID.randomUUID(),pacienteId, "","","");
        
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
            System.out.println("Ingrese el nuevo email del paciente:");
            String email = scanner.nextLine();

            System.out.println("Ingrese la nueva direccion del paciente:");
            String direccion = scanner.nextLine();

            System.out.println("Ingrese la nueva obra social del paciente:");
            String obraSocial = scanner.nextLine();

            paciente.setEmail(email);
            paciente.setDireccion(direccion);
            paciente.setObraSocial(obraSocial);

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
            pacientes.remove(paciente);
            System.out.println("Paciente eliminado.");
        } else {
            System.out.println("Paciente no encontrado.");
        }
    }

    public void listarPacientes() {
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
        } else {
            for (Paciente paciente : pacientes) {
                paciente.mostrarInformacion();
            }
        }
    }

    public static Paciente buscarPacientePorDNI(int dni) {
        var pacientesList = PacienteService.getPacientes();
        for (Paciente paciente : pacientesList) {
            if (paciente.getDni() == dni) {
                return paciente;
            }
        }
        return null;
    }
    
}
