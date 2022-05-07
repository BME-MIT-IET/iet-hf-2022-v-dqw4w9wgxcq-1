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

public class TemplateMatcherWithNumerPathCucumberTest {
    private Ontology invalidOntology;
    private String path;

    public TemplateMatcherWithNumerPathCucumberTest() {
        JenaSystem.init();
        BuiltinPersonalities.model.add(Template.class, TemplateImpl.factory);
    }

    @Given("an invalid template in numerical path test")
    public void an_invalid_template_in_numerical_path_test() {
        invalidOntology = ModelFactory.createOntologyModel().createOntology("http://test/invalid-ontology");
        Template invalidTemplate = invalidOntology.getOntModel().createIndividual("http://test/invalid-ontology/invalid-template", LDT.Template).
                as(Template.class);
        invalidTemplate.addLiteral(LDT.match, 123).
                addProperty(RDFS.isDefinedBy, invalidOntology);
    }

    @When("the path is valid in numerical path test")
    public void the_path_is_valid_in_numerical_path_test() {
        this.path = "other/something";
    }

    @Then("we get an exception in numerical path test")
    public void we_get_an_exception_in_numerical_path_test() {
        Assertions.assertThatThrownBy(() ->
                new TemplateMatcher(invalidOntology).match(path)).isInstanceOf(OntologyException.class);
    }
}
