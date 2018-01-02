package cn.ylapl.entity.bank;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * 银行账户
 *
 * date: 2017/12/29
 * time: 下午4:28
 * @author: Angle
 */
@Aggregate(repository = "accountRepository")
@Entity
@Data
@Accessors(chain = true)
@Slf4j
@NoArgsConstructor
public class BankAccount {

    @Id
    @AggregateIdentifier
    private String accountId;

    private String accountName;

    private BigDecimal balance;

    @CommandHandler
    public BankAccount(CreateAccountCommand command){
        apply(new CreatedAccountEvent(command.getAccountId(), command.getAccountName(), command.getAmount()));
    }

    @CommandHandler
    public void handle(WithdrawMoneyCommand command){
        apply(new WithdrawnMoneyEvent(command.getAccountId(), command.getAmount()));
    }

    @EventHandler
    public void on(CreatedAccountEvent event){
        this.accountId = event.getAccountId();
        this.accountName = event.getAccountName();
        this.balance = new BigDecimal(event.getAmount());
        log.info("Account {} is created with balance {}", accountId, this.balance);
    }

    @EventHandler
    public void on(WithdrawnMoneyEvent event) {

        BigDecimal result = this.balance.subtract(new BigDecimal(event.getAmount()));
        if(result.compareTo(BigDecimal.ZERO) < 0) {
            log.error("Cannot withdraw more money than the balance!");
        }
        else {
            this.balance = result;
            log.info("Withdraw {} from account {}, balance result: {}", event.getAmount(), accountId, balance);
        }
    }
}