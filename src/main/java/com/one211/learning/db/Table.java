package com.one211.learning.db;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

public interface Table extends Iterable<Row> {
    Table filter(Filter filter);
    Table project(Expression... projections);
    Table join(Table input);
    Table aggregate(Expression groupBy, AggregateExpression... expression);

    abstract class AbstractTable implements Table {
        public Table filter(Filter filter){
            List<Row> result = new ArrayList<>();

            Iterator<Row> thisIterator = this.iterator();
            while (thisIterator.hasNext()){
                Row currentRow = thisIterator.next();
                if ((Boolean) filter.apply(currentRow)) {
                    result.add(currentRow);
                }
            }

            return new ListBackedTable(result);
        }

        public Table project(Expression... projections){
            List<Row> result = new ArrayList<>();

            Iterator<Row> thisIterator = this.iterator();

            while (thisIterator.hasNext()) {
                Row currentRow = thisIterator.next();
                Object[] val = new Object[projections.length];
                for (int i = 0; i < projections.length; i++)
                {
                    val[i] = projections[i].apply(currentRow);
                }
                result.add(Row.apply(val));
            }

            return new ListBackedTable(result);
        }

        public Table join(Table input) {
            List<Row> result = new ArrayList<Row>();
            Iterator<Row> thisIterator = this.iterator();
            while (thisIterator.hasNext()){
                Row thisRow = thisIterator.next();
                Iterator<Row> thatIterator = input.iterator();
                while (thatIterator.hasNext()){
                    Row thatRow = thatIterator.next();
                    Row res = thisRow.join(thatRow);

                    result.add(res);
                }
            }

            return new ListBackedTable(result);
        }

        @Override
        public Table aggregate(Expression groupBy, AggregateExpression... expression) {
            Map<Object, List<Row>> grouped = new HashMap<Object, List<Row>>();

            Iterator<Row> thisIterator = this.iterator();

            while (thisIterator.hasNext()){
                Row currentRow = thisIterator.next();
                Object key = groupBy.apply(currentRow);
                grouped.computeIfAbsent(key, k -> new ArrayList<>()).add(currentRow);
            }

            List<Row> resultRows = new ArrayList<>();

            for (Map.Entry<Object, List<Row>> entry : grouped.entrySet()) {
                Object groupKey = entry.getKey();
                List<Row> rowsInGroup = entry.getValue();

                // Create fresh instances of each aggregation per group
                AggregateExpression[] aggInstances = new AggregateExpression[expression.length];
                for (int i = 0; i < expression.length; i++) {
                    aggInstances[i] = expression[i].fresh();
                }

                // Apply rows to aggregate expressions
                for (Row r : rowsInGroup) {
                    for (AggregateExpression agg : aggInstances) {
                        agg.apply(r);
                    }
                }

                // Collect results: group key + all aggregate final values
                Object[] result = new Object[aggInstances.length + 1];
                result[0] = groupKey;
                for (int i = 0; i < aggInstances.length; i++) {
                    result[i + 1] = aggInstances[i].finalValue();
                }

                resultRows.add(Row.apply(result));
            }

            return new ListBackedTable(resultRows);
        }
    }

    public class ListBackedTable extends AbstractTable {

        List<Row> rows;
        public ListBackedTable(List<Row> rows) {
            this.rows = rows;
        }

        @Override
        public Iterator<Row> iterator() {
            return rows.stream().iterator();
        }
    }

    public class CsvTable extends AbstractTable {
        private final List<Row> rows;

        public CsvTable(List<Row> rows) {
            this.rows = rows;
        }

        public static CsvTable fromCsv(String path) {
            List<Row> rows = new ArrayList<>();

            try (CSVReader reader = new CSVReaderBuilder(new FileReader(path)).build()) {
                Iterator<String[]> iterator = reader.iterator();

                while (iterator.hasNext()) {
                    String[] csvRow = iterator.next();
                    rows.add(Row.apply((Object[]) csvRow)); // convert to Row
                }

            } catch (IOException e) {
                System.err.println("Error reading CSV: " + e.getMessage());
            }

            return new CsvTable(rows);
        }

        @Override
        public Iterator<Row> iterator() {
            return rows.iterator(); // or rows.stream().iterator()
        }

        public List<Row> getRows() {
            return rows;
        }
    }
}
