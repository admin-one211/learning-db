package com.one211.learning.db;

public interface Filter extends Expression {

     record NotNullFilter(Expression expression) implements Filter {
        @Override
        public Object apply(Row row) {
            return expression.apply(row) != null;
        }
    }

    record NullFiler(Expression expression) implements Filter {
        @Override
        public Object apply(Row row) {
            return expression.apply(row) == null;
        }
    }

    record And(Filter left, Filter right) implements  Filter {

        @Override
        public Boolean apply(Row row) {
            return (Boolean) left.apply(row) && (Boolean)right.apply(row);
        }
    }
}
