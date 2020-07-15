package deal;

import cart.CartPricer;
import cart.Cart;
import cart.Price;
import cart.CartSize;

public class DuplicateBooksDeal implements Deal {
    @Override
    public boolean doesDealApply(Cart cart) {
        return cart.onlyDuplicates();
    }

    @Override
    public Price priceDeal(CartPricer cartPricer, Cart cart) {
        CartSize cartSize = cart.size();
        return cartSize.withCentsPerItem(800);
    }
}
