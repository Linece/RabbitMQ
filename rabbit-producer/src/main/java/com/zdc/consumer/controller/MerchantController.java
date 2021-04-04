package com.zdc.consumer.controller;

import com.zdc.consumer.entity.Merchant;
import com.zdc.consumer.send.RabbitSender;
import com.zdc.consumer.server.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private RabbitSender rabbitSender;

    @RequestMapping("/add")
    public Integer merchantAdd (){
        Merchant merchant = new Merchant();
        merchant.setId(new Random().nextInt(101));
        merchant.setAccountNo("zdc001");
        merchant.setName("zdc");
        merchant.setAddress("梁野山");
        merchant.setAccountName("zdc");
        merchant.setState("1");
        int num = merchantService.add(merchant);
        return num;
    }


    @RequestMapping("/send")
    public String send() throws Exception{
        rabbitSender.send();
        return "success";
    }

}
