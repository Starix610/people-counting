package com.weiyun.peoplecounting;

import com.weiyun.peoplecounting.error.BusinessException;
import com.weiyun.peoplecounting.service.ComboxDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUploadApplicationTests {

    @Autowired
    private ComboxDataService comboxDataService;

    @Test
    public void contextLoads() throws IOException, BusinessException {
//        Map<String, Object> map = comboxDataService.getCoursesByTeacherId(12321);
//        List<Course> courseList = (List<Course>) map.get("courseList");
//        List<TeachDetail> teachDetailList = (List<TeachDetail>) map.get("teachDetailList");

    }

}
