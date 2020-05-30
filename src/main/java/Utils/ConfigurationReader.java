package Utils;

public class ConfigurationReader extends FileReader {
    private String configurationLocation;

    private String baseUrl;
    private String hubUrl;
    private String browser;
    private String pathScreenshot;

    public ConfigurationReader(String configurationLocation) {
        super(configurationLocation);
        this.configurationLocation = configurationLocation;
    }

    void loadData() {
        hubUrl              = properties.getProperty("hubUrl");
        baseUrl             = properties.getProperty("baseUrl");
        browser             = properties.getProperty("browser");
        pathScreenshot      = properties.getProperty("pathScreenshot");
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

    public String getPathScreenshot() {
        return pathScreenshot;
    }

    public String getConfigurationLocation() {
        return configurationLocation;
    }
}
