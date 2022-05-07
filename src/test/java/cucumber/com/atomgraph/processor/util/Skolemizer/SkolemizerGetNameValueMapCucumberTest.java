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
import org.glassfish.jersey.uri.internal.UriTemplateParser;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SkolemizerGetNameValueMapCucumberTest {
    private final UriTemplateParser parser;
    private final Literal firstTitle;
    private final Literal secondTitle;
    private final Resource first;
    private Map<String, String> expected, result;

    public SkolemizerGetNameValueMapCucumberTest() {
        parser = new UriTemplateParser("{name}/{title}|{smth}|{primaryTopic.title}");
        Model model = ModelFactory.createDefaultModel();
        secondTitle = model.createLiteral("Second");
        firstTitle = model.createLiteral("First");

        Resource second = model.createResource().addLiteral(DCTerms.title, secondTitle);
        first = model.createResource().addLiteral(DCTerms.title, firstTitle).addProperty(FOAF.primaryTopic, second);
    }

    @Given("the expected map of get name value map skolemizer test")
    public void the_expected_map_of_get_name_value_map_skolemizer_test() {
        expected = new HashMap<>();
        expected.put("title", firstTitle.getString());
        expected.put("primaryTopic.title", secondTitle.getString());
    }

    @When("the result map is calculable of get name value map skolemizer test")
    public void the_result_map_is_calculable_of_get_name_value_map_skolemizer_test() {
        result = Skolemizer.getNameValueMap(first, parser);
    }

    @Then("the expected and the result map in get name value map test are equal")
    public void the_expected_and_the_result_map_in_get_name_value_map_test_are_equal() {
        assertEquals(expected, result);
    }
}
