package menu;

import java.util.Scanner;
import services.PacienteService;

public class MenuPacientes {
    
    public static void mostrarMenuPacientes(Scanner scanner) {
        int opcion;
        PacienteService pacienteService = new PacienteService();

        do {
            System.out.println("\nAdministrar Pacientes");
            System.out.println("1. Registrar Paciente");
            System.out.println("2. Editar Paciente");
            System.out.println("3. Eliminar Paciente");
            System.out.println("4. Listar Pacientes");
            System.out.println("5. Volver al Menu Principal");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();  

            switch (opcion) {
                case 1 -> pacienteService.registrarPaciente();
                case 2 -> pacienteService.editarPaciente();
                case 3 -> pacienteService.eliminarPaciente();
                case 4 -> pacienteService.listarPacientes();
                case 5 -> System.out.println("Volviendo al Menu Principal...");
                default -> System.out.println("Opcion no valida. Intente de nuevo.");
            }
        } while (opcion != 5);
    }
}
