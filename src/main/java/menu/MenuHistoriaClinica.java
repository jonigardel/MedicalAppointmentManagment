package menu;

import java.util.Scanner;
import services.HistoriaClinicaService;

public class MenuHistoriaClinica {
    public static void mostrarMenuHistoriasClinicas(Scanner scanner) {
        int opcion;
        HistoriaClinicaService historiaClinicaService = new HistoriaClinicaService();

        do {
            System.out.println("\nAdministrar Historias Clinicas");
            System.out.println("1. Registrar Historia Clinica");
            System.out.println("2. Editar Historia Clinica");
            System.out.println("3. Eliminar Historia Clinica");
            System.out.println("4. Listar Historias Clinicas");
            System.out.println("5. Consultar Historia Clinica por Paciente");
            System.out.println("6. Volver al Menu Principal");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();  

            switch (opcion) {
                case 1 -> historiaClinicaService.crearHistoriaClinica();
                case 2 -> historiaClinicaService.editarHistoriaClinica();
                case 3 -> historiaClinicaService.eliminarHistoriaClinica();
                case 4 -> historiaClinicaService.listarHistoriasClinicas();
                case 5 -> historiaClinicaService.buscarHistoriaClinicaPorDNI();
                case 6 -> System.out.println("Volviendo al Menu Principal...");
                default -> System.out.println("Opcion no valida. Intente de nuevo.");
            }
        } while (opcion != 6);
    }
}
