package cn.ylapl.config;

import cn.ylapl.entity.bank.BankAccount;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.model.GenericJpaRepository;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.jpa.ContainerManagedEntityManagerProvider;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.messaging.interceptors.TransactionManagingInterceptor;
import org.axonframework.monitoring.NoOpMessageMonitor;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * axon JPA配置
 *
 * @date: 2017/12/29
 * @time: 下午4:22
 * @author: Angle
 */
@Configuration
@Slf4j
public class JpaConfig {

    private final PlatformTransactionManager transactionManager;

    @Autowired
    public JpaConfig(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * 事件存储引擎
     */
    @Bean
    public EventStorageEngine eventStorageEngine(){
        return new InMemoryEventStorageEngine();
    }

    /**
     * 事务管理器
     */
    @Bean
    public TransactionManager axonTransactionManager() {
        return new SpringTransactionManager(transactionManager);
    }

    /**
     * 事件总线
     */
    @Bean
    public EventBus eventBus(){
        return new SimpleEventBus();
    }

    /**
     * 命令总线
     */
    @Bean
    public CommandBus commandBus() {
        return new SimpleCommandBus(axonTransactionManager(), NoOpMessageMonitor.INSTANCE);
    }

    /**
     * 事务拦截器
     */
    @Bean
    public TransactionManagingInterceptor transactionManagingInterceptor(){
        return new TransactionManagingInterceptor(new SpringTransactionManager(transactionManager));
    }

    /**
     * 实体管理提供商
     */
    @Bean
    public EntityManagerProvider entityManagerProvider() {
        return new ContainerManagedEntityManagerProvider();
    }

    @Bean
    public Repository<BankAccount> accountRepository(){
        return new GenericJpaRepository<>(entityManagerProvider(), BankAccount.class, eventBus());
    }
}