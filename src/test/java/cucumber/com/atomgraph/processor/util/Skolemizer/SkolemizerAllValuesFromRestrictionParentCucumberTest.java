package cucumber.com.atomgraph.processor.util.Skolemizer;

import com.atomgraph.processor.util.Skolemizer;
import com.atomgraph.processor.vocabulary.LDT;
import com.atomgraph.processor.vocabulary.SIOC;
import com.atomgraph.server.util.OntologyLoader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static org.junit.Assert.assertEquals;

public class SkolemizerAllValuesFromRestrictionParentCucumberTest {
    private final UriBuilder baseUriBuilder = UriBuilder.fromUri("http://base/"), absolutePathBuilder = UriBuilder.fromUri("http://base/absolute/path");
    private final String hasValue = "http://restricted/", id = "987654321";
    private final Ontology ontology;
    private final Resource inst;
    private URI expected, actual;

    public SkolemizerAllValuesFromRestrictionParentCucumberTest() {
        ontology = ModelFactory.createOntologyModel().createOntology("http://test/ontology");
        HasValueRestriction hvr = ontology.getOntModel().
                createHasValueRestriction("http://test/ontology/hvr", SIOC.HAS_CONTAINER, ontology.getOntModel().
                        createResource(hasValue));

        OntClass hvrCls = ontology.getOntModel().createClass("http://test/ontology/hvr-class");
        hvrCls.addProperty(RDFS.subClassOf, hvr);

        ontology.getOntModel().createResource(SIOC.HAS_CONTAINER.getURI()).
                addProperty(RDF.type, OWL.ObjectProperty);

        AllValuesFromRestriction avfr = ontology.getOntModel().
                createAllValuesFromRestriction(null, FOAF.primaryTopic, hvrCls);

        OntClass superCls = ontology.getOntModel().createClass();
        superCls.addLiteral(LDT.path, "avf-{isPrimaryTopicOf.identifier}");
        OntClass cls = ontology.getOntModel().createClass();
        cls.addProperty(RDFS.subClassOf, avfr).
                addProperty(RDFS.subClassOf, superCls);

        Model model = ModelFactory.createDefaultModel();
        Resource doc = model.createResource().
                addLiteral(DCTerms.identifier, id);
        inst = model.createResource().
                addProperty(RDF.type, cls).
                addProperty(FOAF.isPrimaryTopicOf, doc);
    }

    public Skolemizer getSkolemizer(OntDocumentManager ontMgr, OntModel ontModel, String ontologyURI) {
        ontMgr.addModel(ontologyURI, ontModel);
        Ontology ontology = new OntologyLoader(ontMgr, ontologyURI, ontModel.getSpecification(), true).getOntology();
        return new Skolemizer(ontology, baseUriBuilder, absolutePathBuilder);
    }

    @Given("the expected uri of all values from restriction path skolemizer test")
    public void the_excepted_uri_of_all_values_from_restriction_path_skolemizer_test() {
        expected = UriBuilder.fromUri(hasValue).path("avf-" + id).build();
    }

    @When("the actual uri is gave of all values from restriction path skolemizer test")
    public void the_actual_uri_is_gave_of_all_values_from_restriction_path_skolemizer_test() {
        actual = getSkolemizer(new OntDocumentManager(), ontology.getOntModel(), ontology.getURI()).build(inst);
    }

    @Then("the expected and the actual uri in all values from restriction path test are equal")
    public void the_expected_and_the_actual_uri_in_all_values_from_restriction_path_test_are_equal() {
        assertEquals(expected, actual);
    }
}
