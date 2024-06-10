package entidades;

import java.util.Date;
import java.util.UUID;

public class Turno {
    
    private UUID id;
    private UUID idMedico;
    private UUID idPaciente;
    private UUID idAdministrativo;
    private Date fecha;
    private String hora;
    private String estado;

     public Turno(UUID id, UUID idMedico, UUID idPaciente, Date fecha, String hora, String estado) {
        this.id = id;
        this.idMedico = idMedico;
        this.idPaciente = idPaciente;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }
    
    public Turno(UUID id, UUID idMedico, UUID idPaciente, UUID idAdministrativo, Date fecha, String hora, String estado) {
        this.id = id;
        this.idMedico = idMedico;
        this.idPaciente = idPaciente;
        this.idAdministrativo = idAdministrativo;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(UUID idMedico) {
        this.idMedico = idMedico;
    }

    public UUID getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(UUID idPaciente) {
        this.idPaciente = idPaciente;
    }

    public UUID getIdAdministrativo() {
        return idAdministrativo;
    }

    public void setIdAdministrativo(UUID idAdministrativo) {
        this.idAdministrativo = idAdministrativo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return "Turno{" + "id=" + id + ", idMedico=" + idMedico + ", idPaciente=" + idPaciente + ", idAdministrativo=" + idAdministrativo + ", fecha=" + fecha + ", hora=" + hora + ", estado=" + estado + '}';
    }
}
