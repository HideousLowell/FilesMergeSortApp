package ru.eshakin.filesmergesorter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

class FilesMergeSorterAppTest {

    public static final String OUTPUT_FILE = "test_output.txt";
    private final FilesMergeSorterApp app = new FilesMergeSorterApp();

    @Test
    void testStartWithIntegerAscending() {
        app.start(new String[]{"-i", "-a", OUTPUT_FILE, "in1.txt", "in2.txt", "in3.txt"});
        compareOutputWithExpected("int_asc.txt");
    }

    @Test
    void testStartWithIntegerDescending() {
        app.start(new String[]{"-i", "-d", OUTPUT_FILE, "in1.txt", "in2.txt", "in3.txt"});
        compareOutputWithExpected("int_desc.txt");
    }

    @Test
    void testStartWithStringAscending() {
        app.start(new String[]{"-s", "-a", OUTPUT_FILE, "in1.txt", "in2.txt", "in3.txt"});
        compareOutputWithExpected("str_asc.txt");
    }

    @Test
    void testStartWithStringDescending() {
        app.start(new String[]{"-s", "-d", OUTPUT_FILE, "in1.txt", "in2.txt", "in3.txt"});
        compareOutputWithExpected("str_desc.txt");
    }

    private void compareOutputWithExpected(String expectedFileName) {
        List<String> testResult = readOutputFileThanDelete(OUTPUT_FILE);
        List<String> expectedResult = readResourceFile(expectedFileName);
        Assertions.assertEquals(expectedResult, testResult);
    }

    private List<String> readResourceFile(String fileName) {
        try {
            URL url = FilesMergeSorterAppTest.class.getClassLoader().getResource(fileName);
            Assertions.assertNotNull(url);
            File file = new File(url.getFile());
            Path filePath = Paths.get(file.getPath());
            return Files.readAllLines(filePath, StandardCharsets.UTF_8);
        } catch (IOException ignore) {
            fail("Resource file can not be opened");
        }
        return Collections.emptyList();
    }

    private List<String> readOutputFileThanDelete(String fileName) {
        try {
            Path filePath = Paths.get(fileName);
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            Files.delete(filePath);
            return lines;
        } catch (IOException ignore) {
            fail("Output file can not be opened");
        }
        return Collections.emptyList();
    }

}