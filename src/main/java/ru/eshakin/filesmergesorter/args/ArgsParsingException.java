package ru.eshakin.filesmergesorter.args;

/**
 * Исключение парсера пользовательских аргументов
 */
public class ArgsParsingException extends RuntimeException {

    public ArgsParsingException(String message) {
        super(message);
    }

}
