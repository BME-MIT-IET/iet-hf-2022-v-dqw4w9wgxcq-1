package cucumber.com.atomgraph.processor.util.TemplateMatcher;

import com.atomgraph.processor.model.Template;
import com.atomgraph.processor.model.impl.TemplateImpl;
import com.atomgraph.processor.util.TemplateMatcher;
import com.atomgraph.processor.vocabulary.LDT;
import com.atomgraph.server.util.OntologyLoader;
import org.apache.jena.enhanced.BuiltinPersonalities;
import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.Ontology;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.sys.JenaSystem;
import org.apache.jena.vocabulary.RDFS;

import java.util.HashMap;
import java.util.Map;

public class TemplateAndMatcherSource {
    private static TemplateAndMatcherSource instance;
    private final Map<String, Template> templateMap;
    private TemplateMatcher matcher;

    private TemplateAndMatcherSource() {
        this.templateMap = new HashMap<>();
        initFields();
    }

    public static TemplateAndMatcherSource getInstance() {
        if(instance == null) {
            instance = new TemplateAndMatcherSource();
        }
        return instance;
    }
    private void initFields() {
        JenaSystem.init();
        BuiltinPersonalities.model.add(Template.class, TemplateImpl.factory);

        Ontology ontology = ModelFactory.createOntologyModel().createOntology("http://test/ontology");
        Ontology importedOntology = ontology.getOntModel().createOntology("http://test/ontology/import");
        ontology.addImport(importedOntology);
        Ontology importedImportedOntology = ontology.getOntModel().createOntology("http://test/ontology/import/import");
        importedOntology.addImport(importedImportedOntology);

        templateMap.put("whateverTemp", importedImportedOntology.getOntModel().createIndividual("http://test/ontology/import/import/template", LDT.Template).
                addLiteral(LDT.match, "{path}").
                addProperty(RDFS.isDefinedBy, importedImportedOntology).
                as(Template.class));

        templateMap.put("oneTwoThreeTemp", importedOntology.getOntModel().createIndividual("http://test/ontology/import/template1", LDT.Template).
                addLiteral(LDT.match, "{less}/{specific}/{path}").
                addProperty(RDFS.isDefinedBy, importedOntology).
                as(Template.class));

        templateMap.put("oneTwoTemp", importedOntology.getOntModel().createIndividual("http://test/ontology/import/template2", LDT.Template).
                addLiteral(LDT.match, "{other}/{path}").
                addProperty(RDFS.isDefinedBy, importedOntology).
                as(Template.class));

        templateMap.put("moreSpecificSomethingTemp", ontology.getOntModel().createIndividual("http://test/ontology/template1", LDT.Template).
                addLiteral(LDT.match, "more/specific/{path}").
                addProperty(RDFS.isDefinedBy, ontology).
                as(Template.class));

        templateMap.put("otherSomethingTemp", ontology.getOntModel().createIndividual("http://test/ontology/template3", LDT.Template).
                addLiteral(LDT.match, "other/{path}").
                addLiteral(LDT.priority, 1).
                addProperty(RDFS.isDefinedBy, ontology).
                as(Template.class));

        templateMap.put("null", null);


        OntDocumentManager.getInstance().addModel(ontology.getURI(), ontology.getOntModel());
        ontology = new OntologyLoader( OntDocumentManager.getInstance(), ontology.getURI(), ontology.getOntModel().getSpecification(), true).getOntology();

        matcher = new TemplateMatcher(ontology);
    }

    public Template getTemplate(String templateName) {
        return templateMap.get(templateName);
    }

    public TemplateMatcher getMatcher() {
        return matcher;
    }
}
