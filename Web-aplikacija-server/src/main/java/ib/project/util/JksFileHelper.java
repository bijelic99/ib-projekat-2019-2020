package ib.project.util;

import ib.project.certificate.CertificateGenerator;
import ib.project.certificate.CertificateReader;
import ib.project.certificate.SignedCertificateGenerator;
import ib.project.keystore.KeyStoreReader;
import ib.project.keystore.KeyStoreWriter;
import ib.project.model.IssuerData;
import ib.project.model.SubjectData;
import ib.project.model.User;
import org.bouncycastle.asn1.x500.X500Name;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class JksFileHelper {
    //Trebalo bi pomeriti u application.properties file
    public static String DATA_DIR_PATH="C:\\ib_projekat";
    private static String SERVER_CERT_FILENAME="Certificate_server.cer";
    private static String SERVER_JKS_FILENAME="server.jks";
    private static String SERVER_JKS_PASSWORD="123";

    private static final String password = "Password";
    public static String createNewCertificate(User user) throws Exception {
        File cert = new File(DATA_DIR_PATH+"\\"+SERVER_CERT_FILENAME);
        File jksFile = new File(DATA_DIR_PATH+"\\"+SERVER_CERT_FILENAME);
        if(!cert.exists() && !jksFile.exists()){
            throw new Exception("File not found");
        }
        //TODO ucitati server jks fajl iz c://files i onda cert koristiti za potpis novog cert

        //CertificateReader cr = new CertificateReader();
        KeyStoreReader ksr = new KeyStoreReader();

        KeyStore keyStore = ksr.readKeyStore(DATA_DIR_PATH+"\\"+SERVER_JKS_FILENAME, SERVER_JKS_PASSWORD.toCharArray());

        PrivateKey privateKey = ksr.getPrivateKeyFromKeyStore(keyStore, "certificate server", SERVER_JKS_PASSWORD.toCharArray());
        Certificate issuerCert = ksr.getCertificateFromKeyStore(keyStore, "certificate server");

        IssuerData issuerData = ksr.getIssuerFromCertificate(issuerCert, privateKey);
        SignedCertificateGenerator signedCertificateGenerator = new SignedCertificateGenerator();
        KeyPair keyPair = signedCertificateGenerator.generateKeyPair();
        X500Name x500NameSub = signedCertificateGenerator
                .generateX509Name(user.getEmail(), "", "",
                        "", "", "", user.getEmail(), user.getId().toString());
        Date start, end = null;
        Calendar calendar = GregorianCalendar.getInstance();
        start=calendar.getTime();
        calendar.setTime(start);
        calendar.add(GregorianCalendar.YEAR, 1);
        end = calendar.getTime();

        String serialNumber = user.getId().toString();


        SubjectData subjectData = new SubjectData(keyPair.getPublic(), x500NameSub, serialNumber, start, end);

        X509Certificate certificate = signedCertificateGenerator.generateSignedCertificate(issuerData, subjectData);




        KeyStoreWriter keyStoreWriter = new KeyStoreWriter();

        KeyStore usersKS = keyStoreWriter.loadKeyStore(null, user.getPassword().toCharArray());

        keyStoreWriter.addToKeyStore(usersKS, user.getId().toString(), keyPair.getPrivate(), user.getPassword().toCharArray(), certificate);

        String filename = user.getId().toString()+".jks";

        keyStoreWriter.saveKeyStore(usersKS, DATA_DIR_PATH+"\\"+filename, user.getPassword().toCharArray());



        return filename;
    }

    public static byte[] getCertificate(String keyStorePath, User user) throws CertificateEncodingException {
        KeyStoreReader keyStoreReader = new KeyStoreReader();
        KeyStore keyStore = keyStoreReader.readKeyStore(DATA_DIR_PATH+"\\"+keyStorePath, user.getPassword().toCharArray());
        Certificate certificate = keyStoreReader.getCertificateFromKeyStore(keyStore, user.getId().toString());
        return certificate.getEncoded();
    }
}
