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

        blocks = mergedBlocks;
    }
}
