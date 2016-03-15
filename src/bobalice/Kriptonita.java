package bobalice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.*;
import javax.crypto.Cipher;

/*
 * @author a1320564
 */
public class Kriptonita {

    public static final String ALGORITHM = "RSA";
//  Linux
    public static final String PATH_CHAVE_PRIVADA = "/keys/private.key";
    public static final String PATH_CHAVE_PUBLICA = "/keys/public.key";

//    Windows
//    public static final String PATH_CHAVE_PRIVADA = "C:/keys/private.key";
//    public static final String PATH_CHAVE_PUBLICA = "C:/keys/public.key";

    public void geraChave() {
        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyGen.initialize(1024);
            final KeyPair key = keyGen.generateKeyPair();
            File chavePrivadaFile = new File(PATH_CHAVE_PRIVADA);
            File chavePublicaFile = new File(PATH_CHAVE_PUBLICA); // Cria os arquivos para armazenar a chave Privada e a chave Publica 
            if (chavePrivadaFile.getParentFile() != null) {
                chavePrivadaFile.getParentFile().mkdirs();
            }
            chavePrivadaFile.createNewFile();
            if (chavePublicaFile.getParentFile() != null) {
                chavePublicaFile.getParentFile().mkdirs();
            }
            chavePublicaFile.createNewFile(); // Salva a Chave Pública no arquivo 
            ObjectOutputStream chavePublicaOS = new ObjectOutputStream(new FileOutputStream(chavePublicaFile));
            chavePublicaOS.writeObject(key.getPublic());
            chavePublicaOS.close(); // Salva a Chave Privada no arquivo 
            ObjectOutputStream chavePrivadaOS = new ObjectOutputStream(new FileOutputStream(chavePrivadaFile));
            chavePrivadaOS.writeObject(key.getPrivate());
            chavePrivadaOS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean verificaSeExisteChavesNoSO() {
        File chavePrivada = new File(PATH_CHAVE_PRIVADA);
        File chavePublica = new File(PATH_CHAVE_PUBLICA);
        if (chavePrivada.exists() && chavePublica.exists()) {
            return true;
        }
        return false;
    }

    public byte[] criptografa(String hash, PublicKey chave) {
        byte[] crip = null;
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM); // Criptografa o texto puro usando a chave Púlica 
            cipher.init(Cipher.ENCRYPT_MODE, chave);
            crip = cipher.doFinal(hash.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return crip;
    }

    public String decriptografa(byte[] crip, PrivateKey chave) {
        byte[] hash = null;
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM); // Decriptografa o texto puro usando a chave Privada 
            cipher.init(Cipher.DECRYPT_MODE, chave);
            hash = cipher.doFinal(crip);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new String(hash);
    }

    public String hashificando(String texto) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        byte[] byteHash = mDigest.digest(texto.getBytes());
        StringBuffer hash = new StringBuffer();
        for (int i = 0; i < byteHash.length; i++) {
            hash.append(Integer.toString((byteHash[i] & 0xff) + 0x100, 16).substring(1));
        }

        return hash.toString();
    }
}
