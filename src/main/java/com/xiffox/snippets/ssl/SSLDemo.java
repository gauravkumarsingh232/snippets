package com.xiffox.snippets.ssl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Arrays;

public class SSLDemo {

    String keyStoreFile;
    String password = "pass";
    String alias = "alias";

    public static void main(String[] args) {
        SSLDemo demo = new SSLDemo();

        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            String defaultAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
            X509TrustManager jvmTrustManager = demo.getTrustManager(defaultAlgorithm, null);
            TrustManager[] trustManagers = {new CompositeX509TrustManager(ImmutableList.of(jvmTrustManager))};

            if (StringUtils.isNotBlank(demo.alias)) {
                // Support for Two -way ssl
                KeyStore identityStore = KeyStore.getInstance("JKS");
                InputStream identityStream = new FileInputStream(demo.keyStoreFile);
                identityStore.load(identityStream, demo.password.toCharArray());

                X509KeyManager customKeyManager = demo.getKeyManager("SunX509",
                        identityStore, demo.password.toCharArray());

                KeyManager[] keyManagers = {new CompositeX509KeyManager(
                        ImmutableList.of(customKeyManager), demo.alias)};
                ctx.init(keyManagers, trustManagers, null);

            } else {
                // Support for One -way ssl
                ctx.init(null, trustManagers, null);
            }
        } catch (Exception e) {
            System.out.println("Exception occured - " + e);
        }

    }

    private X509TrustManager getTrustManager(String algorithm, KeyStore keystore)
            throws NoSuchAlgorithmException, KeyStoreException {
        TrustManagerFactory factory = TrustManagerFactory
                .getInstance(algorithm);
        factory.init(keystore);
        return Iterables.get(Iterables.filter(
                Arrays.asList(factory.getTrustManagers()),
                X509TrustManager.class), 0);
    }

    private X509KeyManager getKeyManager(String algorithm, KeyStore keystore,
                                         char[] password) throws NoSuchAlgorithmException,
            UnrecoverableKeyException, KeyStoreException {
        KeyManagerFactory factory = KeyManagerFactory.getInstance(algorithm);
        factory.init(keystore, password);
        return Iterables.get(Iterables.filter(
                Arrays.asList(factory.getKeyManagers()), X509KeyManager.class),
                0);
    }

}



