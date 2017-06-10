package com.code405.entity.enumeration;

/**
 * Created by hajrullinbulat on 14.05.17.
 */
public enum PassType {
    ЭКЗАМЕН("Экзамен"), ЗАЧЕТ("Зачет"), ДИФ_ЗАЧЕТ("Диф. зачет");

    private final String name;

    PassType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
