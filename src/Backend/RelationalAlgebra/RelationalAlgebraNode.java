package Backend.RelationalAlgebra;

public abstract class RelationalAlgebraNode {
    abstract void accept(RelationalAlgebraVisitor visitor);
    abstract int estimateResultSize();

}
