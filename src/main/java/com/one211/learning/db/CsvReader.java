package com.one211.learning.db;

import java.io.IOException;
import java.util.List;

public interface CsvReader {
    List<Row> readCsv(String filePath) throws IOException;
}
