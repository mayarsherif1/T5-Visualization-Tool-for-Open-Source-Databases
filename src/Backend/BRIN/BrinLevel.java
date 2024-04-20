package Backend.BRIN;

import Backend.BRIN.BrinBlock;

import java.util.ArrayList;
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
    }
}
