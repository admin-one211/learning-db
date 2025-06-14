package com.one211.learning.db;

import  org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class ShrikantTest {
    @Test
    public void testListBackedTable (){
        var testRow1 = Row.apply("one", 1, "one");
        var testRow2 = Row.apply("two", 2, "two");
        var listBackedTable = new Table.ListBackedTable(List.of(testRow1, testRow2));


        // number of rows
        var count = 0;
        for(var row : listBackedTable){
            count++;
        }

        assertEquals( 2, count);
    }

    @Test
    public void testCsvRowContentUsingIterator() throws Exception {
        String filePath = "src/example/csv/test.csv";
        SimpleCsvReader csvReader = new SimpleCsvReader(filePath);
        Iterator<Row> iterator = csvReader.iterator();

        assertTrue(iterator.hasNext());
        Row header = iterator.next();
        assertEquals("ID", header.get(0));
        assertEquals("Name", header.get(1));
        assertEquals("Age", header.get(2));

        assertTrue(iterator.hasNext());
        Row row1 = iterator.next();
        assertEquals("1", row1.get(0));
        assertEquals("Shivam", row1.get(1));
        assertEquals("20", row1.get(2));

        assertTrue(iterator.hasNext());
        Row row2 = iterator.next();
        assertEquals("2", row2.get(0));
        assertEquals("Dev", row2.get(1));
        assertEquals("25", row2.get(2));

        assertTrue(iterator.hasNext());
        Row row3 = iterator.next();
        assertEquals("3", row3.get(0));
        assertEquals("Honey", row3.get(1));
        assertEquals("19", row3.get(2));

        assertFalse(iterator.hasNext());
    }


    @Test
    public void testFilterTable() {

        var row1 = Row.apply("Shivam", 20);
        var row2 = Row.apply("Dev", 17);
        var row3 = Row.apply("Manu", 23);

        var table = new Table.ListBackedTable(List.of(row1, row2, row3));

        var ageExpression = new Expression.BoundedExpression(1);
        var literal18 = new Expression.Literal(18);
        var ageGreaterThan18 = new Filter.IsGraterThan(ageExpression, literal18);

        var filteredTable = table.filter(ageGreaterThan18);

        List<Row> resultRows = new ArrayList<>();
        for (Row row : filteredTable) {
            resultRows.add(row);
        }
        assertEquals(2, resultRows.size());
//        System.out.println("Row: " + resultRows.size());
    }

    @Test
    public void tableProjectTest(){
        var row1 = Row.apply("Shivam", 20);
        var row2 = Row.apply("Dev", 17);
        var row3 = Row.apply("Manu", 23);

        var table = new Table.ListBackedTable(List.of(row1, row2, row3));

        Expression nameProjection = new Expression.BoundedExpression(0);

        Table projected = table.project(nameProjection);
        int count = 0;
        for (Row r: projected)
        {
//            System.out.println("Row: " + count + " " + r.get(0));
            count ++;

        }
        assertEquals(3, count);

        List<Row> resultRows = ((Table.ListBackedTable) projected).rows;

        assertEquals("Shivam", resultRows.get(0).get(0));
        assertEquals("Dev", resultRows.get(1).get(0));
        assertEquals("Manu", resultRows.get(2).get(0));
    }


    @Test
    public void rowJoinTest(){
        var tab1 = Row.apply("name", "rahul", "age", "23");
        var tab2 = Row.apply("name", "jay", "age", "32");

        var newTable = tab1.join(tab2);

        assertEquals(8, newTable.length());
    }


    @Test
    public void testMin() {
        Table table = new Table.ListBackedTable(List.of(
                Row.apply("A", 5),
                Row.apply("A", 3),
                Row.apply("A", 9),
                Row.apply("B", 8),
                Row.apply("B", 2)
        ));

        Table result = table.aggregate(
                new Expression.BoundedExpression(0),
                new AggregateExpression.Min(new Expression.BoundedExpression(1))
        );

        List<Row> resultRows = ((Table.ListBackedTable) result).rows;

        assertEquals("A", resultRows.get(0).get(0));
        assertEquals(3, resultRows.get(0).get(1));
        assertEquals("B", resultRows.get(1).get(0));
        assertEquals(2, resultRows.get(1).get(1));
    }

    @Test
    public void testMax() {
        Table table = new Table.ListBackedTable(List.of(
                Row.apply("A", 1),
                Row.apply("A", 7),
                Row.apply("A", 5),
                Row.apply("B", 3),
                Row.apply("B", 9)
        ));

        Table result = table.aggregate(
                new Expression.BoundedExpression(0),
                new AggregateExpression.Max(new Expression.BoundedExpression(1))
        );

        List<Row> resultRows = ((Table.ListBackedTable) result).rows;

        assertEquals("A", resultRows.get(0).get(0));
        assertEquals(7, resultRows.get(0).get(1));
        assertEquals("B", resultRows.get(1).get(0));
        assertEquals(9, resultRows.get(1).get(1));
    }

    @Test
    public void testSum() {
        Table table = new Table.ListBackedTable(List.of(
                Row.apply("X", 10),
                Row.apply("X", 20),
                Row.apply("X", 30),
                Row.apply("Y", 5),
                Row.apply("Y", 15)
        ));

        Table result = table.aggregate(
                new Expression.BoundedExpression(0),
                new AggregateExpression.Sum(new Expression.BoundedExpression(1))
        );

        List<Row> resultRows = ((Table.ListBackedTable) result).rows;

        assertEquals("X", resultRows.get(0).get(0));
        assertEquals(60, resultRows.get(0).get(1));
        assertEquals("Y", resultRows.get(1).get(0));
        assertEquals(20, resultRows.get(1).get(1));
    }


    @Test
    public void testCount() {
        Table table = new Table.ListBackedTable(List.of(
                Row.apply("A", 1),
                Row.apply("A", 2),
                Row.apply("A", 3),
                Row.apply("B", 4),
                Row.apply("B", 5)
        ));

        Table result = table.aggregate(
                new Expression.BoundedExpression(0),
                new AggregateExpression.Count(new Expression.BoundedExpression(1))
        );

        List<Row> resultRows = ((Table.ListBackedTable) result).rows;

        assertEquals("A", resultRows.get(0).get(0));
        assertEquals(3, resultRows.get(0).get(1));
        assertEquals("B", resultRows.get(1).get(0));
        assertEquals(2, resultRows.get(1).get(1));
    }

}