package com.kingcjy.main.repository.user;

import com.kingcjy.main.TestConfig;
import com.kingcjy.main.entity.UserEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = TestConfig.class)
public class UserEntityRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void autowiredTest() {
        Assert.assertNotNull(userRepository);
    }

    @Test
    public void findOneTest() {
        UserEntity userEntity = userRepository.selectOneByPhoneNumber("");
        Assert.assertEquals(userEntity.getName(), "");
    }
}
