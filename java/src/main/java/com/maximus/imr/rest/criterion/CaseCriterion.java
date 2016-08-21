package com.maximus.imr.rest.criterion;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tstockton on 8/4/16.
 */
public class CaseCriterion {
    private String claimNumber;
    private String caseNumber;
    private Integer start = 0;
    private Integer limit = 30;
    private List<SORT> sortProperties = new ArrayList<>();
    private List<STATUS> statusValues = new ArrayList<>();
    private static Gson gson = new Gson();

    public CaseCriterion() {

    }

    public CaseCriterion setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
        return this;
    }

    public CaseCriterion setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
        return this;
    }

    public CaseCriterion setStart(Integer start) {
        this.start = start;
        return this;
    }

    public CaseCriterion setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public CaseCriterion addSortProperties(SORT... sortProperty) {
        for (SORT property : sortProperty) {
            if (!sortProperties.contains(property)) {
                sortProperties.add(property);
            }
        }
        return this;
    }

    public CaseCriterion clearSortProperties() {
        sortProperties.clear();
        return this;
    }

    public CaseCriterion addStatus(STATUS... status) {
        for (STATUS aStatus : status) {
            if (!statusValues.contains(aStatus.value)) {
                statusValues.add(aStatus);
            }
        }
        return this;
    }

    public CaseCriterion clearStatus() {
        statusValues.clear();
        return this;
    }

    public Map<String, Object> toJsonMap() {
        return gson.fromJson(toJsonString(), Map.class);

    }

    public String toJsonString() {
        return "{" +
                "\n\"start\":" + start +
                ",\n\"limit\":" + limit +
                ",\n\"sort\":[\n" + getSortProperties() + "],\n" +
                "\"statuses\":[\n" + getStatusProperties() + "],\n" +
                getCAIDJSon() + ",\n" +
                getIMRIDJson() + "\n}";
    }

    private String getCAIDJSon() {
        String caid = claimNumber == null ? "" : claimNumber.trim();
        return "\"CAID\": \"" + caid + "\"";
    }

    private String getIMRIDJson() {
        String imrId = caseNumber == null ? "" : caseNumber.trim();
        return "\"IMRID\": \"" + imrId + "\"";

    }

    private String getSortProperties() {
        if (sortProperties.isEmpty()) {
            return "";
        }
        else {
            StringBuilder sb = new StringBuilder();
            for (SORT property : sortProperties) {
                sb.append("{").append("\"property\": \"").append(property.toString()).append("\",\"desc\": true },\n");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            return sb.toString();
        }
    }

    private String getStatusProperties() {
        if (statusValues.isEmpty()) {
            return "";
        }
        else {
            StringBuilder sb = new StringBuilder();
            for (STATUS status : statusValues) {
                sb.append("\"").append(status.value).append("\",\n");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            return sb.toString();
        }
    }

    public static enum STATUS {
        ONE("1"), TWO("2"), THREE("3");
        private String value;

        private STATUS(String value) {
            this.value = value;
        }
    }

    ;

    public static enum SORT {
        claimNumber, caseNumber, dateOfInjury, priority, injuredWorkerPrefix, injuredWorkerFirstName, injuredWorkerLastName,
        injuredWorkerMiddleInitial, injuredWorkrSuffix, employerName, treatmentRequested, claimsAdministratorAddress1,
        claimsAdministratorAddress2, claimsAdministratorCity, claimsAdministratorState, claimsAdministratorZipCode,
        claimsAdministratorCompanyName, providerPrefix, providerFirtName, providerLastName, providerMidleInitial, providerSuffix,
        organizationName, status, extStatus, terminationReason, closedReason, assignDate, dateofURDecision, receiveDate, modificationDate
    }
}
