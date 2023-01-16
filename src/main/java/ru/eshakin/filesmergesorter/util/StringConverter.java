package ru.eshakin.filesmergesorter.util;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * Класс, осуществляющий конвертацию строки к необходимому типу
 */

@RequiredArgsConstructor
public class StringConverter<T extends Comparable<T>> {

    private final Class<? extends Comparable<?>> type;

    @SuppressWarnings({"unchecked"})
    public T convert(String string) {
        if (type == String.class)
            return (T) string;
        if (type == Integer.class)
            return (T) Integer.valueOf(string);
        throw new ConverterException("Not implemented yet");
    }

    public Optional<T> safeConvert(String string) {
        try {
            return Optional.ofNullable(convert(string));
        } catch (ConverterException e) {
            System.err.println(e.getMessage());
        } catch (NumberFormatException e) {
            String message = String.format("Error: unable to convert \"%s\" into %s", string, type.getSimpleName());
            System.err.println(message);
        }
        return Optional.empty();
    }
}
