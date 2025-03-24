package br.com.gpf.view.screen;

import br.com.gpf.service.DataEnum;

public enum FilterColumnEnum {

    CLASSIFICATION ("Classification", DataEnum.FILTER_CLASSIFICATION),
    VALUE ("Value",DataEnum.FILTER_VALUE),
    TYPE ("Type",DataEnum.FILTER_TYPE),
    DESCRIPTION ("Transaction Description",DataEnum.FILTER_DESCRIPTION),
    DATE ("Date",DataEnum.FILTER_DATE);


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
            if (value.name().equalsIgnoreCase(str)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown enum constant: " + str);
    }
}
