package br.com.gpf.view.screen;

public enum ScreenEnum {
    LOGIN("LOGIN"),
    HOME("HOME"),
    NEW_ACCOUNT("NOVA CONTA"),
    ADD_TRANSACTION("ADICIONAR TRANSAÇÃO"),
    TRANSACTION_HISTORY("HISTÓRICO DE TRANSAÇÕES"),
    TRANSACTION_TYPES("TIPOS DE TRANSAÇÕES"),
    REPORTS("RELATÓRIOS");

    private final String title;

    ScreenEnum(String s) {
        this.title = s;
    }

    public String getTitle() {
        return title;
    }
}
