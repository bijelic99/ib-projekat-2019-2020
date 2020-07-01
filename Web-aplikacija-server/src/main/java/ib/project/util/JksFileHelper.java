package ib.project.util;

import ib.project.certificate.CertificateGenerator;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;

public class JksFileHelper {
    private static String DATA_DIR_PATH="C:\\files";

    private static final String password = "Password";
    public static String createNewJksFile(Integer userID) throws KeyStoreException, IOException {
        File f = new File(DATA_DIR_PATH);
        if(!f.exists()){
            f.mkdir()
        }
        //TODO ucitati server jks fajl iz c://files i onda cert koristiti za potpis novog cert

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        CertificateGenerator certificateGenerator = new CertificateGenerator();
        certificateGenerator.ge

        keyStore.load(null, password);
        String filename = userID+".jks";
        FileOutputStream fos = new FileOutputStream(filename);
        keyStore.store(fos, password);
        fos.close();
        return filename;
    }
}
