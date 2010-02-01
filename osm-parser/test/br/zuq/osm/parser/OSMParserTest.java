/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.zuq.osm.parser;

import br.zuq.osm.parser.model.OSM;
import br.zuq.osm.parser.model.Way;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zuq
 */
public class OSMParserTest {

    public OSMParserTest() {
    }

    /**
     * Test of parse method, of class OSMParser.
     */
    @Test
    public void testParse() throws Exception {
        OSM osm = OSMParser.parse("test/testmap.osm");

        assertEquals(50, osm.getNodes().size());
        assertEquals(7, osm.getWays().size());

        boolean found = false;

        for (Way way : osm.getWays()) {
            if ("R. Quintino Bocaiúva".equals(way.getName())) {
                found = true;
                assertEquals("R1;R2;R3", way.getAltNames());
                assertEquals("residential", way.getType());
            }
        }

        assertTrue(found);
    }

}