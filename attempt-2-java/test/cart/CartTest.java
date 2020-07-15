package cart;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CartTest {
    @Test
    void canRemoveASetOfTwoUniqueBooks() {
        Cart cart = new Cart(Arrays.asList(HarryPotterBook.I, HarryPotterBook.II));

        cart.removeUniqueBookSetOfSize(2);

        assertTrue(cart.isEmpty());
    }

    @Test
    void canRemoveASetOfTwoUniqueBooksWithAnotherBookPresent() {
        Cart cart = new Cart(Arrays.asList(HarryPotterBook.I, HarryPotterBook.I, HarryPotterBook.II));

        cart.removeUniqueBookSetOfSize(2);

        assertEquals(new CartSize(1), cart.size());
        assertEquals(new CartSize(1), cart.count(HarryPotterBook.I));
    }

    @Test
    void canRemoveASetOfOneBookWithOtherBooksPresent() {
        Cart cart = new Cart(Arrays.asList(HarryPotterBook.I, HarryPotterBook.I, HarryPotterBook.II));

        cart.removeUniqueBookSetOfSize(1);

        assertEquals(new CartSize(2), cart.size());
        assertEquals(new CartSize(1), cart.count(HarryPotterBook.I));
        assertEquals(new CartSize(1), cart.count(HarryPotterBook.II));
    }

    @Test
    void canRemoveASetOfFourBooksWithOtherBooksPresent() {
        Cart cart = new Cart(Arrays.asList(
                HarryPotterBook.I, HarryPotterBook.I,
                HarryPotterBook.II, HarryPotterBook.II,
                HarryPotterBook.III, HarryPotterBook.III,
                HarryPotterBook.IV, HarryPotterBook.V
        ));

        cart.removeUniqueBookSetOfSize(4);
        assertEquals(new CartSize(4), cart.size());

        cart.removeUniqueBookSetOfSize(4);
        assertTrue(cart.isEmpty());
    }
}