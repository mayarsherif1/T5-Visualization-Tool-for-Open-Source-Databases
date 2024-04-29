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
        System.out.println("Inserting value " + value + " at level " + (levelIndex + 1));

        if (levelIndex >= levels.size()) {
            levels.add(new BrinLevel());
            System.out.println("Added new level due to overflow");

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
            System.out.println("Creating new block for value " + value);
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

}
