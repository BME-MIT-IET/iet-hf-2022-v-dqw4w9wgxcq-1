package cucumber.com.atomgraph.processor.model.impl;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.jena.query.QuerySolutionMap;

import static org.junit.Assert.assertEquals;

public class TemplateCallImplGetQuerySMCucumberTest {
    TemplateCallSource source = TemplateCallSource.getInstance();
    QuerySolutionMap qsm;
    QuerySolutionMap askedFor;

    @Given("query solution map exists")
    public void query_solution_map_exists() {
        qsm = new QuerySolutionMap();
        qsm.add(source.getProperty("1").getLocalName(), source.getQueryBinding());
    }

    @When("query solution map is asked for")
    public void query_solution_map_is_asked_for() {
        askedFor = source.getCall().getQuerySolutionMap();
    }

    @Then("query solution map is provided")
    public void query_solution_map_is_provided() {
        assertEquals(qsm.asMap(), askedFor.asMap());
    }
}
