package com.x.commons.util.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;

import java.io.IOException;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * @Desc elasticsearch http 客户端
 * @Date 2020-04-30 01:38
 * @Author AD
 */
public final class ESClient {
    
    // ------------------------ 变量定义 ------------------------
    
    private final RestHighLevelClient client;
    
    // ------------------------ 构造方法 ------------------------
    
    ESClient(String ip, int port) {
        this.client = new RestHighLevelClient(RestClient.builder(new HttpHost(ip, port)));
    }
    
    // ------------------------ 方法定义 ------------------------
    
    public boolean existIndex(String index) throws IOException {
        return client.indices().exists(new GetIndexRequest(index), DEFAULT);
    }
    
    public void existIndexAsync(String index, ActionListener<Boolean> listener) throws IOException {
        client.indices().existsAsync(new GetIndexRequest(index), DEFAULT, listener);
    }
    
    public boolean createIndex(){
        
        return false;
    }
    
    void close() throws IOException {
        client.close();
    }
    
    // ------------------------ 私有方法 ------------------------
    public static void main(String[] args) throws IOException {
        ESClient client = ESClients.getClient("localhost", 9200);
        try {
            boolean ad = client.existIndex("ad");
            System.out.println(ad);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            client.existIndexAsync("ad", new ActionListener<Boolean>() {
                
                @Override
                public void onResponse(Boolean aBoolean) {
                    System.out.println(aBoolean);
                }
                
                @Override
                public void onFailure(Exception e) {
                    System.out.println(e + "<<<");
                }
            });
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
