package com.hxlm.health.web.plugin.weixinPay.util.util;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.hxlm.health.web.plugin.weixinPay.bean.Configure;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import javax.net.ssl.SSLContext;

/**
 * Created by dengyang on 15/8/3.
 */
public class GetWxOrderno  {

    public static DefaultHttpClient httpclient;

    //表示请求器是否已经做了初始化工作
    private static boolean hasInit = false;

    //HTTP请求器
    private static CloseableHttpClient httpClient;

    //请求器的配置
    private static RequestConfig requestConfig;

    //连接超时时间，默认10秒
    private static int socketTimeout = 10000;

    //传输超时时间，默认30秒
    private static int connectTimeout = 30000;

    static
    {
        httpclient = new DefaultHttpClient();
        httpclient = (DefaultHttpClient)HttpClientConnectionManager.getSSLInstance(httpclient);

        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    }


    public static String getPayNo(String url,String xmlParam){
        System.out.println("xml是:"+xmlParam);
        DefaultHttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
        String prepay_id = "";
        try {
            httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
            HttpResponse response = httpclient.execute(httpost);
            String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            Map<String, Object> dataMap = new HashMap<String, Object>();
            System.out.println("json是:"+jsonStr);

            if(jsonStr.indexOf("FAIL")!=-1){
                return prepay_id;
            }
            Map map = doXMLParse(jsonStr);
            String return_code  = (String) map.get("return_code");
            prepay_id  = (String) map.get("prepay_id");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return prepay_id;
    }


    private void init() throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream instream = new FileInputStream(new File("my.keystore"));//加载本地的证书进行https加密传输
        try {
//            keyStore.load(instream, Configure.getCertPassword().toCharArray());//设置证书密码
            keyStore.load(instream, "nopassword".toCharArray());
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, Configure.getCertPassword().toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

        httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();

        //根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();

        hasInit = true;
    }

    /**
     * 通过Https往API post xml数据
     * @param url
     *      API地址
     * @param xmlParam
     *      要提交的XML数据对象
     * @return
     */
    public  Map getRefundsMap(String url,String xmlParam){

        try {
            this.init();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        // 本地或者服务器的证书位置（证书在微信支付申请成功发来的通知邮件中）
        String cert = "D:";
        //私钥（在安装证书时设置）
        String password = "";

        System.out.println("xml是:"+xmlParam);
        DefaultHttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
        String prepay_id = "";
        try {
            HttpGet httpget = new HttpGet("https://localhost:8080/");

            System.out.println("executing request" + httpget.getRequestLine());

            CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget);
            HttpEntity entity = closeableHttpResponse.getEntity();
            String jsonStr = EntityUtils.toString(entity, "UTF-8");

//            httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
//            httpost.setConfig(requestConfig);
//            HttpResponse response = httpClient.execute(httpost);
//            String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println("json是:"+jsonStr);
            Map map = doXMLParse(jsonStr);
            return  map;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     * @param strxml
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    public static Map doXMLParse(String strxml) throws Exception {
        if(null == strxml || "".equals(strxml)) {
            return null;
        }

        Map m = new HashMap();
        InputStream in = String2Inputstream(strxml);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while(it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if(children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }

            m.put(k, v);
        }

        //关闭流
        in.close();

        return m;
    }
    /**
     * 获取子结点的xml
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if(!children.isEmpty()) {
            Iterator it = children.iterator();
            while(it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if(!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }
    public static InputStream String2Inputstream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

}
