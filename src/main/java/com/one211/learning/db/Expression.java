package com.one211.learning.db;

public interface Expression {
    Object apply(Row row);

    record Add(Expression left, Expression right) implements Expression {
        @Override
        public Object apply(Row row) {
            var leftVal = (Number) left.apply(row);
            var rightVal = (Number) right.apply(row);
            switch (leftVal) {
                case Integer i -> {
                    return i + rightVal.intValue();
                }
                case Long l -> {
                    return l + rightVal.longValue();
                }
                default -> {
                    throw new UnsupportedOperationException("data type :" + leftVal + "not supported");
                }
            }
        }
    }

    record Literal(Object literal) implements Expression {
        @Override
        public Object apply(Row row) {
            return literal;
        }
    }

    record BoundedExpression(int index ) implements Expression {
        @Override
        public Object apply(Row row) {
            return row.get(index);
        }
    }
}
