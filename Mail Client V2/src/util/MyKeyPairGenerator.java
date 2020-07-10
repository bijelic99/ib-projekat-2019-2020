package util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public interface MyKeyPairGenerator {
	static KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
		
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
		KeyPairGenerator  kpg = KeyPairGenerator .getInstance("RSA");
		
		kpg.initialize(1024, sr);
		
		return kpg.generateKeyPair();
		
	}
	
}
