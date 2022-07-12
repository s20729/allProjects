import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.Scanner;

public class g21s20729 {
    public static String napis = "";
    public static String currentNapis = "";

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(args[0]));
        Node mainNode = new Node();
        Node currentNode = mainNode;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String name = String.valueOf(line.charAt(0));
            if (line.length() > 1) {
                String path = line.substring(1);
                for (int i = 0; i < path.length(); i++) {
                    if (path.charAt(i) == 'R') {
                        if (currentNode.right == null)
                            currentNode.right = new Node();

                        currentNode.right.parent = currentNode;
                        currentNode = currentNode.right;
                    } else if (path.charAt(i) == 'L') {
                        if (currentNode.left == null)
                            currentNode.left = new Node();

                        currentNode.left.parent = currentNode;
                        currentNode = currentNode.left;
                    }
                    if (i == path.length() - 1) {
                        currentNode.name = name;
                        currentNode = mainNode;
                    }
                }
            } else {
                mainNode.name = name;
            }
        }
        getName(mainNode);
        System.out.println(napis);


    }

    public static void getName(Node mainNode) {
        if (mainNode.left == null && mainNode.right == null) {
            order(mainNode);
        } else {
            if (mainNode.left != null) {
                getName(mainNode.left);
            }
            if (mainNode.right != null) {
                getName(mainNode.right);
            }
        }
    }

    public static void order(Node node) {
       while (node!=null){
           currentNapis+=node.name;
           node=node.parent;
       }
        Comparator<String> comparator = Comparator.naturalOrder();
        if(comparator.compare(currentNapis, napis) > 0){
            napis = currentNapis;
        }
        currentNapis = "";
    }
}

class Node {
    Node left;
    Node right;
    Node parent;
    String name;
}