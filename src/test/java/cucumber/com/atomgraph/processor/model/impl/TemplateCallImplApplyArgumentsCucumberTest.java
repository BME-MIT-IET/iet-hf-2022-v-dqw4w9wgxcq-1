package cucumber.com.atomgraph.processor.model.impl;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemplateCallImplApplyArgumentsCucumberTest {
    TemplateCallSource source = TemplateCallSource.getInstance();
    MultivaluedMap queryParams = new MultivaluedHashMap();
    private String url;
    private String param1;
    private String param2;

    @Given("argument parameters are {string} and {string}")
    public void argument_parameters_are(String _param1, String _param2) {
        param1 = _param1;
        param2 = _param2;
        queryParams.add(source.getPredicateLocalName("1"), param1);
        queryParams.add(source.getPredicateLocalName("2"), param2);
        queryParams.add(source.getPredicateLocalName("unused"), "X");
    }

    @When("arguments are applied")
    public void arguments_are_applied() {
        url = source.getResource().getURI() + "?" + source.getPredicateLocalName("1") + "=" + param1 + "&" + source.getPredicateLocalName("2") + "=" + param2.replace(" ", "%20");
    }

    @Then("argument URLs matches")
    public void argument_URLs_matches() {
        assertEquals(url, source.getCall().applyArguments(queryParams).build().getURI());
    }
}
