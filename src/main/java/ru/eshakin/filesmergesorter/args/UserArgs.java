package ru.eshakin.filesmergesorter.args;

import lombok.Getter;
import lombok.Setter;
import ru.eshakin.filesmergesorter.util.SortingMode;

import java.io.File;
import java.util.List;

/**
 * Класс, хранящий пользовательские аргументы
 */

@Getter
@Setter
public class UserArgs {

    private List<String> inputFiles;
    private String outputFile;
    private SortingMode sortingMode;
    private Class<? extends Comparable<?>> classType;
}
