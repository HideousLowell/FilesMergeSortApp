package ru.eshakin.filesmergesorter.args;

import ru.eshakin.filesmergesorter.util.SortingMode;

import java.util.ArrayList;

/**
 * Класс, осуществляющий парсинг аргументы командной строки
 */
public class ArgsHandler {

    private final UserArgs userArgs = new UserArgs();

    public UserArgs parse(String[] someArgs) throws ArgsParsingException {
        for (String arg : someArgs) {
            if ("-a".equals(arg))
                setSortingMode(SortingMode.ASCENDING);
            else if ("-d".equals(arg))
                setSortingMode(SortingMode.DESCENDING);
            else if ("-i".equals(arg))
                setDataType(Integer.class);
            else if ("-s".equals(arg))
                setDataType(String.class);
            else if (userArgs.getOutputFile() == null)
                setOutputFile(arg);
            else
                addInputFile(arg);
        }
        doubleCheckErrors();
        return userArgs;
    }

    private void doubleCheckErrors() {
        if (userArgs.getOutputFile() == null)
            throw new ArgsParsingException("Error: output file required");
        else if (userArgs.getClassType() == null)
            throw new ArgsParsingException("Error: data type required");
        else if (userArgs.getInputFiles()== null)
            throw new ArgsParsingException("Error: input files required");
        else if (userArgs.getSortingMode() == null)
            setSortingMode(SortingMode.ASCENDING);
    }

    private void setSortingMode(SortingMode sortingMode) {
        if (userArgs.getSortingMode() != null)
            throw new ArgsParsingException("Usage: [ -a or -d ]");
        userArgs.setSortingMode(sortingMode);
    }

    private void setDataType(Class<? extends Comparable<?>> dataType) {
        if (userArgs.getClassType() != null)
            throw new ArgsParsingException("Usage: [ -s or -i ]");
        userArgs.setClassType(dataType);
    }

    private void setOutputFile(String outputFile) {
        userArgs.setOutputFile(outputFile);
    }

    private void addInputFile(String inputFile) {
        if (userArgs.getInputFiles() == null)
            userArgs.setInputFiles(new ArrayList<>());
        userArgs.getInputFiles().add(inputFile);
    }

}
