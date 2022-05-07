package cucumber.com.atomgraph.processor.util.Skolemizer;

import com.atomgraph.processor.exception.OntologyException;
import com.atomgraph.processor.util.Skolemizer;
import com.atomgraph.processor.util.TemplateMatcher;
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
import org.assertj.core.api.Assertions;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static org.junit.Assert.assertEquals;

public class SkolemizerInvalidPathCucumberTest {
    private final UriBuilder baseUriBuilder = UriBuilder.fromUri("http://base/"), absolutePathBuilder = UriBuilder.fromUri("http://base/absolute/path");
    private final Ontology ontology;
    private final OntClass cls;
    private OntModel model;
    private String uri;
    private Resource invalid;

    public SkolemizerInvalidPathCucumberTest() {
        ontology = ModelFactory.createOntologyModel().createOntology("http://test/ontology");
        cls = ontology.getOntModel().createClass("http://test/ontology/class");
        cls.addLiteral(LDT.path, 123);
    }

    public Skolemizer getSkolemizer(OntDocumentManager ontMgr, OntModel ontModel, String ontologyURI) {
        ontMgr.addModel(ontologyURI, ontModel);
        Ontology ontology = new OntologyLoader(ontMgr, ontologyURI, ontModel.getSpecification(), true).getOntology();
        return new Skolemizer(ontology, baseUriBuilder, absolutePathBuilder);
    }

    @Given("an invalid resource in invalid path test")
    public void an_invalid_resource_in_invalid_path_test() {
        invalid = ModelFactory.createDefaultModel().
                createResource().
                addProperty(RDF.type, cls);
    }

    @When("the model and the uri are valid in invalid path test")
    public void the_model_and_the_uri_are_valid_in_invalid_path_test() {
        model = ontology.getOntModel();
        uri = ontology.getURI();
    }

    @Then("we get an exception when we want get the skolemizer in invalid path test")
    public void we_get_an_exception_when_we_want_get_the_skolemizer_in_invalid_path_test() {
        Assertions.assertThatThrownBy(() ->
                getSkolemizer(new OntDocumentManager(), model, uri).build(invalid)).isInstanceOf(OntologyException.class);
    }
}
