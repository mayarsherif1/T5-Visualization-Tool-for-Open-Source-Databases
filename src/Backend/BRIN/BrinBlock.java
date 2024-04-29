package Backend.BRIN;

import java.util.*;

public class BrinBlock {
    private int min;
    private int max;
    private List<BrinBlock> children;

    public BrinBlock(int min, int max) {
        this.min = min;
        this.max = max;
        this.children = new ArrayList<>();

    }

    public void insert(int value) {
        System.out.println("Inserting value: " + value);
        if (value < min) min = value;
        if (value > max) max = value;
        boolean merged = false;
        for (BrinBlock child : children) {
            if (child.fits(value) || child.isAdjacent(value)) {
                System.out.println("Merging value " + value + " into child block " + child);
                child.insert(value);
                merged = true;
                break;
            }
        }
        if (!merged) {
            children.add(new BrinBlock(value, value));
            System.out.println("Created new child block for value: " + value);
        }
        mergeChildrenIfNeeded();
    }

    private void mergeChildrenIfNeeded() {
        Collections.sort(children, Comparator.comparingInt(BrinBlock::getMin));
        List<BrinBlock> merged = new ArrayList<>();
        BrinBlock prev = null;
        for (BrinBlock curr : children) {
            if (prev != null && (prev.max + 1 >= curr.min)) {
                System.out.println("Merging blocks " + prev + " and " + curr);
                prev.max = Math.max(prev.max, curr.max);
            } else {
                merged.add(curr);
                prev = curr;
            }
        }
        children = merged;
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
        return this.max + 1 == other.min || this.min - 1 == other.max;
    }

    public void mergeWith(BrinBlock other) {
        this.min = Math.min(this.min, other.min);
        this.max = Math.max(this.max, other.max);
    }

    public List<BrinBlock> getChildren() {
        return children;
    }

    public void setChildren(List<BrinBlock> children) {
        this.children = children;
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
        return min == brinBlock.min && max == brinBlock.max;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }
}
