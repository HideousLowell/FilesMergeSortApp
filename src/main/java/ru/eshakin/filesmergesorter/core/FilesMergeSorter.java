package ru.eshakin.filesmergesorter.core;

import lombok.RequiredArgsConstructor;
import ru.eshakin.filesmergesorter.util.StringConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Ядро программы, осуществляющее слияние файлов
 * @param <T> Тип контента сравниваемых файлов
 */

@RequiredArgsConstructor
public class FilesMergeSorter<T extends Comparable<T>> {

    private final Comparator<T> comparator;
    private final StringConverter<T> converter;
    private List<Scanner> scanners;
    private List<T> buffer;

    public void merge(List<File> inputFiles, File outputFile) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(outputFile)) {
            initScanners(inputFiles);
            initBufferWithFirstLines();
            while (!scanners.isEmpty()) {
                int index = 0;
                T value = buffer.get(0);
                for (int i = 1; i < buffer.size(); i++) {
                    if (comparator.compare(value, buffer.get(i)) > 0) {
                        value = buffer.get(i);
                        index = i;
                    }
                }
                printWriter.println(value);
                updateBuffer(index);
            }
        }
    }

    private void updateBuffer(int index) {
        if (scanners.get(index).hasNextLine()) {
            String nextLine = scanners.get(index).nextLine();
            if (nextLine.contains(" ")) {
                updateBuffer(index);
                return;
            }
            Optional<T> value = converter.safeConvert(nextLine);
            if (!value.isPresent())
                updateBuffer(index);
            else if (comparator.compare(value.get(), buffer.get(index)) < 0)
                updateBuffer(index);
            else
                buffer.set(index, value.get());
        } else {
            buffer.remove(index);
            scanners.remove(index);
        }
    }

    private void initScanners(List<File> inputFiles) {
        scanners = inputFiles.stream()
                .map(this::safeOpen)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(Scanner::hasNextLine)
                .collect(Collectors.toList());
    }

    private void initBufferWithFirstLines() {
        buffer = new ArrayList<>(scanners.size());
        for (Scanner current : scanners) {
            boolean isAnyAdded = false;
            while (!isAnyAdded && current.hasNextLine()) {
                String nextLine = current.nextLine();
                if (nextLine.contains(" "))
                    continue;
                Optional<T> value = converter.safeConvert(nextLine);
                if (!value.isPresent())
                    continue;
                isAnyAdded = buffer.add(value.get());
            }
            if (!isAnyAdded) scanners.remove(current);
        }
    }

    private Optional<Scanner> safeOpen(File file) {
        try {
            return Optional.of(new Scanner(file));
        } catch (FileNotFoundException e) {
            System.err.printf("Error: file \"%s\" wasn't found%n", file.getName());
        }
        return Optional.empty();
    }

}
