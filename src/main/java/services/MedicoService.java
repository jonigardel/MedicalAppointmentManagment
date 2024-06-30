package services;

import controller.MedicoDao;
import controller.TurnoDao;
import entidades.Medico;
import entidades.Turno;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MedicoService {

    private List<Medico> medicos = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void crearMedico() {
        System.out.println("Ingrese el nombre del medico:");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese el apellido del medico:");
        String apellido = scanner.nextLine();

        System.out.println("Ingrese el DNI del medico:");
        int dni = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.println("Ingrese el email del medico:");
        String email = scanner.nextLine();

        System.out.println("Ingrese la direccion del medico:");
        String direccion = scanner.nextLine();

        System.out.println("Ingrese la fecha de nacimiento del medico (yyyy-MM-dd):");
        String fechaNacimientoStr = scanner.nextLine();
        Date fechaNacimiento;
        try {
            fechaNacimiento = dateFormat.parse(fechaNacimientoStr);
        } catch (ParseException e) {
            System.out.println("Fecha de nacimiento no valida. Usando fecha por defecto.");
            fechaNacimiento = new Date();
        }

        System.out.println("Ingrese la nacionalidad del medico:");
        String nacionalidad = scanner.nextLine();

        System.out.println("Ingrese la especialidad del medico:");
        String especialidad = scanner.nextLine();

        System.out.println("Ingrese la matricula del medico:");
        String matricula = scanner.nextLine();

        System.out.println("Ingrese la disponibilidad horaria del medico por dia:");
        String dia;
        Map<String, String> horarios = new HashMap<>();
        do {
            System.out.print("Ingrese el dia (o 'exit' para Continuar): ");
            dia = scanner.nextLine();
            if (!dia.equalsIgnoreCase("exit")) {
                System.out.print("Ingrese hora de inicio (HH:mm): ");
                String inicio = scanner.nextLine();
                System.out.print("Ingrese hora de fin (HH:mm): ");
                String fin = scanner.nextLine();
                horarios.put(dia.toLowerCase(), inicio + "/" + fin);
            }
        } while (!dia.equalsIgnoreCase("exit"));

        Medico medico = new Medico(UUID.randomUUID(), nombre, apellido, dni, email, direccion, fechaNacimiento, nacionalidad, especialidad, matricula, horarios);

        MedicoDao.insert(medico);
        System.out.println("---------------------------------");
        System.out.println("Medico creado correctamente.");
        medico.mostrarInformacion();
    }

    public void editarMedico() {
        System.out.println("Ingrese el DNI del medico a editar:");
        int dni = scanner.nextInt();
        scanner.nextLine();

        Medico medico = buscarMedicoPorDNI(dni);
        if (medico != null) {
            System.out.println("Para no editar un campo, deje vacio y presione enter.");
            System.out.println("Ingrese el nombre del medico:");
            String nombre = scanner.nextLine();
            if (!nombre.isEmpty()) {
                medico.setNombre(nombre);
            }

            System.out.println("Ingrese el apellido del medico:");
            String apellido = scanner.nextLine();
            if (!apellido.isEmpty()) {
                medico.setApellido(apellido);
            }

            System.out.println("Ingrese el email del medico:");
            String email = scanner.nextLine();
            if (!email.isEmpty()) {
                medico.setEmail(email);
            }
            System.out.println("Ingrese la direccion del medico:");
            String direccion = scanner.nextLine();
            if (!email.isEmpty()) {
                medico.setEmail(email);
            }

            System.out.println("Ingrese la fecha de nacimiento del medico (yyyy-MM-dd):");
            String fechaNacimientoStr = scanner.nextLine();
            if (!fechaNacimientoStr.isEmpty()) {
                Date fechaNacimiento = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    fechaNacimiento = dateFormat.parse(fechaNacimientoStr);
                    medico.setFechaDeNacimiento(fechaNacimiento);
                } catch (ParseException e) {
                    System.out.println("Fecha de nacimiento no valida. No se modificara.");
                }
            }

            System.out.println("Ingrese la nacionalidad del medico:");
            String nacionalidad = scanner.nextLine();
            if (!nacionalidad.isEmpty()) {
                medico.setNacionalidad(nacionalidad);
            }

            System.out.println("Ingrese la especialidad del medico:");
            String especialidad = scanner.nextLine();
            if (!especialidad.isEmpty()) {
                medico.setEspecialidad(especialidad);
            }

            System.out.println("Ingrese la matricula del medico:");
            String matricula = scanner.nextLine();
            if (!matricula.isEmpty()) {
                medico.setMatricula(matricula);
            }
            System.out.println("Quiere editar la disponiblidad horaria?(si/no)");
            String editHorario = scanner.nextLine();
            if (!editHorario.isEmpty()) {
                if ("si".equals(editHorario)) {
                    System.out.println("Ingrese la disponibilidad horaria del medico por dia:");
                    String dia;
                    Map<String, String> horarios = new HashMap<>();
                    do {
                        System.out.print("Ingrese el dia (o 'exit' para Continuar): ");
                        dia = scanner.nextLine();
                        if (!dia.equalsIgnoreCase("exit")) {
                            System.out.print("Ingrese hora de inicio (HH:mm): ");
                            String inicio = scanner.nextLine();
                            System.out.print("Ingrese hora de fin (HH:mm): ");
                            String fin = scanner.nextLine();
                            horarios.put(dia.toLowerCase(), inicio + "/" + fin);
                        }
                    } while (!dia.equalsIgnoreCase("exit"));
                    medico.setHorarios(horarios);
                }
            }
            MedicoDao.update(medico);
            System.out.println("------------------");
            System.out.println("Medico actualizado");
            medico.mostrarInformacion();

        } else {
            System.out.println("medico no encontrado.");
        }
    }

    public void eliminarMedico() {
        System.out.println("Ingrese el DNI del medico a eliminar:");
        int dni = scanner.nextInt();
        scanner.nextLine();

        Medico medico = buscarMedicoPorDNI(dni);
        if (medico != null) {

            //Al estar referenciados por fk, se necesita primero eliminar todas los turnos del Medico.
            List<Turno> turnos = TurnoDao.listTurnosPorMedico(medico.getId().toString());

            for (Turno turno : turnos) {
                TurnoDao.delete(turno.getId().toString());
            }
            MedicoDao.delete(medico.getId().toString());
            System.out.println("Paciente eliminado con éxito.");
        } else {
            System.out.println("medico no encontrado.");
        }
    }

    public void listarMedicos() {
        medicos = MedicoDao.listMedicos(false, "");
        for (Medico medico : medicos) {
            medico.mostrarInformacion();
        }
    }

    public void mostrarEspecialidades() {
        List<String> especialidades = listarEspecialidades();
        for (int i = 0; i < especialidades.size(); i++) {
            System.out.println(especialidades.get(i));
        }
    }

    public void consultarMedicoPorDNI() {
        System.out.println("Ingrese el DNI del medico a consultar:");
        int dni = scanner.nextInt();
        scanner.nextLine();
        Medico medico = buscarMedicoPorDNI(dni);
        if (medico != null) {
            medico.mostrarInformacion();
        } else {
            System.out.println("No se encontro medico con el dni indicado");
        }
    }

    public List<String> listarEspecialidades() {
        List<String> especialidades = MedicoDao.listarEspecialidades();
        return especialidades;
    }

    public List<Medico> medicosPorEspecialidad(String especialidad) {
        List<Medico> especialistas = MedicoDao.listMedicos(true, especialidad);
        return especialistas;
    }

    private Medico buscarMedicoPorDNI(int dni) {
        return MedicoDao.getMedicoByDni(dni);
    }
}
