package cucumber.com.atomgraph.processor.util.TemplateMatcher;

import com.atomgraph.processor.exception.OntologyException;
import com.atomgraph.processor.model.Template;
import com.atomgraph.processor.model.impl.TemplateImpl;
import com.atomgraph.processor.util.TemplateMatcher;
import com.atomgraph.processor.vocabulary.LDT;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.jena.enhanced.BuiltinPersonalities;
import org.apache.jena.ontology.Ontology;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.sys.JenaSystem;
import org.apache.jena.vocabulary.RDFS;
import org.assertj.core.api.Assertions;

public class TemplateMatcherNoPathCucumberTest {
    private Ontology invalidOntology;
    private String path;

    public TemplateMatcherNoPathCucumberTest() {
        JenaSystem.init();
        BuiltinPersonalities.model.add(Template.class, TemplateImpl.factory);
    }

    @Given("an invalid template in no path test")
    public void an_invalid_template_in_no_path_test() {
        invalidOntology = ModelFactory.createOntologyModel().createOntology("http://test/invalid-ontology");
        Template invalidTemplate = invalidOntology.getOntModel().createIndividual("http://test/invalid-ontology/no-path-template", LDT.Template).
                as(Template.class);
        invalidTemplate.addProperty(RDFS.isDefinedBy, invalidOntology);
    }

    @When("the path is valid in no path test")
    public void the_path_is_valid_in_no_path_test() {
        this.path = "other/something";
    }

    @Then("we get an exception in no path test")
    public void we_get_an_exception_in_no_path_test() {
        Assertions.assertThatThrownBy(() ->
                new TemplateMatcher(invalidOntology).match(path)).isInstanceOf(OntologyException.class);
    }
}
