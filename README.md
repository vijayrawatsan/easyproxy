# Easy Proxy For Android & Java

Easy Proxy for android & java apps. For those days when you want proxy for your app only. Supports different proxy for different urls.

We all know how painful is :

Going to wifi -> Long click your wifi -> Modify network -> Enter IP and port

Later forgetting that you have done this setting and thinking why the fuck my internet is not working. Why God why???

## Features

 - Setup single proxy for all the urls.
 - Setup single proxy for all urls except some exclusion rules.
 - Setup single proxy for all urls except some exclusion rules & some separate proxy rules for some url patterns.
 - Library agnostic. You can use any library you want Volley, OkHttp, Ion or any other http library.

## Example Usage

Initialize EasyProxy in you application class like below.

```java
package com.vijayrawatsan.easyproxy.demo;

import android.app.Application;
import com.vijayrawatsan.easyproxy.EasyProxy;
import com.vijayrawatsan.easyproxy.ProxyInfo;

public class EasyProxyDemoApplication extends Application {
    @Override
    public void onCreate() {
        //This will set 10.125.24.28:8080 as proxy for all the urls from this app.
        EasyProxy.init(new ProxyInfo("10.125.24.28", 8080));
        super.onCreate();
    }
}
```
or

```java
boolean isLogEnabled = true;
Set<String> exclusions = new HashSet<>(Arrays.asList("^.*google*$"));
//This will set 10.125.24.28:8080 as proxy for all the urls from this app.
//Except any url that contains "google" in it.
EasyProxy.init(new ProxyInfo("10.125.24.28", 8080), exclusions, isLogEnabled);
```
or

```java
Set<String> exclusions = new HashSet<>(Arrays.asList("^.*google.*$"));
Set<UrlProxyInfo> inclusions = new HashSet<>(Arrays.asList( new UrlProxyInfo( "^.*yahoo.*$", new ProxyInfo("10.65.65.87", 8080))));
//This will set 10.125.24.28:8080 as proxy for all the urls from this app.
//Except any url that contains "google" in it.
//And for any url containing "yahoo" it will set proxy to 10.65.65.87:8080
EasyProxy.init(new ProxyInfo("10.125.24.28", 8080), exclusions, inclusions, true);
```
# Including in your project

gradle:

Step 1. Add the JitPack repository to your build file

```groovy
repositories {
    maven {
        url "https://jitpack.io"
    }
}
```

Step 2. Add the dependency in the form

```groovy
dependencies {
    compile 'com.github.vijayrawatsan:easyproxy:1.0'
}
```

maven:

Step 1. Add the JitPack repository to your build file

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

Step 2. Add the dependency in the form

```xml
<dependency>
    <groupId>com.github.vijayrawatsan</groupId>
    <artifactId>easyproxy</artifactId>
    <version>1.0</version>
</dependency>
```

# Contributing

Contributions welcome via Github pull requests.

