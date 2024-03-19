import java.util.Random;

public class Main {
    public static void main(String[] args) {
        RBTree tree = new RBTree();
        Random r = new Random();

        for (int i = 0; i < 20; i++) {
            try {
                int n = r.nextInt(1, 40);
                tree.add(n);
                System.out.println("Adding " + n);
                System.out.println(tree);
            } catch (IllegalArgumentException e) {
                i--;
            }
        }

        System.out.println("Height: " + tree.height());

        for (int i = 0; i < 20; i++) {
            try {
                int n = r.nextInt(1, 40);
                tree.remove(n);
                System.out.println("Removing " + n);
                System.out.println(tree);
            } catch (IllegalArgumentException e) {
                i--;
            }
        }

        System.out.println(tree);
    }
}