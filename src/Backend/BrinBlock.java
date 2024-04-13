package Backend;

import java.util.Objects;

public class BrinBlock {
    private int min;
    private int max;
    public BrinBlock(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public boolean fits(int value) {
        return value >= min && value <= max;
    }

    public void updateRange(int value) {
        if (value < this.min) this.min = value;
        if (value > this.max) this.max = value;
    }


    public boolean isAdjacent(int value) {
        return value == min - 1 || value == max + 1;
    }

    public boolean canMergeWith(BrinBlock other) {
        return this.max + 1 >= other.min;
    }

    public void mergeWith(BrinBlock other) {
        this.min = Math.min(this.min, other.min);
        this.max = Math.max(this.max, other.max);
    }

    public int rangeSize() {
        return this.max - this.min + 1;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    @Override
    public String toString() {
        return String.format("[%d, %d]", min, max);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrinBlock brinBlock = (BrinBlock) o;
        return min == brinBlock.min &&
                max == brinBlock.max;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }
}
