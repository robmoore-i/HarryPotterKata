package cart;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartPricerTest {

    private final CartPricer cartPricer = new CartPricer();
    private final List<HarryPotterBook> allHarryPotterBooks = Arrays.asList(
            HarryPotterBook.I,
            HarryPotterBook.II,
            HarryPotterBook.III,
            HarryPotterBook.IV,
            HarryPotterBook.V);

    @Test
    void emptyCartCostsNothing() {
        assertEquals(new Price(0), cartPricer.priceOf(new Cart(new ArrayList<>())));
    }

    @Test
    public void pricesASetOfUniqueBooks() {
        assertEquals(new Price(800), priceUnique(1));
        assertEquals(new Price(1520), priceUnique(2));
        assertEquals(new Price(2160), priceUnique(3));
        assertEquals(new Price(2560), priceUnique(4));
        assertEquals(new Price(3000), priceUnique(5));
    }

    @Test
    void doesntGiveDiscountForRepeatedBooks() {
        assertEquals(new Price(800), priceRepeated(1));
        assertEquals(new Price(1600), priceRepeated(2));
        assertEquals(new Price(2400), priceRepeated(3));
        assertEquals(new Price(3200), priceRepeated(4));
        assertEquals(new Price(4000), priceRepeated(5));
    }

    @Test
    void pricesCartWithTwoUniqueBooks() {
        assertEquals(new Price(2320), priceCartWithTwoUniqueBooks(1, 1));
        assertEquals(new Price(3040), priceCartWithTwoUniqueBooks(2, 0));
        assertEquals(new Price(4640), priceCartWithTwoUniqueBooks(2, 2));
    }

    @Test
    void pricesCartWithThreeUniqueBooks() {
        assertEquals(new Price(2960), cartPricer.priceOf(new Cart(Arrays.asList(
                HarryPotterBook.I, HarryPotterBook.II, HarryPotterBook.III,
                HarryPotterBook.I
        ))));

        assertEquals(new Price(3680), cartPricer.priceOf(new Cart(Arrays.asList(
                HarryPotterBook.I, HarryPotterBook.II, HarryPotterBook.III,
                HarryPotterBook.I, HarryPotterBook.II
        ))));

        assertEquals(new Price(4320), cartPricer.priceOf(new Cart(Arrays.asList(
                HarryPotterBook.I, HarryPotterBook.II, HarryPotterBook.III,
                HarryPotterBook.I, HarryPotterBook.II, HarryPotterBook.III
        ))));
    }

    @Test
    void pricesCartWithFourUniqueBooks() {
        assertEquals(new Price(3360), cartPricer.priceOf(new Cart(Arrays.asList(
                HarryPotterBook.I, HarryPotterBook.II, HarryPotterBook.III, HarryPotterBook.IV,
                HarryPotterBook.I
        ))));

        assertEquals(new Price(4080), cartPricer.priceOf(new Cart(Arrays.asList(
                HarryPotterBook.I, HarryPotterBook.II, HarryPotterBook.III, HarryPotterBook.IV,
                HarryPotterBook.I, HarryPotterBook.II
        ))));

        assertEquals(new Price(4720), cartPricer.priceOf(new Cart(Arrays.asList(
                HarryPotterBook.I, HarryPotterBook.II, HarryPotterBook.III, HarryPotterBook.IV,
                HarryPotterBook.I, HarryPotterBook.II, HarryPotterBook.III
        ))));

        assertEquals(new Price(6240), cartPricer.priceOf(new Cart(Arrays.asList(
                HarryPotterBook.I, HarryPotterBook.II, HarryPotterBook.III, HarryPotterBook.IV,
                HarryPotterBook.I, HarryPotterBook.II, HarryPotterBook.III,
                HarryPotterBook.I, HarryPotterBook.II
        ))));
    }

    @Test
    void pricesCartWithFiveUniqueBooks() {
        assertEquals(new Price(5120), cartPricer.priceOf(new Cart(Arrays.asList(
                HarryPotterBook.I, HarryPotterBook.II, HarryPotterBook.III, HarryPotterBook.IV, HarryPotterBook.V,
                HarryPotterBook.I, HarryPotterBook.II, HarryPotterBook.III
        ))));
    }

    private Price priceCartWithTwoUniqueBooks(int numberOfUniqueSets, int numberOfRepeatedBooks) {
        ArrayList<HarryPotterBook> harryPotterBooks = new ArrayList<>();
        for (int i = 0; i < numberOfUniqueSets; i++) {
            harryPotterBooks.add(HarryPotterBook.I);
            harryPotterBooks.add(HarryPotterBook.II);
        }
        for (int i = 0; i < numberOfRepeatedBooks; i++) {
            harryPotterBooks.add(HarryPotterBook.II);
        }
        return cartPricer.priceOf(new Cart(harryPotterBooks));
    }

    private Price priceRepeated(int numberOfRepeatedBooks) {
        ArrayList<HarryPotterBook> harryPotterBooks = new ArrayList<>();
        for (int i = 0; i < numberOfRepeatedBooks; i++) {
            harryPotterBooks.add(HarryPotterBook.I);
        }
        return cartPricer.priceOf(new Cart(harryPotterBooks));
    }

    private Price priceUnique(int numberOfUniqueBooks) {
        return cartPricer.priceOf(new Cart(allHarryPotterBooks.subList(0, numberOfUniqueBooks)));
    }
}
