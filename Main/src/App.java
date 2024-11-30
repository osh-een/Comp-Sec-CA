import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        int choice;

        Scanner input = new Scanner(System.in);

        while (true) {
            choice = AppUtility.getNumber();
            if (choice == 1) {
                System.out.println("\nEnter the filename to encrypt: ");
                String inputFile = input.nextLine();
                String encryptedFile = "ciphertext.txt";

                AppUtility.encryptFile(inputFile, encryptedFile);
            } else if (choice == 2) {
                System.out.println("\nEnter the filename to decrypt: ");
                String encryptedFile = input.nextLine();
                System.out.println("\nEnter the decryption key (Base64 encoded): ");
                String key = input.nextLine();
                String decryptedFile = "plaintext.txt";

                AppUtility.decryptFile(encryptedFile, decryptedFile, key);
            } else if (choice == 3) {
                System.out.println("Goodbye!");
                break;
            }
        }
        input.close();
    }
}
