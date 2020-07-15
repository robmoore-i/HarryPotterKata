import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class BookPricerTest {

    private final BookPricer bookPricer = new BookPricer();

    @Test
    public void canPriceOneBook() {
        assertEquals(800, bookPricer.price(Book.one));
    }

    @Test
    public void canPriceTwoIdenticalBooks() {
        assertEquals(1600, bookPricer.price(Book.one, Book.one));
    }

    @Test
    public void canPriceTwoDifferentBooks() {
        assertEquals(1520, bookPricer.price(Book.one, Book.two));
    }

    @Test
    public void canPriceThreeDifferentBooks() {
        assertEquals(2160, bookPricer.price(Book.one, Book.two, Book.three));
    }

    @Test
    public void canPriceFourDifferentBooks() {
        assertEquals(2560, bookPricer.price(Book.one, Book.two, Book.three, Book.four));
    }

    @Test
    public void canPriceFiveDifferentBooks() {
        assertEquals(3000, bookPricer.price(Book.one, Book.two, Book.three, Book.four, Book.five));
    }

    @Test
    public void canPriceTwoDifferentBooksPlusOneDuplicate() {
        assertEquals(2320, bookPricer.price(Book.one, Book.two, Book.two));
    }

    @Test
    public void canPriceThreeDifferentBooksPlusOneDuplicate() {
        assertEquals(2960, bookPricer.price(Book.one, Book.two, Book.two, Book.three));
    }

    @Test
    public void canPriceThreeDifferentBooksPlusThreeDuplicates() {
        assertEquals(4320, bookPricer.price(Book.one, Book.one, Book.two, Book.two, Book.three, Book.three));
    }

    @Test
    public void canPriceFiveDifferentBooksPlusThreeDuplicates() {
        assertEquals(5120, bookPricer.price(Book.one, Book.one, Book.two, Book.two, Book.three, Book.three, Book.four, Book.five));
    }

    private static class Book {
        public static final Book one = new Book(BookNumber.ONE);
        public static final Book two = new Book(BookNumber.TWO);
        public static final Book three = new Book(BookNumber.THREE);
        public static final Book four = new Book(BookNumber.FOUR);
        public static final Book five = new Book(BookNumber.FIVE);

        public static final int individualBookPrice = 800;

        private final BookNumber bookNumber;

        public Book(BookNumber bookNumber) {
            this.bookNumber = bookNumber;
        }

        public BookNumber title() {
            return bookNumber;
        }
    }

    private static class BookPricer {
        public int price(Book... books) {
            List<Book> booksForPricing = Arrays.stream(books).collect(Collectors.toList());
            int withMaxClusterSizeFive = clusterPricing(clone(booksForPricing), 5);
            int withMaxClusterSizeFour = clusterPricing(clone(booksForPricing), 4);
            return Math.min(withMaxClusterSizeFive, withMaxClusterSizeFour);
        }

        /**
         * Prices books by identifying discountable book clusters, pricing them,
         * removing them from the set of books remaining to be priced, and then
         * repeating, until all the books have been priced.
         */
        private int clusterPricing(List<Book> booksForPricing, int maxClusterSize) {
            int price = 0;
            while (!booksForPricing.isEmpty()) {
                Set<BookNumber> distinctTitles = booksForPricing.stream()
                        .map(Book::title)
                        .distinct()
                        .limit(maxClusterSize)
                        .collect(Collectors.toSet());
                double discount = distinctBookDiscountRate(distinctTitles.size());
                price += applyDiscount(Book.individualBookPrice * distinctTitles.size(), discount);
                ArrayList<Book> booksToRemove = new ArrayList<>();
                for (Book book : booksForPricing) {
                    if (distinctTitles.contains(book.title())) {
                        booksToRemove.add(book);
                        distinctTitles.remove(book.title());
                    }
                }
                booksToRemove.forEach(booksForPricing::remove);
            }
            return price;
        }

        private int applyDiscount(int undiscountedPrice, double discount) {
            return undiscountedPrice - (int) (undiscountedPrice * discount);
        }

        private double distinctBookDiscountRate(int distinctBookTitles) {
            if (distinctBookTitles == 2) {
                return 0.05;
            } else if (distinctBookTitles == 3) {
                return 0.1;
            } else if (distinctBookTitles == 4) {
                return 0.2;
            } else if (distinctBookTitles == 5) {
                return 0.25;
            } else {
                return 0;
            }
        }

        private <T> List<T> clone(List<T> list) {
            return new ArrayList<>(list);
        }
    }

    public enum BookNumber {
        ONE, TWO, THREE, FOUR, FIVE
    }
}
