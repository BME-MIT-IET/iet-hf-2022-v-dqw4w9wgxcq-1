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

public class SkolemizerInvalidSuperClassCucumberTest {
    private final UriBuilder baseUriBuilder = UriBuilder.fromUri("http://base/"), absolutePathBuilder = UriBuilder.fromUri("http://base/absolute/path");
    private URI expected,actual;
    private final Resource inst;
    private final Ontology ontology;

    public SkolemizerInvalidSuperClassCucumberTest() {
        ontology = ModelFactory.createOntologyModel().createOntology("http://test/ontology");
        OntClass cls = ontology.getOntModel().createClass("http://test/ontology/class");
        cls.addLiteral(LDT.path, "{title}").
                addProperty(RDFS.subClassOf, FOAF.Document).
                addProperty(RDFS.subClassOf, ResourceFactory.createResource("http://whatever"));

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

    @Given("the expected uri in invalid super class test")
    public void the_expected_uri_in_invalid_super_class_test(){
        expected = absolutePathBuilder.clone().path("Whateverest").build();
    }

    @When("the actual uri is inheritance of this invalid superclass in invalid super class test")
    public void the_actual_uri_is_inheritance_of_this_invalid_superclass_in_invalid_super_class_test() {
        actual = getSkolemizer(new OntDocumentManager(), ontology.getOntModel(), ontology.getURI()).build(inst);
    }

    @Then("the invalid superclass uri and its inherit are equal in invalid super class test")
    public void the_invalid_superclass_uri_and_its_inherit_are_equal_in_invalid_super_class_test() {
        assertEquals(expected, actual);
    }
}
