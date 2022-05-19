package cucumber.com.atomgraph.processor.util.TemplateMatcher;

import com.atomgraph.processor.model.Template;
import com.atomgraph.processor.util.TemplateMatcher;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class TemplateMatcherMatchPathCucumberTest {
    private Template template;
    private String path;

    @Given("template of the template matcher is {string}")
    public void template_of_the_template_matcher_is(String templateName) {
        this.template = TemplateAndMatcherSource.getInstance().getTemplate(templateName);
    }

    @When("the path of the template matcher is {string}")
    public void the_path_of_the_template_matcher_is(String path) {
        this.path = path;
    }

    @Then("the template and the path are equal")
    public void the_template_and_the_path_are_equal() {
        TemplateMatcher matcher = TemplateAndMatcherSource.getInstance().getMatcher();
        assertEquals(template, matcher.match(path));
    }
}
