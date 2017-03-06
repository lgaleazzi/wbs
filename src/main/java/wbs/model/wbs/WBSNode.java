package wbs.model.wbs;

import wbs.model.wbs.elements.WBSElement;

import javax.persistence.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/*
 * Container of WBSElement for tree structure
 */

//TODO: check getters and setters

@Entity
@Table(name="node")
public class WBSNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="parent_id")
    private WBSNode parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<WBSNode> children;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="element_id")
    private WBSElement element;

    @ManyToOne
    @JoinColumn(name="tree_id")
    private WBSTree tree;

    @Column(name="breakdown")
    private String breakdownId;

    public WBSNode(WBSElement element, WBSNode parent) {
        this.element = element;
        this.parent = parent;
        children = new LinkedList<WBSNode>();
    }

    public WBSNode(WBSElement element) {
        this(element, null);
    }

    public WBSNode() {}

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


    public void setTree(WBSTree tree) {
        this.tree = tree;
    }

    public void setBreakdownId(String breakdownId) {
        this.breakdownId = breakdownId;
    }

    public void setParent(WBSNode parent) {
        this.parent = parent;
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
        return parent.getLevel()+1;
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

    public List<WBSNode> getChildren() {
        return new LinkedList<WBSNode>(children);
    }

    public String getBreakdownId() {
        return breakdownId;
    }

    public WBSTree getTree() {
        return tree;
    }

    public boolean acceptsChildren() {
        return element.acceptsChildren();
    }
}
