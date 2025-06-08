package com.one211.learning.db;

public interface Row {
    Object get(int index);
    Row join(Row other);
    int length();

    public static Row apply(Object... row) {
        return new DefaultRow(row);
    }

    record DefaultRow(Object... obj) implements Row {
        @Override
        public Object get(int index) {
            return obj[index];
        }

        @Override
        public int length() {
            return obj.length;
        }

        @Override
        public Row join(Row other) {
            Object[] combined = new Object[this.length() + other.length()];
            for (int i = 0; i < this.length(); i++) {
                combined[i] = this.get(i);
            }
            for (int j = 0; j < other.length(); j++) {
                combined[this.length() + j] = other.get(j);
            }
            return new DefaultRow(combined);
        }
    }
}