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

    String fragmentUpdateFromSuperSuper;
    String fragmentUpdateFromSuper;
    String fragmentUpdateFromSub;
    String fragmentUpdateFromSuperSuperTemplateOverriding;
    String fragmentUpdateFromSubTemplate1;

    @Given("super templates with a fragment template exists")
    public void superTemplatesWithAFragmentTemplateExists() {
        superSuperFragmentTemplate = source.getSuperSuperTemplate().getProperty(LDT.fragment).getString();
        superFragmentTemplate = source.getSuperTemplateOverriding().getProperty(LDT.fragment).getString();
    }

    @When("fragment template is asked for")
    public void fragmentTemplateIsAskedFor() {
        fragmentUpdateFromSuperSuper = source.getSuperSuperTemplate().getFragmentTemplate();
        fragmentUpdateFromSuper = source.getSuperTemplate().getFragmentTemplate();
        fragmentUpdateFromSub = source.getSubTemplate().getFragmentTemplate();

        fragmentUpdateFromSuperSuperTemplateOverriding = source.getSuperTemplateOverriding().getFragmentTemplate();
        fragmentUpdateFromSubTemplate1 = source.getSubTemplate1().getFragmentTemplate();
    }

    @Then("fragment template is provided")
    public void fragmentTemplateIsProvided() {
        assertEquals(superSuperFragmentTemplate, fragmentUpdateFromSuperSuper);
        assertEquals(superSuperFragmentTemplate, fragmentUpdateFromSuper);
        assertEquals(superSuperFragmentTemplate, fragmentUpdateFromSub);

        assertEquals(superFragmentTemplate, fragmentUpdateFromSuperSuperTemplateOverriding);
        assertEquals(superFragmentTemplate, fragmentUpdateFromSubTemplate1);
    }
}
