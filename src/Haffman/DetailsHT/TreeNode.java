package Haffman.DetailsHT;

public class TreeNode {

    private int frequency;
    private char symbol;
    private TreeNode left;
    private TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int frequency, char symbol) {
        this.frequency = frequency;
        this.symbol = symbol;
    }

    public void addNode(TreeNode newTreeNode) {
        if (left == null) {
            left = newTreeNode;
        } else if (left.getFrequency() <= newTreeNode.getFrequency()) {
            right = newTreeNode;
        } else {
            right = left;
            left = newTreeNode;
        }

        frequency += newTreeNode.getFrequency();
    }

    public int getFrequency() {
        return frequency;
    }

    public char getSymbol() {
        return symbol;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }
}
