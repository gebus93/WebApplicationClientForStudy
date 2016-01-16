package pl.gebickionline.exception;

import javafx.scene.control.Alert;
import pl.gebickionline.restclient.ExecuteRequestException;
import pl.gebickionline.ui.Main;

/**
 * Created by Łukasz on 2016-01-05.
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final Main mainApp;

    public ExceptionHandler(Main main) {
        this.mainApp = main;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Throwable realException = realExceptionOf(throwable);

        if (realException instanceof ExecuteRequestException) {
            showConnectionErrorAlert();
            return;
        }

        if (realException instanceof AuthorizationException) {
            showAuthorizationErrorAlert();
            mainApp.showLoginForm();
            return;
        }

        realException.printStackTrace();
        throw new RuntimeException(realException);
    }

    private Throwable realExceptionOf(Throwable throwable) {
        while (throwable.getCause() != null)
            throwable = throwable.getCause();
        return throwable;
    }

    private void showConnectionErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Wystąpił nieoczekiwany błąd!");
        alert.setHeaderText("Błąd połączenia z serwerem!");
        alert.setContentText("Nastąpił nieoczekiwany błąd, podczas próby nawiązania połączenia z serwerem.\nSprawdź połączenie z internetem, lub upewnij się, że serwer jest uruchomiony.");
        alert.showAndWait();
    }

    private void showAuthorizationErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Wystąpił nieoczekiwany błąd!");
        alert.setHeaderText("Brak wymaganych uprawnień!");
        alert.setContentText("Nie posiadasz wystarczających uprawnień, aby uzyskać dostęp do wybranej metody.\n\nProszę się zalogować.");
        alert.showAndWait();
    }
}
