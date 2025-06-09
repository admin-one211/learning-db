package com.one211.learning.db;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TableTest  {

@Test
    public void testFilter() {
    Row row1 = Row.apply("abc", 22);
    Row row2  = Row.apply("xyz", 15);
    Row row3  = Row.apply("zxc", 22);
    var table = new Table.ListBackedTable(List.of(row1, row2,row3));

    var ageExpression = new Expression.BoundedExpression(1);
    var literal = new Expression.Literal(22);
    var age = new Filter.EqualFilter(ageExpression, literal);

    var filteredTable = table.filter(age);

    List<Row> resultRows = new ArrayList<>();
    for (Row row : filteredTable) {
        resultRows.add(row);
    }

    assertEquals(2, resultRows.size());

}
@Test
    public void testProject() {
        Row row1 = Row.apply("abc", 22,"ngp");
        Row row2  = Row.apply("xyz", 15,"cwa");
        Row row3  = Row.apply("zxc", 22,"del");
        var table = new Table.ListBackedTable(List.of(row1, row2,row3));
        var Index0 = new Expression.BoundedExpression(0);

        var Index1 = new Expression.BoundedExpression(1);

        var resultData = table.project(Index0,Index1);
        int count = 0;

        for (Row r: resultData)
        {
            count ++;
        }
        assertEquals(3, count);

        Row row0 = ((Table.ListBackedTable) resultData).rows.get(0);
        assertEquals("abc", row0.get(0));
        assertEquals(22, row0.get(1));

        Row p1 = ((Table.ListBackedTable) resultData).rows.get(1);
        assertEquals("xyz", p1.get(0));
        assertEquals(15, p1.get(1));
    }
@Test
    public void testJoin() {
    var t1 = Row.apply("ash",12);
    var t2 = Row.apply(898978045,"Chhindwara");
        var newTable = t1.join(t2);

        assertEquals(4, newTable.length());
    var t3 = Row.apply("email@gmail.com");
    var newTable2 = newTable.join(t3);
        assertEquals(5, newTable2.length());
    var age = new Expression.BoundedExpression(4);
    assertEquals("email@gmail.com",age.apply(newTable2));


    }
}