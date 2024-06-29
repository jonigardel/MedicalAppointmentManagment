package services;

import controller.HistoriaClinicaDao;
import entidades.HistoriaClinica;
import entidades.Paciente;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class HistoriaClinicaService {
    private List<HistoriaClinica> historiasClinicas = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void crearHistoriaClinica() {

        System.out.println("Ingrese el diagnostico:");
        String diagnostico = scanner.nextLine();

        System.out.println("Ingrese las observaciones:");
        String observaciones = scanner.nextLine();

        System.out.println("Ingrese el tratamiento:");
        String tratamiento = scanner.nextLine();

        HistoriaClinica historiaClinica = new HistoriaClinica(UUID.randomUUID(), diagnostico, observaciones, tratamiento);
HistoriaClinicaDao.insert(historiaClinica);
        historiasClinicas.add(historiaClinica);

        mostrarHistoriaClinica(historiaClinica);
    }

    public void editarHistoriaClinica() {
        System.out.println("Ingrese el ID del paciente de la historia clinica a editar:");
        int dniPaciente = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        HistoriaClinica historiaClinica = buscarHistoriaClinicaPorPaciente(dniPaciente);
        if (historiaClinica != null) {
            System.out.println("Ingrese el nuevo diagnostico:");
            String diagnostico = scanner.nextLine();

            System.out.println("Ingrese las nuevas observaciones:");
            String observaciones = scanner.nextLine();

            System.out.println("Ingrese el nuevo tratamiento:");
            String tratamiento = scanner.nextLine();

            historiaClinica.setDiagnostico(diagnostico);
            historiaClinica.setObservaciones(observaciones);
            historiaClinica.setTratamiento(tratamiento);

            System.out.println("Historia clinica actualizada:");
            mostrarHistoriaClinica(historiaClinica);
        } else {
            System.out.println("Historia clinica no encontrada.");
        }
    }

    public void eliminarHistoriaClinica() {
        System.out.println("Ingrese el dni de la historia clinica a eliminar:");
        int dniPaciente = scanner.nextInt();
        scanner.nextLine(); 

        HistoriaClinica historiaClinica = buscarHistoriaClinicaPorPaciente(dniPaciente);
        if (historiaClinica != null) {
            historiasClinicas.remove(historiaClinica);
            System.out.println("Historia clnica eliminada.");
        } else {
            System.out.println("Historia clinica no encontrada.");
        }
    }

    public void listarHistoriasClinicas() {
        if (historiasClinicas.isEmpty()) {
            System.out.println("No hay historias clinicas registradas.");
        } else {
            for (HistoriaClinica historiaClinica : historiasClinicas) {
                mostrarHistoriaClinica(historiaClinica);
            }
        }
    }

    private HistoriaClinica buscarHistoriaClinicaPorPaciente(int dniPaciente) {
        Paciente paciente = PacienteService.buscarPacientePorDNI(dniPaciente);
        for (HistoriaClinica historiaClinica : historiasClinicas) {
            if (historiaClinica.getId() == paciente.getIdHistoriaCLinica()) {
                return historiaClinica;
            }
        }
        return null;
    }

    private void mostrarHistoriaClinica(HistoriaClinica historiaClinica) {
        System.out.println("Datos de la historia clinica:");
        System.out.println("Diagnostico: " + historiaClinica.getDiagnostico());
        System.out.println("Observaciones: " + historiaClinica.getObservaciones());
        System.out.println("Tratamiento: " + historiaClinica.getTratamiento());
    }
}
