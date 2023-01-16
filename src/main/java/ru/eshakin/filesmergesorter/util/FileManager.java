package ru.eshakin.filesmergesorter.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
*   Класс, отвечающий за работу с файлами
 */
public class FileManager {

    private final ClassLoader classLoader = FileManager.class.getClassLoader();

    public List<File> safeOpenResourceFiles(List<String> fileNames) {
        List<File> files = new ArrayList<>(fileNames.size());
        for (String fileName : fileNames) {
            URL url = classLoader.getResource(fileName);
            if (url == null) {
                System.err.printf("File \"%s\" not found in resources%n", fileName);
                continue;
            }
            File file = new File(url.getFile());
            if (!file.isFile())
                System.err.printf("File \"%s\" is not a file%n", fileName);
            else
                files.add(file);
        }
        return files;
    }

    public File createFile(String fileName) {
        return new File(fileName);
    }

}
