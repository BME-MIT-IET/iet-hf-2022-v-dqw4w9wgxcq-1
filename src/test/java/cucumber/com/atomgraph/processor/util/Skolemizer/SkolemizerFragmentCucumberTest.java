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
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static org.junit.Assert.assertEquals;

public class SkolemizerFragmentCucumberTest {
    private final UriBuilder baseUriBuilder = UriBuilder.fromUri("http://base/"), absolutePathBuilder = UriBuilder.fromUri("http://base/absolute/path");
    private final String fragment = "something", id = "ABCDEFGHI";
    private final Ontology ontology;
    private final Resource inst;
    private URI expected, actual;

    public SkolemizerFragmentCucumberTest() {
        ontology = ModelFactory.createOntologyModel().createOntology("http://test/ontology");
        OntClass superCls = ontology.getOntModel().createClass("http://test/ontology/super-class");
        superCls.addLiteral(LDT.fragment, fragment);
        OntClass cls = ontology.getOntModel().createClass("http://test/ontology/class");
        cls.addLiteral(LDT.path, "{identifier}").addProperty(RDFS.subClassOf, superCls);

        inst = ModelFactory.createDefaultModel().createResource().addProperty(RDF.type, cls).addLiteral(DCTerms.identifier, id);
    }

    public Skolemizer getSkolemizer(OntDocumentManager ontMgr, OntModel ontModel, String ontologyURI) {
        ontMgr.addModel(ontologyURI, ontModel);
        Ontology ontology = new OntologyLoader(ontMgr, ontologyURI, ontModel.getSpecification(), true).getOntology();
        return new Skolemizer(ontology, baseUriBuilder, absolutePathBuilder);
    }

    @Given("the expected uri of fragment skolemizer test")
    public void the_excepted_uri_of_fragment_skolemizer_test() {
        expected = absolutePathBuilder.clone().path(id).fragment(fragment).build();
    }

    @When("the actual uri is gave of fragment skolemizer test")
    public void the_actual_uri_is_gave_of_fragment_skolemizer_test() {
        actual = getSkolemizer(new OntDocumentManager(), ontology.getOntModel(), ontology.getURI()).build(inst);
    }

    @Then("the expected and the actual uri in fragment test are equal")
    public void the_expected_and_the_actual_uri_in_fragment_test_are_equal() {
        assertEquals(expected, actual);
    }
}
