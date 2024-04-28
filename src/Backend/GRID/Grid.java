package Backend.GRID;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private int rows;
    private int columns;
    private List<List<int[]>> grid;
    private List<List<List<Integer>>> buckets;

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.grid = new ArrayList<>(rows);
        this.buckets = new ArrayList<>();
        initialize();
        initializeBuckets(rows, columns);
    }

    public void initialize() {
        for (int i = 0; i < rows; i++) {
            List<int[]> row = new ArrayList<>(columns);
            for (int j = 0; j < columns; j++) {
                row.add(new int[]{0,0});
            }
            grid.add(row);
        }
    }

    public void initializeBuckets(int numRows, int numCols) {
        for (int i = 0; i < numRows; i++) {
            List<List<Integer>> bucketRow = new ArrayList<>();
            for (int j = 0; j < numCols; j++) {
                bucketRow.add(new ArrayList<>());
                System.out.println("Bucket row: " + bucketRow);
            }
            buckets.add(bucketRow);
        }
    }

//    public void updateCell(int row, int column, int value) {
//        if (isValidCell(row, column)) {
//            grid.get(row).set(column, value);
//        } else {
//            System.out.println("Invalid cell coordinates.");
//        }
//    }

    public int[] getCellValue(int row, int column) {
        if (isValidCell(row, column)) {
            return grid.get(row).get(column);
        } else {
            System.out.println("Invalid cell coordinates.");
            return new int[]{-1,-1};
        }
    }

    public void addToBucket(int row, int col, int value) {
        if (isValidCell(row, col)) {
            buckets.get(row).get(col).add(value);
            System.out.println("Value added to bucket: " + value);
        } else {
            System.out.println("Invalid bucket coordinates.");
        }
    }

    public List<Integer> getBucket(int row, int col) {
        if (isValidCell(row, col)) {
            return buckets.get(row).get(col);
        } else {
            System.out.println("Invalid bucket coordinates.");
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
        return rows * columns;
    }
    public int getBucketValue(int row, int col) {
        if (isValidCell(row, col)) {
            int sum = 0;
            List<Integer> bucket = buckets.get(row).get(col);
            for (Integer value : bucket) {
                sum += value;
            }
            return sum;
        } else {
            System.out.println("Invalid bucket coordinates.");
            return -1;
        }
    }


    public void setCellRange(int row, int col, int[] range) {
        if (row >= 0 && row < rows && col >= 0 && col < columns) {
            grid.get(row).set(col, range);
        }
    }

    public int[] getCellRange(int row, int col) {
        return grid.get(row).get(col);
    }

}