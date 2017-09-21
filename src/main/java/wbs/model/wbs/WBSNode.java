package wbs.model.wbs;

import wbs.model.wbs.elements.WBSElement;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/*
 Container of WBSElement for tree structure
 */


@Entity
@Table(name = "node")
public class WBSNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private WBSNode parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<WBSNode> children;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "element_id")
    private WBSElement element;

    @ManyToOne
    @JoinColumn(name = "tree_id")
    private WBSTree tree;

    @Column(name = "breakdown")
    private String breakdownId;

    public WBSNode(WBSElement element, WBSNode parent) {
        this.element = element;
        this.parent = parent;
        children = new LinkedList<WBSNode>();
    }

    public WBSNode(WBSElement element) {
        this(element, null);
    }

    public WBSNode() {
    }

    //Walk the subtree and add the nodes to a list
    private void walk(WBSNode node, List<WBSNode> list) {
        list.add(node);
        for (WBSNode eachNode : node.getChildren()) {
            walk(eachNode, list);
        }
    }


    public List<WBSNode> toList() {
        List<WBSNode> list = new LinkedList<WBSNode>();
        walk(this, list);
        return list;
    }

    public boolean hasParent() {
        if (parent == null) {
            return false;
        }
        return true;
    }

    public boolean isLeaf() {
        if (children == null || children.isEmpty()) {
            return true;
        }
        return false;
    }

    public WBSNode getTreeRoot() {
        if (tree == null) {
            return null;
        }
        if (!this.hasParent()) {
            return this;
        }
        return parent.getTreeRoot();
    }

    public int getLevel() {
        if (!hasParent()) {
            return 1;
        }
        return parent.getLevel() + 1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WBSElement getElement() {
        return element;
    }

    public WBSNode getParent() {
        return parent;
    }

    public void setParent(WBSNode parent) {
        this.parent = parent;
    }

    public List<WBSNode> getChildren() {
        return new LinkedList<WBSNode>(children);
    }

    public String getBreakdownId() {
        return breakdownId;
    }

    public void setBreakdownId(String breakdownId) {
        this.breakdownId = breakdownId;
    }

    public WBSTree getTree() {
        return tree;
    }

    public void setTree(WBSTree tree) {
        this.tree = tree;
    }

    public boolean acceptsChildren() {
        return element.acceptsChildren();
    }

    @Override
    public String toString() {
        return "WBSNode{" +
                "id=" + id +
                ", element=" + element +
                ", tree=" + tree +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WBSNode node = (WBSNode) o;

        if (id != null ? !id.equals(node.id) : node.id != null) return false;
        if (element != null ? !element.equals(node.element) : node.element != null) return false;
        return tree != null ? tree.equals(node.tree) : node.tree == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (element != null ? element.hashCode() : 0);
        result = 31 * result + (tree != null ? tree.hashCode() : 0);
        return result;
    }
}
