package com.one211.learning.db;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CsvParserScannerTest {

   @Test
   public void readCsv(){
      String path = "";
      var readCsv = new CsvParserScanner().readCsv(path);

      assertEquals(3, readCsv.size());
   }
}
