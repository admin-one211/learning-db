package com.one211.learning.db;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.one211.learning.db.Table.ListBackedTable;

public class PiyushTableTest {
    @Test
    public void TableTest() {
        var firstRow = Row.apply("12", "21", "11");

        var secondRow = Row.apply(null);

        var table = new ListBackedTable(List.of(firstRow, secondRow));

        // test number of rows
        int count = 0;
        Row testFirstRow = null;
        Row testSecondRow = null;
        for(Row r : table) {
            if(count == 0){
                testFirstRow = r;
            } 
            if(count == 1 ) {
                testSecondRow = r;
            }
            count ++;
        }
        assertEquals(2, count);

        // test first Row
        assertEquals(firstRow, testFirstRow);
       
        // test Second Row 
        assertEquals(secondRow, testSecondRow);
    }
}
