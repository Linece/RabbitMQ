package com.zdc.consumer.server.impl;

import com.alibaba.fastjson.JSONObject;
import com.zdc.consumer.entity.Merchant;
import com.zdc.consumer.server.MerchantService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {

    private String topicExchange = "TOPIC_BASIC_EXCHANGE";

    private String topicRoutingKey = "asd.zdc.qqq";

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<Merchant> getMerchantList(String name, int page, int limit) {
        return null;
    }

    @Override
    public Merchant getMerchantById(Integer id) {
        return null;
    }

    @Override
    public int add(Merchant merchant) {
        System.out.println("#############:"+merchant.getId());
        JSONObject jsonObject = new JSONObject();
        String s = jsonObject.toJSONString(merchant);
        jsonObject.put("type","add");
        jsonObject.put("desc","新增用户");
        jsonObject.put("content",s);
        amqpTemplate.convertAndSend(topicExchange,topicRoutingKey,jsonObject.toJSONString());
        return 0;
    }

    @Override
    public int update(Merchant merchant) {
        return 0;
    }

    @Override
    public int updateState(Merchant merchant) {
        return 0;
    }

    @Override
    public int delete(Integer id) {
        return 0;
    }

    @Override
    public int getMerchantCount() {
        return 0;
    }
}
