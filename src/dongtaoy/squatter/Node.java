package dongtaoy.squatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongtao on 5/8/2015.
 */

public class Node<T> {
    private T data;
    private List<Node<T>> children;
    private Node<T> parent;


    public Node(T data) {
        this.data = data;
        this.children = new ArrayList<Node<T>>();
    }

    public void addChild(Node<T> child) {
        child.setParent(this);
        children.add(child);
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return children.size() == 0;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getParent() {
        return this.parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public List<Node<T>> getChildren() {
        return this.children;
    }
}
