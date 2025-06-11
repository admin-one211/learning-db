package com.one211.learning.db;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AggregateExpressionTests {
    @Test
    public void testSumWithIntegers() {
        Expression expr = new Expression.BoundedExpression(0);
        AggregateExpression sum = new AggregateExpression.Sum(expr);

        sum.apply(Row.apply(10));
        sum.apply(Row.apply(20));
        sum.apply(Row.apply(30));

        assertEquals(60.0, sum.finalValue());
    }

    @Test
    public void testCount() {
        AggregateExpression count = new AggregateExpression.Count();

        count.apply(Row.apply("Hii"));
        count.apply(Row.apply("SOO"));
        count.apply(Row.apply("BYY"));

        assertEquals(3, count.finalValue());
    }

    @Test
    public void minCount() {
        AggregateExpression min = new AggregateExpression.Min(new Expression.BoundedExpression(0));

        min.apply(Row.apply(4));
        min.apply(Row.apply(43));
        min.apply(Row.apply(13));

        assertEquals(4, min.finalValue());
    }

    @Test
    public void maxCount() {
        AggregateExpression max = new AggregateExpression.Max(new Expression.BoundedExpression(0));

        max.apply(Row.apply(4));
        max.apply(Row.apply(43));
        max.apply(Row.apply(13));

        assertEquals(43, max.finalValue());
    }
}