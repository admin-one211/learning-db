package com.one211.learning.db;

import  org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void testCsvRowCountAndPrint() throws Exception {
        CsvReader reader = new SimpleCsvReader();
        String filePath = "src/example/csv/test.csv";

        List<Row> rows = reader.readCsv(filePath);

        assertEquals(3, rows.size());

        System.out.println("Rows: " + rows.size());
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
}