package cucumber.com.atomgraph.processor.model.impl.Template;

import com.atomgraph.processor.model.Parameter;
import com.atomgraph.processor.model.Template;
import com.atomgraph.processor.model.impl.ParameterImpl;
import com.atomgraph.processor.model.impl.TemplateImpl;
import com.atomgraph.processor.vocabulary.LDT;
import com.atomgraph.spinrdf.vocabulary.SP;
import com.atomgraph.spinrdf.vocabulary.SPL;
import org.apache.jena.enhanced.BuiltinPersonalities;
import org.apache.jena.ontology.Ontology;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.sys.JenaSystem;
import org.apache.jena.vocabulary.RDFS;
import org.junit.Before;
import org.junit.BeforeClass;

public class TemplateSource {
    private static TemplateSource instance;
    private Ontology ontology;
    private Template superSuperTemplate, superTemplate, superTemplateOverriding, subTemplate, subTemplate1, template;

    static { JenaSystem.init(); }

    private TemplateSource() {
        setUpClass();
        setUp();
    }

    public static TemplateSource getInstance() {
        instance = new TemplateSource();
        return instance;
    }

    @BeforeClass
    public static void setUpClass() {
        BuiltinPersonalities.model.add(Template.class, TemplateImpl.factory);
        BuiltinPersonalities.model.add(Parameter.class, ParameterImpl.factory);
    }

    @Before
    public void setUp() {
        ontology = ModelFactory.createOntologyModel().createOntology("http://test/ontology"); //ModelFactory.createOntologyModel(rulesSpec).createOntology("http://test/ontology");
        ontology.addImport(LDT.NAMESPACE);
        ontology.getOntModel().loadImports();
        superSuperTemplate = ontology.getOntModel().createIndividual("http://test/ontology/super-super-template", LDT.Template).
                addLiteral(LDT.priority, 5).
                addLiteral(LDT.match, "{path}").
                addLiteral(LDT.fragment, "{fragment}").
                addProperty(LDT.query, ontology.getOntModel().createIndividual("http://test/query", SP.Describe)).
                addProperty(LDT.update, ontology.getOntModel().createIndividual("http://test/update", SP.DeleteWhere)).
                addProperty(LDT.param, ontology.getOntModel().createIndividual("http://test/ontology/param", LDT.Parameter).
                        addProperty(SPL.predicate, FOAF.name)).
                addProperty(LDT.lang, ontology.getOntModel().createList().
                        with(ontology.getOntModel().createLiteral("en")).
                        with(ontology.getOntModel().createLiteral("da"))).
                addLiteral(LDT.cacheControl, "max-age=3600").
                addProperty(LDT.loadClass, ontology.getOntModel().createResource("java:some.Class")).
                addProperty(RDFS.isDefinedBy, ontology).
                as(Template.class);

        superTemplate = ontology.getOntModel().createIndividual("http://test/ontology/super-template", LDT.Template).
                addProperty(LDT.extends_, superSuperTemplate).
                addProperty(RDFS.isDefinedBy, ontology).
                as(Template.class);

        superTemplateOverriding = ontology.getOntModel().createIndividual("http://test/ontology/super-template-overriding", LDT.Template).
                addProperty(LDT.extends_, superSuperTemplate).
                addLiteral(LDT.priority, 7).
                addLiteral(LDT.match, "/whatever").
                addLiteral(LDT.fragment, "est").
                addProperty(LDT.query, ontology.getOntModel().createIndividual("http://test/query-overriding", SP.Describe)).
                addProperty(LDT.update, ontology.getOntModel().createIndividual("http://test/update-overriding", SP.DeleteWhere)).
                addProperty(LDT.param, ontology.getOntModel().createIndividual("http://test/ontology/param-overriding", LDT.Parameter).
                        addProperty(SPL.predicate, FOAF.maker)).
                addProperty(LDT.lang, ontology.getOntModel().createList().
                        with(ontology.getOntModel().createLiteral("lt")).
                        with(ontology.getOntModel().createLiteral("da"))).
                addLiteral(LDT.cacheControl, "max-age=9999").
                addProperty(LDT.loadClass, ontology.getOntModel().createResource("java:some.OtherClass")).
                addProperty(RDFS.isDefinedBy, ontology).
                as(Template.class);

        subTemplate = ontology.getOntModel().createIndividual("http://test/ontology/sub-template", LDT.Template).
                addProperty(LDT.extends_, superTemplate).
                addProperty(RDFS.isDefinedBy, ontology).
                as(Template.class);

        subTemplate1 = ontology.getOntModel().createIndividual("http://test/ontology/sub-template1", LDT.Template).
                addProperty(LDT.extends_, superTemplateOverriding).
                addProperty(RDFS.isDefinedBy, ontology).
                as(Template.class);

        template = ontology.getOntModel().createIndividual("http://test/ontology/template", LDT.Template).
                addProperty(RDFS.isDefinedBy, ontology).
                as(Template.class);
    }

    public Ontology getOntology(){
        return ontology;
    }

    public Template getSuperTemplate() {
        return superTemplate;
    }

    public Template getSuperSuperTemplate() {
        return superSuperTemplate;
    }

    public Template getSuperTemplateOverriding() {
        return superTemplateOverriding;
    }

    public Template getSubTemplate1() {
        return subTemplate1;
    }

    public Template getSubTemplate() {
        return subTemplate;
    }

    public Template getTemplate() {
        return template;
    }
}
