package Cifrar;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Contrasena_MD5 {
    public static String getMD5(String input)
    {
        String hashtext = "";
        try
        {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md5.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            hashtext = number.toString(16);

            while (hashtext.length() < 32)
            {
                hashtext = "0" + hashtext;
            }
        }
        catch (Exception error)
        {
            System.out.println("ERROR " + error);
        }
        return hashtext;
    }
}
