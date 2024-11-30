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
                System.out.println("Please enter a valid input: ");
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

            // Write the encrypted data (Base64 encoded) to the output file
            FileOutputStream fos = new FileOutputStream(outputFile);
            String base64Encrypted = Base64.getEncoder().encodeToString(encryptedWithIv);
            fos.write(base64Encrypted.getBytes());
            fos.close();

            System.out.println("Encryption successful! The key to decrypt this file is:");
            System.out.println(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
            System.out.println("The encrypted file has been saved to 'ciphertext.txt'.");
        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
