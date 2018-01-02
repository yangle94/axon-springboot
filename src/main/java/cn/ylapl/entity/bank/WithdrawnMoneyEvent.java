package cn.ylapl.entity.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 修改
 * date: 2017/12/29
 * time: 下午5:00
 * @author: Angle
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WithdrawnMoneyEvent {

    private String accountId;

    private long amount;
}