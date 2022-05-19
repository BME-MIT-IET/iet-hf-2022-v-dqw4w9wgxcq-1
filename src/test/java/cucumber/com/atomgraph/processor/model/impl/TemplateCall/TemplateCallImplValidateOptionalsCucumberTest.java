package cucumber.com.atomgraph.processor.model.impl.TemplateCall;

import com.atomgraph.processor.model.TemplateCall;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static org.junit.Assert.assertNotNull;

public class TemplateCallImplValidateOptionalsCucumberTest {
    TemplateCallSource source = TemplateCallSource.getInstance();
    MultivaluedMap queryParams = new MultivaluedHashMap();
    TemplateCall applied;

    @Given("parameters for optionals are {string} and {string}")
    public void parameters_for_optionals_are(String _param1, String _param2) {
        queryParams.add(source.getPredicateLocalName("1"), _param1);
        queryParams.add(source.getPredicateLocalName("3"), _param2);
    }

    @When("optionals are validated")
    public void options_are_validated() {
        applied = source.getCall().applyArguments(queryParams).applyDefaults().validateOptionals();
    }

    @Then("argument should exist")
    public void argument_should_exist() {
        assertNotNull(applied.getArgument(source.getProperty("1")));
        assertNotNull(applied.getArgument(source.getProperty("2")));
        assertNotNull(applied.getArgument(source.getProperty("3")));
    }
}
