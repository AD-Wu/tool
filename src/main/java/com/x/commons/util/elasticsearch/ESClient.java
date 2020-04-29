package com.x.commons.util.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;

import java.io.IOException;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * @Desc elasticsearch http 客户端
 * @Date 2020-04-30 01:38
 * @Author AD
 */
public final class ESClient extends RestHighLevelClient{
    
    // ------------------------ 变量定义 ------------------------
    
    private  RestHighLevelClient client;
    
    
    
    // ------------------------ 构造方法 ------------------------
    
    // public ESClient(String ip, int port) {
    //     this.client = getClient(ip, port);
    // }
    
    public ESClient(RestClientBuilder builder) {
        super(builder);
    }
    
    // ------------------------ 方法定义 ------------------------
    public boolean existIndex(String index) throws IOException {
        boolean exists =  client.indices().exists(new GetIndexRequest(index), DEFAULT);
        closeClient();
        return exists;
    }
    
    public void existIndexAsync(String index,ActionListener<Boolean> listener) throws IOException {
        client.indices().existsAsync(new GetIndexRequest(index), DEFAULT, listener);
    }
    
    // ------------------------ 私有方法 ------------------------
    private RestHighLevelClient getClient(String ip, int port) {
        return  new RestHighLevelClient(RestClient.builder(new HttpHost(ip, port)));
    }
    
    private void closeClient() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // public static void main(String[] args) throws IOException {
    //     ESClient client = new ESClient("localhost", 9000);
    //     client.existIndexAsync("ad", new ActionListener<Boolean>() {
    //
    //         @Override
    //         public void onResponse(Boolean exists) {
    //             System.out.println(exists.booleanValue());
    //             client.closeClient();
    //         }
    //
    //         @Override
    //         public void onFailure(Exception e) {
    //             System.out.println(Strings.getExceptionTrace(e));
    //             client.closeClient();
    //         }
    //     });
    //
    // }
    
}
