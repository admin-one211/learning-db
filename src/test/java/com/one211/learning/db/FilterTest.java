package com.one211.learning.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class FilterTest {
    @Test
    public void notNullTest() {
        var testRow  = Row.apply("one", null, "two");
        var notNull0 = new Filter.NotNullFilter(new Expression.BoundedExpression(0));
        assertTrue((Boolean) notNull0.apply(testRow));

        var notNull1 = new Filter.NotNullFilter(new Expression.BoundedExpression(1));
        assertFalse((Boolean) notNull1.apply(testRow));
    }

    @Test
    public void isEqualTest() {
        var testRow = Row.apply("RAJ", "RAJ");
        var isEqualTest = new Filter.IsEqualTo(
            new Expression.BoundedExpression(0),
            new Expression.BoundedExpression(1)
        );
        assertTrue(isEqualTest.apply(testRow));
    }

    @Test
    public void isNotEqualTest() {
        var testRow = Row.apply("RAJ", "VERMA");
        var isNotEqualTest = new Filter.IsNotEqualsTo(
            new Expression.BoundedExpression(0), 
            new Expression.BoundedExpression(1)
        );
        assertTrue(isNotEqualTest.apply(testRow));
    }

    @Test
    public void isGraterThanTest() {
        var testRow = Row.apply(24, 5);
        var isGraterThanTest = new Filter.IsGraterThan(
            new Expression.BoundedExpression(0), 
            new Expression.BoundedExpression(1)
        );
        assertTrue(isGraterThanTest.apply(testRow));
    }
}
