package com.honeypot.honeypot.dao;

import com.honeypot.honeypot.entity.WarningSum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestWarningDao  {
    @Autowired
    private WarningDao warningDao;

    @Test
    public void testWarningDao(){

//        time = "%" + time + "%";
        List<WarningSum> sumsOfSensitive = warningDao.getWarningSum("%%",0);
        assertEquals(3,sumsOfSensitive.size());

    }
}
