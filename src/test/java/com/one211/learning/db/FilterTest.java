package com.one211.learning.db;

import org.junit.Test;

import static org.junit.Assert.*;

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
    public void orTest() {
        var testRow  = Row.apply("one", null, "two");

        var notNull0 = new Filter.NotNullFilter(new Expression.BoundedExpression(0));
        var notNull1 = new Filter.NotNullFilter(new Expression.BoundedExpression(1));

        var orFilter = new Filter.Or(notNull0, notNull1);
        assertTrue(orFilter.apply(testRow));
    }

    @Test
    public void equalsTest(){
        var testRow = Row.apply("abc", "abc");

        var left = new Expression.BoundedExpression(0);
        var right = new Expression.BoundedExpression(1);

        var equalsFilter = new Filter.EqualsFilter(left, right);

        assertTrue(equalsFilter.apply(testRow));
    }

    @Test
    public void notEqualsTest(){
        var testRow = Row.apply("abc", "abc");

        var left = new Expression.BoundedExpression(0);
        var right = new Expression.BoundedExpression(1);

        var notEquals = new Filter.NotEqualsFilter(left, right);

        assertFalse(notEquals.apply(testRow));
    }
}
