package com.one211.learning.db;

import java.util.*;
import java.util.stream.IntStream;

import org.junit.Ignore;
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

    @Test
    public void aggrigateSumTest() {
        Row r1 = Row.apply("A", 10);
        Row r2 = Row.apply("A", 15);
        Row r3 = Row.apply("B", 5);
        Row r4 = Row.apply("C", 30);
        Row r5 = Row.apply("B", 15);

        Table table = new ListBackedTable(List.of(r1, r2, r3, r4, r5));

        var groupBy = new Expression.BoundedExpression(0);

        var sum = new AggregateExpression.Sum(new Expression.BoundedExpression(1));

        Table grouped = table.aggregate(groupBy, sum);

        List<Row> resultRows = new ArrayList<>();
        for (Row r : grouped) {
            resultRows.add(r);
        }

        assertEquals(3, resultRows.size());

        assertEquals("A", resultRows.get(0).get(0));
        assertEquals(25.0, resultRows.get(0).get(1));

        assertEquals("B", resultRows.get(1).get(0));
        assertEquals(20.0, resultRows.get(1).get(1));

        assertEquals("C", resultRows.get(2).get(0));
        assertEquals(30.0, resultRows.get(2).get(1));
    }

    @Test
    public void aggrigateCountTest() {
        Row r1 = Row.apply("A", 10);
        Row r2 = Row.apply("B", 5);
        Row r3 = Row.apply("C", 30);
        Row r4 = Row.apply("D", 15);

        Table table = new ListBackedTable(List.of(r1, r2, r3, r4));

        var groupBy = new Expression.BoundedExpression(0);

        var count = new AggregateExpression.Count(new Expression.BoundedExpression(1));

        Table grouped = table.aggregate(groupBy, count);

        List<Row> resultRows = new ArrayList<>();
        for (Row r : grouped) {
            resultRows.add(r);
        }

        assertEquals(4, resultRows.size());
    }

    @Test
    public void aggregateMaxTest() {
        Row row1 = Row.apply(1, 50);
        Row row2 = Row.apply(1, 20);
        Row row3 = Row.apply(2, 80);
        Row row4 = Row.apply(2, 30);

        Table table = new ListBackedTable(List.of(row1, row2, row3, row4));

        Expression groupBy = new Expression.BoundedExpression(0);
        AggregateExpression maxExpr = new AggregateExpression.Max(new Expression.BoundedExpression(1));

        Table result = table.aggregate(groupBy, maxExpr);

        List<Row> rows = new ArrayList<>();
        result.iterator().forEachRemaining(rows::add);

        // Verify max per group
        assertEquals(50, rows.get(0).get(1)); // Group 1 -> max(50, 20)
        assertEquals(80, rows.get(1).get(1)); // Group 2 -> max(80, 30)
    }


    @Test
    public void aggregateMinTest() {
        Row row1 = Row.apply(1, 50);
        Row row2 = Row.apply(1, 20);
        Row row3 = Row.apply(2, 80);
        Row row4 = Row.apply(2, 30);

        Table table = new ListBackedTable(List.of(row1, row2, row3, row4));

        Expression groupBy = new Expression.BoundedExpression(0);
        AggregateExpression minExpr = new AggregateExpression.Min(new Expression.BoundedExpression(1));

        Table result = table.aggregate(groupBy, minExpr);

        List<Row> rows = new ArrayList<>();
        result.iterator().forEachRemaining(rows::add);

        // Verify min per group
        assertEquals(20, rows.get(0).get(1)); // Group 1 -> min(50, 20)
        assertEquals(30, rows.get(1).get(1)); // Group 2 -> min(80, 30)
    }

    @Test
    public void csvTableTest() {
        var csvTable = Table.CsvTable.fromCsv("src/examples/csv/piyushCsvFile.csv");

        List<Row> rows = csvTable.getRows();

        assertEquals(3, rows.size());

        assertEquals("FirstName", rows.get(0).get(0));
        assertEquals("LastName", rows.get(0).get(1));
        assertEquals("age", rows.get(0).get(2));

        assertEquals("Aarush", rows.get(1).get(0));
        assertEquals("Jain", rows.get(1).get(1));
        assertEquals("22", rows.get(1).get(2));

        assertEquals("Aayush", rows.get(2).get(0));
        assertEquals("Rai", rows.get(2).get(1));
        assertEquals("27", rows.get(2).get(2));
    }
}
