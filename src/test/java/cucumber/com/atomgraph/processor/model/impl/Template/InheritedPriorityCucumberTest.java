package cucumber.com.atomgraph.processor.model.impl.Template;

import com.atomgraph.processor.vocabulary.LDT;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static junit.framework.Assert.assertEquals;

public class InheritedPriorityCucumberTest {
    TemplateSource source = TemplateSource.getInstance();
    Double superSuperPriority;
    Double superPriority;

    Double priorityFromSuperSuper;
    Double priorityFromSuper;
    Double priorityFromSub;
    Double priorityFromSuperSuperTemplateOverriding;
    Double priorityFromSubTemplate1;

    @Given("super templates with a priority exists")
    public void superTemplatesWithAPriorityExists() {
        superSuperPriority = source.getSuperSuperTemplate().getProperty(LDT.priority).getDouble();
        superPriority = source.getSuperTemplateOverriding().getProperty(LDT.priority).getDouble();
    }

    @When("priority is asked for")
    public void priorityIsAskedFor() {
        priorityFromSuperSuper = source.getSuperSuperTemplate().getPriority();
        priorityFromSuper = source.getSuperTemplate().getPriority();
        priorityFromSub = source.getSubTemplate().getPriority();

        priorityFromSuperSuperTemplateOverriding = source.getSuperTemplateOverriding().getPriority();
        priorityFromSubTemplate1 = source.getSubTemplate1().getPriority();
    }

    @Then("priority is provided")
    public void priorityIsProvided() {
        assertEquals(superSuperPriority, priorityFromSuperSuper);
        assertEquals(superSuperPriority, priorityFromSuper);
        assertEquals(superSuperPriority, priorityFromSub);

        assertEquals(superPriority, priorityFromSuperSuperTemplateOverriding);
        assertEquals(superPriority, priorityFromSubTemplate1);
    }
}
