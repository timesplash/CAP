package org.dev.api.CAP.enums;

public enum Type {
    GAINS("Gains"),
    LOSSES("Expenses");

    private final String text;
    Type(String text) {
        this.text = text;
    }

    @Override
    public String toString(){
        return text;
    }
}
