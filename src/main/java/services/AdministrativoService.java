package services;

import entidades.Administrativo;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class AdministrativoService {
    private List<Administrativo> administrativos = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void crearAdministrativo() {
        System.out.println("Ingrese el nombre del administrativo:");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese el apellido del administrativo:");
        String apellido = scanner.nextLine();

        System.out.println("Ingrese el DNI del administrativo:");
        int dni = scanner.nextInt();
        scanner.nextLine();  

        System.out.println("Ingrese el email del administrativo:");
        String email = scanner.nextLine();

        System.out.println("Ingrese la direccion del administrativo:");
        String direccion = scanner.nextLine();

        System.out.println("Ingrese la fecha de nacimiento del administrativo (yyyy-MM-dd):");
        String fechaNacimientoStr = scanner.nextLine();
        Date fechaNacimiento = null;
        try {
            fechaNacimiento = dateFormat.parse(fechaNacimientoStr);
        } catch (ParseException e) {
            System.out.println("Fecha de nacimiento no valida. Usando fecha por defecto.");
            fechaNacimiento = new Date();
        }

        System.out.println("Ingrese la nacionalidad del administrativo:");
        String nacionalidad = scanner.nextLine();

        System.out.println("Ingrese el puesto del administrativo:");
        String puesto = scanner.nextLine();

        System.out.println("Ingrese si es administrador (true/false):");
        boolean administrador = scanner.nextBoolean();

        Administrativo administrativo = new Administrativo(UUID.randomUUID(), nombre, apellido, dni, email, direccion, fechaNacimiento, nacionalidad, puesto, administrador);

        administrativos.add(administrativo);

        administrativo.mostrarInformacion();
    }

    public void editarAdministrativo() {
        System.out.println("Ingrese el DNI del administrativo a editar:");
        int dni = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Administrativo administrativo = buscarAdministrativoPorDNI(dni);
        if (administrativo != null) {
            System.out.println("Ingrese el nuevo email del administrativo:");
            String email = scanner.nextLine();

            System.out.println("Ingrese la nueva direccion del administrativo:");
            String direccion = scanner.nextLine();

            System.out.println("Ingrese el nuevo puesto del administrativo:");
            String puesto = scanner.nextLine();

            System.out.println("Ingrese si es administrador (true/false):");
            boolean administrador = scanner.nextBoolean();

            administrativo.setEmail(email);
            administrativo.setDireccion(direccion);
            administrativo.setPuesto(puesto);
            administrativo.setAdministrador(administrador);

            System.out.println("Administrativo actualizado:");
            administrativo.mostrarInformacion();
        } else {
            System.out.println("Administrativo no encontrado.");
        }
    }

    public void eliminarAdministrativo() {
        System.out.println("Ingrese el DNI del administrativo a eliminar:");
        int dni = scanner.nextInt();
        scanner.nextLine();

        Administrativo administrativo = buscarAdministrativoPorDNI(dni);
        if (administrativo != null) {
            administrativos.remove(administrativo);
            System.out.println("Administrativo eliminado.");
        } else {
            System.out.println("Administrativo no encontrado.");
        }
    }

    public void listarAdministrativos() {
        if (administrativos.isEmpty()) {
            System.out.println("No hay administrativos registrados.");
        } else {
            for (Administrativo administrativo : administrativos) {
                administrativo.mostrarInformacion();
            }
        }
    }

    private Administrativo buscarAdministrativoPorDNI(int dni) {
        for (Administrativo administrativo : administrativos) {
            if (administrativo.getDni() == dni) {
                return administrativo;
            }
        }
        return null;
    }
}
