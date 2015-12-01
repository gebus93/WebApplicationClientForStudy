package pl.gebickionline.ui.cli;

import pl.gebickionline.security.AuthorizationProvider;

/**
 * Created by Łukasz on 2015-11-28.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Nieprawidłowe wywołanie");
            return;
        }

        switch (args[0]) {
            case "user":
                user(args);
                break;
            default:
                System.out.println("Nieznana operacja");
        }
    }

    private static void user(String[] args) {
        AuthorizationProvider authProvider = AuthorizationProvider.getInstance();

        if (args.length == 4 && "login".equals(args[1])) {
            authProvider.login(args[2], args[3]);

            if (authProvider.isLoggedIn()) {
                System.out.println("Zalogowano");
                authProvider.logout();
            } else
                System.out.println("Logowanie nie powiodło się");
        }
    }
}
