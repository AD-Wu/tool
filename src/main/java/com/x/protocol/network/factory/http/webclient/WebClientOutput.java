package com.x.protocol.network.factory.http.webclient;

import com.x.commons.collection.DataSet;
import com.x.commons.util.bean.New;
import com.x.commons.util.string.Strings;
import com.x.protocol.network.factory.NetworkConsent;
import com.x.protocol.network.factory.NetworkOutput;
import com.x.protocol.network.factory.NetworkService;
import com.x.protocol.network.factory.http.utils.HttpRequestHelper;
import com.x.protocol.network.factory.http.utils.HttpURLParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Desc
 * @Date 2020-03-07 13:29
 * @Author AD
 */
public class WebClientOutput extends NetworkOutput implements IWebClientOutput {
    
    private WebClientConsent consent;
    
    private Object requestData;
    
    public WebClientOutput(NetworkService service, NetworkConsent consent) {
        super(service, consent);
        this.consent = (WebClientConsent) consent;
    }
    
    @Override
    public String getDefaultURL() {
        return consent.url;
    }
    
    @Override
    public String getDefaultDownloadURL() {
        return consent.downloadURL;
    }
    
    @Override
    public String getDefaultUploadURL() {
        return consent.uploadURL;
    }
    
    @Override
    public String getDefaultMethod() {
        return consent.method;
    }
    
    @Override
    public String getDefaultContentType() {
        return consent.contentType;
    }
    
    @Override
    public String getDefaultCharset() {
        return consent.charset;
    }
    
    @Override
    public IWebClientHandler sendRequest(DataSet dataSet) {
        return this.sendRequest(consent.url, consent.method, consent.contentType, dataSet);
    }
    
    @Override
    public IWebClientHandler sendRequest(HttpEntity entity) {
        return this.sendRequest(consent.url, entity);
    }
    
    @Override
    public IWebClientHandler sendRequest(String url, byte[] bytes) {
        ContentType type = ContentType.create(consent.contentType, consent.charset);
        ByteArrayEntity entity = new ByteArrayEntity(bytes, type);
        
        return this.sendRequest(url, entity);
    }
    
    @Override
    public IWebClientHandler sendRequest(String url, byte[] bytes, ContentType contentType) {
        return this.sendRequest(url, new ByteArrayEntity(bytes, contentType));
    }
    
    @Override
    public IWebClientHandler sendRequest(String url, HttpEntity entity) {
        if (consent.httpClient == null) {
            return null;
        } else {
            HttpPost post = null;
            try {
                post = new HttpPost(url);
                post.setEntity(entity);
                HttpRequestHelper.setHttpClientHeader(post, consent.contentType, consent.charset, null);
                WebClientInput input = new WebClientInput(service, consent, requestData);
                input.future = consent.httpClient.execute(post, consent.localContext, input);
                return input;
            } catch (Exception e) {
                e.printStackTrace();
                if (post != null) {
                    try {
                        post.releaseConnection();
                    } catch (Exception ex) {
                    }
                }
                return null;
            }
        }
    }
    
    @Override
    public IWebClientHandler sendRequest(String url, String method, String contentType, DataSet dataSet) {
        if (consent.httpClient == null) {
            return null;
        } else {
            if (Strings.isNull(method)) {
                method = "POST";
            }
            method = method.toUpperCase();
            Object request = null;
            try {
                if ("POST".equals(method)) {
                    HttpPost post = new HttpPost(url);
                    List<BasicNameValuePair> pairs = New.list();
                    if (dataSet != null) {
                        Iterator<Map.Entry<String, Object>> it = dataSet.getMap().entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry<String, Object> next = it.next();
                            String key = next.getKey();
                            if (!Strings.isNull(key)) {
                                Object value = next.getValue();
                                if (value != null) {
                                    pairs.add(new BasicNameValuePair(key.toLowerCase(), value.toString()));
                                }
                            }
                        }
                    }
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, consent.charset);
                    post.setEntity(entity);
                    request = post;
                } else {
                    HttpURLParser parser = new HttpURLParser();
                    parser.parse(url, consent.charset);
                    if (dataSet != null) {
                        Iterator<Map.Entry<String, Object>> it = dataSet.getMap().entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry<String, Object> next = it.next();
                            String key = next.getKey();
                            if (!Strings.isNull(key)) {
                                Object value = next.getValue();
                                if (value != null) {
                                    parser.addParameter(key, value);
                                }
                            }
                        }
                    }
                    request = new HttpGet(parser.getURL());
                }
                HttpRequestHelper.setHttpClientHeader((AbstractHttpMessage) request,
                        contentType == null ? consent.contentType : contentType,
                        consent.charset, null);
                WebClientInput input = new WebClientInput(super.service, consent, requestData);
                input.future = consent.httpClient.execute((HttpUriRequest) request, consent.localContext, input);
                return input;
            } catch (Exception e) {
                e.printStackTrace();
                if (request != null) {
                    try {
                        ((HttpRequestBase) request).releaseConnection();
                    } catch (Exception ex) {
                    }
                }
                return null;
            }
        }
    }
    
    @Override
    public <T> void setRequestData(T t) {
        this.requestData = t;
    }
    
    @Override
    public IWebClientHandler uploadFile(File file) {
        return this.uploadFile(consent.uploadURL, file);
    }
    
    @Override
    public IWebClientHandler uploadFile(String url, File file) {
        return this.sendRequest(url, new FileEntity(file));
    }
    
    @Override
    public WebClientInput downloadFile(String savePath) {
        return this.downloadFile(consent.downloadURL, savePath);
    }
    
    @Override
    public WebClientInput downloadFile(String url, String savePath) {
        if (consent.httpClient == null) {
            return null;
        } else {
            HttpGet get = null;
            HttpEntity entity = null;
            WebClientInput input = new WebClientInput(super.service, consent, requestData);
            try {
                get = new HttpGet(url);
                input.future = consent.httpClient.execute(get, null);
                HttpResponse response = input.future.get();
                input.response = response;
                input.completed = true;
                if (response != null) {
                    StatusLine statusLine = response.getStatusLine();
                    input.statusCode = statusLine.getStatusCode();
                    input.statusMessage = statusLine.getReasonPhrase();
                    if (input.statusCode == HttpServletResponse.SC_OK) {
                        entity = response.getEntity();
                        File file = new File(savePath);
                        if (entity != null) {
                            FileOutputStream out = new FileOutputStream(file);
                            InputStream in = entity.getContent();
                            byte[] buf = new byte[1024];
                            int read = -1;
                            while ((read = in.read(buf)) != -1) {
                                out.write(buf, 0, read);
                            }
                            out.flush();
                            out.close();
                        }
                        input.succeed = true;
                        input.responseFile = file;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                input.failMessage = e.getMessage();
            } finally {
                if (entity != null) {
                    try {
                        EntityUtils.consume(entity);
                    } catch (IOException e) {
                    }
                }
                if (get != null) {
                    try {
                        get.releaseConnection();
                    } catch (Exception e) {
                    }
                }
            }
            return input;
        }
    }
    
    @Override
    public void flush() {
    
    }
    
}
