package cucumber.com.atomgraph.processor.model.impl.Template;

import com.atomgraph.processor.model.Template;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class SuperTemplateCucumberTest {
    TemplateSource source = TemplateSource.getInstance();
    List<Template> superTemplates;
    List<Template> superTemplatesFromSubTemplates;

    @Given("super and sub templates exists")
    public void superAndSubTemplatesExists(){
        superTemplates = Arrays.asList(source.getSuperTemplate(), source.getSuperSuperTemplate());
    }

    @When("super template is asked for through its sub template")
    public void superTemplateIsAskedForThroughItsSubTemplate() {
        superTemplatesFromSubTemplates = source.getSubTemplate().getSuperTemplates();
    }

    @Then("super template is provided")
    public void superTemplateIsProvided() {
        assertEquals(superTemplates, superTemplatesFromSubTemplates);
    }
}
