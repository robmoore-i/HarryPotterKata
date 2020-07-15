package cart;

import java.util.List;
import java.util.Objects;

public class CartSize {
    private final int size;

    public CartSize(int size) {
        this.size = size;
    }

    public Price withCentsPerItem(int centsPerItem) {
        return new Price(size * centsPerItem);
    }

    public <T> T indexInto(List<T> list) {
        return list.get(size);
    }

    public boolean atLeastAsBigAs(CartSize cartSize) {
        return this.size >= cartSize.size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartSize that = (CartSize) o;
        return size == that.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size);
    }
}
