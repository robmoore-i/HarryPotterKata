package deal;

import cart.CartPricer;
import cart.Cart;
import cart.Price;
import cart.CartSize;

public abstract class MixedUniqueBooksDeal implements Deal {
    private final Price priceOfUniqueBookSet;
    private final int applicableToCartWithAtLeastThisManyUniqueBooks;

    public MixedUniqueBooksDeal(Price priceOfUniqueBookSet, int applicableToCartWithAtLeastThisManyUniqueBooks) {
        this.priceOfUniqueBookSet = priceOfUniqueBookSet;
        this.applicableToCartWithAtLeastThisManyUniqueBooks = applicableToCartWithAtLeastThisManyUniqueBooks;
    }

    @Override
    public Price priceDeal(CartPricer cartPricer, Cart cart) {
        cart.removeUniqueBookSetOfSize(applicableToCartWithAtLeastThisManyUniqueBooks);
        return cartPricer.priceOf(cart).plus(priceOfUniqueBookSet);
    }

    @Override
    public boolean doesDealApply(Cart cart) {
        CartSize distinctBooks = cart.numberOfDistinctBooks();
        return distinctBooks.atLeastAsBigAs(new CartSize(applicableToCartWithAtLeastThisManyUniqueBooks));
    }
}
