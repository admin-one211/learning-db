package com.one211.learning.db;

public class Person {
    public String name;
    public int age;

    // Required for Jackson
    public Person() {}

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters & Setters (optional for Jackson if fields are public)
    public String getName() { return name; }
    public int getAge() { return age; }
}
