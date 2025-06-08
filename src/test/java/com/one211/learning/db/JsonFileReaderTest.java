package com.one211.learning.db;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class JsonFileReaderTest {

    @Test
    public void testReadJsonReturnsCorrectRowCount() {
        String path = "Json_data/data.json";  // Make sure this exists
        JsonReader reader = new JsonReader.JsonFileReader();
        List<Person> people = reader.readJson(path);

        assertFalse(people.isEmpty());
        assertEquals(3, people.size());  // Adjust based on actual data.json
    }
}
