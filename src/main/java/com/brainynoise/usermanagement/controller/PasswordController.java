package com.brainynoise.usermanagement.controller;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordController {
    /*private final CredentialService credentialService;

    public PasswordController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    public void savePassword(String email, String password) {
        //Generate and save hash and salt in database
        Credential credential = generateHash(email, password);
        credentialService.saveCredential(credential);
        //Verify password with data previously saved
        //credential = credentialService.getCredentialByEmail(email);
        //verifyPassword(password, credential.getHash(), credential.getSalt());
    }
    public Credential generateHash(String email, String password) {
        String saltvalue = PassBasedEnc.getSaltvalue(30);
        String encryptedpassword = PassBasedEnc.generateSecurePassword(password, saltvalue);

        System.out.println("Plain text password = " + password);
        System.out.println("Secure password = " + encryptedpassword);
        System.out.println("Salt value = " + saltvalue);
        return new Credential(email, encryptedpassword, saltvalue);
    }*/

    public boolean verifyPassword(String password, String encryptedPassword, String salt) { /* Remove this method from the controller */
        boolean passwordMatch = PassBasedEnc.verifyUserPassword(password, encryptedPassword, salt);
        if(passwordMatch)
            System.out.println("Provided user password " + password + " is correct.");
        else
            System.out.println("Provided password is incorrect");
        return passwordMatch;
    }
}


class PassBasedEnc
{
    /* Declaration of variables */
    private static final Random random = new SecureRandom();
    private static final String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int iterations = 10000;
    private static final int keylength = 256;

    /* Method to generate the salt value. */
    public static String getSaltvalue(int length)
    {
        StringBuilder finalval = new StringBuilder(length);

        for (int i = 0; i < length; i++)
        {
            finalval.append(characters.charAt(random.nextInt(characters.length())));
        }

        return new String(finalval);
    }

    /* Method to generate the hash value */
    public static byte[] hash(char[] password, byte[] salt)
    {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keylength);
        Arrays.fill(password, Character.MIN_VALUE);
        try
        {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e)
        {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        }
        finally
        {
            spec.clearPassword();
        }
    }

    /* Method to encrypt the password using the original password and salt value. */
    public static String generateSecurePassword(String password, String salt)
    {
        String finalval = null;

        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());

        finalval = Base64.getEncoder().encodeToString(securePassword);

        return finalval;
    }

    /* Method to verify if both password matches or not */
    public static boolean verifyUserPassword(String providedPassword,
                                             String securedPassword, String salt)
    {
        boolean finalval = false;

        /* Generate New secure password with the same salt */
        String newSecurePassword = generateSecurePassword(providedPassword, salt);

        /* Check if two passwords are equal */
        finalval = newSecurePassword.equalsIgnoreCase(securedPassword);

        return finalval;
    }
}