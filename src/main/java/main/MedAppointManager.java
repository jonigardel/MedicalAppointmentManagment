package main;
import menu.Login;
import menu.MainMenu;
import java.util.Scanner;

public class MedAppointManager {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();
        
        if (login.procesarLogin(scanner)) {
            MainMenu menu = new MainMenu();
            menu.mostrarMenu(scanner);
        }

        scanner.close();
    }
}
