package pl.gebickionline.config;

/**
 * Created by Łukasz on 2015-11-28.
 */
public class Configuration {
    private static final String EXTERNAL_APPLICATION_TOKEN = "1c226459-a721-4337-a7e6-f2864e0186b9";
    private static final String APPLICATION_URL = "http://localhost:8080/WebApplicationForStudy/rest/";
    private static Configuration instance;

    private Configuration() {
    }

    public static Configuration getInstance() {
        if (instance == null)
            instance = new Configuration();
        return instance;
    }

    public String applicationAuthToken() {
        return EXTERNAL_APPLICATION_TOKEN;
    }

    public String applicationUrl() {
        return APPLICATION_URL;
    }
}
