package com.one211.learning.db;

import java.util.*;

public interface Table extends Iterable<Row> {
    Table filter(Filter filter);
    Table project(Expression... projections);
    Table join(Table input);

    abstract class AbstractTable implements Table {
        public Table filter(Filter filter){
            List<Row> result = new ArrayList<>();

            for (Row r : this) {
                if ((Boolean) filter.apply(r)) {
                    result.add(r);
                }
            }

            return new ListBackedTable(result);
        }

        public Table project(Expression... projections){
            List<Row> result = new ArrayList<>();

            for (Row r: this)
            {
                Object[] val = new Object[projections.length];
                for (int i = 0; i < projections.length; i++)
                {
                    val[i] = projections[i].apply(r);
                }
                result.add(Row.apply(val));
            }

            return new ListBackedTable(result);
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
}
