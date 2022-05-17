package com.atomgraph.processor.util;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.apache.jena.graph.Node;
import org.apache.jena.reasoner.TriplePattern;
import org.apache.jena.reasoner.rulesys.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RulePrinterTest {
    private Rule mockedRule;
    private TriplePattern mockedTriplePattern; /* TriplePattern inherits from ClauseEntry */
    private Node mockedNode;

    @BeforeEach
    public void setUp() {
        mockedRule = mock(Rule.class);
        mockedTriplePattern = mock(TriplePattern.class);
        mockedNode = mock(Node.class);

        when(mockedRule.getName()).thenReturn("ruleName");
        TriplePattern[] entries = { new TriplePattern(mockedNode, mockedNode, mockedNode) };
        when(mockedRule.getBody()).thenReturn(entries);
        when(mockedRule.getHead()).thenReturn(entries);

        when(mockedTriplePattern.getSubject()).thenReturn(mockedNode);
        when(mockedTriplePattern.getPredicate()).thenReturn(mockedNode);
        when(mockedTriplePattern.getObject()).thenReturn(mockedNode);
        when(mockedNode.isURI()).thenReturn(true);
        when(mockedNode.getURI()).thenReturn("URI");
        when(mockedNode.toString()).thenReturn("URI");
    }

    @Test
    public void testPrint() {
        String printedRule = RulePrinter.print(mockedRule);
        assertEquals(
            "[ ruleName: (<URI> <URI> <URI>) -> (<URI> <URI> <URI>) ]",
            printedRule);
    }
}
