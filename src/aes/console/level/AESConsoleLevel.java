/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aes.console.level;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 *
 * @author Mo R Torky
 */
public class AESConsoleLevel {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("heloo workls");
        try {
            //CBC 
        final int n = 128;
        SecretKey key = generateKey(n);
        String algorithm = "AES/CBC/PKCS5Padding";
        String algorithm2 = "AES/ECB/PKCS5Padding";
        String algorithm3 = "AES/CTR/PKCS5Padding";
        IvParameterSpec ivParameterSpec = generateIv();
        String URI = "C:\\Users\\Mo R Torky\\Documents\\NetBeansProjects\\image AES encrypt & decrypt\\AES console level\\images\\image.png";
        File inputFile = new File(URI);
        File encryptedFile = new File("document.encrypted_CBC.jpg");
        File decryptedFile = new File("document.decrypted_CBC.jpg");
        CBCencryptFile(algorithm, key, ivParameterSpec, inputFile, encryptedFile);
        CBCdecryptFile(algorithm, key, ivParameterSpec, encryptedFile, decryptedFile);
//        
        File encryptedFile2 = new File("encrypted_ECB.png");
        File decryptedFile2 = new File("decrypted_ECB.jpg");
        ECBencryptFile(algorithm2, key, inputFile, encryptedFile2);
        ECBdecryptFile(algorithm2, key, encryptedFile2, decryptedFile2);
       
        File encryptedFile3 = new File("encrypted_CTR.jpg");
        File decryptedFile3 = new File("decrypted_CTR.jpg");
        CBCencryptFile(algorithm3, key, ivParameterSpec, inputFile, encryptedFile3);
        CBCdecryptFile(algorithm3, key, ivParameterSpec,encryptedFile3, decryptedFile3);
//    
//copy(inputFile, encryptedFile2);
            System.out.println(key.toString());
            
        } catch (Exception e) {
            System.err.println(e.getMessage()+"\n"+e.toString());
        }
        
    }
    
    //GENERATE AES KEY 
    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    keyGenerator.init(n);
    SecretKey key = keyGenerator.generateKey();
    return key;
    }   
    
    //GENERATE IV
    public static IvParameterSpec generateIv() {
    byte[] iv = new byte[16];
    new SecureRandom().nextBytes(iv);
    return new IvParameterSpec(iv);
    }
    
    public static void CBCencryptFile(String algorithm, SecretKey key, IvParameterSpec iv,
            File inputFile, File outputFile) throws IOException, NoSuchPaddingException,
    NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
    
        Cipher cipher = Cipher.getInstance(algorithm);
    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        FileInputStream inputStream = new FileInputStream(inputFile);
    FileOutputStream outputStream = new FileOutputStream(outputFile);
     byte[] imgHeader = new byte[620];
     int l=  inputStream.read(imgHeader,0,imgHeader.length);
    outputStream.write(imgHeader);
        int bytesRead;
    byte[] buffer = new byte[64];
    while ((bytesRead = inputStream.read(buffer)) != -1) {
        byte[] output = cipher.update(buffer, 0, bytesRead);
        if (output != null) {
            outputStream.write(output);
        }
    }
    byte[] outputBytes = cipher.doFinal();
    if (outputBytes != null) {
        outputStream.write(outputBytes);
    }
    inputStream.close();
    outputStream.close();
}   
    public static void CBCdecryptFile(String algorithm, SecretKey key, IvParameterSpec iv,
            File inputFile, File outputFile) throws IOException, NoSuchPaddingException,
    NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
    
        Cipher cipher = Cipher.getInstance(algorithm);
    cipher.init(Cipher.DECRYPT_MODE, key, iv);
        FileInputStream inputStream = new FileInputStream(inputFile);
    FileOutputStream outputStream = new FileOutputStream(outputFile);
     byte[] imgHeader = new byte[12];
     int l=  inputStream.read(imgHeader,0,imgHeader.length);
    outputStream.write(imgHeader);
        int bytesRead;
    byte[] buffer = new byte[64];
    while ((bytesRead = inputStream.read(buffer)) != -1) {
        byte[] output = cipher.update(buffer, 0, bytesRead);
        if (output != null) {
            outputStream.write(output);
        }
    }
    byte[] outputBytes = cipher.doFinal();
    if (outputBytes != null) {
        outputStream.write(outputBytes);
    }
    inputStream.close();
    outputStream.close();
}
    
    
    
    public static void ECBencryptFile(String algorithm, SecretKey key,
            File inputFile, File outputFile) throws IOException, NoSuchPaddingException,
    NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
    
        Cipher cipher = Cipher.getInstance(algorithm);
    cipher.init(Cipher.ENCRYPT_MODE, key);
        FileInputStream inputStream = new FileInputStream(inputFile);
    FileOutputStream outputStream = new FileOutputStream(outputFile);
    int bytesRead;
//    byte[] imgHeader = new byte[40];
//    while ((bytesRead = inputStream.read(imgHeader)) != -1) {
//        if (imgHeader != null) {
//            outputStream.write(imgHeader);
//        }
//    }
    byte[] imgHeader = new byte[636];
     int l=  inputStream.read(imgHeader,0,imgHeader.length);
    outputStream.write(imgHeader);
    for (int i = 0; i < imgHeader.length; i++) {
        System.out.print((char)imgHeader[i]);
    }
    byte[] buffer = new byte[64];
    while ((bytesRead = inputStream.read(buffer)) != -1) {
        byte[] output = cipher.update(buffer, 0, bytesRead);
        if (output != null) {
            outputStream.write(output);
        }
    }
    byte[] outputBytes = cipher.doFinal();
    if (outputBytes != null) {
        outputStream.write(outputBytes);
    }
//    outputStream.read(imgHeader,0,imgHeader.length);
//    for (int i = 0; i < imgHeader.length; i++) {
//        System.out.print((char)imgHeader[i]);
//    }
    inputStream.close();
    outputStream.close();
}
    
    public static void copy(File inputFile, File outputFile)throws IOException{
         FileInputStream inputStream = new FileInputStream(inputFile);
    FileOutputStream outputStream = new FileOutputStream(outputFile);
    int bytesRead;
    byte[] imgHeader = new byte[8];
          int l=  inputStream.read(imgHeader,0,imgHeader.length);
    outputStream.write(imgHeader);
    for (int i = 0; i < imgHeader.length; i++) {
        System.out.print((char)imgHeader[i]);
    }
        for (int i = 0; i < 100; i++) {
            
        }
    byte[] buffer = new byte[64];
    while ((bytesRead = inputStream.read(buffer)) != -1) {
        if (buffer != null) {
            outputStream.write(buffer);
        }
    }

    inputStream.close();
    outputStream.close();
    }
    
    public static void ECBdecryptFile(String algorithm, SecretKey key,
            File inputFile, File outputFile) throws IOException, NoSuchPaddingException,
    NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
    
        Cipher cipher = Cipher.getInstance(algorithm);
    cipher.init(Cipher.DECRYPT_MODE, key);
        FileInputStream inputStream = new FileInputStream(inputFile);
    FileOutputStream outputStream = new FileOutputStream(outputFile);
     byte[] imgHeader = new byte[636];
     int l=  inputStream.read(imgHeader,0,imgHeader.length);
    outputStream.write(imgHeader);
    for (int i = 0; i < imgHeader.length; i++) {
        System.out.print((char)imgHeader[i]);
    }
    int bytesRead;
    byte[] buffer = new byte[64];
    while ((bytesRead = inputStream.read(buffer)) != -1) {
        byte[] output = cipher.update(buffer, 0, bytesRead);
        if (output != null) {
            outputStream.write(output);
        }
    }
    byte[] outputBytes = cipher.doFinal();
    if (outputBytes != null) {
        outputStream.write(outputBytes);
    }
    inputStream.close();
    outputStream.close();
}
     
    
    
//    @Test
//void givenFile_whenEncrypt_thenSuccess() 
//    throws NoSuchAlgorithmException, IOException, IllegalBlockSizeException, 
//    InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, 
//    NoSuchPaddingException {
//    
//    SecretKey key = AESUtil.generateKey(128);
//    String algorithm = "AES/CBC/PKCS5Padding";
//    IvParameterSpec ivParameterSpec = AESUtil.generateIv();
//    Resource resource = new ClassPathResource("inputFile/baeldung.txt");
//    File inputFile = resource.getFile();
//    File encryptedFile = new File("classpath:baeldung.encrypted");
//    File decryptedFile = new File("document.decrypted");
//    AESUtil.CBCencryptFile(algorithm, key, ivParameterSpec, inputFile, encryptedFile);
//    AESUtil.CBCdecryptFile(algorithm, key, ivParameterSpec, encryptedFile, decryptedFile);
//    assertThat(inputFile).hasSameTextualContentAs(decryptedFile);
//}
}
