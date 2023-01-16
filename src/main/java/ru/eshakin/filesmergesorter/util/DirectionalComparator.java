package ru.eshakin.filesmergesorter.util;

import lombok.RequiredArgsConstructor;

import java.util.Comparator;

/**
 * Компаратор, осуществляющий сравнение в соответствии с режимом сортировки
 */

@RequiredArgsConstructor
public class DirectionalComparator<T extends Comparable<T>> implements Comparator<T> {

    private final SortingMode sortingMode;

    @Override
    public int compare(T value, T comparable) {
        if (sortingMode == SortingMode.DESCENDING) {
            return value.compareTo(comparable) * -1;
        } else if (sortingMode == SortingMode.ASCENDING) {
            return value.compareTo(comparable);
        }
        throw new RuntimeException("Not implemented yet");
    }
}