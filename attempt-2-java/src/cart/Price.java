package cart;

import java.util.Objects;

public class Price {
    private final int cents;

    public Price(int cents) {
        this.cents = cents;
    }

    public Price plus(Price other) {
        return new Price(this.cents + other.cents);
    }

    public int compare(Price other) {
        return Integer.compare(cents, other.cents);
    }

    @Override
    public String toString() {
        return "cart.Price{" +
                "cents=" + cents +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return cents == price.cents;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cents);
    }
}
