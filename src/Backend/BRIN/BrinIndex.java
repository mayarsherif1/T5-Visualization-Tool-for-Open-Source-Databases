package Backend.BRIN;

import java.util.ArrayList;
import java.util.List;

public class BrinIndex {
    //private final List<BrinBlock> blocks;
    private List<BrinLevel>levels;

    public BrinIndex() {
        this.levels = new ArrayList<>();
        this.levels.add(new BrinLevel());
    }

    public void insertValue(int value) {
        insertIntoLevel(value, 0);
    }

    private void insertIntoLevel(int value, int levelIndex) {
        if (levelIndex >= levels.size()) {
            levels.add(new BrinLevel());
        }

        BrinLevel level = levels.get(levelIndex);
        BrinBlock insertedBlock = null;
        for (BrinBlock block : level.getBlocks()) {
            if (block.fits(value) || block.isAdjacent(value)) {
                block.updateRange(value);
                insertedBlock = block;
                break;
            }
        }

        if (insertedBlock == null) {
            level.addBlock(new BrinBlock(value, value));
        } else {
            if (levelIndex + 1 < levels.size()) {
                insertIntoLevel(value, levelIndex + 1);
            }
        }
    }
    public void addBlock(BrinBlock block) {
        if (!levels.isEmpty()) {
            levels.get(0).addBlock(block);
        } else {
            BrinLevel newLevel = new BrinLevel();
            newLevel.addBlock(block);
            levels.add(newLevel);
        }
    }

    public void printIndex() {
        System.out.println("Multi-Level BRIN Index Structure:");
        for (int i = 0; i < levels.size(); i++) {
            System.out.println("Level " + (i + 1) + ":");
            for (BrinBlock block : levels.get(i).getBlocks()) {
                System.out.printf("  Block Range: [%d, %d]%n", block.getMin(), block.getMax());
            }
        }
    }

    public List<BrinLevel> getLevels() {
        return levels;
    }
//
//    public void insertValue(int value) {
//        BrinBlock insertedBlock = null;
//        for (BrinBlock block : blocks) {
//            if (block.fits(value)|| block.isAdjacent(value)) {
//                block.updateRange(value);
//                insertedBlock = block;
//                break;
//            }
//        }
//        if (insertedBlock == null) {
//            blocks.add(new BrinBlock(value, value));
//        }
//        mergeAdjacentBlocks();
//    }
//
//    private void mergeAdjacentBlocks() {
//        blocks.sort(Comparator.comparingInt(BrinBlock::getMin));
//        List<BrinBlock> mergedBlocks = new ArrayList<>();
//        BrinBlock currentBlock = null;
//        for (BrinBlock block : blocks) {
//            if (currentBlock == null || !currentBlock.canMergeWith(block)) {
//                currentBlock = block;
//                mergedBlocks.add(currentBlock);
//            } else {
//                currentBlock.mergeWith(block);
//            }
//        }
//        blocks.clear();
//        blocks.addAll(mergedBlocks);
//
//    }
//
//
//    public List<BrinBlock> getBlocks() {
//        return new ArrayList<>(blocks);
//    }
//    public void printIndex() {
//        System.out.println("BRIN Index Structure:");
//        for (BrinBlock block : blocks) {
//            System.out.printf("Block Range: [%d, %d]%n", block.getMin(), block.getMax());
//        }
//    }
//
//    public void bulkInsert(List<Integer> values) {
//        for (int value : values) {
//            insertValue(value);
//        }
//    }

}
