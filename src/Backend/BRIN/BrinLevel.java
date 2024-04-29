package Backend.BRIN;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BrinLevel {
    private List<BrinBlock> blocks;

    public BrinLevel() {
        this.blocks = new ArrayList<>();
    }

    public List<BrinBlock> getBlocks() {
        return blocks;
    }

    public void addBlock(BrinBlock block) {
        this.blocks.add(block);
        mergeAdjacentBlocks();
    }

    private void mergeAdjacentBlocks() {
        System.out.println("Merging blocks. Initial count: " + blocks.size());
        blocks.sort(Comparator.comparingInt(BrinBlock::getMin));
        List<BrinBlock> mergedBlocks = new ArrayList<>();
        BrinBlock currentBlock = null;

        for (BrinBlock block : blocks) {
            if (currentBlock == null || !currentBlock.canMergeWith(block)) {
                currentBlock = block;
                mergedBlocks.add(currentBlock);
                System.out.println("Adding new block to merged list: [" + currentBlock.getMin() + ", " + currentBlock.getMax() + "]");
            } else {
                currentBlock.mergeWith(block);
            }
        }
        System.out.println("Merged blocks count: " + mergedBlocks.size());
        blocks = mergedBlocks;
    }
}
