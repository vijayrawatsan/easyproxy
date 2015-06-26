package com.vijayrawatsan.easyproxy.demo;

import android.app.Application;

import com.vijayrawatsan.easyproxy.EasyProxy;
import com.vijayrawatsan.easyproxy.ProxyInfo;

/**
 * Created by vijay.rawat01 on 6/26/15.
 */
public class EasyProxyDemoApplication extends Application {

    @Override
    public void onCreate() {
        EasyProxy.init(new ProxyInfo("10.125.24.28", 8080));
        super.onCreate();
    }
}
