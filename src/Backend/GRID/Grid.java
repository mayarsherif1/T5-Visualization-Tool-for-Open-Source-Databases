package Backend.GRID;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private int rows;
    private int columns;
    private List<List<Integer>> grid;
    private List<List<Integer>> buckets;

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.grid = new ArrayList<>();
        this.buckets = new ArrayList<>();
        initialize();
    }

    public void initialize() {
        for (int i = 0; i < rows; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                row.add(0);
            }
            grid.add(row);
        }
    }

    public void initializeBuckets(int numberOfBuckets) {
        for (int i = 0; i < numberOfBuckets; i++) {
            buckets.add(new ArrayList<>());
        }
    }

    public void updateCell(int row, int column, int value) {
        if (isValidCell(row, column)) {
            grid.get(row).set(column, value);
        } else {
            System.out.println("Invalid cell coordinates.");
        }
    }

    public int getCellValue(int row, int column) {
        if (isValidCell(row, column)) {
            return grid.get(row).get(column);
        } else {
            System.out.println("Invalid cell coordinates.");
            return -1;
        }
    }

    public void addToBucket(int bucketIndex, int value) {
        if (isValidBucket(bucketIndex)) {
            buckets.get(bucketIndex).add(value);
        } else {
            System.out.println("Invalid bucket index.");
        }
    }

    public List<Integer> getBucket(int bucketIndex) {
        if (isValidBucket(bucketIndex)) {
            return buckets.get(bucketIndex);
        } else {
            System.out.println("Invalid bucket index.");
            return new ArrayList<>();
        }
    }
    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    private boolean isValidCell(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    private boolean isValidBucket(int bucketIndex) {
        return bucketIndex >= 0 && bucketIndex < buckets.size();
    }

    public int getBucketsSize() {
        return buckets.size();
    }
}