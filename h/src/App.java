import java.util.*;

public class App {

    static Scanner kb = new Scanner(System.in);
    // 1. Encrypt a File (Task 2)
    // 2. Decrypt a File (Task 3)
    // 3. Quit the application

    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println("1. Encrypt File\n2. Decrypt File\n3. Exit\n");
            int num = getNumber();
            if (num == 1) {
                System.out.println("\nEncrypting...\n");
            } else if (num == 2) {
                System.out.println("\nDecrypting...\n");
            } else if (num == 3) {
                System.out.println("\nExiting...\n");
                break;
            }
        }
    }

    public static int getNumber() {
        int num;
        while (true) {
            try {
                num = kb.nextInt();
                return num;
            } catch (java.util.InputMismatchException e) {
                kb.nextLine();
                System.out.println("Enter valid Integer");
            }
        }
    }
}
