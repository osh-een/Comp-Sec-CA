import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;
import java.security.*;
import java.util.*;

public class AppUtility {
    private static final int IV_LENGTH = 16;
    private static final String ALGORITHM = "AES";
    private static final String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";

    static Scanner kb = new Scanner(System.in);

    public static int getNumber() {
        int num;
        while (true) {
            try {
                System.out
                        .println(
                                "\n--------------------\n1. Encrypt file\n2. Decrypt file\n3. Exit\n--------------------");
                System.out.println("Please enter a valid input: (1-3)");
                num = kb.nextInt();
                return num;
            } catch (InputMismatchException e) {
                kb.nextLine();
            }
        }
    }

    public static void encryptFile(String inputFile, String outputFile) {
        try {
            File file = new File(inputFile);
            FileInputStream fis = new FileInputStream(file);
            byte[] fileBytes = new byte[(int) file.length()];
            fis.read(fileBytes);
            fis.close();

            KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
            keyGen.init(256);
            SecretKey secretKey = keyGen.generateKey();

            SecureRandom secureRandom = new SecureRandom();
            byte[] iv = new byte[IV_LENGTH];
            secureRandom.nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            byte[] encryptedData = cipher.doFinal(fileBytes);

            byte[] encryptedWithIv = new byte[iv.length + encryptedData.length];
            System.arraycopy(iv, 0, encryptedWithIv, 0, iv.length);
            System.arraycopy(encryptedData, 0, encryptedWithIv, iv.length, encryptedData.length);

            FileOutputStream fos = new FileOutputStream(outputFile);
            String base64Encrypted = Base64.getEncoder().encodeToString(encryptedWithIv);
            fos.write(base64Encrypted.getBytes());
            fos.close();

            System.out.println("Encryption successful! The key to decrypt this file is:");
            System.out.println(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
            System.out.println("The encrypted file has been saved to 'ciphertext.txt'.");

        } catch (FileNotFoundException e) {
            System.err.println("\n[Error: The file '" + inputFile + "' was not found]");
        } catch (IOException e) {
            System.err.println("\n[Error: There was an I/O issue while reading or writing the file]");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("\n[Error: The specified algorithm for key generation is not available]");
        } catch (NoSuchPaddingException e) {
            System.err.println("\n[Error: The specified padding scheme for encryption is not supported]");
        } catch (InvalidKeyException e) {
            System.err.println("\n[Error: The generated AES key is invalid]");
        } catch (InvalidAlgorithmParameterException e) {
            System.err.println("\n[Error: The algorithm parameter (IV) is invalid]");
        } catch (BadPaddingException e) {
            System.err.println("\n[Error: The padding used during encryption is incorrect]");
        } catch (IllegalBlockSizeException e) {
            System.err.println("\n[Error: The block size used during encryption is incorrect]");
        } catch (Exception e) {
            System.err.println("\n[An unexpected error occurred during the encryption process]");
        }
    }

    public static void decryptFile(String inputFile, String outputFile, String base64Key) {
        try {
            File file = new File(inputFile);
            FileInputStream fis = new FileInputStream(file);
            byte[] fileBytes = new byte[(int) file.length()];
            fis.read(fileBytes);
            fis.close();

            byte[] encryptedData = Base64.getDecoder().decode(new String(fileBytes));

            byte[] iv = new byte[IV_LENGTH];
            System.arraycopy(encryptedData, 0, iv, 0, iv.length);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            byte[] decodedKey = Base64.getDecoder().decode(base64Key);
            SecretKey secretKey = new SecretKeySpec(decodedKey, ALGORITHM);

            byte[] cipherText = new byte[encryptedData.length - iv.length];
            System.arraycopy(encryptedData, iv.length, cipherText, 0, cipherText.length);

            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            byte[] decryptedData = cipher.doFinal(cipherText);

            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(decryptedData);
            fos.close();

            System.out.println("Decryption successful! The decrypted file has been saved to 'plaintext.txt'.");
        } catch (FileNotFoundException e) {
            System.err.println("[Error: The file '" + inputFile + "' was not found]");
        } catch (IOException e) {
            System.err.println("[Error: There was an I/O issue while reading or writing the file]");
        } catch (IllegalArgumentException e) {
            System.err.println("[Error: The Base64 key or encrypted data is invalid]");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("[Error: The specified algorithm for decryption is not available]");
        } catch (NoSuchPaddingException e) {
            System.err.println("[Error: The specified padding scheme for decryption is not supported]");
        } catch (InvalidKeyException e) {
            System.err.println("[Error: The provided key for decryption is invalid]");
        } catch (InvalidAlgorithmParameterException e) {
            System.err.println("[Error: The algorithm parameter (IV) is invalid]");
        } catch (BadPaddingException e) {
            System.err.println("[Error: The padding used during decryption is incorrect]");
        } catch (IllegalBlockSizeException e) {
            System.err.println("[Error: The block size used during decryption is incorrect]");
        } catch (Exception e) {
            System.err.println("[Error: An unexpected error occurred during the decryption process]");
        }
    }
}
