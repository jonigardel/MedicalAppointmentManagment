package entidades;

import java.util.Date;
import java.util.UUID;
import java.text.SimpleDateFormat;
import java.util.Map;

public class Medico extends Usuario {
    private String especialidad;
    private String matricula;
    private Map horarios;
    
    public Medico(UUID id, String nombre, String apellido, int dni, String email, String direccion, Date fechaDeNacimiento, String nacionalidad, String especialidad, String matricula, Map horarios) {
        super(id, nombre, apellido, dni, email, direccion, fechaDeNacimiento, nacionalidad);
        this.especialidad = especialidad;
        this.matricula = matricula;
        this.horarios = horarios;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
     public Map getHorarios() {
        return horarios;
    }

    public void setHorarios(Map horarios) {
        this.horarios = horarios;
    }
    
    @Override
    public void mostrarInformacion() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Datos del Medico:");
        System.out.println("Nombre: " + this.getNombre());
        System.out.println("Apellido: " + this.getApellido());
        System.out.println("DNI: " + this.getDni());
        System.out.println("Email: " + this.getEmail());
        System.out.println("Direccion: " + this.getDireccion());
        System.out.println("Fecha de Nacimiento: " + dateFormat.format(this.getFechaDeNacimiento()));
        System.out.println("Nacionalidad: " + this.getNacionalidad());
        System.out.println("Especialidad: " + this.getEspecialidad());
        System.out.println("Matricula: " + this.getMatricula());
        System.out.println("Horarios de disponibilidad");
        for(var clave : this.horarios.keySet()) {
            System.out.println("Dia: "+ clave + ". Horario: "+ this.horarios.get(clave));
        }
        System.out.println("--------------------------------------------------------------------------------");
    }
}
