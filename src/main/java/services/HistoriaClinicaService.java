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
        System.out.println("Ingrese el DNI del paciente de la historia clinica a editar:");
        int dniPaciente = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        HistoriaClinica historiaClinica = buscarHistoriaClinicaPorPaciente(dniPaciente);
        if (historiaClinica != null) {
            System.out.println("Para no editar un campo, deje vacio y presione enter.");
            System.out.println("Ingrese el nuevo diagnostico:");
            String diagnostico = scanner.nextLine();
            if (!diagnostico.isEmpty()) {
                historiaClinica.setDiagnostico(diagnostico);

            }
            System.out.println("Ingrese las nuevas observaciones:");
            String observaciones = scanner.nextLine();
            if (!observaciones.isEmpty()) {
                historiaClinica.setObservaciones(observaciones);

            }
            System.out.println("Ingrese el nuevo tratamiento:");
            String tratamiento = scanner.nextLine();
            if (!tratamiento.isEmpty()) {
                historiaClinica.setTratamiento(tratamiento);

            }
            HistoriaClinicaDao.update(historiaClinica);
            System.out.println("Historia clinica actualizada:");
            mostrarHistoriaClinica(historiaClinica);
        } else {
            System.out.println("Historia clinica no encontrada.");
        }
    }

    public void eliminarHistoriaClinica() {
        System.out.println("Ingrese el id de la historia clinica a eliminar:");
        String id = scanner.nextLine();
        scanner.nextLine();
        HistoriaClinicaDao.delete(id);
       
    }

    public void listarHistoriasClinicas() {
        historiasClinicas = HistoriaClinicaDao.listHistoriasClinicas();
        for (HistoriaClinica historiaClinica : historiasClinicas) {
            mostrarHistoriaClinica(historiaClinica);
        }
        
    }
    
    public void buscarHistoriaClinicaPorDNI(){
        System.out.println("Ingrese el DNI del paciente de la historia clinica a editar:");
        int dniPaciente = scanner.nextInt();
        HistoriaClinica historiaClinica = buscarHistoriaClinicaPorPaciente(dniPaciente);
        mostrarHistoriaClinica(historiaClinica);
    }

    private HistoriaClinica buscarHistoriaClinicaPorPaciente(int dniPaciente) {
        Paciente paciente = PacienteService.buscarPacientePorDNI(dniPaciente);
        if(paciente != null){
           historiasClinicas = HistoriaClinicaDao.listHistoriasClinicas();
            for (HistoriaClinica historiaClinica : historiasClinicas) {
                if (historiaClinica.getId().toString().equals(paciente.getIdHistoriaCLinica().toString())) {
                    return historiaClinica;
                }
            } 
        }
        return null;
    }

    private void mostrarHistoriaClinica(HistoriaClinica historiaClinica) {
        System.out.println("-------------------------------");
        System.out.println("Datos de la historia clinica:");
        System.out.println("Diagnostico: " + historiaClinica.getDiagnostico());
        System.out.println("Observaciones: " + historiaClinica.getObservaciones());
        System.out.println("Tratamiento: " + historiaClinica.getTratamiento());
        System.out.println("-------------------------------");
    }
}
