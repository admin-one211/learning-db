package com.one211.learning.db;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FilterTest {


    @Test
    public void notNullTest() {
        var testRow  = Row.apply("one", null, "two");
        var notNull0 = new Filter.NotNullFilter(new Expression.BoundedExpression(0));
        assertTrue((Boolean) notNull0.apply(testRow));

        var notNull1 = new Filter.NotNullFilter(new Expression.BoundedExpression(1));
        assertFalse((Boolean) notNull1.apply(testRow));
    }
}
