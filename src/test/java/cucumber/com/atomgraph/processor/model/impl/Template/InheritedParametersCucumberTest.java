package cucumber.com.atomgraph.processor.model.impl.Template;

import com.atomgraph.processor.model.Parameter;
import org.apache.jena.rdf.model.Resource;
import com.atomgraph.processor.vocabulary.LDT;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

public class InheritedParametersCucumberTest {
    TemplateSource source = TemplateSource.getInstance();
    Resource superSuperParameter;
    Set<Resource> superParametersOverriding = new HashSet<>();

    Set<Parameter> parametersFromSuperSuper;
    Set<Parameter> parametersFromSuper;
    Set<Parameter> parametersFromSub;
    Set<Parameter> parametersFromSuperSuperTemplateOverriding;
    Set<Parameter> parametersFromSubTemplate1;

    @Given("super templates with parameters exists")
    public void superTemplatesWithParametersExists() {
        superSuperParameter = source.getSuperSuperTemplate().getPropertyResourceValue(LDT.param);
        superParametersOverriding.add(superSuperParameter);
        superParametersOverriding.add(source.getSuperTemplateOverriding().getPropertyResourceValue(LDT.param));
    }

    @When("parameters are asked for")
    public void parametersAreAskedFor() {
        parametersFromSuperSuper = new HashSet<>(source.getSuperSuperTemplate().getParameters().values());
        parametersFromSuper = new HashSet<>(source.getSuperTemplate().getParameters().values());
        parametersFromSub = new HashSet<>(source.getSubTemplate().getParameters().values());

        parametersFromSuperSuperTemplateOverriding = new HashSet<>(source.getSuperTemplateOverriding().getParameters().values());
        parametersFromSubTemplate1 = new HashSet<>(source.getSubTemplate1().getParameters().values());
    }

    @Then("parameters are provided")
    public void parametersAreProvided() {
        assertEquals(Collections.singleton(superSuperParameter), parametersFromSuperSuper);
        assertEquals(Collections.singleton(superSuperParameter), parametersFromSuper);
        assertEquals(Collections.singleton(superSuperParameter), parametersFromSub);

        assertEquals(parametersFromSuperSuperTemplateOverriding, parametersFromSuperSuperTemplateOverriding);
        assertEquals(parametersFromSuperSuperTemplateOverriding, parametersFromSubTemplate1);
    }
}
