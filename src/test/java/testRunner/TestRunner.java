package testRunner;


import io.cucumber.java.Scenario;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import utils.Helper;

@RunWith(Cucumber.class)
@CucumberOptions(

		features = {".//src/test/java/features/Customers.feature",
					".//src/test/java/features/login.feature"},
		glue = "stepDefinations",
		monochrome=true,
		tags= "@sanity",
		plugin = {"pretty",
				"html:target/testReport.html",
				"io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm"
					}
		
		
		)
public class TestRunner {

}
