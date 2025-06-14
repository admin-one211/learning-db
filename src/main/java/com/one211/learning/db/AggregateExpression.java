package com.one211.learning.db;

public interface AggregateExpression extends Expression {
    AggregateExpression fresh();
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
        public AggregateExpression fresh() {
            return new Min(this.expression);  // ✅ returns new instance without shared state
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
        public AggregateExpression fresh() {
            return new Max(this.expression);  // ✅ returns new instance without shared state
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

        @Override
        public AggregateExpression fresh() {
            return new Sum(this.expression);  // ✅ returns new instance without shared state
        }
    }

    final class Count implements AggregateExpression {
        private final Expression expression; // can be unused or used for filtering
        private int count = 0;

        public Count(Expression expression) {
            this.expression = expression;
        }

        @Override
        public Object apply(Row row) {
            count++;
            return count;
        }

        @Override
        public Object finalValue() {
            return count;
        }

        @Override
        public AggregateExpression fresh() {
            return new Count(this.expression);  // ✅ returns new instance without shared state
        }
    }
}
