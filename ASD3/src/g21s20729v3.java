import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

class g21s20729 {
    public static int cyckle=8;
    public static int wkaznik;
    public static int lcounter=0;
    private static g21s20729 tree;
    public static boolean flag2 = false;
    public static boolean flag3=false;
    public static Node rezOfCustomInOrder;
    public static Node rezNextToDelete;
    public static int counter =0;
    public class Node {
        int value;
        double key;
        int height;
        Node left;
        Node right;
        boolean isWkaznik=false;

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
    void preOrder(Node node)
    {
        if (node != null)
        {
            preOrder(node.left);
            System.out.print(node.key + " ");
            preOrder(node.right);
        }
    }
    void preOrder2(Node node)
    {
        if (node != null)
        {
            preOrder2(node.left);
            System.out.print(node.value + " ");
            preOrder2(node.right);
        }
    }
    Node maxValueNode(Node node)
    {
        Node current = node;
        while (current.right != null)
            current = current.right;

        return current;
    }
    static void nextToDelete(Node node){
        if (node!=null){
            nextToDelete(node.left);
            if (node.isWkaznik==true){
                flag2=true;
            }
            if (flag2==true && node.right!=null && node.isWkaznik==true){
            rezNextToDelete=tree.mostLeftChild(node.right);
            flag2=false;
            }else if (flag2==true && node.isWkaznik==false){
                flag2=false;
                rezNextToDelete=node;
            }else if (rezNextToDelete==null){
                nextToDelete(node.right);
            }
        }
    }
    static void rezult(Node node){
        if (node!=null){
            rezult(node.left);
            if (node.isWkaznik==true && flag2==true){
                flag3=true;
            }
            if (flag2==true && node.isWkaznik==false && flag3!=true){
                System.out.print(node.value+" ");
            }
            if (node.isWkaznik==true && flag2==false &&flag3!= true){
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
    void customInOrder(Node node) {
        if (flag2==false){
            if (node != null) {
                customInOrder(node.left);
                if (flag3==true) {
                    lcounter++;
                }
                if (node.isWkaznik == true) {
                    flag3=true;
                    node.isWkaznik = false;
                }
 //               System.out.println(lcounter + " - l");
 //               System.out.println(node.key + " - k");
                if (lcounter != wkaznik) {
                    if (node != tree.maxValueNode(tree.root)) {
                        customInOrder(node.right);
                    } else {
                        customInOrder(tree.root);
                    }
                } else {
                    lcounter = 0;
                    node.isWkaznik = true;
                    rezOfCustomInOrder = node;
                    flag2=true;
                    flag3=false;
//                    System.out.println("finish");
                }
            }
        }
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

        tree.find(1).isWkaznik=true;
        rezOfCustomInOrder=tree.find(1);

        while (cyckle!=0){
            Node tmp;
            if((tmp = rezOfCustomInOrder).value%2==1){
                if (tmp.key!=tree.maxValueNode(tree.root).key){
                    flag2=false;
                    rezNextToDelete=null;
                    nextToDelete(tree.getRoot());
                    tree.insert(tree.root, ((tmp.key+rezNextToDelete.key)/2), tmp.value-1);
                }else {
                    tree.insert(tree.root, ((tmp.key+1)), tmp.value-1);
                }
                wkaznik=tmp.value;
//                    System.out.println(tmp.key);
//                    System.out.println(counter);
//                    System.out.println("Do " +wkaznik + " " + rezOfCustomInOrder.key );
                flag2=false;
                tree.customInOrder(tree.root);
//                    System.out.println("PO " +wkaznik + " " + rezOfCustomInOrder.key );
                // System.out.println(wkaznik);
                // System.out.println(((tmp.key+tree.minValueSubTree(tmp))/2) + " " + (tmp.value-1));
            }else if (tmp.value%2==0){
                int deletedValue;
                if (tree.getRoot()!=null){
                if (tmp.key!=tree.maxValueNode(tree.root).key){
                    //                   System.out.println("usuwamy " + tmp.right.key);
                    flag2=false;
                    rezNextToDelete=null;
                    nextToDelete(tree.getRoot());
                    deletedValue=rezNextToDelete.value;
                    tree.delete(rezNextToDelete.key);

                }else {
                    deletedValue=tree.mostLeftChild(tree.root).value;
                    tree.delete(tree.mostLeftChild(tree.root).key);
                }
                wkaznik=deletedValue;
                flag2=false;
                tree.customInOrder(tree.root);
                }
            }
            cyckle--;
        }
        flag3=false;
        flag2=false;
        rezult(tree.getRoot());
    }
}