
package ui.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties", "classpath:config.properties", "classpath:allure.properties"})
public interface Configuration extends Config {
    @Key("allure.results.directory")
    String allureResultsDir();
    @Key("base.url")
    String baseUrl();
    @Key("browser")
    String browser();
    @Key("headless")
    boolean headless();
    @Key("incognito")
    boolean incognito();
    @Key("timeout")
    int timeout();

}
