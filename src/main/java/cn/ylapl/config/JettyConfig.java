package cn.ylapl.config;

import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Jetty线程设定
 * date: 2018/1/4
 * time: 上午11:19
 * author: Angle
 */
@Configuration
public class JettyConfig {

    @Profile("jetty")
    @Bean
    public JettyEmbeddedServletContainerFactory jettyEmbeddedServletContainerFactory(
            JettyServerCustomizer jettyServerCustomizer) {
        JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory();
        factory.addServerCustomizers(jettyServerCustomizer);
        return factory;
    }


    @Bean
    public JettyServerCustomizer jettyServerCustomizer() {
        return server -> {
            // Tweak the connection config used by Jetty to handle incoming HTTP
            // connections
            final QueuedThreadPool threadPool = server.getBean(QueuedThreadPool.class);
            threadPool.setMaxThreads(100);
            threadPool.setMinThreads(20);
        };
    }
}