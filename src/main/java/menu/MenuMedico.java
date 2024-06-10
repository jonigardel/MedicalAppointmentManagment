package menu;

import java.util.Scanner;
import services.MedicoService;

public class MenuMedico {

    public static void mostrarMenuMedicos(Scanner scanner) {
        int opcion;
        MedicoService medicoService = new MedicoService();

        do {
            System.out.println("\nAdministrar Medicos");
            System.out.println("1. Registrar Medico");
            System.out.println("2. Editar Medico");
            System.out.println("3. Eliminar Medico");
            System.out.println("4. Listar Medicos");
            System.out.println("5. Volver al Menu Principal");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 ->
                    medicoService.crearMedico();
                case 2 ->
                    medicoService.editarMedico();
                case 3 ->
                    medicoService.eliminarMedico();
                case 4 ->
                    medicoService.listarMedicos();
                case 5 ->
                    System.out.println("Volviendo al Menu Principal...");
                default ->
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        } while (opcion != 5);
    }
}
