package com.one211.learning.db;

public interface AggregateExpression extends Expression {
    Object finalValue();

    final class Min implements AggregateExpression {
        private final Expression expression;
        Object state = null;

        public Min(Expression expression) {
            this.expression = expression;
        }

        @Override
        public Object finalValue() {
            return state;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Object apply(Row row) {
            Object current = expression.apply(row);
            if (current instanceof Comparable) {
                if (state == null || ((Comparable<Object>) current).compareTo(state) < 0) {
                    state = current;
                }
            }
            return state;
        }
    }

    final class Max implements AggregateExpression {
        private final Expression expression;
        Object state = null;

        public Max(Expression expression) {
            this.expression = expression;
        }

        @Override
        public Object finalValue() {
            return state;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Object apply(Row row) {
            Object current = expression.apply(row);
            if (current instanceof Comparable) {
                if (state == null || ((Comparable<Object>) current).compareTo(state) > 0) {
                    state = current;
                }
            }
            return state;
        }
    }

    final class Sum implements AggregateExpression {
        private final Expression expression;
        private double sum = 0;

        public Sum(Expression expression) {
            this.expression = expression;
        }

        @Override
        public Object apply(Row row) {
            Object value = expression.apply(row);
            if (value instanceof Number) {
                sum += ((Number) value).doubleValue();
            }
            return value;
        }

        @Override
        public Object finalValue() {
            return sum;
        }
    }

    final class Count implements AggregateExpression {
        int count = 0;

        @Override
        public Object finalValue() {
            return count;
        }

        @Override
        public Object apply(Row row) {
            count++;
            return count;
        }
    }
}
