/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m09uf1AES;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Eric
 */
public class SeguretatAES {
    
    
    /**
     * Aquest metode li arriba una contrasenya i un tamany i la genera en AES i SHA-256.
     * @param contrasenya
     * @param tamany
     * @return
     * @throws UnsupportedEncodingException 
     */
    public SecretKey generarClau(String contrasenya, int tamany) throws UnsupportedEncodingException {
        SecretKey clau = null;
        //Mira si el tamany de la contrasenya es el correcte
        if ((tamany == 128) || (tamany == 192) || (tamany == 256)) {
            try {
                //Passa la contrasenya a bytes
                byte[] data = contrasenya.getBytes("UTF-8");
                //Declarem el MessageDigest a SHA-256
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                //Passem la contrasenya a bytes amb el Digest
                byte[] hash = md.digest(data);
                byte[] key = Arrays.copyOf(hash, tamany / 8);
                //Generem la clau secreta amb AES
                clau = new SecretKeySpec(key, "AES");
            } catch (NoSuchAlgorithmException ex) {
                System.err.println("Generador no disponible.");
            }
        }
        return clau;
    }
    /**
     * Aquest metode xifra el fitxer amb la contrasenya per tal de no poder obtenir la informacio si no disposem de la clau.
     * @param fitxer
     * @param clau
     * @throws FileNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IOException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException 
     */
    public void xifrarFitxer(String fitxer, SecretKey clau) throws FileNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException{
        //Declarem el FileInputStream amb el fitxer que volem xifrar.
        FileInputStream fis = new FileInputStream(fitxer);
        //Declarem el FileOutputStream amb la ruta del nou fitxer que es generara xifrat.
        FileOutputStream fos = new FileOutputStream(new File("fitxerXifrat.txt"));
        //Declarem el Cipher que sera qui encripti.
        Cipher ci = Cipher.getInstance("AES/ECB/PKCS5Padding");
        //Posem Cipher en forma de encriptat i li passem la clau amb la que s'encriptara
        ci.init(Cipher.ENCRYPT_MODE, clau);
        //Per ultim llegeix el fitxer i el passa a un fitxer amb el fos.
        byte[] buffer = new byte[1000];
        int bytes;
        while((bytes = fis.read(buffer, 0, buffer.length)) != -1){
            ci.update(buffer, 0, bytes);
        }
        fos.write(ci.doFinal());               
    }
    /**
     * Aquest metode li arriba un fitxer encriptat, una clau i una ruta per desxifrar el fitxer.
     * Fa basicament lo mateix que lo altre pero invertit, encomptes de Encrypt fa Decrypt.
     * @param fitxerEncriptat
     * @param clau
     * @param fitxerDesencriptat
     * @throws FileNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IOException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException 
     */
    public void desxifrarFitxer(String fitxerEncriptat, SecretKey clau, String fitxerDesencriptat) throws FileNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException{
        FileInputStream fis = new FileInputStream(fitxerEncriptat);
        FileOutputStream fos = new FileOutputStream(new File(fitxerDesencriptat));
        Cipher ci = Cipher.getInstance("AES/ECB/PKCS5Padding");
        ci.init(Cipher.DECRYPT_MODE, clau);
        byte[] buffer = new byte[1000];
        int bytes;
        while ((bytes = fis.read(buffer, 0, buffer.length)) != -1){
            ci.update(buffer, 0, bytes);
        }
        fos.write(ci.doFinal());
    }

    
}
