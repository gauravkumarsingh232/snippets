package com.xiffox.snippets.encryption;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;


public class Encrypt {
	public static void main(String[] args) {
     	try {

     		if (args.length < 3) {
				System.out.println("Usage requires 3 argument, \n" +
						"expected format: java -jar Encrypt.jar <public_key_path> <padding_algorithm> <text>\n" +
						"sample: java -jar Encrypt.jar public_key.cert RSA/ECB/OAEPWithMD5AndMGF1Padding <text>");
			}


		    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

			File certificateFile = new File(args[0]);
			InputStream inputStream = new FileInputStream(certificateFile);
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
			X509Certificate certificate = (X509Certificate)certificateFactory.generateCertificate(inputStream);
			inputStream.close();
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(certificate.getPublicKey().getEncoded());
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
			Cipher encryptCipher = Cipher.getInstance(args[1], "BC");
			encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
			String message = args[2];
			byte[] messageEncrypt = encryptCipher.doFinal(message.getBytes());
			byte []output = Base64.encodeBase64(messageEncrypt);

			System.out.println("\nSource : "+message);
		    System.out.println("output : "+new String(output));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
