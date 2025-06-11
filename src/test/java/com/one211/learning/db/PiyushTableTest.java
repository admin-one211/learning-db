package com.one211.learning.db;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.one211.learning.db.Table.ListBackedTable;

import static org.junit.Assert.*;

public class PiyushTableTest {

    @Test
    public void tableFilterTest() {
        Row row1 = Row.apply("Piyush", 22);
        Row row2  = Row.apply("chota bhai", 15);

        var table = new Table.ListBackedTable(List.of(row1, row2));

        var ageExpression = new Expression.BoundedExpression(1);
        var literal20 = new Expression.Literal(20);
        var ageGreaterThan20 = new Filter.IsGraterThan(ageExpression, literal20);

        var filteredTable = table.filter(ageGreaterThan20);

        List<Row> resultRows = new ArrayList<>();
        for (Row row : filteredTable) {
            resultRows.add(row);
        }

        assertEquals(1, resultRows.size());
    }

    @Test
    public void tableProjectionTest() {
        var row = Row.apply("Piyush", "class", "BCA");
        var row1 = Row.apply("Vijay", "class", "BA");
        var row2 = Row.apply("rohan", "class", "BSC");

        var table = new Table.ListBackedTable(List.of(row, row2, row1));

        var exp = new Expression.BoundedExpression(0);

        var exp2 = new Expression.BoundedExpression(2);

        var resultData = table.project(exp, exp2);

        int count = 0;

        for (Row r: resultData)
        {
            count ++;
        }

        assertEquals(3, count);

        Row row0 = ((ListBackedTable) resultData).rows.get(0);
        assertEquals("Piyush", row0.get(0));
        assertEquals("BCA", row0.get(1));
    }

    @Test
    public void tableIsNullOrNotTest() {
        var secondRow = Row.apply(null);

        var table = new ListBackedTable(List.of(secondRow));

        Row testSecondRow = null;
        for(Row r : table)
        {
            testSecondRow = r;
        }

        assertEquals(secondRow, testSecondRow);
    }

    @Test
    public  void tableRowsTest() {
        var row1 = Row.apply("32", "323");

        var table = new ListBackedTable(List.of(row1));

        int count = 0;

        for (Row r: table)
        {
            count++;
        }

        assertEquals(1, count);
    }

    @Test
    public void rowJoinTest() {
        var tab1 = Row.apply("name", "rahul", "age", "23");
        var tab2 = Row.apply("name", "jay", "age", "32");

        var newTable = tab1.join(tab2);

        assertEquals(8, newTable.length());
    }
}
