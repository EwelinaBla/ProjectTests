package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {
    private String baseUrl;
    private String hubUrl;
    private String browser;
    private final String configurationLocation = "src/Configs/Configurations.properties";
    Properties properties;

    public ConfigurationReader() {
        loadFile();
        loadData();
    }

    private void loadFile() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(getConfigurationLocation()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        hubUrl = properties.getProperty("hubUrl");
        baseUrl = properties.getProperty("baseUrl");
        browser = properties.getProperty("browser");
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getHubUrl() {
        return hubUrl;
    }

    public String getBrowser() {
        return browser;
    }

    public String getConfigurationLocation() {
        return configurationLocation;
    }
}
