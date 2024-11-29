import java.util.*;

import javax.crypto.SecretKey;

public class App {

    static Scanner kb = new Scanner(System.in);
    // 1. Encrypt a File (Task 2)
    // 2. Decrypt a File (Task 3)
    // 3. Quit the application

    public static void main(String[] args) {
        String algorithm = "AES/CBC/PKCS5Padding", input = "Hello World";
        while (true) {
            System.out.println("Please select an option: \n1. Encrypt File \n2.Decrypt File \n3. Quit");
            int num = AppUtility.getNumber();
            if (num == 1) {

                System.out.println("Encrypting...");
            } else if (num == 2) {
                System.out.println("Decrypting...");
            } else if (num == 3) {
                System.out.println("GoodBye!");
                break;
            }
        }
    }

}