package com.xiffox.snippets.ssl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import javax.net.ssl.X509KeyManager;
import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.List;

class CompositeX509KeyManager implements X509KeyManager {

    private final List<X509KeyManager> keyManagers;
    private String clientAlias;

    public CompositeX509KeyManager(List<X509KeyManager> keyManagers,
                                   String alias) {
        this.keyManagers = ImmutableList.copyOf(keyManagers);
        this.clientAlias = alias;
    }

    @Override
    public String chooseClientAlias(String[] keyType, Principal[] issuers,
                                    Socket socket) {
        return clientAlias;
    }

    @Override
    public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
        for (X509KeyManager keyManager : keyManagers) {
            String alias = keyManager.chooseServerAlias(keyType, issuers,
                    socket);
            if (alias != null) {
                return alias;
            }
        }
        return null;
    }

    /**
     * Returns the first non-null private key associated with the given alias,
     * or {@code null} if the alias can't be found.
     */
    @Override
    public PrivateKey getPrivateKey(String alias) {
        for (X509KeyManager keyManager : keyManagers) {
            PrivateKey privateKey = keyManager.getPrivateKey(alias);
            if (privateKey != null) {
                return privateKey;
            }
        }
        return null;
    }

    /**
     * Returns the first non-null certificate chain associated with the given
     * alias, or {@code null} if the alias can't be found.
     */
    @Override
    public X509Certificate[] getCertificateChain(String alias) {
        for (X509KeyManager keyManager : keyManagers) {
            X509Certificate[] chain = keyManager.getCertificateChain(alias);
            if (chain != null && chain.length > 0) {
                return chain;
            }
        }
        return new X509Certificate[0];
    }

    /**
     * Get all matching aliases for authenticating the client side of a secure
     * socket, or {@code null} if there are no matches.
     */
    @SuppressWarnings("unchecked")
    @Override
    public String[] getClientAliases(String keyType, Principal[] issuers) {
        ImmutableList.Builder aliases = ImmutableList.builder();
        for (X509KeyManager keyManager : keyManagers) {
            aliases.add(keyManager.getClientAliases(keyType, issuers));
        }
        String[] aliasesArr = (String[]) Iterables.toArray(aliases.build(),
                String.class);

        return aliasesArr.length == 0 ? null : aliasesArr;
    }

    /**
     * Get all matching aliases for authenticating the server side of a secure
     * socket, or {@code null} if there are no matches.
     */
    @SuppressWarnings("unchecked")
    @Override
    public String[] getServerAliases(String keyType, Principal[] issuers) {
        ImmutableList.Builder aliases = ImmutableList.builder();
        for (X509KeyManager keyManager : keyManagers) {
            aliases.add(keyManager.getServerAliases(keyType, issuers));
        }

        String[] aliasesArr = (String[]) Iterables.toArray(aliases.build(),
                String.class);

        return aliasesArr.length == 0 ? null : aliasesArr;
    }

}