package com.maximus.imr.rest.criterion;

import org.junit.Test;

import java.util.Map;

import static com.maximus.imr.rest.criterion.RFICriterion.SORT;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by tstockton on 8/4/16.
 */
public class RFICriterionTest {
    @Test
    public void testJsonOne() {
        RFICriterion rfiCriterion = new RFICriterion().
                addSortProperties(SORT.assignDate, SORT.caseNumber);
        String json = rfiCriterion.toJsonString();
        System.out.println(json);
        assertTrue(json.startsWith("{"));
        assertTrue(json.endsWith("}"));
        Map map = rfiCriterion.toJsonMap();
        assertNotNull(map);
        Double limit = (Double) map.get("limit");
        assertTrue(limit.equals(new Double(30)));
    }

    @Test
    public void testJsonTwo() {
        RFICriterion rFICriterion = new RFICriterion();
        String json = rFICriterion.toJsonString();
        System.out.println(json);
        assertTrue(json.startsWith("{"));
        assertTrue(json.endsWith("}"));
        Map map = rFICriterion.toJsonMap();
        assertNotNull(map);
        Double limit = (Double) map.get("limit");
        assertTrue(limit.equals(new Double(30)));

    }
}
