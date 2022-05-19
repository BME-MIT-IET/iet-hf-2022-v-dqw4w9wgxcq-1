package cucumber.com.atomgraph.processor.util.Skolemizer;

import com.atomgraph.processor.util.Skolemizer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DCTerms;

import static org.junit.Assert.assertEquals;

public class SkolemizerGetResourceCucumberTest {
    private final Resource first, second;
    private Resource result, expected;

    public SkolemizerGetResourceCucumberTest() {
        Model model = ModelFactory.createDefaultModel();

        second = model.createResource().addLiteral(DCTerms.title, "Second");
        first = model.createResource().addLiteral(DCTerms.title, "First").addProperty(FOAF.primaryTopic, second);
    }

    @Given("the name of result in get resource test, what is {string}")
    public void the_name_of_result_in_get_resource_test_what_is(String name) {
        result = Skolemizer.getResource(first, name);
    }

    @When("the expected resource in get resource test, what is {string}")
    public void the_expected_resource_in_get_resource_test_what_is(String expectedString) {
        if ("second".equals(expectedString)) {
            expected = second;
        } else {
            expected = null;
        }
    }

    @Then("the expected and the result resource are equal in get resource test")
    public void the_expected_and_the_result_resource_are_equal_in_get_resource_test() {
        assertEquals(expected, result);
    }
}
