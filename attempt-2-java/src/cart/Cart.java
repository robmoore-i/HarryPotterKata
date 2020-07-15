package cart;

import java.util.*;
import java.util.stream.Stream;

public class Cart {
    private final CartBookRemover cartBookRemover = new CartBookRemover();
    private final List<HarryPotterBook> books;

    public Cart(List<HarryPotterBook> books) {
        this.books = new ArrayList<>(books);
    }

    public CartSize size() {
        return new CartSize(books.size());
    }

    public CartSize numberOfDistinctBooks() {
        Stream<HarryPotterBook> harryPotterBookStream = books.stream();
        Stream<HarryPotterBook> distinctHarryPotterBookStream = harryPotterBookStream.distinct();
        return new CartSize((int) distinctHarryPotterBookStream.count());
    }

    public CartSize count(HarryPotterBook harryPotterBook) {
        return new CartSize((int) books.stream().filter(b -> b.equals(harryPotterBook)).count());
    }

    public boolean noDuplicates() {
        return numberOfDistinctBooks().equals(size());
    }

    public boolean onlyDuplicates() {
        return numberOfDistinctBooks().equals(new CartSize(1));
    }

    public boolean isEmpty() {
        return size().equals(new CartSize(0));
    }

    public void removeUniqueBookSetOfSize(int setSize) {
        cartBookRemover.remove(books, setSize);
    }

    public Cart copy() {
        return new Cart(books);
    }
}
