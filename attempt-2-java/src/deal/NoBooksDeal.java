package deal;

import cart.CartPricer;
import cart.Cart;
import cart.Price;

public class NoBooksDeal implements Deal {
    @Override
    public Price priceDeal(CartPricer cartPricer, Cart cart) {
        return new Price(0);
    }

    @Override
    public boolean doesDealApply(Cart cart) {
        return cart.isEmpty();
    }
}
