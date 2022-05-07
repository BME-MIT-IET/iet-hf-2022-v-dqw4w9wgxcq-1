package cucumber.com.atomgraph.processor.model.impl.Template;

import com.atomgraph.processor.vocabulary.LDT;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.glassfish.jersey.uri.UriTemplate;

import static junit.framework.Assert.assertEquals;

public class InheritedMatchCucumberTest {
    TemplateSource source = TemplateSource.getInstance();
    UriTemplate superSuperMatch;
    UriTemplate superMatch;

    UriTemplate matchFromSuperSuper;
    UriTemplate matchFromSuper;
    UriTemplate matchFromSub;
    UriTemplate matchFromSuperSuperTemplateOverriding;
    UriTemplate matchFromSubTemplate1;

    @Given("super templates and a match exists")
    public void superTemplatesAndAMatchExists() {
        superSuperMatch = new UriTemplate(source.getSuperSuperTemplate().getProperty(LDT.match).getString());
        superMatch = new UriTemplate(source.getSuperTemplateOverriding().getProperty(LDT.match).getString());
    }

    @When("match is asked for")
    public void matchIsAskedFor() {
        matchFromSuperSuper = source.getSuperSuperTemplate().getMatch();
        matchFromSuper = source.getSuperTemplate().getMatch();
        matchFromSub = source.getSubTemplate().getMatch();

        matchFromSuperSuperTemplateOverriding = source.getSuperTemplateOverriding().getMatch();
        matchFromSubTemplate1 = source.getSubTemplate1().getMatch();
    }

    @Then("match is provided")
    public void matchIsProvided() {
        assertEquals(superSuperMatch, matchFromSuperSuper);
        assertEquals(superSuperMatch, matchFromSuper);
        assertEquals(superSuperMatch, matchFromSub);

        assertEquals(superMatch, matchFromSuperSuperTemplateOverriding);
        assertEquals(superMatch, matchFromSubTemplate1);
    }
}
