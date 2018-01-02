package cn.ylapl.controller;

import cn.ylapl.entity.bank.CreateAccountCommand;
import cn.ylapl.entity.bank.WithdrawMoneyCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.IdentifierFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yangle
 * @version $Id IndexController.java, v 0.1 2017-02-08 下午1:30 yangle Exp $$
 */
@Api(tags = "银行接口")
@RestController
@RequestMapping("/bank")
@Slf4j
public class BankController {

    @Autowired
    private CommandGateway commandGateway;

    @ApiOperation("创建账户接口")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public void index() {
        log.debug("创建");
        String id = IdentifierFactory.getInstance().generateIdentifier();
        CreateAccountCommand command = new CreateAccountCommand(id,"杨乐",1000);
        commandGateway.send(command);
        commandGateway.send(new WithdrawMoneyCommand(id, 500));
        commandGateway.send(new WithdrawMoneyCommand(id, 500));
    }
}