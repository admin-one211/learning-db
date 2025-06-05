package com.one211.learning.db;

public interface Row {
    Object get(int index);

    public static Row apply(Object... row) {
        return new DefaultRow(row);
    }

    record DefaultRow(Object... obj) implements Row {
        @Override
        public Object get(int index) {
            return obj[index];
        }
    }
}
