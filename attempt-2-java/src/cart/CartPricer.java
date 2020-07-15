package cart;

import deal.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CartPricer {

    private final List<Deal> deals = Arrays.asList(
            new NoBooksDeal(),
            new AllUniqueBooksDeal(),
            new DuplicateBooksDeal(),
            new TwoUniqueBooksDeal(),
            new ThreeUniqueBooksDeal(),
            new FourUniqueBooksDeal(),
            new FiveUniqueBooksDeal()
    );

    public Price priceOf(Cart cart) {
        Stream<Deal> availableDeals = deals.stream();
        Stream<Deal> applicableDeals = availableDeals.filter(deal -> deal.doesDealApply(cart));
        Stream<Price> dealPrices = applicableDeals.map(deal -> deal.priceDeal(this, cart.copy()));
        Optional<Price> maybeMinPrice = dealPrices.min(Price::compare);
        if (maybeMinPrice.isPresent()) {
            return maybeMinPrice.get();
        }
        throw new RuntimeException("No applicable deal for the cart: " + cart.toString());
    }
}
