package menu;

import java.util.Scanner;
import services.AdministrativoService;

public class MenuAdministrativo {

    public static void mostrarMenuAdministrativos(Scanner scanner) {
        int opcion;
        AdministrativoService administrativoService = new AdministrativoService();

        do {
            System.out.println("\nGestionar Administrativos");
            System.out.println("1. Registrar Administrativo");
            System.out.println("2. Editar Administrativo");
            System.out.println("3. Eliminar Administrativo");
            System.out.println("4. Listar Administrativos");
            System.out.println("5. Volver al Menu Principal");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (opcion) {
                case 1 ->
                    administrativoService.crearAdministrativo();
                case 2 ->
                    administrativoService.editarAdministrativo();

                case 3 ->
                    administrativoService.eliminarAdministrativo();

                case 4 ->
                    administrativoService.listarAdministrativos();

                case 5 ->
                    System.out.println("Volviendo al Menu Principal...");
                default ->
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }

        } while (opcion != 5);
    }
}
