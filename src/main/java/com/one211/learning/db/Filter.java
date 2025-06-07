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

    record AndFilter(Expression left, Expression right) implements  Filter {

        @Override
        public Boolean apply(Row row) {
            return (Boolean) left.apply(row) && (Boolean)right.apply(row);
        }
    }
    record EqualFilter(Expression left, Expression right) implements Filter {
        @Override
        public Boolean apply(Row row) {
            return left.apply(row).equals(right.apply(row));
        }
    }

    record OrFilter(Expression left, Expression right) implements Filter {
        @Override
        public Boolean apply(Row row) {
            return (Boolean) left.apply(row) || (Boolean) right.apply(row);
        }
    }

}
