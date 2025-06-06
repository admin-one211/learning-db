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

    @Test
    public void testArthamaticalOprator() {
        var row = Row.apply(12, 33);

        var leftVal = new Expression.BoundedExpression(0);
        var rightVal = new Expression.BoundedExpression(1);

        var binaryArithmeticTest = new Expression.BinaryArithmetic(leftVal, rightVal, "+");

        var result = binaryArithmeticTest.apply(row);
        assertEquals(45, result);
    }
}
