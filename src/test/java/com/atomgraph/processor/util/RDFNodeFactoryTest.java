package com.atomgraph.processor.util;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.jena.vocabulary.XSD;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;

public class RDFNodeFactoryTest {
    @Test
    public void testCreatedType() {
        /* When value should be treated as typed literal */
        String value = "http://whatever";
        Property mockedValueType = mock(Property.class);
        when(mockedValueType.getNameSpace()).thenReturn(XSD.getURI());

        /* function call */
        RDFNode result = RDFNodeFactory.createTyped(value, mockedValueType);

        /* assertion */
        assertEquals(true, result.isLiteral());

        /* When value should be treated as URI resource */
        when(mockedValueType.getNameSpace()).thenReturn("whatever");

        /* function call */
        result = RDFNodeFactory.createTyped(value, mockedValueType);

        /* assertion */
        assertEquals(true, result.isResource());
    }
}
