package com.one211.learning.db;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SimpleCsvReader implements Iterable<Row> {
    private final BufferedReader reader;
    private final String delimiter = ",";

    public SimpleCsvReader(String path) throws IOException {
        this.reader = Files.newBufferedReader(Paths.get(path));
    }

    @Override
    public Iterator<Row> iterator() {
        return new Iterator<Row>() {
            String nextLine = advance();

            private String advance() {
                try {
                    return reader.readLine();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public boolean hasNext() {
                if (nextLine == null) {
                    closeQuietly();
                    return false;
                }
                return true;
            }

            @Override
            public Row next() {
                if (!hasNext()) throw new NoSuchElementException();
                String[] values = nextLine.split(delimiter, -1);
                nextLine = advance();
                return Row.apply((Object[]) values);
            }

            private void closeQuietly() {
                try { reader.close(); } catch (IOException ignored) {}
            }
        };
    }
}
