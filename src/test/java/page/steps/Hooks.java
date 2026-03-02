package page.steps;

import com.sun.jna.platform.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Hooks {

    public static WebDriver driver;

    @Before
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                String screenshotName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");
                System.out.println("Failed Scenario: " + screenshotName);
                TakesScreenshot ts = (TakesScreenshot) driver;

                // Create folder if not exists
                File folder = new File(System.getProperty("user.dir") + "/ScreenShots");
                if (!folder.exists()) {
                    folder.mkdir();
                }

                // Capture screenshot as file
                File source = ts.getScreenshotAs(OutputType.FILE);
                File destination = new File(folder.getAbsolutePath() + "/" + screenshotName + ".png");

                // Copy file using Java NIO
                Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Optional: attach to Cucumber report
                byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", screenshotName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (driver != null) {
            driver.quit();
        }
    }
}