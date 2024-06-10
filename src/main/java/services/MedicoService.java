package services;

import entidades.Medico;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MedicoService {
    private List<Medico> medicos = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void crearMedico() {
        System.out.println("Ingrese el nombre del medico:");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese el apellido del medico:");
        String apellido = scanner.nextLine();

        System.out.println("Ingrese el DNI del medico:");
        int dni = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.println("Ingrese el email del medico:");
        String email = scanner.nextLine();

        System.out.println("Ingrese la direccion del medico:");
        String direccion = scanner.nextLine();

        System.out.println("Ingrese la fecha de nacimiento del medico (yyyy-MM-dd):");
        String fechaNacimientoStr = scanner.nextLine();
        Date fechaNacimiento;
        try {
            fechaNacimiento = dateFormat.parse(fechaNacimientoStr);
        } catch (ParseException e) {
            System.out.println("Fecha de nacimiento no valida. Usando fecha por defecto.");
            fechaNacimiento = new Date();
        }

        System.out.println("Ingrese la nacionalidad del medico:");
        String nacionalidad = scanner.nextLine();

        System.out.println("Ingrese la especialidad del medico:");
        String especialidad = scanner.nextLine();

        System.out.println("Ingrese la matricula del medico:");
        String matricula = scanner.nextLine();
        
        System.out.println("Ingrese la disponibilidad horaria del medico por dia:");
        String dia;
        Map<String, String> horarios = new HashMap<>();
        do {
            System.out.print("Ingrese el dia (o 'exit' para Continuar): ");
            dia = scanner.nextLine();
            if (!dia.equalsIgnoreCase("exit")) {
                System.out.print("Ingrese hora de inicio (HH:mm): ");
                String inicio = scanner.nextLine();
                System.out.print("Ingrese hora de fin (HH:mm): ");
                String fin = scanner.nextLine();
                horarios.put(dia.toLowerCase(), inicio + "/"+ fin);
            }
        } while (!dia.equalsIgnoreCase("exit"));
        
        
        Medico medico = new Medico(UUID.randomUUID(), nombre, apellido, dni, email, direccion, fechaNacimiento, nacionalidad, especialidad, matricula, horarios);

        medicos.add(medico);

        medico.mostrarInformacion();
    }

    public void editarMedico() {
        System.out.println("Ingrese el DNI del medico a editar:");
        int dni = scanner.nextInt();
        scanner.nextLine(); 

        Medico medico = buscarMedicoPorDNI(dni);
        if (medico != null) {
            System.out.println("Ingrese el nuevo email del medico:");
            String email = scanner.nextLine();

            System.out.println("Ingrese la nueva direccion del medico:");
            String direccion = scanner.nextLine();

            System.out.println("Ingrese la nueva especialidad del medico:");
            String especialidad = scanner.nextLine();

            medico.setEmail(email);
            medico.setDireccion(direccion);
            medico.setEspecialidad(especialidad);

            System.out.println("medico actualizado:");
            medico.mostrarInformacion();
        } else {
            System.out.println("medico no encontrado.");
        }
    }

    public void eliminarMedico() {
        System.out.println("Ingrese el DNI del medico a eliminar:");
        int dni = scanner.nextInt();
        scanner.nextLine();

        Medico medico = buscarMedicoPorDNI(dni);
        if (medico != null) {
            medicos.remove(medico);
            System.out.println("medico eliminado.");
        } else {
            System.out.println("medico no encontrado.");
        }
    }

    public void listarMedicos() {
        if (medicos.isEmpty()) {
            System.out.println("No hay medico registrados.");
        } else {
            for (Medico medico : medicos) {
                medico.mostrarInformacion();
            }
        }
    }

    public List<String> listarEspecialidades(){
        List<String> especialidades = new ArrayList<>();
        if (!medicos.isEmpty()) {
            for (Medico medico : medicos) {
                especialidades.add(medico.getEspecialidad());
            }
        }
        return especialidades;
    }
    
     public List<Medico> medicosPorEspecialidad(String especialidad){
        List<Medico> especialistas = new ArrayList<>();
        if (!medicos.isEmpty()) {
            for (Medico medico : medicos) {
                if(medico.getEspecialidad().equalsIgnoreCase(especialidad)){
                    especialistas.add(medico);
                }
            }
        }
        return especialistas;
    }
    
    private Medico buscarMedicoPorDNI(int dni) {
        for (Medico medico : medicos) {
            if (medico.getDni() == dni) {
                return medico;
            }
        }
        return null;
    }
}
