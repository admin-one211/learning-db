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
        var row = Row.apply(12, 60);

        var leftVal = new Expression.BoundedExpression(0);
        var rightVal = new Expression.BoundedExpression(1);

        var additionTest = new Expression.BinaryArithmetic(leftVal, rightVal, "+");
        var subtractionTest = new Expression.BinaryArithmetic(leftVal, rightVal, "-");
        var divisionTest = new Expression.BinaryArithmetic(leftVal, rightVal, "/");
        var multiplicationTest = new Expression.BinaryArithmetic(leftVal, rightVal, "*");
        var modulasTest = new Expression.BinaryArithmetic(leftVal, rightVal, "%");

        var result = additionTest.apply(row);
        var result1 = subtractionTest.apply(row);
        var result2 = divisionTest.apply(row);
        var result3 = multiplicationTest.apply(row);
        var result4 = modulasTest.apply(row);

        assertEquals(72, result);
        assertEquals(-48, result1);
        assertEquals(12 / 60, result2);
        assertEquals(720, result3);
        assertEquals(12, result4);
    }
}
