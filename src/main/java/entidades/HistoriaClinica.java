package entidades;

import java.util.UUID;

public class HistoriaClinica {
    private UUID id;
    private UUID idPaciente;
    private String diagnostico;
    private String observaciones;
    private String tratamiento;
    
    public HistoriaClinica(UUID id,String diagnostico, String observaciones, String tratamiento) {
        this.id = id;
        this.diagnostico = diagnostico;
        this.observaciones = observaciones;
        this.tratamiento = tratamiento;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }
}
