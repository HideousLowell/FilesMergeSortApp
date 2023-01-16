package ru.eshakin.filesmergesorter;

import ru.eshakin.filesmergesorter.args.ArgsHandler;
import ru.eshakin.filesmergesorter.args.ArgsParsingException;
import ru.eshakin.filesmergesorter.args.UserArgs;
import ru.eshakin.filesmergesorter.core.FilesMergeSorter;
import ru.eshakin.filesmergesorter.util.StringConverter;
import ru.eshakin.filesmergesorter.util.FileManager;
import ru.eshakin.filesmergesorter.util.DirectionalComparator;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class FilesMergeSorterApp {

    public static void main(String[] args) {
        FilesMergeSorterApp filesMergeSorterApp = new FilesMergeSorterApp();
        filesMergeSorterApp.start(args);
    }

    public void start(String[] args) {
        try {
            ArgsHandler argsHandler = new ArgsHandler();
            UserArgs userArgs = argsHandler.parse(args);
            if (userArgs.getClassType() == Integer.class)
                this.<Integer>merge(userArgs);
            else if (userArgs.getClassType() == String.class)
                this.<String>merge(userArgs);
        } catch (IOException e) {
            System.err.println("Unexpected error, unable to write to the output file");
        } catch (ArgsParsingException e) {
            System.err.println(e.getMessage());
        }
    }

    private <T extends Comparable<T>> void merge(UserArgs userArgs) throws IOException {
        FileManager fileManager = new FileManager();
        List<File> inputFiles = fileManager.safeOpenResourceFiles(userArgs.getInputFiles());
        File outputFile = fileManager.createFile(userArgs.getOutputFile());
        StringConverter<T> stringConverter = new StringConverter<>(userArgs.getClassType());
        Comparator<T> comparator = new DirectionalComparator<>(userArgs.getSortingMode());
        FilesMergeSorter<T> filesMergeSorter = new FilesMergeSorter<>(comparator, stringConverter);
        filesMergeSorter.merge(inputFiles,outputFile);
    }

}