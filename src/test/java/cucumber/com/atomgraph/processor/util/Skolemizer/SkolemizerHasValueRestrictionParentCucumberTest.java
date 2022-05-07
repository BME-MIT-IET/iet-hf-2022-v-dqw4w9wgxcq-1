package cucumber.com.atomgraph.processor.util.Skolemizer;

import com.atomgraph.processor.util.Skolemizer;
import com.atomgraph.processor.vocabulary.LDT;
import com.atomgraph.processor.vocabulary.SIOC;
import com.atomgraph.server.util.OntologyLoader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static org.junit.Assert.assertEquals;

public class SkolemizerHasValueRestrictionParentCucumberTest {
    private final UriBuilder baseUriBuilder = UriBuilder.fromUri("http://base/"), absolutePathBuilder = UriBuilder.fromUri("http://base/absolute/path");
    private final String hasValue = "http://restricted/", id = "987654321";
    private final Ontology ontology;
    private final Resource inst;
    private URI expected, actual;

    public SkolemizerHasValueRestrictionParentCucumberTest() {
        ontology = ModelFactory.createOntologyModel().createOntology("http://test/ontology");
        HasValueRestriction hvr = ontology.getOntModel().createHasValueRestriction("http://test/ontology/hvr", SIOC.HAS_CONTAINER, ontology.getOntModel().createResource(hasValue));

        OntClass superCls = ontology.getOntModel().createClass("http://test/ontology/super-class");
        superCls.addLiteral(LDT.path, "hv-{identifier}");
        OntClass cls = ontology.getOntModel().createClass("http://test/ontology/class");
        cls.addProperty(RDFS.subClassOf, hvr).
                addProperty(RDFS.subClassOf, superCls);

        ontology.getOntModel().createResource(SIOC.HAS_CONTAINER.getURI()).
                addProperty(RDF.type, OWL.ObjectProperty);

        inst = ModelFactory.createDefaultModel().createResource().addProperty(RDF.type, cls).addLiteral(DCTerms.identifier, id);
    }

    public Skolemizer getSkolemizer(OntDocumentManager ontMgr, OntModel ontModel, String ontologyURI) {
        ontMgr.addModel(ontologyURI, ontModel);
        Ontology ontology = new OntologyLoader(ontMgr, ontologyURI, ontModel.getSpecification(), true).getOntology();
        return new Skolemizer(ontology, baseUriBuilder, absolutePathBuilder);
    }

    @Given("the expected uri in has value restriction path test")
    public void the_excepted_uri_in_has_value_restriction_path_test() {
        expected = UriBuilder.fromUri(hasValue).path("hv-" + id).build();
    }

    @When("the actual uri is gave in has value restriction path test")
    public void the_actual_uri_is_gave_of_has_value_restriction_path_test() {
        actual = getSkolemizer(new OntDocumentManager(), ontology.getOntModel(), ontology.getURI()).build(inst);
    }

    @Then("the expected and the actual uri are equal in has value restriction path test")
    public void the_expected_and_the_actual_uri_are_equal_in_has_value_restriction_path_test() {
        assertEquals(expected, actual);
    }
}
