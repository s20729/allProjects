import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

class g21s20729 {
    public static int cyckle;
    public static int wkaznik=1;
    public static int lcounter=0;
    private static g21s20729 tree;
    public static boolean flag2 = false;
    public static boolean flag3=false;
    public static int counter =0;
    public static Node sout;
    public class Node {
        int value;
        double key;
        int height;
        Node left;
        Node right;

        Node(double key, int v) {
            value=v;
            this.key = key;
        }
    }

    private Node root;

    public Node find(double key) {
        Node current = root;
        while (current != null) {
            if (current.key == key) {
                break;
            }
            current = current.key < key ? current.right : current.left;
        }
        return current;
    }

    public void insert(double key, int v) {
        root = insert(root, key, v);
    }

    public void delete(double key) {
        root = delete(root, key);
    }

    public Node getRoot() {
        return root;
    }

    public int height() {
        return root == null ? -1 : root.height;
    }

    private Node insert(Node node, double key, int v) {
        if (node == null) {
            return new Node(key, v);
        } else if (node.key > key) {
            node.left = insert(node.left, key, v);
        } else if (node.key < key) {
            node.right = insert(node.right, key, v);
        } else {
            throw new RuntimeException("duplicate Key!");
        }
        return rebalance(node);
    }

    private Node delete(Node node, double key) {
        if (node == null) {
            return node;
        } else if (node.key > key) {
            node.left = delete(node.left, key);
        } else if (node.key < key) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                Node mostLeftChild = mostLeftChild(node.right);
                node.key = mostLeftChild.key;
                node.value=mostLeftChild.value;
                node.right = delete(node.right, node.key);
            }
        }
        if (node != null) {
            node = rebalance(node);
        }
        return node;
    }

    private Node mostLeftChild(Node node) {
        Node current = node;
        /* loop down to find the leftmost leaf */
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private Node rebalance(Node z) {
        updateHeight(z);
        int balance = getBalance(z);
        if (balance > 1) {
            if (height(z.right.right) > height(z.right.left)) {
                z = rotateLeft(z);
            } else {
                z.right = rotateRight(z.right);
                z = rotateLeft(z);
            }
        } else if (balance < -1) {
            if (height(z.left.left) > height(z.left.right)) {
                z = rotateRight(z);
            } else {
                z.left = rotateLeft(z.left);
                z = rotateRight(z);
            }
        }
        return z;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node z = x.right;
        x.right = y;
        y.left = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private Node rotateLeft(Node y) {
        Node x = y.right;
        Node z = x.left;
        x.left = y;
        y.right = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private void updateHeight(Node n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    private int height(Node n) {
        return n == null ? -1 : n.height;
    }

    public int getBalance(Node n) {
        return (n == null) ? 0 : height(n.right) - height(n.left);
    }

    Node maxValueNode(Node node)
    {
        Node current = node;
        while (current.right != null)
            current = current.right;

        return current;
    }
    static void rezult(Node node){
        if (node!=null){
            rezult(node.left);
            if (node.key==sout.key && flag2==true){
                flag3=true;
            }
            if (flag2==true && node.key!=sout.key && flag3!=true){
                System.out.print(node.value+" ");
            }
            if (node.key==sout.key && flag2==false &&flag3!= true){
                flag2=true;
                System.out.print(node.value + " ");
            }
            if (node.key!=tree.maxValueNode(tree.getRoot()).key && flag3!=true){
                rezult(node.right);
            }else if (flag3!=true){
                rezult(tree.getRoot());
            }
        }
    }
public static Node kthSmallest(Node root, int k)
{
    if (root == null)
        return null;

    Node left = kthSmallest(root.left, k);

    if (left != null)
        return left;

    lcounter++;
    if (lcounter == k)
        return root;

    return kthSmallest(root.right, k);
}

    public static void main(String[] args) throws FileNotFoundException {
        tree = new g21s20729();
        boolean flag = false;
            Scanner scanner = new Scanner(new FileReader(new File(args[0])));
            while (scanner.hasNextInt()) {
            int liczba = scanner.nextInt();
            if (flag == false){
                flag = true;
                cyckle = liczba;
            }else{
               tree.insert(++counter, liczba);
            }
        }

        while (cyckle!=0){
            lcounter=0;
            Node tmp = kthSmallest(tree.getRoot(), wkaznik);
            if(tmp.value%2==1){
                if (tmp.key!=tree.maxValueNode(tree.getRoot()).key){
                    lcounter=0;
                    lcounter=0;
                    tree.insert(((tmp.key+kthSmallest(tree.getRoot(), wkaznik+1).key)/2), tmp.value-1);
                }else {
                    tree.insert(((tmp.key+1)), tmp.value-1);
                }
                counter++;
                wkaznik=(tmp.value+wkaznik)%counter;
                if (wkaznik==0)
                    wkaznik=counter;
            }else{
                    int deletedValue;
                if (tmp.key!=tree.maxValueNode(tree.getRoot()).key){
                    lcounter=0;
                    lcounter=0;
                    deletedValue = kthSmallest(tree.getRoot(), wkaznik+1).value;
                    lcounter=0;
                    tree.delete(kthSmallest(tree.getRoot(), wkaznik+1).key);
                    --counter;
                    if (counter!=0){
                        wkaznik=(deletedValue+wkaznik)%counter;
                    }else {
                        return;
                    }

                }else {
                    deletedValue = tree.mostLeftChild(tree.getRoot()).value;
                    tree.delete(tree.mostLeftChild(tree.getRoot()).key);
                    --counter;
                    if (counter!=0){
                        wkaznik=(deletedValue+wkaznik-1)%counter;
                    }else {
                        return;
                    }
                }
                    if (wkaznik==0)
                        wkaznik=counter;
            }
            cyckle--;
        }
        flag3=false;
        flag2=false;
        lcounter=0;
        sout=kthSmallest(tree.getRoot(), wkaznik);
        rezult(tree.getRoot());
    }
}