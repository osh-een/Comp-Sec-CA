import java.util.Scanner;

public class App {

    // https://howtodoinjava.com/java/java-security/aes-256-encryption-decryption/#:~:text=AES%2D256%20Encryption%20Example&text=The%20encrypt()%20method%20takes,using%20PBKDF2%20with%20SHA%2D256.
    // https://www.geeksforgeeks.org/advanced-encryption-standard-aes/
    // https://www.baeldung.com/java-aes-encryption-decryption
    // https://medium.com/@deepak.sirohi9188/java-aes-encryption-and-decryption-1b30c9a5d900

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