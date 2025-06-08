package com.one211.learning.db;

import javax.swing.*;
import javax.swing.plaf.ListUI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface Table extends Iterable<Row> {
    Table filter(Filter filter);
    Table project(Expression... projections);
    Table join(Table input);

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

