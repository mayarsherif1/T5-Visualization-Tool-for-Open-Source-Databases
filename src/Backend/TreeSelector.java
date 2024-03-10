package Backend;

public class TreeSelector {
    public static GenericTree createTree(String type){
        switch (type){
            case "BinaryTree":
                return new BinaryTree();
            case "BPlusTree":
                return new BPlusTree();
            case "KDTree":
                return new KDTree();
            default:
                return null;
        }
    }
}
