package wbs.model.wbs;

//The WBS tree holds the root node of the tree structure

import wbs.model.wbs.elements.StandardWBSElement;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Entity
@Table(name = "tree")
public class WBSTree {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rootnode_id")
    protected WBSNode root;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public WBSTree(WBSNode root) {
        if (root.hasParent()) {
            throw new IllegalArgumentException("Root node cannot have a parent node.");
        } else {
            this.root = root;
            root.setTree(this);
        }
    }

    public WBSTree() {
        root = new WBSNode(new StandardWBSElement());
        root.setTree(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WBSNode getRoot() {
        return root;
    }

    public void setRoot(WBSNode newRoot) {
        if (newRoot.hasParent()) {
            throw new IllegalArgumentException("Root node cannot have a parent node.");
        } else {
            root.setTree(null);
            root = newRoot;
            newRoot.setTree(this);
        }
    }

    //converts tree to linked list
    public List<WBSNode> toList() {
        List<WBSNode> list = root.toList();
        return list;
    }

    //calculates tree depth
    public int depth() {
        int maxDepth = root.getLevel();
        List<WBSNode> nodeList = toList();
        for (WBSNode node : nodeList) {
            if (node.getLevel() > maxDepth) {
                maxDepth = node.getLevel();
            }
        }
        return maxDepth;
    }

    //Setting breakdown number for all nodes in the tree with breadth-first search
    //Oder of siblings is retained
    public void setBreakdownId() {
        Queue<WBSNode> queue = new LinkedList<WBSNode>();
        if (root == null) {
            System.out.println("root is null");
            return;
        }
        queue.add(root);
        root.setBreakdownId("1");
        while (!queue.isEmpty()) {
            WBSNode node = (WBSNode) queue.poll();
            if (!node.getChildren().isEmpty()) {
                for (int i = 0; i < node.getChildren().size(); i++) {
                    WBSNode child = node.getChildren().get(i);
                    queue.add(child);
                    child.setBreakdownId(node.getBreakdownId() + "." + (i + 1));
                }
            }
        }
    }

    @Override
    public String toString() {
        return "WBSTree{" +
                "id=" + id +
                ", root=" + root +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WBSTree wbsTree = (WBSTree) o;

        if (id != null ? !id.equals(wbsTree.id) : wbsTree.id != null) return false;
        return root != null ? root.equals(wbsTree.root) : wbsTree.root == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (root != null ? root.hashCode() : 0);
        return result;
    }
}
