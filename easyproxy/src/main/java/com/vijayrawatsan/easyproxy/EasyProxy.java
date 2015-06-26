package com.vijayrawatsan.easyproxy;

import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by vijay.rawat01 on 6/26/15.
 */
public class EasyProxy {

    private static final String TAG = "EASY_PROXY";
    private static final String HTTP = "http";
    private static final String SOCKS = "socket";
    private static boolean sLogEnabled;
    private static Map<String, ProxyInfo> sUrlToProxyInfoMap = new HashMap<>();
    private static Set<String> sExcludeSet = new HashSet<>();
    private static Set<UrlProxyInfo> sIncludeSet = new HashSet<>();
    private static ProxyInfo sDefaultProxy;

    public static void init(ProxyInfo defaultProxy) {
        init(defaultProxy, false);
    }

    public static void init(ProxyInfo defaultProxy, boolean isLogEnabled) {
        init(defaultProxy, null, isLogEnabled);
    }

    public static void init(ProxyInfo defaultProxy, Set<String> exclude, boolean isLogEnabled) {
        init(defaultProxy, exclude, null, isLogEnabled);
    }

    public static void init(ProxyInfo defaultProxy, Set<String> exclude, Set<UrlProxyInfo> include, boolean isLogEnabled) {
        sLogEnabled = isLogEnabled;
        if(exclude != null) {
            sExcludeSet = exclude;
        }
        if(include != null) {
            sIncludeSet = include;
        }
        sDefaultProxy = defaultProxy;
        ProxySelector.setDefault(new EasyProxySelector(ProxySelector.getDefault()));
    }

    static class EasyProxySelector extends ProxySelector {
        private final ProxySelector mDefaultProxySelector;

        public EasyProxySelector(ProxySelector defaultProxySelector) {
            this.mDefaultProxySelector = defaultProxySelector;
        }

        @Override
        public List<Proxy> select(URI uri) {
            if (HTTP.equalsIgnoreCase(uri.getScheme()) || SOCKS.equalsIgnoreCase(uri.getScheme())) {
                if(isInExcludedSet(uri.toASCIIString())) {
                    if (isLogEnabled()) {
                        Log.d(TAG, "Found in excludedSet. Returning default proxy for url : " + uri);
                    }
                    if(sDefaultProxy == null) {
                        return mDefaultProxySelector.select(uri);
                    }
                    return Arrays.asList(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(sDefaultProxy.getHost(), sDefaultProxy.getPort())));
                }
                ProxyInfo proxyForUrl = getProxyForUrl(uri.toASCIIString());
                if(proxyForUrl != null) {
                    if (isLogEnabled()) {
                        Log.d(TAG, "Found in includeSet. Returning proxy (" + proxyForUrl.getHost() + ", " + proxyForUrl.getPort() + ") for url : " + uri);
                    }
                    return Arrays.asList(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyForUrl.getHost(), proxyForUrl.getPort())));
                }
            }
            if (isLogEnabled()) {
                Log.d(TAG, "Not found anywhere. Returning default proxy (which is none generally).");
            }
            if(sDefaultProxy == null) {
                return mDefaultProxySelector.select(uri);
            }
            return Arrays.asList(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(sDefaultProxy.getHost(), sDefaultProxy.getPort())));
        }

        @Override
        public void connectFailed(URI uri, SocketAddress socketAddress, IOException e) {
            //Using DefaultProxySelector Implementation
            if (uri == null || socketAddress == null || e == null) {
                throw new IllegalArgumentException("Arguments can\'t be null.");
            }
        }

        private boolean isInExcludedSet(String url) {
            for(String regex : sExcludeSet) {
                if(url.matches(regex)) {
                    return true;
                }
            }
            return false;
        }

        private ProxyInfo getProxyForUrl(String url) {
            ProxyInfo proxyInfo = sUrlToProxyInfoMap.get(url);
            if(proxyInfo != null) {
                return proxyInfo;
            }
            for(UrlProxyInfo urlProxyInfo: sIncludeSet) {
                if(url.matches(urlProxyInfo.getRegex())) {
                    sUrlToProxyInfoMap.put(url, urlProxyInfo.getProxyInfo());
                    return urlProxyInfo.getProxyInfo();
                }
            }
            return null;
        }
    }

    public static boolean isLogEnabled() {
        return sLogEnabled;
    }
}
