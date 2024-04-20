package com.api.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Map;

public class TestDataReader {
    public static Map<String, Object> readTestDataFromJSON(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), new TypeReference<Map<String, Object>>() {});
    }
    public static String readValueFromJSON(String filePath, String key) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File(filePath));
        return rootNode.get(key).asText();
    }

    // Method to read test data from Excel file
    public static Map<String, Object> readTestDataFromExcel(String filePath) {
        // Implement method to read test data from Excel file using Apache POI or other libraries
    return null;
    }


    // Method to read test data from CSV file
    public static Map<String, Object> readTestDataFromCSV(String filePath) {
        // Implement method to read test data from CSV file using libraries like OpenCSV
    return null;
    }

    // Method to read test data from text file
    public static String readTestDataFromTextFile(String filePath) throws IOException {
        StringBuilder testData = new StringBuilder();
        try (InputStream inputStream = new FileInputStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                testData.append(line).append("\n");
            }
        }
        return testData.toString();
    }
}

