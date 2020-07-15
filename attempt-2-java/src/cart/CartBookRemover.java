package cart;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CartBookRemover {
    /*
    Find the books with the most instances, and remove one instance
    of each of them from the list.
    */
    public void remove(List<HarryPotterBook> books, int setSize) {
        HashMap<HarryPotterBook, Integer> counts = new HashMap<>();
        for (HarryPotterBook book : HarryPotterBook.values()) {
            Stream<HarryPotterBook> bookStream = books.stream();
            Predicate<HarryPotterBook> isThisParticularBook = b -> b.equals(book);
            Stream<HarryPotterBook> booksOfGivenNumber = bookStream.filter(isThisParticularBook);
            long count = booksOfGivenNumber.count();
            counts.put(book, (int) count);
        }
        while (counts.size() > setSize) {
            counts.remove(getKeyForCount(counts, minValue(counts)));
        }
        Collection<HarryPotterBook> harryPotterBooksToRemove = counts.keySet();
        for (HarryPotterBook harryPotterBook : harryPotterBooksToRemove) {
            books.remove(harryPotterBook);
        }
    }

    private int minValue(HashMap<HarryPotterBook, Integer> counts) {
        Collection<Integer> bookCounts = counts.values();
        Stream<Integer> bookCountsStream = bookCounts.stream();
        IntStream countStream = bookCountsStream.mapToInt(Integer::intValue);
        OptionalInt maybeMinimumCount = countStream.min();
        return maybeMinimumCount.orElseThrow();
    }

    private HarryPotterBook getKeyForCount(HashMap<HarryPotterBook, Integer> counts, int count) {
        Set<Map.Entry<HarryPotterBook, Integer>> countMapEntries = counts.entrySet();
        Stream<Map.Entry<HarryPotterBook, Integer>> entryStream = countMapEntries.stream();
        Predicate<Map.Entry<HarryPotterBook, Integer>> hasThisNumberOfOccurrencesInTheCart = entry -> entry.getValue() == count;
        Stream<Map.Entry<HarryPotterBook, Integer>> entriesWithCount = entryStream.filter(hasThisNumberOfOccurrencesInTheCart);
        Optional<Map.Entry<HarryPotterBook, Integer>> maybeFirstEntryWithCount = entriesWithCount.findFirst();
        Map.Entry<HarryPotterBook, Integer> firstEntryWithCount = maybeFirstEntryWithCount.orElseThrow();
        return firstEntryWithCount.getKey();
    }
}
