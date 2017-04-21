package m09uf1AES;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;

public class TestAES {

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IOException, IOException, IllegalBlockSizeException, FileNotFoundException, FileNotFoundException, BadPaddingException, BadPaddingException, BadPaddingException {
        
        //Declarem la clase
        SeguretatAES saes = new SeguretatAES();
        //Demanem la longitut de la contrasenya
        int passLength = Integer.parseInt(JOptionPane.showInputDialog("Introdueix la longitut de la contrasenya"));
        //Generem la key amb la contrasenya i la seva longitut
        SecretKey key = saes.generarClau(JOptionPane.showInputDialog("Introdueix la password"), passLength);
        //Xifrem el fitxer
        saes.xifrarFitxer("fitxer.txt", key);
        //Desxifrem el fitxer
        saes.desxifrarFitxer("fitxerXifrat.txt", key, "fitxerDesxifrat.txt");
    }
}
