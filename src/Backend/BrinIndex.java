package Backend;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BrinIndex {
    private final List<BrinBlock> blocks;

    public BrinIndex() {
        this.blocks = new ArrayList<>();
    }

    public void insertValue(int value) {
        BrinBlock insertedBlock = null;
        for (BrinBlock block : blocks) {
            if (block.fits(value)|| block.isAdjacent(value)) {
                block.updateRange(value);
                insertedBlock = block;
                break;
            }
        }
        if (insertedBlock == null) {
            blocks.add(new BrinBlock(value, value));
        }
        mergeAdjacentBlocks();
    }

    private void mergeAdjacentBlocks() {
        blocks.sort(Comparator.comparingInt(BrinBlock::getMin));
        List<BrinBlock> mergedBlocks = new ArrayList<>();
        BrinBlock currentBlock = null;
        for (BrinBlock block : blocks) {
            if (currentBlock == null || !currentBlock.canMergeWith(block)) {
                currentBlock = block;
                mergedBlocks.add(currentBlock);
            } else {
                currentBlock.mergeWith(block);
            }
        }
        blocks.clear();
        blocks.addAll(mergedBlocks);

    }

    public boolean queryValue(int value) {
        for (BrinBlock block : blocks) {
            if (block.fits(value)) {
                return true;
            } else if (block.getMin()> value) {
                return false;

            }
        }
        return false;
    }

    public List<BrinBlock> getBlocks() {
        return new ArrayList<>(blocks);
    }
    public void printIndex() {
        System.out.println("BRIN Index Structure:");
        for (BrinBlock block : blocks) {
            System.out.printf("Block Range: [%d, %d]%n", block.getMin(), block.getMax());
        }
    }

    public void bulkInsert(List<Integer> values) {
        for (int value : values) {
            insertValue(value);
        }
    }

}
