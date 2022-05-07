package cucumber.com.atomgraph.processor.model.impl;

import com.atomgraph.processor.model.TemplateCall;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

public class TemplateCallImplGetArgumentsCucumberTest {
    TemplateCallSource source = TemplateCallSource.getInstance();
    MultivaluedMap queryParams = new MultivaluedHashMap();
    TemplateCall applied;

    @Given("parameter is {string}")
    public void parameters_are(String _param) {
        queryParams.add(source.getPredicateLocalName("1"), _param);
        queryParams.add(source.getPredicateLocalName("unused"), "X");
    }

    @When("argument is asked for")
    public void argument_is_asked_for() {
        applied = source.getCall().applyArguments(queryParams).applyDefaults();
    }

    @Then("argument is provided")
    public void argument_is_provided() {
        assertNotNull(applied.getArgument(source.getProperty("1")));
        assertNotNull(applied.getArgument(source.getProperty("2")));
        assertNull(applied.getArgument(source.getProperty("unused")));
    }
}
