package com.tw;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LibraryTest {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private ByteArrayInputStream inContent = new ByteArrayInputStream(new byte[1024]);
    @Test
    public void test_checkInputStdInfo(){
        Menu menu = new Menu();
        assertTrue("this sentence should return true",menu.checkInputStdInfo("zhangsan,1234,书写:100,语文:99"));
        assertFalse("this sentence should return false because its format", menu.checkInputStdInfo("ahang,1234,书写,90,语文,90"));
        assertFalse("empty input should also return flase", menu.checkInputStdInfo(""));
    }

    @Test
    public void test_checkInputStdId(){
        Menu menu = new Menu();
        assertTrue("this sentence should return true", menu.checkInputStdId("1234,4321"));
        assertFalse("these not number input should return false", menu.checkInputStdId("wee,weew"));
        assertFalse("empty input should also return false", menu.checkInputStdId(""));
    }


    @Test
    public void test_addStudent(){

        Menu menu1 = new Menu();
        menu1.addStudent("张三,1234,数学:100,语文:80,英语:99");

        Map<String, Double> map = new HashMap<>();
        map.put("数学",100.0);
        map.put("语文",80.0);
        map.put("英语",99.0);
        Student student = new Student(map, "张三","1234");
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);

        Menu menu = mock(Menu.class);
        when(menu.getStudentList()).thenReturn(studentList);
    }

    @Test
    public void test_calculateMedian(){
        Menu menu = new Menu();
        menu.addStudent("张三,1234,数学:100,语文:80,英语:90");
        menu.addStudent("里斯,124,数学:100,语文:80,英语:90");
        assertEquals(270.0, menu.calculateMedian(), 0.1);
    }

}
