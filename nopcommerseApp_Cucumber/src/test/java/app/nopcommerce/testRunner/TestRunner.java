package app.nopcommerce.testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(
		
		features= {".//Features/Login.feature"},
		glue="app.nopcommerce.stepDefinitions",
		dryRun = false,
		monochrome= true,
		plugin= {"pretty","html:.//Report/test-output16.html"}
		)

public class TestRunner {
//tags= "@sanity",
}
