package cucumber.com.atomgraph.processor.util.Skolemizer;

import com.atomgraph.processor.util.Skolemizer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DCTerms;

import static org.junit.Assert.assertEquals;

public class SkolemizerGetLiteralCucumberTest {
    private final Literal firstTitle, secondTitle;
    private Literal expected;
    private Literal result;
    private final Resource first;

    public SkolemizerGetLiteralCucumberTest() {
        Model model = ModelFactory.createDefaultModel();
        secondTitle = model.createLiteral("Second");
        firstTitle = model.createLiteral("First");

        Resource second = model.createResource().
                addLiteral(DCTerms.title, secondTitle);
        first = model.createResource().
                addLiteral(DCTerms.title, firstTitle).
                addProperty(FOAF.primaryTopic, second);
    }

    @Given("the name path in get literal test, what is {string}")
    public void the_name_path_in_get_literal_test_what_is(String namePath) {
        result = Skolemizer.getLiteral(first, namePath);
    }

    @When("the expected literal in get literal test, what is {string}")
    public void the_expected_literal_in_get_literal_test_what_is(String expectedString) {
        switch(expectedString) {
            case "first": expected = firstTitle; break;
            case "second": expected = secondTitle; break;
            default: expected = null;
        }
    }

    @Then("the expected and the result literal are equal in get literal test")
    public void the_expected_and_the_result_literal_are_equal_in_get_literal_test() {
        assertEquals(expected, result);
    }
}
