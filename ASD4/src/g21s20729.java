import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class g21s20729 {
    public static Node mainNode = new Node();
static class HuffmanNode {
    int data;
    char c;
    HuffmanNode left;
    HuffmanNode right;
}
static class Node{
    Node son;
    int freq;
    char litera;
}
    public static class Huffman {

        public static void printCode(HuffmanNode root, String s)
        {
            if (root.left == null && root.right == null && Character.isLetter(root.c)) {
                System.out.println(root.c + ":" + s);

                return;
            }

            printCode(root.left, s + "0");
            printCode(root.right, s + "1");
        }
        public static void main(String[] args) throws FileNotFoundException {
             String line;
             int n=0;
             boolean flag = false;
             Node currentNode = new Node();
            Scanner scanner = new Scanner(new FileReader(new File(args[0])));
            while (scanner.hasNextLine()){
                n++;
                line = scanner.nextLine();
                if (flag==false){
                    flag = true;
                    currentNode.freq=Integer.parseInt(line.substring(2));
                    currentNode.litera=line.charAt(0);
                    mainNode=currentNode;
                }else {
                    currentNode.son = new Node();
                    currentNode.son.freq=Integer.parseInt(line.substring(2));
                    currentNode.son.litera=line.charAt(0);
                    currentNode=currentNode.son;
                }
            }
            MPriorityQueue q = new MPriorityQueue(n);
            for (int i = 0; i < n; i++) {
                HuffmanNode hn = new HuffmanNode();
               hn.c = mainNode.litera;
                hn.data = mainNode.freq;
                hn.left = null;
                hn.right = null;
                q.add(hn);
                mainNode=mainNode.son;
            }
            HuffmanNode root = null;

            while (q.size() > 1) {
                HuffmanNode x = q.peek();
                q.poll();
                HuffmanNode y = q.peek();
                q.poll();
                HuffmanNode f = new HuffmanNode();
                f.data = x.data + y.data;
                f.c = '-';
                f.left = x;
                f.right = y;
                root = f;
                q.add(f);
            }
            printCode(root, "");
        }
    }
static class MPriorityQueue{
    private HuffmanNode[] Heap;
    private int size;
    private int maxsize;

    private static final int FRONT = 1;

    public MPriorityQueue(int maxsize)
    {
        this.maxsize = maxsize;
        this.size = 0;
        Heap = new HuffmanNode[this.maxsize + 1];
        HuffmanNode min = new HuffmanNode();
        min.data=-100;
        Heap[0] = min;
    }
    private int parent(int pos)
    {
        return pos / 2;
    }
    private int leftChild(int pos)
    {
        return (2 * pos);
    }
    private int rightChild(int pos)
    {
        return (2 * pos) + 1;
    }
    private boolean isLeaf(int pos)
    {
        if (pos> size/2 && pos <= size) {return true;}
        return false;
    }
    private void swap(int fpos, int spos)
    {
        HuffmanNode tmp;
        tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }

    private void minHeapify(int pos) {
        if (isLeaf(pos) || size==0 || size==1) return;
        if (Heap[pos].data > Heap[leftChild(pos)].data && Heap[leftChild(pos)].data <= Heap[rightChild(pos)].data) {
            swap(pos, leftChild(pos));
            minHeapify(leftChild(pos));
        }

        if (Heap[pos].data > Heap[rightChild(pos)].data && Heap[leftChild(pos)].data > Heap[rightChild(pos)].data) {
            swap(pos, rightChild(pos));
            minHeapify(rightChild(pos));
        }
    }
    public void add(HuffmanNode element) {
        if (size >= maxsize) {
            return;
        }
        Heap[++size] = element;
        int current = size;

        while (Heap[current].data < Heap[parent(current)].data) {
            swap(current, parent(current));
            current = parent(current);
        }
    }
    public HuffmanNode poll() {
        HuffmanNode popped = Heap[FRONT];
        Heap[FRONT] = Heap[size--];
        minHeapify(FRONT);
        return popped;
    }
    public HuffmanNode peek(){
        return Heap[FRONT];
    }
    int size(){
        return size;
    }
}
}
