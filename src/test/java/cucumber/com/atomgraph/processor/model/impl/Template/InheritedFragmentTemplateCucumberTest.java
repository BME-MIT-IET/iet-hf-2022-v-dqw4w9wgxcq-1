package cucumber.com.atomgraph.processor.model.impl.Template;

import com.atomgraph.processor.vocabulary.LDT;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static junit.framework.Assert.assertEquals;

public class InheritedFragmentTemplateCucumberTest {
    TemplateSource source = TemplateSource.getInstance();
    String superSuperFragmentTemplate;
    String superFragmentTemplate;

    String fragmentTemplateFromSuperSuper;
    String fragmentTemplateFromSuper;
    String fragmentTemplateFromSub;
    String fragmentTemplateFromSuperSuperTemplateOverriding;
    String fragmentTemplateFromSubTemplate1;

    @Given("super templates with a fragment template exists")
    public void superTemplatesWithAFragmentTemplateExists() {
        superSuperFragmentTemplate = source.getSuperSuperTemplate().getProperty(LDT.fragment).getString();
        superFragmentTemplate = source.getSuperTemplateOverriding().getProperty(LDT.fragment).getString();
    }

    @When("fragment template is asked for")
    public void fragmentTemplateIsAskedFor() {
        fragmentTemplateFromSuperSuper = source.getSuperSuperTemplate().getFragmentTemplate();
        fragmentTemplateFromSuper = source.getSuperTemplate().getFragmentTemplate();
        fragmentTemplateFromSub = source.getSubTemplate().getFragmentTemplate();

        fragmentTemplateFromSuperSuperTemplateOverriding = source.getSuperTemplateOverriding().getFragmentTemplate();
        fragmentTemplateFromSubTemplate1 = source.getSubTemplate1().getFragmentTemplate();
    }

    @Then("fragment template is provided")
    public void fragmentTemplateIsProvided() {
        assertEquals(superSuperFragmentTemplate, fragmentTemplateFromSuperSuper);
        assertEquals(superSuperFragmentTemplate, fragmentTemplateFromSuper);
        assertEquals(superSuperFragmentTemplate, fragmentTemplateFromSub);

        assertEquals(superFragmentTemplate, fragmentTemplateFromSuperSuperTemplateOverriding);
        assertEquals(superFragmentTemplate, fragmentTemplateFromSubTemplate1);
    }
}
