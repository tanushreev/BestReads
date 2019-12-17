package com.tanushree.bestreads.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JSONData {

    @SerializedName("results")
    @Expose
    private Results results;

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "JSONData{" +
                "results=" + results +
                '}';
    }
}