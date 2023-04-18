package factory;

import com.microsoft.playwright.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class PlaywrightFactory {

    Properties properties;
    BrowserContext browserContext;

    private static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
    private static ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();
    private static ThreadLocal<Page> tlPage = new ThreadLocal<>();

    public static Playwright getPlaywright() {
        return tlPlaywright.get();
    }

    public static Browser getBrowser() {
        return tlBrowser.get();
    }

    public static BrowserContext getBrowserContext() {
        return tlBrowserContext.get();
    }

    public static Page getPage() {
        return tlPage.get();
    }

    public Page initBrowser(Properties properties) {

        String browserName = properties.getProperty("browser").trim();
        System.out.println("Browser Name is : " + browserName);
        tlPlaywright.set(Playwright.create());

        switch (browserName.toLowerCase()) {
            case "chromium":
                tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
            case "chrome":
                tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false)));
                break;
            case "firefox":
                tlBrowser.set(getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
            case "safari":
                tlBrowser.set(getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
            default:
                System.out.println("Please pass the right browser name : [chromium, chrome, firefox, safari]");
        }
        browserContext = getBrowser().newContext();
        tlBrowserContext.set(browserContext);
        browserContext.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
        tlPage.set(getBrowserContext().newPage());
        getPage().navigate(properties.getProperty("url").trim());

        return getPage();
    }

    public Properties initProperty() {
        try {
            FileInputStream fileInputStream = new FileInputStream("./src/test/resources/config/config.properties");
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    public static String takeScreenshot() {
        long currentTimeInMillis = System.currentTimeMillis();
        String path = System.getProperty("user.dir") + "/report/screenshot/" + currentTimeInMillis + ".png";
        getPage().screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(path))
                .setFullPage(true));
        String pathForExtentReport = "../screenshot/"+ currentTimeInMillis + ".png";
        return pathForExtentReport;
    }

    public void closeTracing() {
        browserContext.tracing().stop(new Tracing.StopOptions().setPath(Paths.get(System.getProperty("user.dir") + "/report/trace/" + System.currentTimeMillis() + ".zip")));
    }
}
