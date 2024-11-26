import java.security.*;
import java.util.*;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

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

    public static String encrypt(String algorithm, String input, SecretKey key, IvParameterSpec iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }
}