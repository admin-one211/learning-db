package com.one211.learning.db;

import javax.swing.plaf.ListUI;
import java.util.Iterator;
import java.util.List;

public interface Table extends Iterable<Row> {
    Table filter(Filter filter);
    Table project(Expression... projections);
    Table join(Table input);

    abstract class AbstractTable implements Table {
        public Table filter(Filter filter){
            return this;
        }

        public Table project(Expression... projections){
            return this;
        }

        public Table join(Table input) {
            return this;
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

