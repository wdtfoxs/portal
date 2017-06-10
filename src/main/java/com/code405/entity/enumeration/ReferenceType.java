package com.code405.entity.enumeration;

/**
 * Created by hajrullinbulat on 14.05.17.
 */
public enum ReferenceType {
    TO_WORK{
        @Override
        public String toString() {
            return "Для работы";
        }
    }, ABOUT_STUDING{
        @Override
        public String toString() {
            return "Об обучении";
        }
    }
}
