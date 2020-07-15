package deal;

import cart.CartPricer;
import cart.Cart;
import cart.Price;
import cart.CartSize;

import java.util.Arrays;
import java.util.List;

public class AllUniqueBooksDeal implements Deal {

    private final List<Price> uniqueBookPrices = Arrays.asList(
            new Price(0),
            new Price(800),
            new Price(1520),
            new Price(2160),
            new Price(2560),
            new Price(3000));

    @Override
    public Price priceDeal(CartPricer cartPricer, Cart cart) {
        CartSize cartSize = cart.size();
        return cartSize.indexInto(uniqueBookPrices);
    }

    @Override
    public boolean doesDealApply(Cart cart) {
        return cart.noDuplicates();
    }
}
