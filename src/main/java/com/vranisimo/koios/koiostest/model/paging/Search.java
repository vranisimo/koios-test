package com.vranisimo.koios.koiostest.model.paging;

public class Search {

    public Search() {
    }

    private String value;
    private String regexp;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRegexp() {
        return regexp;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }
}