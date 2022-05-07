package cucumber.com.atomgraph.processor.util.Skolemizer;

import com.atomgraph.processor.util.Skolemizer;
import com.atomgraph.server.util.OntologyLoader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.Ontology;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static org.junit.Assert.assertEquals;

public class SkolemizerNoPathCucumberTest {
    private final UriBuilder baseUriBuilder = UriBuilder.fromUri("http://base/"), absolutePathBuilder = UriBuilder.fromUri("http://base/absolute/path");
    private URI expected,actual;
    private final Resource inst;
    private final Ontology ontology;

    public SkolemizerNoPathCucumberTest() {
        ontology = ModelFactory.createOntologyModel().createOntology("http://test/ontology");
        OntClass cls = ontology.getOntModel().createClass("http://test/ontology/class");

        inst = ModelFactory.createDefaultModel().
                createResource().
                addProperty(RDF.type, cls).
                addLiteral(DCTerms.title, "Whateverest");
    }

    public Skolemizer getSkolemizer(OntDocumentManager ontMgr, OntModel ontModel, String ontologyURI)
    {
        ontMgr.addModel(ontologyURI, ontModel);
        Ontology ontology = new OntologyLoader(ontMgr, ontologyURI, ontModel.getSpecification(), true).getOntology();
        return new Skolemizer(ontology, baseUriBuilder, absolutePathBuilder);
    }

    @Given("the expected uri in no path test, which is null")
    public void the_expected_uri_in_no_path_test_which_is_null(){
        expected = null;
    }

    @When("the actual uri has no path in no path test")
    public void the_actual_uri_has_no_path_in_no_path_test() {
        actual = getSkolemizer(new OntDocumentManager(), ontology.getOntModel(), ontology.getURI()).build(inst);
    }

    @Then("the null and the pathless uri are equal in no path test")
    public void they_null_and_the_pathless_uri_are_equal_in_no_path_test() {
        assertEquals(expected, actual);
    }
}
