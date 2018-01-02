package cn.ylapl.entity.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 创建账户事件
 *
 * @date: 2017/12/29
 * @time: 下午4:58
 * @author: Angle
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CreatedAccountEvent {

    private String accountId;

    private String accountName;

    private long amount;
}