package runner;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty",
        "html:target/cucumber-html-report",
        "de.monochromata.cucumber.report.PrettyReports"},
        features = "src/test/java/resources/features",
        glue = {"steps"})
public class TestRunner {


}
