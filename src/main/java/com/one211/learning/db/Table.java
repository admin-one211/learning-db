package com.one211.learning.db;

public interface Table extends Iterable<Row> {
    Table filter(Filter filter);
    Table project(Expression... projections);
    Table join(Table input);
}

