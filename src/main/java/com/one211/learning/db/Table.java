package com.one211.learning.db;

import java.util.*;

public interface Table extends Iterable<Row> {
    Table filter(Filter filter);
    Table project(Expression... projections);
    Table join(Table input);

    Table aggregate(Expression groupBy, AggregateExpression... expression);

    abstract class AbstractTable implements Table {

       List<Row> rows;
        public Table filter(Filter filter){
            List<Row> filteredRows = new ArrayList<>();
           for(Row row: this){
               if ((Boolean)  filter.apply(row)){
                   filteredRows.add(row);
               }
           }
            return new ListBackedTable(filteredRows);
        }


        public Table project(Expression... projections){
            List<Row> projected = new ArrayList<>();
            for (Row row : this) {
                Object[] values = new Object[projections.length];
                for (int i = 0; i < projections.length; i++) {
                    values[i] = projections[i].apply(row);
                }
                projected.add(Row.apply(values));
            }
            return new ListBackedTable(projected);
        }

        public Table join(Table input) {
            List<Row> result = new ArrayList<>();
            for(Row thisRow : this) {
                for(Row thatRow : input) {
                    Row res = thatRow.join(thatRow);
                    result.add(res);
                }
            }
            return new ListBackedTable(result);
        }

        @Override
        public Table aggregate(Expression groupBy, AggregateExpression... expressions) {

            Map<Object, AggregateExpression[]> groups = new LinkedHashMap<>();

            for (Row row : this) {
                Object key = groupBy.apply(row);

                if (!groups.containsKey(key)) {
                    AggregateExpression[] freshAggregates = new AggregateExpression[expressions.length];
                    for (int i = 0; i < expressions.length; i++) {
                        try {
                            freshAggregates[i] = expressions[i].getClass()
                                    .getConstructor(Expression.class)
                                    .newInstance(((AggregateExpression) expressions[i]).getExpression());
                        } catch (Exception e) {
                            throw new RuntimeException("Error creating aggregate expression: " + e.getMessage(), e);
                        }
                    }
                    groups.put(key, freshAggregates);
                }

                for (AggregateExpression agg : groups.get(key)) {
                    agg.apply(row);
                }
            }

            List<Row> resultRows = new ArrayList<>();
            for (var entry : groups.entrySet()) {
                List<Object> values = new ArrayList<>();
                values.add(entry.getKey());
                for (AggregateExpression agg : entry.getValue()) {
                    values.add(agg.finalValue());
                }
                resultRows.add(Row.apply(values.toArray()));
            }
            return new ListBackedTable(resultRows);
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

