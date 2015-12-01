package pl.gebickionline.communication;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import pl.gebickionline.config.Configuration;
import pl.gebickionline.exception.*;
import pl.gebickionline.restclient.*;

import java.util.*;

/**
 * Created by Łukasz on 2015-11-28.
 */
public class CommunicationManager {
    private static CommunicationManager instance;
    private Configuration configuration;
    private Optional<String> authToken;
    private Optional<String> cookieWithSessionId;

    public static CommunicationManager getInstance() {
        if (instance == null)
            instance = new CommunicationManager();
        return instance;
    }

    private CommunicationManager() {
        configuration = Configuration.getInstance();
        authToken = Optional.empty();
        cookieWithSessionId = Optional.empty();
    }

    public boolean login(@NotNull String username, @NotNull String password) {

        Response response = RestClient
                .post(configuration.applicationUrl() + "user/login")
                .header("application", configuration.applicationAuthToken())
                .header("content-type", "application/json")
                .body(
                        new JSONObject()
                                .put("login", username)
                                .put("password", password)
                                .toString())
                .send();

        if (response.statusCode() == 200) {
            authToken = Optional.ofNullable(response.asJsonObject().getString("auth_token"));
            String jsessionid = response.header("Set-Cookie");
            cookieWithSessionId = Optional.ofNullable(jsessionid);
            return authToken.isPresent();
        }

        if (response.statusCode() == 401)
            return false;

        if (response.statusCode() == 400) {
            List<String> errors = response.asJsonObject().getList("errors").asStringList();
            new BadRequestException(errors);
        }

        throw new RemoteServerError();
    }

    public void logout() {

        Response response = RestClient
                .post(configuration.applicationUrl() + "user/logout")
                .header("application", configuration.applicationAuthToken())
                .header("auth_token", authToken.orElse(""))
                .header("Cookie", cookieWithSessionId.orElse(""))
                .send();

        if (response.statusCode() == 200)
            authToken = Optional.empty();

        if (response.statusCode() == 401)
            throw new AuthorizationException();
    }
}
