package pl.gebickionline.security;

import org.jetbrains.annotations.NotNull;
import pl.gebickionline.communication.*;

public class AuthorizationProvider {
    private static AuthorizationProvider instance;
    private CommunicationManager communicationManager;
    private boolean loggedIn;

    private AuthorizationProvider() {
        loggedIn = false;
        communicationManager = CommunicationManagerImpl.getInstance();
    }

    public static AuthorizationProvider getInstance() {
        if (instance == null)
            instance = new AuthorizationProvider();

        return instance;
    }

    public void login(@NotNull String username, @NotNull String password) {
        System.out.printf("login: %s\nPassword: %s\n", username, password);

        if (username.trim().isEmpty() || password.trim().isEmpty())
            throw new IllegalArgumentException("username cannot be empty");

        loggedIn = communicationManager.login(username, password);
    }

    public void logout() {
        communicationManager.logout();
        loggedIn = false;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    void clear() {
        loggedIn = false;
    }

}
