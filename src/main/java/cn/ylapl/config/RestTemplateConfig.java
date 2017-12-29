package cn.ylapl.config;

import okhttp3.OkHttpClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * restTemplate配置文件，启用okHttp3
 *
 * @date: 2017/12/29
 * @time: 上午11:44
 * @author: Angle
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 此restTemp
     * @return restTemp对象
     */
    @Bean
    public RestTemplate restTemplate() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        return new RestTemplateBuilder()
                    .requestFactory(new OkHttp3ClientHttpRequestFactory(okHttpClient))
                .build();
    }
}