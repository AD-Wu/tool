package com.x.commons.util.elasticsearch;

import org.apache.http.HttpHost;
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
    
    private final RestHighLevelClient client;
    
    public ESClient(String ip, int port) {
        this.client = new RestHighLevelClient(RestClient.builder(new HttpHost(ip, port)));
    }
    
    public boolean existIndex(String index) throws IOException {
        return client.indices().exists(new GetIndexRequest(index), DEFAULT);
    }
    
    private void closeClient(){
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
