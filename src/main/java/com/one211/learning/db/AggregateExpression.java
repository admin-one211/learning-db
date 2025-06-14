package com.one211.learning.db;

public interface AggregateExpression extends Expression {
    Object finalValue();
    Expression getExpression();

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

        @Override
        public Expression getExpression() {
            return expression;
        }

    }

    public final class Max implements AggregateExpression {
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
        public Object apply(Row row) {
            Object current = expression.apply(row);
            if ( state == null) {
                state = current;
            } else {
                state = Math.max((Integer) current, (Integer) state);
            }
            return state;
        }

        @Override
        public Expression getExpression() {
            return expression;
        }

    }

    public final class Sum implements AggregateExpression {
        private final Expression expression;
        private int sum = 0;

        public Sum(Expression expression) {
            this.expression = expression;
        }

        @Override
        public Object finalValue() {
            return sum;
        }

        @Override
        public Object apply(Row row) {
            Object current = expression.apply(row);
            sum += (Integer) current;
            return sum;
        }

        @Override
        public Expression getExpression() {
            return expression;
        }
    }



    public final class Count implements AggregateExpression {
        private final Expression expression;
        private int count = 0;


        public Count(Expression expression) {
            this.expression = expression;
        }

        @Override
        public Object finalValue() {
            return count;
        }

        @Override
        public Object apply(Row row) {

            if (expression != null) {
                Object value = expression.apply(row);
                if (value != null) {
                    count++;
                }
            } else {
                count++;
            }
            return count;
        }
        @Override
        public Expression getExpression() {
            return expression;
        }


    }

}