package cucumber.com.atomgraph.processor.util.Skolemizer;

import com.atomgraph.processor.util.Skolemizer;
import com.atomgraph.processor.vocabulary.LDT;
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
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static org.junit.Assert.assertEquals;

public class SkolemizerAbsolutePathCucumberTest {
    private final UriBuilder baseUriBuilder = UriBuilder.fromUri("http://base/"), absolutePathBuilder = UriBuilder.fromUri("http://base/absolute/path");
    private URI expected, actual;
    private final Resource inst;
    private final Ontology ontology;

    public SkolemizerAbsolutePathCucumberTest() {
        ontology = ModelFactory.createOntologyModel().createOntology("http://test/ontology");
        OntClass cls = ontology.getOntModel().createClass("http://test/ontology/class");
        cls.addLiteral(LDT.path, "/{identifier}");

        inst = ModelFactory.createDefaultModel().
                createResource().
                addProperty(RDF.type, cls).
                addLiteral(DCTerms.identifier, "ABCDEFGHI");
    }

    public Skolemizer getSkolemizer(OntDocumentManager ontMgr, OntModel ontModel, String ontologyURI) {
        ontMgr.addModel(ontologyURI, ontModel);
        Ontology ontology = new OntologyLoader(ontMgr, ontologyURI, ontModel.getSpecification(), true).getOntology();
        return new Skolemizer(ontology, baseUriBuilder, absolutePathBuilder);
    }

    @Given("the expected uri in absolute path test")
    public void the_excepted_uri_in_absolute_path_test() {
        expected = baseUriBuilder.clone().path("ABCDEFGHI").build();
    }

    @When("the actual uri is gave in absolute path test")
    public void the_actual_uri_is_gave_in_absolute_path_test() {
        actual = getSkolemizer(new OntDocumentManager(), ontology.getOntModel(), ontology.getURI()).build(inst);
    }

    @Then("the expected and the actual uri are equal in absolute path test")
    public void the_expected_and_the_actual_uri_are_equal_in_absolute_path_test() {
        assertEquals(expected, actual);
    }
}

