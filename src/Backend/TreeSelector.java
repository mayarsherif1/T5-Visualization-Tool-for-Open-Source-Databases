package Backend;

import Backend.KDTree.KDTree;

public class TreeSelector {
    public static GenericTree createTree(String type){
        switch (type){
            case "BPlusTree":
                //return new BPlusTree();
            case "KDTree":
                return new KDTree();
            default:
                return null;
        }
    }
}
