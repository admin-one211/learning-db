package com.one211.learning.db;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExpressionTest {

    @Test
    public void testBoundedExpression() {
        var testRow = Row.apply("one", 1, "two");
        var boundedExpression = new Expression.BoundedExpression(0);
        assertEquals( "one", boundedExpression.apply(testRow));
    }
}
