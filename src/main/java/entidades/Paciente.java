package entidades;

import java.util.UUID;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Paciente extends Usuario {
    private String obraSocial;
    private int numeroCredencial;

    public Paciente(UUID id, String nombre, String apellido, int dni, String email, String direccion, Date fechaDeNacimiento, String nacionalidad, String obraSocial, int numeroCredencial) {
        super(id, nombre, apellido, dni, email, direccion, fechaDeNacimiento, nacionalidad);
        this.obraSocial = obraSocial;
        this.numeroCredencial = numeroCredencial;
    }

    public String getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
    }
    
    public int getNumeroCredencial() {
        return numeroCredencial;
    }

    public void setNumeroCredencial(int numeroCredencial) {
        this.numeroCredencial = numeroCredencial;
    }

    @Override
    public void mostrarInformacion() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Datos del paciente:");
        System.out.println("Nombre: " + this.getNombre());
        System.out.println("Apellido: " + this.getApellido());
        System.out.println("DNI: " + this.getDni());
        System.out.println("Email: " + this.getEmail());
        System.out.println("Direccion: " + this.getDireccion());
        System.out.println("Fecha de Nacimiento: " + dateFormat.format(this.getFechaDeNacimiento()));
        System.out.println("Nacionalidad: " + this.getNacionalidad());
        System.out.println("Obra Social: " + this.getObraSocial());
        System.out.println("Credencial Obra Social: " + this.getNumeroCredencial());
        System.out.println("--------------------------------------------------------------------------------");
    }
}

