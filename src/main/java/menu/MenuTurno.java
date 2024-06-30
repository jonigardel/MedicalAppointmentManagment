package menu;

import java.util.Scanner;
import services.TurnoService;

public class MenuTurno {
    public static void mostrarMenuHistoriasClinicas(Scanner scanner) {
        int opcion;
        TurnoService turnoService = new TurnoService();

        do {
            System.out.println("\nAdministrar Turnos");
            System.out.println("1. Registrar Nuevo Turno");
            System.out.println("2. Editar Turno");
            System.out.println("3. Cancelar Turno");
            System.out.println("4. Marcar Turno Ausente");
            System.out.println("4. Listar Turnos");
            System.out.println("4. Listar Turnos Ausentes del mes");
            System.out.println("5. Volver al Menu Principal");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> turnoService.crearTurno();
                case 2 -> turnoService.editarTurno();
                case 3 -> turnoService.cancelarAusenteTurno("cancelado");
                case 4 -> turnoService.cancelarAusenteTurno("ausente");
                case 5 -> turnoService.listarTurnosPorPaciente();
                case 6 -> turnoService.listarTurnosEstado("ausente");
                case 7 -> System.out.println("Volviendo al Menu Principal...");
                default -> System.out.println("Opcion no valida. Intente de nuevo.");
            }
        } while (opcion != 7);
    }
}
