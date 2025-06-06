package com.one211.learning.db;

import java.util.Objects;

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
            return (Boolean) left.apply(row) && (Boolean) right.apply(row);
        }
    }

    record Or(Filter left, Filter right) implements Filter {
        @Override
        public Boolean apply(Row row) {
            return (Boolean) left.apply(row) || (Boolean) right.apply(row);
        }
    }

    record IsEqualTo(Expression expression1, Expression expression2) implements Filter {
        @Override
        public Boolean apply(Row row) {
            return Objects.equals(expression1.apply(row), expression2.apply(row));
        }
    }

    record IsNotEqualsTo(Expression expression1, Expression expression2) implements Filter {
        @Override
        public Boolean apply(Row row) {
            return !Objects.equals(expression1.apply(row), expression2.apply(row));
        }
    }

    record IsGraterThan(Expression expression1, Expression expression2) implements Filter {
        @Override
        public Boolean apply(Row row) {
            return (Integer) expression1.apply(row) > (Integer) expression2.apply(row);
        }
    }
}
