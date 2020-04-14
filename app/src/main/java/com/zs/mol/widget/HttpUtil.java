package com.zs.mol.widget;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpUtil extends Object {

    Handler mHandler;
    String mReqUrl;
    String mReqData;
    boolean mNoCache;
    boolean mIgnoreSSLCert;
    Thread mThread;
    Object mResult;
    int mMethod;
    String mContentType;
    int mHttpStatusCode;
    int mConnectionTimeout;
    int mSocketTimeout;
    String mAgent;
    String mReferer;
    String mAccept;
    public Object object;

    public final static int REQ_SUCCEDED = 0;
    public final static int REQ_FAILED = 1;
    public final static int REQ_ALREADYRUN = 2;
    public final static int REQ_CANCELED = 3;

    private static final int GET = 0;
    private static final int POST = 1;
    private static final int PUT = 2;
    private static final int DELETE = 3;
    private static final int BITMAP = 4;
    private static final int RAW = 5;

    private static final String[] VERIFIED_HOST_NAMES = {"ssug.api.search.zum.com"};

    public HttpUtil() {
        this(null);
    }

    public HttpUtil(Handler handler) {
        mHandler = handler;
    }

    public Object getResult() {
        return mResult;
    }

    public int getHttpStatusCode() {
        return this.mHttpStatusCode;
    }

    public void setContentType(String type) {
        this.mContentType = type;
    }

    public void setAgent(String agent) {
        this.mAgent = agent;
    }

    public void setReferer(String referer) {
        this.mReferer = referer;
    }

    public void setAccept(String accept) {
        this.mAccept = accept;
    }

    public void setIgnoreSSLCert(boolean ignore) {
        this.mIgnoreSSLCert = ignore;
    }

    public void setConnectionTimeout(int timeout) {
        this.mConnectionTimeout = timeout;
    }

    public void setSocketTimeout(int timeout) {
        this.mSocketTimeout = timeout;
    }

    public static String getQueryParameter(String url, Uri uri, String key) {
        String query = uri.getQueryParameter(key);
        try {
            if (query == null) {
                key = key + "=";
                int idx = url.indexOf(key);
                int end = -1;
                if (idx > 0) {
                    idx += key.length();
                    end = url.indexOf('&', idx);

                    if (end != -1) {
                        query = url.substring(idx, end);
                    } else {
                        query = url.substring(idx);
                    }
                }
                if (query != null) {
                    query = URLDecoder.decode(query, "UTF-8");
                }
            }
        } catch (Exception e) {
        }
        return query;
    }

    public boolean reqPostData(String url, String data, Handler handler) {
        if (mThread == null || !mThread.isAlive()) {
            mHandler = handler;
            mReqUrl = url;
            mReqData = data;

            mThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    String result = postData(mReqUrl, mReqData, mHandler);

                }

            });
            mThread.start();

            return true;
        }

        return false;
    }


    public void cancelPostData() {
        if (mThread == null || !mThread.isAlive()) {
            mThread.stop();
        }
    }

    public String postData(String url, String data) {
        return postData(url, data, null);
    }


    public String post(String strUrl, String data, boolean noCache) {
        mMethod = POST;
        return (String) sendRequest(strUrl, data, noCache);
    }

    public String get(String strUrl, String urlParameters, boolean noCache) {
        mMethod = GET;

        if (urlParameters != null && urlParameters.length() > 0) {
            strUrl += "?" + urlParameters;
        }

        return (String) sendRequest(strUrl, "", noCache);
    }

    public Object getRawData(String strUrl, boolean noCache) {
        mMethod = RAW;

        return (Object) sendRequest(strUrl, "", noCache);
    }

    public boolean reqGet(String strUrl, String requestParameters) {
        mMethod = GET;

        mReqUrl = strUrl;
        mReqData = requestParameters;

        return reqSend();
    }

    public boolean reqPost(String strUrl, String requestParameters, boolean noCache) {
        mMethod = POST;

        mReqUrl = strUrl;
        mNoCache = noCache;
        mReqData = requestParameters;

        return reqSend();
    }

    public boolean reqRaw(String strUrl, boolean noCache) {
        mMethod = RAW;

        mReqUrl = strUrl;
        mNoCache = noCache;

        return reqSend();
    }

    private boolean reqSend() {
        if (mThread == null || !mThread.isAlive()) {
            mThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub

                    switch (mMethod) {
                        case POST:
                            mResult = post(mReqUrl, mReqData, mNoCache);
                            break;
                        case GET:
                            mResult = get(mReqUrl, mReqData, mNoCache);
                            break;
                        case RAW:
                            mResult = getRawData(mReqUrl, mNoCache);
                            break;
                    }
                }
            });
            mThread.start();

            return true;
        }

        return false;
    }

    private String getStringContent(HttpURLConnection connection) throws IOException {

        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();

        return (result != null && result.length() > 0) ? result : null;
    }

    public HttpURLConnection openHttpURLConnection(String strUrl) {
        try {
            URL url = new URL(strUrl);

            if (mIgnoreSSLCert) {
                TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }

                            public void checkClientTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }

                            public void checkServerTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }
                        }
                };

                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        for (String hostName : VERIFIED_HOST_NAMES) {
                            if (s.equals(hostName)) {
                                return true;
                            }
                        }
                        return false;
                    }
                });

                return httpsURLConnection;
            } else {
                return (HttpURLConnection) url.openConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Object sendRequest(String strUrl, String data, boolean noCache) {

        HttpURLConnection connection = null;

        try {
            mResult = null;
            connection = openHttpURLConnection(strUrl);

            if (connection == null) return null;

            connection.setConnectTimeout(25000);
            connection.setReadTimeout(30000);

            if (mAgent != null && !mAgent.isEmpty()) {
                connection.setRequestProperty("http.useragent", mAgent);
            }

            if (noCache) {
                connection.setRequestProperty("Cache-Control", "no-cache");
            }

            switch (mMethod) {
                case GET: {
                    connection.setRequestMethod("GET");

                    if (mAccept != null && mAccept.length() > 0) {
                        connection.setRequestProperty("Accept", mAccept);
                    }

                    if (mReferer != null && mReferer.length() > 0) {
                        connection.setRequestProperty("Referer", mReferer);
                    }

                    break;
                }
                case POST: {
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    if (mContentType != null && mContentType.length() > 0) {
                        connection.setRequestProperty("Content-Type", mContentType);
                    }

                    if (mConnectionTimeout > 0) {
                        connection.setConnectTimeout(mConnectionTimeout);
                    }

                    if (mSocketTimeout > 0) {
                        connection.setReadTimeout(mSocketTimeout);
                    }

                    writePostData(data, connection);
                    break;
                }
                case PUT: {
                    connection.setRequestMethod("PUT");

                    if (mContentType != null && mContentType.length() > 0) {
                        connection.setRequestProperty("Content-Type", mContentType);
                    }

                    writePostData(data, connection);
                    break;
                }
                case DELETE: {
                    break;
                }

                case BITMAP:
                case RAW: {
                    connection.setRequestMethod("GET");
                }

            }

            connection.connect();
            mHttpStatusCode = connection.getResponseCode();

            if (mHttpStatusCode == HttpURLConnection.HTTP_OK) {
                if (mMethod == BITMAP) {
                    mResult = getBitmapContent(connection);
                } else if (mMethod == RAW) {
                    mResult = getRawContent(connection);
                } else {
                    mResult = getStringContent(connection);
                }
                sendResultMessage(mHandler, REQ_SUCCEDED);
            } else {
                sendResultMessage(mHandler, REQ_FAILED);
            }

            connection.disconnect();

            return mResult;

        } catch (Exception e) {
            mResult = e;
            e.printStackTrace();

            if (connection != null) {
                try {
                    mHttpStatusCode = connection.getResponseCode();
                    Log.d("DebugLog", "response code : " + mHttpStatusCode);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            sendResultMessage(mHandler, REQ_FAILED);

            return null;
        }
    }

    private void sendResultMessage(Handler mHandler, int reqSucceded) {
        if (mHandler != null) {
            Message message = Message.obtain(mHandler, reqSucceded, this);
            mHandler.sendMessage(message);
        }
    }

    private void writePostData(String data, HttpURLConnection connection) throws IOException {
        if (connection == null || data == null) return;

        OutputStream os = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(data);
        writer.flush();
        writer.close();
        os.close();
    }

    private ByteBuffer getRawContent(HttpURLConnection connection) throws IOException {
        InputStream is = connection.getInputStream();

        int length = connection.getContentLength();
        if (length > 0) {
            byte[] buffer = new byte[length];
            is.read(buffer);
            return ByteBuffer.wrap(buffer);
        }

        return null;
    }

    private Bitmap getBitmapContent(HttpURLConnection connection) throws IOException {
        if (connection.getContentLength() > 0) {
            InputStream is = connection.getInputStream();
            Bitmap bm = BitmapFactory.decodeStream(is);
            return bm;
        }

        return null;
    }


    private String postData(String url, String data, Handler handler) {

        String result = null;
        Message msg = null;

        if (handler != null) {
            msg = new Message();
            msg.obj = this;
        }

        try {
            if (Thread.interrupted())
                throw new InterruptedException();

            URL connect_url = new URL(url);
            HttpURLConnection httpUrlCon = (HttpURLConnection) connect_url.openConnection();

            httpUrlCon.setDefaultUseCaches(false);
            httpUrlCon.setDoInput(true);
            httpUrlCon.setDoOutput(true);
            httpUrlCon.setRequestMethod("POST");
            //		httpUrlCon.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            httpUrlCon.setReadTimeout(10000 /* milliseconds */);
            httpUrlCon.setConnectTimeout(15000 /* milliseconds */);


            PrintWriter pw = new PrintWriter(new OutputStreamWriter(httpUrlCon.getOutputStream(), "UTF-8"));
            pw.write(data);
            pw.flush();

            if (Thread.interrupted())
                throw new InterruptedException();

            BufferedReader bf = new BufferedReader(new InputStreamReader(httpUrlCon.getInputStream(), "UTF-8"));
            StringBuilder buff = new StringBuilder();
            String line;
            while ((line = bf.readLine()) != null) {
                buff.append(line);
            }

            if (Thread.interrupted())
                throw new InterruptedException();

            result = buff.toString();
            if (msg != null) {
                msg.what = REQ_SUCCEDED;
                mResult = result;
            }

        } catch (IOException e) {

            if (msg != null) {
                msg.what = REQ_FAILED;
            }

        } catch (InterruptedException e) {
            if (msg != null) {
                msg.what = REQ_FAILED;
            }
        } finally {

        }
        // All done

        if (handler != null && msg != null) {
            handler.sendMessage(msg);
        }

        return result;
    }

    private static final Pattern CONTENT_DISPOSITION_PATTERN =
            Pattern.compile("attachment\\s*;\\s*filename\\s*=\\s*\"*([^\"]*)\"*");

    private static final String ESCAPE_PATTERN = "[\\/:*?\"<>|\\\\]";
    private static final String ESCAPE_START_PATTERN = "^\\.+";
}