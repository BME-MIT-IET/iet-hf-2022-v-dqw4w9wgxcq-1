package cucumber.com.atomgraph.processor.model.impl.Template;

import com.atomgraph.processor.vocabulary.LDT;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import javax.ws.rs.core.CacheControl;

import static junit.framework.Assert.assertEquals;

public class InheritedCacheControlCucumberTest {
    TemplateSource source = TemplateSource.getInstance();
    CacheControl superSuperCacheControl;
    CacheControl superCacheControl;

    CacheControl cacheControlFromSuperSuper;
    CacheControl cacheControlFromSuper;
    CacheControl cacheControlFromSub;
    CacheControl cacheControlFromSuperSuperTemplateOverriding;
    CacheControl cacheControlFromSubTemplate1;

    @Given("super templates with a cache control exists")
    public void superTemplatesWithACacheControlExists() {
        superSuperCacheControl = CacheControl.valueOf(source.getSuperSuperTemplate().getProperty(LDT.cacheControl).getString());
        superCacheControl = CacheControl.valueOf(source.getSuperTemplateOverriding().getProperty(LDT.cacheControl).getString());
    }

    @When("cache control is asked for")
    public void cacheControlIsAskedFor() {
        cacheControlFromSuperSuper = source.getSuperSuperTemplate().getCacheControl();
        cacheControlFromSuper = source.getSuperTemplate().getCacheControl();
        cacheControlFromSub = source.getSubTemplate().getCacheControl();

        cacheControlFromSuperSuperTemplateOverriding = source.getSuperTemplateOverriding().getCacheControl();
        cacheControlFromSubTemplate1 = source.getSubTemplate1().getCacheControl();
    }

    @Then("cache control is provided")
    public void cacheControlIsProvided() {
        assertEquals(superSuperCacheControl, cacheControlFromSuperSuper);
        assertEquals(superSuperCacheControl, cacheControlFromSuper);
        assertEquals(superSuperCacheControl, cacheControlFromSub);

        assertEquals(superCacheControl, cacheControlFromSuperSuperTemplateOverriding);
        assertEquals(superCacheControl, cacheControlFromSubTemplate1);
    }
}
