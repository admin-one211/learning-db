package com.one211.learning.db;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.plaf.ListUI;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface Table extends Iterable<Row> {
    Table filter(Filter filter);
    Table project(Expression... projections);
    Table join(Table input);

    Table aggregate(Expression groupBy, AggregateExpression... expression);

    abstract class AbstractTable implements Table {
        public Table filter(Filter filter) {
            List<Row> filtered = new ArrayList<>();
            Iterator<Row> it = this.iterator();

            while (it.hasNext()) {
                Row row = it.next();
                if ((Boolean) filter.apply(row)) {
                    filtered.add(row);
                }
            }

            return new ListBackedTable(filtered);
        }

        public Table project(Expression... projections) {
            List<Row> projected = new ArrayList<>();  // Initialize the list
            Iterator<Row> it = this.iterator();

            while (it.hasNext()) {
                Row row = it.next();
                Object[] projectedVal = new Object[projections.length];
                for (int i = 0; i < projections.length; i++) {
                    projectedVal[i] = projections[i].apply(row);
                }
                projected.add(Row.apply(projectedVal));
            }

            return new ListBackedTable(projected);
        }


        public Table join(Table input) {
            List<Row> result = new ArrayList<>();
            Iterator<Row> outer = this.iterator();

            while (outer.hasNext()) {
                Row thisRow = outer.next();
                Iterator<Row> inner = input.iterator();
                while (inner.hasNext()) {
                    Row thatRow = inner.next();
                    result.add(thisRow.join(thatRow));
                }
            }
            return new ListBackedTable(result);
        }



        public static Table fromJson(String filePath) throws IOException {
            ObjectMapper mapper = new ObjectMapper();

                List<Map<String, Object>> jsonData = mapper.readValue(
                        new File(filePath),
                        new TypeReference<>() {}
                );

                List<Row> rows = new ArrayList<>();
                for (Map<String, Object> record : jsonData) {
                    Object[] values = record.values().toArray();
                    rows.add(Row.apply(values));
                }

                return new ListBackedTable(rows);

        }

        @Override
        public Table aggregate(Expression groupBy, AggregateExpression... expression) {
            return null;
        }
    }





    class ListBackedTable extends AbstractTable {

        List<Row> rows;
        public ListBackedTable(List<Row> rows) {
            this.rows = rows;
        }

        @Override
        public Iterator<Row> iterator() {
            return rows.stream().iterator();
        }

    }
}

