package cucumber.com.atomgraph.processor.util;

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
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static org.junit.Assert.assertEquals;

public class SkolemizerInheritenceCucumberTest {
    private final UriBuilder baseUriBuilder = UriBuilder.fromUri("http://base/"), absolutePathBuilder = UriBuilder.fromUri("http://base/absolute/path");
    private URI expected,actual;
    private final Resource subInst;
    private final Ontology ontology;

    public SkolemizerInheritenceCucumberTest() {
        ontology = ModelFactory.createOntologyModel().createOntology("http://test/ontology");
        Ontology importedOntology = ontology.getOntModel().createOntology("http://test/ontology/import");
        ontology.addImport(importedOntology);
        OntClass superClass = importedOntology.getOntModel().createClass("http://test/ontology/super-class");
        superClass.addLiteral(LDT.path, "super-{title}");

        OntClass cls = ontology.getOntModel().createClass("http://test/ontology/class");
        cls.addProperty(RDFS.subClassOf, FOAF.Document).
                addProperty(RDFS.subClassOf, superClass);

        subInst = ModelFactory.createDefaultModel().
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

    @Given("the expected super skolemizer")
    public void the_excepted_super_skolemizer(){
        expected = absolutePathBuilder.clone().path("super-" + "Whateverest").build();
    }

    @When("the actual skolemizer is its inherit")
    public void the_actual_skolemizer_is_its_inherit() {
        actual = getSkolemizer(new OntDocumentManager(), ontology.getOntModel(), ontology.getURI()).build(subInst);
    }

    @Then("they are equal skolemizers")
    public void they_are_equal_skolemizers() {
        assertEquals(expected, actual);
    }
}
