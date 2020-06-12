# Harry Potter Kata

Not a great effort, but at least I finished the tiresome last test case, which
also nuked the design I was going for up until then.

## What changes would this solution adapt easily to?

- Changes in the individual book price, including if different books had
  different individual prices.
- An increase in the number of books.
- Being stricter about the size of discountable book clusters, such as if the
  business said that customers need to have a collection of at least three books
  to be eligible for a discount, or they need to be buying at least 5 books.
- Changes in the way the discount rate is calculated and applied.

  
## What could be better about this solution?

- The contents of the BookPricer::price method probably warrants some
  explanation. The term 'cluster' has been introduced to the domain without
  really being explained in this method. You could be certainly forgiven for
  being initially confused.
- The method `clusterPricing` has a javadoc comment, principally because without
  the comment, I think the method would be pretty difficult to understand. It's
  probably difficult to understand even with the comment.
- Big if-else block to determine the discount rate from the number of distinct
  books in the collection.
- Having a private method to clone arrays is a bit random.

## Code

```java
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
```

## Instructions

Taken from: https://github.com/xurxodev/HarryPotter-Kata

### HarryPotter-Kata
HarryPotter kata for practice TDD implemented in Java by Jorge Sánchez (Xurxodev) 

### Problem Description

To try and encourage more sales of the 5 different Harry
Potter books they sell, a bookshop has decided to offer
discounts of multiple-book purchases.

One copy of any of the five books costs 8 EUR.

If, however, you buy two different books, you get a 5%
discount on those two books.

If you buy 3 different books, you get a 10% discount.

If you buy 4 different books, you get a 20% discount.

If you go the whole hog, and buy all 5, you get a huge 25%
discount.

Note that if you buy, say, four books, of which 3 are
different titles, you get a 10% discount on the 3 that
form part of a set, but the fourth book still costs 8 EUR.

Your mission is to write a piece of code to calculate the
price of any conceivable shopping basket (containing only
Harry Potter books), giving as big a discount as possible.

For example, how much does this basket of books cost?

- 2 copies of the first book
- 2 copies of the second book
- 2 copies of the third book
- 1 copy of the fourth book
- 1 copy of the fifth book

Answer: 51.20 EUR

|  I  |  II  | III |  IV  |  V  |          Formula          |  Price  |
|-----|------|-----|------|-----|---------------------------|---------|
|  1  |      |     |      |     |     1 * 8                 |  8.00   |
|  1  |  1   |     |      |     |     2 * 8 * 0.95          |  15.20  |
|  1  |  1   |  1  |      |     |     3 * 8 * 0.9           |  21.60  |
|  1  |  1   |  1  |  1   |     |     4 * 8 * 0.8           |  25.60  |
|  1  |  1   |  1  |  1   |  1  |     5 * 8 * 0.75          |  30.00  |
|  2  |      |     |      |     |     2 * 8                 |  16.00  |
|  2  |  1   |     |      |     |     2 * 8 * 0.95 + 1 * 8  |  23.20  |
|  2  |  1   |  1  |      |     |     3 * 8 * 0.90 + 1 * 8  |  29.60  |
|  2  |  2   |  2  |  1   |  1  | 4 * 8 * 0.8 + 4 * 8 * 0.8 |  51.20  |

### Developed By

* Jorge Sánchez Fernández aka [xurxodev](https://twitter.com/xurxodev)

### License


    Copyright 2016 Jorge Sánchez Fernández

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.