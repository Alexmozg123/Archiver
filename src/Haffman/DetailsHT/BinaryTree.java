package Haffman.DetailsHT;

public class BinaryTree {

    private TreeNode root;

    public BinaryTree(TreeNode root) { this.root = root; }

    public BinaryTree() {}

    public int getFrequency() {
        return root.getFrequency();
    }

    public TreeNode getRoot() {
        return root;
    }
}
