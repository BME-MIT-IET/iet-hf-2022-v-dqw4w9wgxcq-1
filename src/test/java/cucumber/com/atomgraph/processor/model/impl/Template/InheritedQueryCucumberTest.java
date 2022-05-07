package cucumber.com.atomgraph.processor.model.impl.Template;

import com.atomgraph.processor.vocabulary.LDT;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.jena.rdf.model.Resource;

import static junit.framework.Assert.assertEquals;

public class InheritedQueryCucumberTest {
    TemplateSource source = TemplateSource.getInstance();
    Resource superSuperQuery;
    Resource superQuery;

    Resource queryFromSuperSuper;
    Resource queryFromSuper;
    Resource queryFromSub;
    Resource queryFromSuperSuperTemplateOverriding;
    Resource queryFromSubTemplate1;

    @Given("super templates and a query exists")
    public void superTemplatesAndAQueryExists() {
        superSuperQuery = source.getSuperSuperTemplate().getProperty(LDT.query).getResource();
        superQuery = source.getSuperTemplateOverriding().getProperty(LDT.query).getResource();
    }

    @When("query is asked for")
    public void queryIsAskedFor() {
        queryFromSuperSuper = source.getSuperSuperTemplate().getQuery();
        queryFromSuper = source.getSuperTemplate().getQuery();
        queryFromSub = source.getSubTemplate().getQuery();

        queryFromSuperSuperTemplateOverriding = source.getSuperTemplateOverriding().getQuery();
        queryFromSubTemplate1 = source.getSubTemplate1().getQuery();
    }

    @Then("query is provided")
    public void queryIsProvided() {
        assertEquals(superSuperQuery, queryFromSuperSuper);
        assertEquals(superSuperQuery, queryFromSuper);
        assertEquals(superSuperQuery, queryFromSub);

        assertEquals(superQuery, queryFromSuperSuperTemplateOverriding);
        assertEquals(superQuery, queryFromSubTemplate1);
    }
}
