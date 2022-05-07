package cucumber.com.atomgraph.processor.model.impl;

import com.atomgraph.processor.model.TemplateCall;
import com.atomgraph.processor.model.impl.TemplateCallImpl;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemplateCallImplArgCucumberTest {
    TemplateCallSource source = TemplateCallSource.getInstance();

    MultivaluedMap queryParams = new MultivaluedHashMap();
    TemplateCall otherCall;
    String param1v;
    String param2v;

    @Given("parameters are {string} and {string}")
    public void parameters_are(String _param1, String _param2) {
        param1v = _param1;
        param2v = _param2;
        queryParams.add(source.getPredicateLocalName("1"), param1v);
        queryParams.add(source.getPredicateLocalName("2"), param2v);
    }

    @When("call is made with arg function")
    public void call_is_made_with_arg_function() {
        otherCall = new TemplateCallImpl(source.getResource(), source.getTemplate()).
                arg(source.getParameter("1"), ResourceFactory.createPlainLiteral(param1v)).
                arg(source.getParameter("2"), ResourceFactory.createPlainLiteral(param2v));
    }

    @Then("URL matches")
    public void URL_matches() {
        assertEquals(source.getCall().applyArguments(queryParams).build(), otherCall.build());
    }
}
