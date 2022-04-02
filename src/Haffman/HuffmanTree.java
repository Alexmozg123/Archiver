package Haffman;

import Haffman.DetailsHT.BinaryTree;
import Haffman.DetailsHT.PriorityQueue;
import Haffman.DetailsHT.TreeNode;

public class HuffmanTree {
    private final byte ENCODING_TABLE_SIZE = 127;   //длина кодировочной таблицы
    private final String myString;                        //сообщение
    private final BinaryTree huffmanTree;                 //дерево Хаффмана
    private final int[] freqArray;                        //частотная таблица
    private final String[] encodingArray;                 //кодировочная таблица


    //----------------constructor----------------------
    public HuffmanTree(String newString) {
        myString = newString;

        freqArray = new int[ENCODING_TABLE_SIZE];
        fillFrequencyArray();

        huffmanTree = getHuffmanTree();

        encodingArray = new String[ENCODING_TABLE_SIZE];
        fillEncodingArray(huffmanTree.getRoot(), "", "");
    }

    //--------------------frequency array------------------------
    private void fillFrequencyArray() {
        for (int i = 0; i < myString.length(); i++) {
            freqArray[myString.charAt(i)]++;
        }
    }

    public int[] getFrequencyArray() {
        return freqArray;
    }

    //------------------------huffman tree creation------------------
    private BinaryTree getHuffmanTree() {
        PriorityQueue pq = new PriorityQueue();
        //алгоритм описан выше
        for (int i = 0; i < ENCODING_TABLE_SIZE; i++) {
            if (freqArray[i] != 0) {                                            //если символ существует в строке
                TreeNode newNode = new TreeNode((char) i, (char) freqArray[i]);        //то создать для него TreeNode
                BinaryTree newTree = new BinaryTree(newNode);                   //а для TreeNode создать BinaryTree
                pq.insert(newTree);                                             //вставить в очередь
            }
        }

        while (true) {
            BinaryTree tree1 = pq.remove();                                     //извлечь из очереди первое дерево.

            try {
                BinaryTree tree2 = pq.remove();                                 //извлечь из очереди второе дерево

                TreeNode newNode = new TreeNode();                              //создать новый Node
                newNode.addNode(tree1.getRoot());                        //сделать его потомками два извлеченных дерева
                newNode.addNode(tree2.getRoot());

                pq.insert(new BinaryTree(newNode));
            } catch (IndexOutOfBoundsException e) {//осталось одно дерево в очереди
                return tree1;
            }
        }
    }

    public BinaryTree getTree() {
        return huffmanTree;
    }

    //-------------------encoding array------------------
    void fillEncodingArray(TreeNode node, String codeBefore, String direction) {    //заполнить кодировочную таблицу
        if (node.isLeaf()) {
            encodingArray[node.getSymbol()] = codeBefore + direction;
        } else {
            fillEncodingArray(node.getLeft(), codeBefore + direction, "0");
            fillEncodingArray(node.getRight(), codeBefore + direction, "1");
        }
    }

    String[] getEncodingArray() {
        return encodingArray;
    }

    public void displayEncodingArray() {                                            //для отладки
        fillEncodingArray(huffmanTree.getRoot(), "", "");

        System.out.println("======================Encoding table====================");
        for (int i = 0; i < ENCODING_TABLE_SIZE; i++) {
            if (freqArray[i] != 0) {
                System.out.print((char)i + " ");
                System.out.println(encodingArray[i]);
            }
        }
        System.out.println("========================================================");
    }
    //-----------------------------------------------------
    String getOriginalString() {
        return myString;
    }
}
