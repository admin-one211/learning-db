package com.one211.learning.db;

import  org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AggregateExpressionTest {

    @Test
    public void testMin() {
        AggregateExpression.Min min = new AggregateExpression.Min(new Expression.BoundedExpression(0));
        List<Row> rows = List.of(
                Row.apply(10),
                Row.apply(5),
                Row.apply(20)
        );
        for (Row row : rows) {
            min.apply(row);
        }
        assertEquals(5, min.finalValue());
    }

    @Test
    public void testMax() {
        AggregateExpression.Max max = new AggregateExpression.Max(new Expression.BoundedExpression(0));
        List<Row> rows = List.of(
                Row.apply(10),
                Row.apply(5),
                Row.apply(20)
        );
        for (Row row : rows) {
            max.apply(row);
        }
        assertEquals(20, max.finalValue());
    }

    @Test
    public void testSum() {
        AggregateExpression.Sum sum = new AggregateExpression.Sum(new Expression.BoundedExpression(0));
        List<Row> rows = List.of(
                Row.apply(10),
                Row.apply(5),
                Row.apply(20)
        );
        for (Row row : rows) {
            sum.apply(row);
        }
        assertEquals(35, sum.finalValue());
    }

    @Test
    public void testCount() {
        AggregateExpression.Count count = new AggregateExpression.Count(new Expression.BoundedExpression(0));
        List<Row> rows = List.of(
                Row.apply(10),
                Row.apply(5),
                Row.apply(20)
        );
        for (Row row : rows) {
            count.apply(row);
        }
        assertEquals(3, count.finalValue());
    }
}
