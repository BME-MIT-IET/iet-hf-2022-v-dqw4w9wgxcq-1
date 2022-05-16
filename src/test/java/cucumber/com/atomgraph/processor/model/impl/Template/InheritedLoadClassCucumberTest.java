package cucumber.com.atomgraph.processor.model.impl.Template;

import com.atomgraph.processor.vocabulary.LDT;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.jena.rdf.model.Resource;

import static junit.framework.Assert.assertEquals;

public class InheritedLoadClassCucumberTest {
    TemplateSource source = TemplateSource.getInstance();
    Resource superSuperLoadClass;
    Resource superLoadClass;

    Resource loadClassFromSuperSuper;
    Resource loadClassFromSuper;
    Resource loadClassFromSub;
    Resource loadClassFromSuperSuperTemplateOverriding;
    Resource loadClassFromSubTemplate1;

    @Given("super templates with a load class exists")
    public void superTemplatesWithALoadClassExists() {
        superSuperLoadClass = source.getSuperSuperTemplate().getProperty(LDT.loadClass).getResource();
        superLoadClass = source.getSuperTemplateOverriding().getProperty(LDT.loadClass).getResource();
    }

    @When("load class is asked for")
    public void loadClassIsAskedFor() {
        loadClassFromSuperSuper = source.getSuperSuperTemplate().getLoadClass();
        loadClassFromSuper = source.getSuperTemplate().getLoadClass();
        loadClassFromSub = source.getSubTemplate().getLoadClass();

        loadClassFromSuperSuperTemplateOverriding = source.getSuperTemplateOverriding().getLoadClass();
        loadClassFromSubTemplate1 = source.getSubTemplate1().getLoadClass();
    }

    @Then("load class is provided")
    public void loadClassIsProvided() {
        assertEquals(superSuperLoadClass, loadClassFromSuperSuper);
        assertEquals(superSuperLoadClass, loadClassFromSuper);
        assertEquals(superSuperLoadClass, loadClassFromSub);

        assertEquals(superLoadClass, loadClassFromSuperSuperTemplateOverriding);
        assertEquals(superLoadClass, loadClassFromSubTemplate1);
    }
}
