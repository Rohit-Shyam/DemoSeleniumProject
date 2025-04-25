package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",  // Path to feature files
        glue = {"stepdefinitions"},  // Path to step definitions
        plugin = {
                "pretty",  // Readable format in the console
                "html:target/cucumber-reports.html",  // HTML report
                "json:target/cucumber.json",  // JSON needed for Masterthought report
                "junit:target/cucumber.xml"  // Optional: For CI tools like Jenkins
        },
        monochrome = true,  // Output without escape characters
        publish = true  // Automatically uploads to the Cucumber reports server (if required)
)
public class TestRunner {
}
