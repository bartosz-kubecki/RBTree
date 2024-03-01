import java.util.Random;

public class Main {
    public static void main(String[] args) {
        RBTree t = new RBTree();
        Random r = new Random();

        for (int i = 0; i < 20; i++) {
            try {
                t.add(r.nextInt(1, 40));
            } catch (IllegalArgumentException e) {
                i--;
            }
        }

        System.out.println(t);
        System.out.print(t.height());
    }
}