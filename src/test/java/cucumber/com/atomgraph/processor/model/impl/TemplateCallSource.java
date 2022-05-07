package cucumber.com.atomgraph.processor.model.impl;

import com.atomgraph.processor.model.Parameter;
import com.atomgraph.processor.model.Template;
import com.atomgraph.processor.model.TemplateCall;
import com.atomgraph.processor.model.impl.ParameterImpl;
import com.atomgraph.processor.model.impl.TemplateCallImpl;
import com.atomgraph.processor.model.impl.TemplateImpl;
import com.atomgraph.processor.vocabulary.LDT;
import com.atomgraph.spinrdf.vocabulary.SP;
import com.atomgraph.spinrdf.vocabulary.SPIN;
import com.atomgraph.spinrdf.vocabulary.SPL;
import org.apache.jena.enhanced.BuiltinPersonalities;
import org.apache.jena.ontology.Ontology;
import org.apache.jena.rdf.model.*;
import org.apache.jena.sys.JenaSystem;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.junit.Before;
import org.junit.BeforeClass;

public class TemplateCallSource {
    private static TemplateCallSource instance;
    private static final String PREDICATE1_LOCAL_NAME = "a", PREDICATE2_LOCAL_NAME = "b", PREDICATE3_LOCAL_NAME = "c", UNUSED_PREDICATE_LOCAL_NAME = "unused";
    private static final String PARAM2_DEFAULT_VALUE = "default value";
    private static final Literal QUERY_BINDING = ResourceFactory.createPlainLiteral("binding");

    private Template template;
    private Property predicate1, predicate2, predicate3, unusedPredicate;
    private Parameter param1, param2, param3;
    private Resource resource;
    private TemplateCall call;

    static { JenaSystem.init(); }

    private TemplateCallSource() {
        setUpClass();
        setUp();
    }

    public static TemplateCallSource getInstance() {
        instance = new TemplateCallSource();
        return instance;
    }

    @BeforeClass
    public static void setUpClass()
    {
        BuiltinPersonalities.model.add(Template.class, TemplateImpl.factory);
        BuiltinPersonalities.model.add(Parameter.class, ParameterImpl.factory);
    }

    @Before
    public void setUp()
    {
        Ontology ontology = ModelFactory.createOntologyModel().createOntology("http://test/ontology");
        ontology.addImport(ResourceFactory.createResource(SP.NS));
        ontology.addImport(ResourceFactory.createResource(SPIN.NS));
        ontology.addImport(ResourceFactory.createResource(SPL.NS));
        ontology.getOntModel().loadImports();

        predicate1 = ontology.getOntModel().createProperty("http://test/" + PREDICATE1_LOCAL_NAME);
        predicate2 = ontology.getOntModel().createProperty("http://test/" + PREDICATE2_LOCAL_NAME);
        predicate3 = ontology.getOntModel().createProperty("http://test/" + PREDICATE3_LOCAL_NAME);
        unusedPredicate = ontology.getOntModel().createProperty("http://test/" + UNUSED_PREDICATE_LOCAL_NAME);

        param1 = ontology.getOntModel().createIndividual("http://test/ontology/param1", LDT.Parameter).
                addProperty(RDF.type, SPL.Argument). // following SPIN convention to use local name
                addProperty(SPL.predicate, predicate1).
                addLiteral(SPL.optional, false).
                addProperty(RDFS.isDefinedBy, ontology).
                as(Parameter.class);
        param2 = ontology.getOntModel().createIndividual("http://test/ontology/param2", LDT.Parameter).
                addProperty(SPL.predicate, predicate2). // following SPIN convention to use local name
                addLiteral(SPL.optional, false).
                addLiteral(SPL.defaultValue, PARAM2_DEFAULT_VALUE).
                addProperty(RDFS.isDefinedBy, ontology).
                as(Parameter.class);
        param3 = ontology.getOntModel().createIndividual("http://test/ontology/param3", LDT.Parameter).
                addProperty(SPL.predicate, predicate3). // following SPIN convention to use local name
                addLiteral(SPL.optional, false).
                addProperty(RDFS.isDefinedBy, ontology).
                as(Parameter.class);

        Resource spinQuery = ontology.getOntModel().createIndividual("http://test/ontology/spin/query", SP.Describe).
                addLiteral(SP.text, "DESCRIBE * { ?a ?b ?c }").
                addProperty(RDFS.isDefinedBy, ontology);
        Resource spinTemplate = ontology.getOntModel().createIndividual("http://test/ontology/spin/template", SPIN.Template).
                addProperty(SPIN.body, spinQuery).
                addProperty(SPIN.constraint, param1). // ldt:Parameter rdfs:subClassOf spl:Argument
                addProperty(RDFS.isDefinedBy, ontology);

        template = ontology.getOntModel().createIndividual("http://test/ontology/template", LDT.Template).
                addLiteral(LDT.match, "{path}").
                addProperty(LDT.query, ontology.getOntModel().createIndividual(spinTemplate).
                        addLiteral(predicate1, QUERY_BINDING)).
                addProperty(LDT.param, param1).
                addProperty(LDT.param, param2).
                addProperty(LDT.param, param3).
                addProperty(RDFS.isDefinedBy, ontology).
                as(Template.class);

        resource = ModelFactory.createDefaultModel().createResource("http://resource/");
        call = new TemplateCallImpl(resource, template);
    }

    public Resource getResource(){
        return resource;
    }

    public TemplateCall getCall(){
        return call;
    }

    public Template getTemplate(){
        return template;
    }

    public String getPredicateLocalName(String _predicate){
        switch (_predicate) {
            case "1":
                return PREDICATE1_LOCAL_NAME;
            case "2":
                return PREDICATE2_LOCAL_NAME;
            case "3":
                return PREDICATE3_LOCAL_NAME;
            case "unused":
                return UNUSED_PREDICATE_LOCAL_NAME;
            default:
                return null;
        }
    }

    public Property getProperty(String _predicate){
        switch (_predicate) {
            case "1":
                return predicate1;
            case "2":
                return predicate2;
            case "3":
                return predicate3;
            case "unused":
                return unusedPredicate;
            default:
                return null;
        }
    }

    public Parameter getParameter(String _parameter){
        switch (_parameter) {
            case "1":
                return param1;
            case "2":
                return param2;
            case "3":
                return param3;
            default:
                return null;
        }
    }

    public Literal getQueryBinding(){
        return QUERY_BINDING;
    }
}
