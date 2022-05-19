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
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static org.junit.Assert.assertEquals;

public class SkolemizerPrimaryTopicCucumberTest {
    private final UriBuilder baseUriBuilder = UriBuilder.fromUri("http://base/"), absolutePathBuilder = UriBuilder.fromUri("http://base/absolute/path");
    private URI expected, actual;
    private final Resource thing;
    private final Ontology ontology;

    public SkolemizerPrimaryTopicCucumberTest() {
        ontology = ModelFactory.createOntologyModel().createOntology("http://test/ontology");
        OntClass cls = ontology.getOntModel().createClass("http://test/ontology/class");
        cls.addLiteral(LDT.path, "thing-{isPrimaryTopicOf.identifier}");

        Model model = ModelFactory.createDefaultModel();
        Resource doc = model.createResource().
                addLiteral(DCTerms.identifier, "123456789");
        thing = model.createResource().
                addProperty(RDF.type, cls).
                addProperty(FOAF.isPrimaryTopicOf, doc);
    }

    public Skolemizer getSkolemizer(OntDocumentManager ontMgr, OntModel ontModel, String ontologyURI) {
        ontMgr.addModel(ontologyURI, ontModel);
        Ontology ontology = new OntologyLoader(ontMgr, ontologyURI, ontModel.getSpecification(), true).getOntology();
        return new Skolemizer(ontology, baseUriBuilder, absolutePathBuilder);
    }

    @Given("the expected uri in primary topic test")
    public void the_excepted_uri_in_primary_topic_test() {
        expected = absolutePathBuilder.clone().path("thing-" + "123456789").build();
    }

    @When("the actual uri is gave in primary topic test")
    public void the_actual_uri_is_gave_in_primary_topic_test() {
        actual = getSkolemizer(new OntDocumentManager(), ontology.getOntModel(), ontology.getURI()).build(thing);
    }

    @Then("the expected and the actual uri are equal in primary topic test")
    public void the_expected_and_the_actual_uri_are_equal_in_primary_topic_test() {
        assertEquals(expected, actual);
    }
}
