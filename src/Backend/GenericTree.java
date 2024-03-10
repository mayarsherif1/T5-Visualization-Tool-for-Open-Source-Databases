package Backend;

import java.util.List;

public abstract class GenericTree implements Comparable {
    public abstract void insert(Comparable x);
    public abstract void delete(Comparable x);
    public abstract boolean search(Comparable x);
    public abstract void update(Comparable oldValue, Comparable newValue);
    public abstract List<Comparable> traverse();

}
