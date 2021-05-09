package com.xiffox.snippets.ssl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

class CompositeX509TrustManager implements X509TrustManager {

    private final List<X509TrustManager> trustManagers;

    public CompositeX509TrustManager(List<X509TrustManager> trustManagers) {
        this.trustManagers = ImmutableList.copyOf(trustManagers);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        for (X509TrustManager trustManager : trustManagers) {
            try {
                trustManager.checkClientTrusted(chain, authType);
                return;
            } catch (CertificateException e) {
                // maybe the next trust manager will trust them
            }
        }
        throw new CertificateException(
                "None of the TrustManagers trust this certificate chain");
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        for (X509TrustManager trustManager : trustManagers) {
            try {
                trustManager.checkServerTrusted(chain, authType);
                return;
            } catch (CertificateException e) {
                // maybe the next trust manager will trust them
            }
        }
        throw new CertificateException(
                "None of the TrustManagers trust this certificate chain");
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        ImmutableList.Builder certificates = ImmutableList.builder();
        for (X509TrustManager trustManager : trustManagers) {
            certificates.add(trustManager.getAcceptedIssuers());
        }
        return (X509Certificate[]) Iterables.toArray(certificates.build(),
                X509Certificate.class);
    }

}