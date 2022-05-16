package cucumber.com.atomgraph.processor.model.impl.Template;

import com.atomgraph.processor.vocabulary.LDT;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.jena.rdf.model.Resource;

import static junit.framework.Assert.assertEquals;

public class InheritedUpdateCucumberTest {
    TemplateSource source = TemplateSource.getInstance();
    Resource superSuperUpdate;
    Resource superUpdate;

    Resource updateFromSuperSuper;
    Resource updateFromSuper;
    Resource updateFromSub;
    Resource updateFromSuperSuperTemplateOverriding;
    Resource updateFromSubTemplate1;

    @Given("super templates with an update exists")
    public void superTemplatesWithAnUpdateExists() {
        superSuperUpdate = source.getSuperSuperTemplate().getProperty(LDT.update).getResource();
        superUpdate = source.getSuperTemplateOverriding().getProperty(LDT.update).getResource();
    }

    @When("update is asked for")
    public void updateIsAskedFor() {
        updateFromSuperSuper = source.getSuperSuperTemplate().getUpdate();
        updateFromSuper = source.getSuperTemplate().getUpdate();
        updateFromSub = source.getSubTemplate().getUpdate();

        updateFromSuperSuperTemplateOverriding = source.getSuperTemplateOverriding().getUpdate();
        updateFromSubTemplate1 = source.getSubTemplate1().getUpdate();
    }

    @Then("update is provided")
    public void updateIsProvided() {
        assertEquals(superSuperUpdate, updateFromSuperSuper);
        assertEquals(superSuperUpdate, updateFromSuper);
        assertEquals(superSuperUpdate, updateFromSub);

        assertEquals(superUpdate, updateFromSuperSuperTemplateOverriding);
        assertEquals(superUpdate, updateFromSubTemplate1);
    }
}
