package com.one211.learning.db;

import  org.junit.Test;

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
    public void testFilterTable(){

        var testRow1 = Row.apply("Shivam", 20);
        var testRow2 = Row.apply("Dev", 17);
        var testRow3 = Row.apply("Rohan", 19);

        var filteredTable = new Table.ListBackedTable(List.of(testRow1, testRow3, testRow3));

        Filter ageGreaterThan18 = row -> (Integer) row.get(1) > 18;



        /*
        List<Row> testRow = List.of(
                Row.apply("Shivam", 20),
                Row.apply("Dev", 25),
                Row.apply("Honey", 19)
        );

         Table table = new Table.ListBackedTable(testRow);

         */


    }
}