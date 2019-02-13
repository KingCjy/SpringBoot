package com.kingcjy.main;

import com.kingcjy.main.oauth.ClientResources;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
public class SpringBootApplicationTests {

    @Autowired
    ClientResources clientResources;

    @Test
    public void contextLoads() {
//        System.out.println(configurationTest.getClient().getClientId());
//        System.out.println(configurationTest.getClient().getAccessTokenUri());
//        System.out.println(configurationTest.getClient().getUserAutorizationUri());

        System.out.println(clientResources.getClient().getClientId());
    }
}

