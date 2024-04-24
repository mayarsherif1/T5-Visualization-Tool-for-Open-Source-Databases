package Backend.Bitmap;

public class BitmapIndex {
    private final int[] bits;
    private final int size;

    public BitmapIndex(int maxRow) {
        this.size = maxRow;
        this.bits = new int[(this.size + 31) / 32];
    }


    public void set(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        System.out.println("Setting bit at index: " + index);
        bits[index / 32] |= (1 << (index % 32));
    }

    public void clear(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        System.out.println("Clearing bit at index: " + index);
        bits[index / 32] &= ~(1 << (index % 32));
    }

    public boolean isSet(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return (bits[index / 32] & (1 << (index % 32))) != 0;
    }

    public BitmapIndex and(BitmapIndex other) {
        if (this.size != other.size) throw new IllegalArgumentException("Bitmap sizes must be the same.");
        BitmapIndex result = new BitmapIndex(size);
        for (int i = 0; i < bits.length; i++) {
            result.bits[i] = this.bits[i] & other.bits[i];
        }
        return result;
    }

    public BitmapIndex or(BitmapIndex other) {
        if (this.size != other.size) throw new IllegalArgumentException("Bitmap sizes must be the same.");
        BitmapIndex result = new BitmapIndex(size);
        for (int i = 0; i < bits.length; i++) {
            result.bits[i] = this.bits[i] | other.bits[i];
        }
        return result;
    }

    public BitmapIndex not() {
        BitmapIndex result = new BitmapIndex(size);
        int fullInts = size / 32;
        for (int i = 0; i < fullInts; i++) {
            result.bits[i] = ~this.bits[i];
        }
        int remainingBits = size % 32;
        if (remainingBits != 0) {
            int mask = (1 << remainingBits) - 1;
            result.bits[fullInts] = (~this.bits[fullInts]) & mask;
        }
        return result;
    }


    public void setRange(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) throw new IndexOutOfBoundsException();
        for (int i = fromIndex; i <= toIndex; i++) {
            set(i);
        }
    }

    public void clearRange(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) throw new IndexOutOfBoundsException();
        for (int i = fromIndex; i <= toIndex; i++) {
            clear(i);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            sb.append(this.isSet(i) ? '1' : '0');
        }
        return sb.toString();
    }

    public int size() {
        return size;
    }
    public void visualizeValues() {
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            if (isSet(i)) {
                sb.append('1');
            } else {
                sb.append('0');
            }
            if ((i + 1) % 10 == 0) {
                sb.append('\n');
            }
        }
        System.out.println("Bitmap Visualization:");
        System.out.println(sb.toString());
    }
}
