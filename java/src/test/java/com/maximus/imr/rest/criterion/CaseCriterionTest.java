package com.maximus.imr.rest.criterion;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

import static com.maximus.imr.rest.criterion.CaseCriterion.STATUS;
import static com.maximus.imr.rest.criterion.CaseCriterion.SORT;

/**
 * Created by tstockton on 8/4/16.
 */
public class CaseCriterionTest {
    @Test
    public void testCriterionOne() {
        CaseCriterion caseCriterion = new CaseCriterion().setCaseNumber("CM15-11234343").setClaimNumber("CA-12221-223-001").
                addSortProperties(SORT.assignDate, SORT.dateOfInjury).
                addStatus(STATUS.ONE, STATUS.TWO);
        String criterion = caseCriterion.toJsonString();
        System.out.println(criterion);
        assertTrue(criterion.startsWith("{"));
        assertTrue(criterion.endsWith("}"));
        Map map = caseCriterion.toJsonMap();
        assertNotNull(map);
        Double limit = (Double) map.get("limit");
        assertTrue(limit.equals(new Double(30)));
    }

    @Test
    public void testCriterionTwo() {
        CaseCriterion caseCriterion = new CaseCriterion();
        System.out.println(caseCriterion.toJsonString());
        String criterion = caseCriterion.toJsonString();
        System.out.println(criterion);
        assertTrue(criterion.startsWith("{"));
        assertTrue(criterion.endsWith("}"));
        Map map = caseCriterion.toJsonMap();
        assertNotNull(map);
        Double limit = (Double) map.get("limit");
        assertTrue(limit.equals(new Double(30)));

    }
}
