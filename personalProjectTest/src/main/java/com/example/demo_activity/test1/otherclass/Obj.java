package com.example.demo_activity.test1.otherclass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: yongl
 * @DATE: 2020/4/8 18:17
 */

public class Obj {
    //用Collections.synchronizedList()方法处理Arraylist，使之变得线程安全
    //private List<String> synchronizedList = Collections.synchronizedList(new ArrayList<>());
    //线程不安全
    private List<String> synchronizedList = new ArrayList<>();
    public void add(String s) {
        synchronizedList.add(s);
    }

    public void remove(String s) {
        synchronizedList.remove(s);
    }

    public int size() {
        return synchronizedList.size();
    }

}
