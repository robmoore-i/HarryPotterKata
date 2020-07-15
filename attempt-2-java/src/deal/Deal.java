package deal;

import cart.CartPricer;
import cart.Cart;
import cart.Price;

public interface Deal {
    Price priceDeal(CartPricer cartPricer, Cart cart);

    boolean doesDealApply(Cart cart);
}
