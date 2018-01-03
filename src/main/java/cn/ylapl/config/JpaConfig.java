package cn.ylapl.config;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * axon JPA配置
 *
 * date: 2017/12/29
 * time: 下午4:22
 * @author: Angle
 */
@Configuration
@Slf4j
public class JpaConfig {

    /**
     * 事件总线（不对事件进行序列化）
     * @return 简单的事件总线
     */
    @Bean
    public EventBus eventBus() {
        return new SimpleEventBus();
    }
}