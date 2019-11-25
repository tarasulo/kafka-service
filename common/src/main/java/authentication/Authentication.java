package authentication;

import java.util.Scanner;

public class Authentication {

    private static String login = "user";
    private static String password = "000";

    public static boolean isValid() {

        System.out.println("You tried to start controller  ");
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter login ");
        String name = sc.next();
        System.out.println("Please enter your password ");
        String pass = sc.next();
        sc.close();

        if (name.equals(login) & pass.equals(password)) {
            System.out.println("Controller started work ");
            return true;
        } else {
            System.out.println("Invalid login or password! Access denied");
            return false;
        }
    }
}
