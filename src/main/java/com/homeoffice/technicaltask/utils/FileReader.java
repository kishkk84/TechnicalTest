package com.homeoffice.technicaltask.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class FileReader {

    public static final String SRC_TEST_RESOURCES = "src/test/resources";

    @SneakyThrows
    public static List<String> readInputFiles() {
        return readFileContents(Paths.get(SRC_TEST_RESOURCES, "inputfiles"))
                .flatMap(FileReader::extractRegistrationNumbers)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public static List<String> readOutputFiles() {
        return readFileContents(Paths.get(SRC_TEST_RESOURCES, "outputfiles"))
                .distinct()
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private static Stream<String> readFileContents(Path path) {
        return Files.walk(path)
                .filter(Files::isRegularFile)
                .flatMap(FileReader::readLines);
    }

    private static Stream<String> readLines(Path file) {
        try {
            return Files.lines(file);
        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }

    private static Stream<String> extractRegistrationNumbers(String content) {
        String regex = "\\b[A-Z]{1,2}\\d{1,2}\\s?[A-Z]{3}\\b";
        Matcher matcher = Pattern.compile(regex).matcher(content);
        List<String> registrationNumbers = new ArrayList<>();

        while (matcher.find()) {
            String registrationNumber = matcher.group().replaceAll("\\s", "");
            registrationNumbers.add(registrationNumber);
        }

        return registrationNumbers.stream();
    }
}