package com.vijayrawatsan.easyproxy;

/**
 * Created by vijay.rawat01 on 6/26/15.
 */
public class ProxyInfo {
    private String host;
    private int port;

    public ProxyInfo(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "ProxyInfo{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProxyInfo)) return false;

        ProxyInfo proxyInfo = (ProxyInfo) o;

        if (getPort() != proxyInfo.getPort()) return false;
        return getHost().equals(proxyInfo.getHost());

    }

    @Override
    public int hashCode() {
        int result = getHost().hashCode();
        result = 31 * result + getPort();
        return result;
    }
}
