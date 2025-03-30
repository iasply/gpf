package br.com.gpf.view.screen;

import br.com.gpf.service.DataEnum;

public enum FilterColumnEnum {

    CLASSIFICATION ("Classificacao", DataEnum.FILTER_CLASSIFICATION),
    VALUE ("Valor",DataEnum.FILTER_VALUE),
    TYPE ("Tipo",DataEnum.FILTER_TYPE),
    DESCRIPTION ("Descricao",DataEnum.FILTER_DESCRIPTION),
    DATE ("Data",DataEnum.FILTER_DATE);


    private final String name;
    private final DataEnum dataEnum;

    FilterColumnEnum(String name, DataEnum dataEnum) {
        this.name = name;
        this.dataEnum = dataEnum;
    }

    public String getName() {
        return name;
    }

    public DataEnum getDataEnum() {
        return dataEnum;
    }

    public static FilterColumnEnum toEnum(String str) {
        for (FilterColumnEnum value : FilterColumnEnum.values()) {
            if (value.getName().equalsIgnoreCase(str)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Constante de enum desconhecida: " + str);
    }
}
