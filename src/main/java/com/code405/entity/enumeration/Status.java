package com.code405.entity.enumeration;

/**
 * Created by hajrullinbulat on 14.05.17.
 */
public enum Status {
    CREATED {
        @Override
        public String toString() {
            return "Не готова";
        }
    },
    READY {
        @Override
        public String toString() {
            return "Готова";
        }
    }
}
