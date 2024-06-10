package menu;

import java.util.Scanner;

public class MainMenu {
    public void mostrarMenu(Scanner scanner) {
        boolean exit = false;

        while (!exit) {
            System.out.println("----- Sistema de Gestion de Turnos Hospitalarios -----");
            System.out.println("1. Administrar Pacientes");
            System.out.println("2. Administrar Medicos");
            System.out.println("3. Administrar Administrativos");
            System.out.println("4. Administrar Historias Clinicas");
            System.out.println("5. Gestionar Turnos");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opcion: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> MenuPacientes.mostrarMenuPacientes(scanner);
                case 2 -> MenuMedico.mostrarMenuMedicos(scanner);
                case 3 -> MenuAdministrativo.mostrarMenuAdministrativos(scanner);
                case 4 -> MenuHistoriaClinica.mostrarMenuHistoriasClinicas(scanner);
                case 5 -> MenuTurno.mostrarMenuHistoriasClinicas(scanner);
                case 6 -> {
                    exit = true;
                    System.out.println("Saliendo del sistema...");
                }
                default -> System.out.println("Opcion no valida. Por favor, intente de nuevo.");
            }
        }
    }
}
