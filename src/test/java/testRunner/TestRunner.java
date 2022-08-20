package testRunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;

@RunWith(Cucumber.class)
@CucumberOptions(
		
		features = {".//src/test/java/features/Customers.feature",
					".//src/test/java/features/login.feature"},
		glue = "stepDefinations",
		monochrome=true,
		tags= "@sanity",
		plugin = {"pretty",
				"html:target/testReport.html"
					}
		
		
		)
public class TestRunner {

}
