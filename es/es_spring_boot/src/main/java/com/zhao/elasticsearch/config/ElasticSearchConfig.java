package com.zhao.elasticsearch.config;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 * es8的Java客户端配置
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/12/23
 */
//@ConfigurationProperties(prefix = "elasticsearch") //配置的前缀
@Configuration
@Slf4j
public class ElasticSearchConfig {

    @Value("${spring.elasticsearch.rest.host}")
    private String host;
    @Value("${spring.elasticsearch.rest.enable}")
    private boolean enable;
    @Value("${spring.elasticsearch.rest.port}")
    private int port;
    @Value("${spring.elasticsearch.rest.username}")
    private String userName;
    @Value("${spring.elasticsearch.rest.password}")
    private String passWord;
    @Value("${spring.elasticsearch.rest.crtName}")
    private String tempCrtName;

    private static String crtName;

    @PostConstruct
    private void init() {
        crtName = tempCrtName;
    }

    /**
     * 同步方式
     *
     * @return
     */
    @Bean
    public ElasticsearchClient elasticsearchClient() {
//        RestClient restClient = RestClient.builder(toHttpHost()).build();
//        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchTransport transport = getElasticsearchTransport(userName, passWord);
        return new ElasticsearchClient(transport);
    }

    /**
     * 异步方式
     *
     * @return
     */
    @Bean
    public ElasticsearchAsyncClient elasticsearchAsyncClient() {
//        RestClient restClient = RestClient.builder(toHttpHost()).build();
//        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchTransport transport = getElasticsearchTransport(userName, passWord);
        return new ElasticsearchAsyncClient(transport);
    }

    private ElasticsearchTransport getElasticsearchTransport(String username, String passwd) {
        // 账号密码的配置
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, passwd));

        // 自签证书的设置，并且还包含了账号密码
        RestClientBuilder.HttpClientConfigCallback callback = httpAsyncClientBuilder -> httpAsyncClientBuilder
                .setSSLContext(buildSSLContext())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .setDefaultCredentialsProvider(credentialsProvider);

        // 用builder创建RestClient对象
        RestClient client = RestClient
                .builder(toHttpHost())
                .setHttpClientConfigCallback(callback)
                .build();
        return new RestClientTransport(client, new JacksonJsonpMapper());
    }

    private SSLContext buildSSLContext() {
        ClassPathResource resource = new ClassPathResource(crtName);
        SSLContext sslContext = null;
        try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            Certificate trustedCa;
            try (InputStream is = resource.getInputStream()) {
                trustedCa = factory.generateCertificate(is);
            }
            KeyStore trustStore = KeyStore.getInstance("pkcs12");
            trustStore.load(null, null);
            trustStore.setCertificateEntry("ca", trustedCa);
            SSLContextBuilder sslContextBuilder = SSLContexts.custom()
                    .loadTrustMaterial(trustStore, null);
            sslContext = sslContextBuilder.build();
        } catch (CertificateException | IOException | KeyStoreException | NoSuchAlgorithmException |
                 KeyManagementException e) {
            log.error("ES连接认证失败", e);
        }

        return sslContext;
    }
    /**
     * 解析配置的字符串，转为HttpHost对象数组
     *
     * @return
     */
    private HttpHost toHttpHost() {
        HttpHost httpHost = new HttpHost(host, port, "https");
        return httpHost;
    }

//    /**
//     * 解析配置的字符串hosts，转为HttpHost对象数组
//     *
//     * @return
//     */
//    private HttpHost[] toHttpHost() {
//        if (!StringUtils.hasLength(hosts)) {
//            throw new RuntimeException("invalid elasticsearch configuration. elasticsearch.hosts不能为空！");
//        }
//
//        // 多个IP逗号隔开
//        String[] hostArray = hosts.split(",");
//        HttpHost[] httpHosts = new HttpHost[hostArray.length];
//        HttpHost httpHost;
//        for (int i = 0; i < hostArray.length; i++) {
//            String[] strings = hostArray[i].split(":");
//            httpHost = new HttpHost(strings[0], Integer.parseInt(strings[1]), "http");
//            httpHosts[i] = httpHost;
//        }
//
//        return httpHosts;
//    }

}
