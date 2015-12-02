package pl.gebickionline.communication;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import pl.gebickionline.config.Configuration;
import pl.gebickionline.exception.*;
import pl.gebickionline.restclient.*;
import pl.gebickionline.security.AuthorizationCleaner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Łukasz on 2015-11-28.
 */
public class CommunicationManagerImpl implements CommunicationManager, AuthorizationCleaner {
    private static CommunicationManager instance;
    private Configuration configuration;
    private Optional<String> authToken;
    private Optional<String> cookieWithSessionId;

    public static CommunicationManager getInstance() {
        if (instance == null)
            instance = new CommunicationManagerImpl();
        return instance;
    }

    private CommunicationManagerImpl() {
        configuration = Configuration.getInstance();
        authToken = Optional.empty();
        cookieWithSessionId = Optional.empty();
    }

    @Override
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

        switch (response.statusCode()) {
            case 200:
                authToken = Optional.ofNullable(response.asJsonObject().getString("auth_token"));
                String jsessionid = response.header("Set-Cookie");
                cookieWithSessionId = Optional.ofNullable(jsessionid);
                return authToken.isPresent();
            case 400:
                List<String> errors = response.asJsonObject().getList("errors").asStringList();
                throw new BadRequestException(errors);
            case 401:
                return false;
            default:
                throw new RemoteServerError();
        }

    }

    @Override
    public void logout() {
        Response response = RestClient
                .post(configuration.applicationUrl() + "user/logout")
                .header("application", configuration.applicationAuthToken())
                .header("auth_token", authToken.orElse(""))
                .header("Cookie", cookieWithSessionId.orElse(""))
                .send();

        switch (response.statusCode()) {
            case 200:
                authToken = Optional.empty();
            case 401:
                throw new AuthorizationException();
            default:
                throw new RemoteServerError();
        }
    }

    @Override
    public long addNews(@NotNull String title, @NotNull String content) {
        Response response = RestClient
                .post(configuration.applicationUrl() + "admin/news")
                .header("application", configuration.applicationAuthToken())
                .header("auth_token", authToken.orElse(""))
                .header("Cookie", cookieWithSessionId.orElse(""))
                .header("content-type", "application/json")
                .body(new JSONObject()
                        .put("title", title)
                        .put("content", content)
                        .toString())
                .send();

        switch (response.statusCode()) {
            case 201:
                return response.asJsonObject().getLong("id");
            case 400:
                List<String> errors = response.asJsonObject().getList("errors").asStringList();
                throw new BadRequestException(errors);
            case 401:
                throw new AuthorizationException();
            default:
                throw new RemoteServerError();
        }
    }

    @Override
    public void modifyNews(long newsId, @NotNull String title, @NotNull String content) {
        Response response = RestClient
                .put(configuration.applicationUrl() + "admin/news/" + newsId)
                .header("application", configuration.applicationAuthToken())
                .header("auth_token", authToken.orElse(""))
                .header("Cookie", cookieWithSessionId.orElse(""))
                .header("content-type", "application/json")
                .body(new JSONObject()
                        .put("title", title)
                        .put("content", content)
                        .toString())
                .send();

        switch (response.statusCode()) {
            case 200:
                return;
            case 400:
            case 404:
                List<String> errors = response.asJsonObject().getList("errors").asStringList();
                throw new BadRequestException(errors);
            case 401:
                throw new AuthorizationException();
            default:
                throw new RemoteServerError();
        }
    }

    @Override
    public void deleteNews(long newsId) {
        Response response = RestClient
                .delete(configuration.applicationUrl() + "admin/news/" + newsId)
                .header("application", configuration.applicationAuthToken())
                .header("auth_token", authToken.orElse(""))
                .header("Cookie", cookieWithSessionId.orElse(""))
                .send();

        switch (response.statusCode()) {
            case 204:
                return;
            case 401:
                throw new AuthorizationException();
            case 404:
                List<String> errors = response.asJsonObject().getList("errors").asStringList();
                throw new BadRequestException(errors);
            default:
                throw new RemoteServerError();
        }
    }

    @Override
    public List<News> getNewsList() {
        Response response = RestClient
                .get(configuration.applicationUrl() + "admin/news/list")
                .header("application", configuration.applicationAuthToken())
                .header("auth_token", authToken.orElse(""))
                .header("Cookie", cookieWithSessionId.orElse(""))
                .send();

        switch (response.statusCode()) {
            case 200:
                return response.asList().asObjectList().stream()
                        .map(n -> new News.Builder()
                                .withId(n.getLong("id"))
                                .withTitle(n.getString("title"))
                                .withContent(n.getString("content"))
                                .withCreationTime(n.getString("creationTime"))
                                .withLastUpdateTime(n.getString("lastUpdateTime"))
                                .build())
                        .collect(Collectors.toList());
            case 401:
                throw new AuthorizationException();
            default:
                throw new RemoteServerError();
        }
    }

    @Override
    public void turnOnNewsVisibility() {
        Response response = RestClient
                .put(configuration.applicationUrl() + "admin/news/visibility")
                .header("application", configuration.applicationAuthToken())
                .header("auth_token", authToken.orElse(""))
                .header("Cookie", cookieWithSessionId.orElse(""))
                .send();

        switch (response.statusCode()) {
            case 200:
                return;
            case 401:
                throw new AuthorizationException();
            default:
                throw new RemoteServerError();
        }
    }

    @Override
    public void turnOffNewsVisibility() {
        Response response = RestClient
                .delete(configuration.applicationUrl() + "admin/news/visibility")
                .header("application", configuration.applicationAuthToken())
                .header("auth_token", authToken.orElse(""))
                .header("Cookie", cookieWithSessionId.orElse(""))
                .send();

        switch (response.statusCode()) {
            case 200:
                return;
            case 401:
                throw new AuthorizationException();
            default:
                throw new RemoteServerError();
        }
    }

    @Override
    public NewsVisibility getNewsVisibility() {
        Response response = RestClient
                .get(configuration.applicationUrl() + "news/visibility")
                .header("application", configuration.applicationAuthToken())
                .header("Cookie", cookieWithSessionId.orElse(""))
                .send();

        switch (response.statusCode()) {
            case 200:
                Boolean visible = response.asJsonObject().getBoolean("visible");
                return visible ? NewsVisibility.VISIBLE : NewsVisibility.INVISIBLE;
            default:
                throw new RemoteServerError();
        }
    }

    @Override
    public void clearAuthorizationData() {
        authToken = Optional.empty();
        cookieWithSessionId = Optional.empty();
    }
}
