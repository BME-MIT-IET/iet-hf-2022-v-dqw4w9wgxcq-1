package cucumber.com.atomgraph.processor.model.impl.Template;

import com.atomgraph.processor.vocabulary.LDT;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertEquals;


public class NonNullLanguagesCucumberTest {
    TemplateSource source = TemplateSource.getInstance();
    List<Locale> superSuperLanguages;
    List<Locale> superLanguages;

    List<Locale> languagesFromSuper;
    List<Locale> languagesFromSuperSuper;
    List<Locale> languagesFromSub;
    List<Locale> languagesFromSuperTemplateOverriding;
    List<Locale> languagesFromSubTemplate1;

    @Given("super templates and corresponding languages exists")
    public void superTemplatesAndCorrespondingLanguagesExists(){
        superSuperLanguages = source.getSuperSuperTemplate().getProperty(LDT.lang).getList().
                asJavaList().stream().map(n -> Locale.forLanguageTag(n.asLiteral().getString())).collect(Collectors.toList());

        superLanguages = source.getSuperTemplateOverriding().getProperty(LDT.lang).getList().
                asJavaList().stream().map(n -> Locale.forLanguageTag(n.asLiteral().getString())).collect(Collectors.toList());

    }

    @When("languages is asked for")
    public void languagesIsAskedFor() {
        languagesFromSuper = source.getSuperTemplate().getLanguages();
        languagesFromSuperSuper = source.getSuperSuperTemplate().getLanguages();
        languagesFromSub = source.getSubTemplate().getLanguages();

        languagesFromSuperTemplateOverriding = source.getSuperTemplateOverriding().getLanguages();
        languagesFromSubTemplate1 = source.getSubTemplate1().getLanguages();
    }

    @Then("list of languages are provided")
    public void listOfLanguagesAreProvided() {
        assertEquals(superSuperLanguages, languagesFromSuper);
        assertEquals(superSuperLanguages, languagesFromSuperSuper);
        assertEquals(superSuperLanguages, languagesFromSub);

        assertEquals(superLanguages, languagesFromSuperTemplateOverriding);
        assertEquals(superLanguages, languagesFromSubTemplate1);
    }
}
