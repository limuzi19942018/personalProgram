package com.example.demo_activity;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: yongl
 * @DATE: 2020/1/17 16:55
 */

public class TestDemo {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(TestDemo.class);
    public void testOrder(Double d1){
        Double []  d={8.0,9.2,10.5,87.3,200.25,312.5};
        List<Object> list = new ArrayList<>(d.length+1);
        for (int i = 0; i <d.length ; i++) {
            BigDecimal compareD1 = new BigDecimal(d1);
            List<Double> doubles = Arrays.asList(d);
            if(compareD1.compareTo(new BigDecimal(d[d.length-1]))>0){
                list.addAll(doubles);
                list.add(d1);
                break;
            }
            if(compareD1.compareTo(new BigDecimal(d[0]))<0){
                list.add(d1);
                list.addAll(doubles);
                break;
            }
            list.add(d[i]);
            if(i<d.length-1){
                BigDecimal bigDecimald1 = new BigDecimal(d[i]);
                BigDecimal bigDecimald2 = new BigDecimal(d[i+1]);
                if(compareD1.compareTo(bigDecimald1)>0 && compareD1.compareTo(bigDecimald2)<0){
                    list.add(d1);
                }
            }
        }
        System.out.println(list.toString());
    }

   @Test
    public void test(){
        //testOrder(7.55);
       int i1=100;
       Double d1=2555.25;
       BigDecimal divide = new BigDecimal(d1).divide(new BigDecimal(i1));
       System.out.println(divide.toString());
   }

   @Test
    public void testFormula(){
       boolean blank1 = StringUtils.isBlank("");
       boolean blank2 = StringUtils.isBlank(null);
       boolean empty1 = StringUtils.isEmpty("");
       boolean empty2 = StringUtils.isEmpty(null);
       System.out.println("111"+blank1);
       System.out.println("222"+blank2);
       System.out.println("333"+empty1);
       System.out.println("444"+empty2);
       //第一次提交
       //第二次提交
       //第三次提交


   }
    @Test
    public void testVersion(){
        String springVersion = SpringVersion.getVersion();
        LOGGER.info("spring版本号:{}",springVersion);
        String springBootVersion = SpringBootVersion.getVersion();
        LOGGER.info("springboot版本号:{}",springBootVersion);
    }
}
