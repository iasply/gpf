package br.com.gpf.view.screen;

public enum ScreenEnum {
    LOGIN("LOGIN"),
    HOME("HOME"),
    NEW_ACCOUNT("NEW ACCOUNT"),
    ADD_TRANSACTION("ADD TRANSACTION"),
    TRANSACTION_HISTORY("TRANSACTION HISTORY"),
    TRANSACTION_TYPES("TRANSACTION TYPES"),
    REPORTS("REPORTS");


    private final String title;

    ScreenEnum(String s) {
        this.title = s;
    }

    public String getTitle() {
        return title;
    }
}
