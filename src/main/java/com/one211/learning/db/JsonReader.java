package com.one211.learning.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public interface JsonReader {

    record JsonFileReader() implements JsonReader {
        @Override
        public List<Person> readJson(String filePath) {

                ObjectMapper mapper = new ObjectMapper();
                File file = new File(filePath);
            Person[] people = null;
            try {
                people = mapper.readValue(file, Person[].class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return Arrays.asList(people);

        }
    }

    List<Person> readJson(String filePath);
}
