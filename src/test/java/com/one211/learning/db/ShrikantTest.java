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
}