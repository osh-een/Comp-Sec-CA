import java.util.*;

public class AppUtility {
    static Scanner kb = new Scanner(System.in);

    public static int getNumber() {
        int num;
        while (true) {
            try {
                System.out.println("Please enter a valid input: ");
                num = kb.nextInt();
                return num;
            } catch (InputMismatchException e) {
                kb.nextLine();
            }
        }
    }
}