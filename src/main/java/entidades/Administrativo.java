package entidades;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Administrativo extends Usuario {
    private String puesto;
    private boolean administrador; 

    public Administrativo(UUID id, String nombre, String apellido, int dni, String email, String direccion, Date fechaDeNacimiento, String nacionalidad, String puesto, boolean administrador) {
        super(id, nombre, apellido, dni, email, direccion, fechaDeNacimiento, nacionalidad);
        this.puesto = puesto;
        this.administrador = administrador;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
    
    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    @Override
    public void mostrarInformacion() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String isAdministrador = (this.isAdministrador()) ? "Si" : "No";
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Datos del Medico:");
        System.out.println("Nombre: " + this.getNombre());
        System.out.println("Apellido: " + this.getApellido());
        System.out.println("DNI: " + this.getDni());
        System.out.println("Email: " + this.getEmail());
        System.out.println("Direccion: " + this.getDireccion());
        System.out.println("Fecha de Nacimiento: " + dateFormat.format(this.getFechaDeNacimiento()));
        System.out.println("Nacionalidad: " + this.getNacionalidad());
        System.out.println("Es adaministrador: " + isAdministrador);
        System.out.println("Posicion: " + this.getPuesto());
        System.out.println("--------------------------------------------------------------------------------");
    }
}
