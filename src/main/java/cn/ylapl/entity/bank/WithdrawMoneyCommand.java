package cn.ylapl.entity.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * 修改金额命令
 *
 * @date: 2017/12/29
 * @time: 下午4:57
 * @author: Angle
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WithdrawMoneyCommand {

    @TargetAggregateIdentifier
    private String accountId;

    private long amount;

}