package com.one211.learning.db;

public interface AggregateExpression extends Expression {
    Object finalValue();

    public final class Min implements AggregateExpression {
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
        public Object apply(Row row) {
            Object current = expression.apply(row);
            if ( state == null) {
                state = current;
            } else {
                state = Math.min((Integer) current, (Integer) state);
            }
            return state;
        }
    }
    public final class Max implements AggregateExpression{
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
        public Object apply(Row row)  {
            Object current = expression.apply(row);
            if ( state == null) {
                state = current;
            } else {
                state = Math.max((Integer) current, (Integer) state);
            }
            return state;
        }
    }
    public  final class Sum implements AggregateExpression{
        public final Expression expression;
        Double sum =0.0;
        public Sum(Expression expression){
            this.expression=expression;
        }
        @Override
        public Object finalValue() {
            return sum;
        }

        @Override
        public Object apply(Row row) {
            Object current = expression.apply(row);
            if (current instanceof Number) {
                sum += ((Number) current).doubleValue();
            }
            return sum;
        }
    }

}
