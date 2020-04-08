package com.example.demo_activity.test1.utils;


import cn.hutool.core.bean.BeanUtil;

import java.io.*;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * 对象工具类
 *
 * @author s.li
 * @create 2016-12-15-9:51
 */
public class ObjectUtils {

    /**
     * 把Obj换成Map
     *
     * @param obj
     * @return Map<String   ,   String>
     */
    public static Map<String, Object> objToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        return BeanUtil.beanToMap(obj);
    }

    /**
     * 判断对象是否非空
     *
     * @param o
     * @return
     */
    public static boolean isNull(Object o) {
        return null == o;
    }

    /**
     * 判断集合是否非空
     *
     * @param list
     * @return
     */
    public static boolean isNull(List<?> list) {

        return null == list || list.size() == 0;
    }

    /**
     * 判断集合是否非空
     *
     * @param set
     * @return
     */
    public static boolean isNull(Set<?> set) {

        return null == set || set.size() == 0;
    }

    /**
     * 判断集合是否为空
     *
     * @param map
     * @return
     */
    public static boolean isNull(Map<?, ?> map) {
        return null == map || map.size() == 0;
    }

    /**
     * 判断Long是否为空
     *
     * @param lg
     * @return
     */
    public static boolean isNull(Long lg) {
        return null == lg || lg == 0;
    }

    /**
     * 判断String是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        return null == str || str == "" || str.equals("");
    }

    /**
     * 判断Integer是否为空
     *
     * @param it
     * @return
     */
    public static boolean isNull(Integer it) {
        return null == it || it == 0;
    }

    public static boolean isNull(File file) {
        return null == file || !file.exists();
    }

    /**
     * 判断数组是否为空
     *
     * @param strs
     * @return
     */
    public static boolean isNull(Object[] strs) {
        return null == strs || strs.length == 0;
    }

    /**
     * 获取数字 空返回0
     *
     * @param number
     * @return
     */
    public static Number getNumber(Number number) {
        return ObjectUtils.isNull(number) ? 0L : number;
    }

    /**
     * 数字格式化
     *
     * @param number
     * @param pattern (转化格式，默认#.##，其它的自己上网查)
     * @return
     */
    public static String numberFormat(Number number, String... pattern) {
        if (isNull(pattern)) {
            return FORMAT.format(number);
        }
        return FORMAT.format(pattern[0]);
    }

    private static Format FORMAT = new DecimalFormat("#.##");

    /**
     * 克隆
     *
     * @param o
     * @return
     */
    public static Object clone(Object o) {
        if (null == o) {
            return null;
        }

        // 将对象序列化后写在流里,因为写在流里面的对象是一份拷贝,
        // 原对象仍然在JVM里
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(o);
            ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            return ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bos) {
                    bos.close();
                }
                if (null != oos) {
                    oos.close();
                }
                if (null != ois) {
                    ois.close();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 判断对象是否非空
     *
     * @param o
     * @return
     */
    public static boolean isNotNull(Object o) {
        return !isNull(o);
    }

    /**
     * 判断集合是否非空
     *
     * @param list
     * @return
     */
    public static boolean isNotNull(List<?> list) {

        return !isNull(list);
    }

    /**
     * 判断集合是否非空
     *
     * @param set
     * @return
     */
    public static boolean isNotNull(Set<?> set) {

        return !isNull(set);
    }

    /**
     * 判断集合是否为空
     *
     * @param map
     * @return
     */
    public static boolean isNotNull(Map<?, ?> map) {
        return !isNull(map);
    }

    /**
     * 判断Long是否为空
     *
     * @param lg
     * @return
     */
    public static boolean isNotNull(Long lg) {
        return !isNull(lg);
    }

    /**
     * 判断String是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNotNull(String str) {
        return !isNull(str);
    }

    /**
     * 判断Integer是否为空
     *
     * @param it
     * @return
     */
    public static boolean isNotNull(Integer it) {
        return !isNull(it);
    }

    public static boolean isNotNull(File file) {
        return !isNull(file);
    }

    /**
     * 判断数组是否为空
     *
     * @param strs
     * @return
     */
    public static boolean isNotNull(Object[] strs) {
        return !isNull(strs);
    }

    /**
     * 转换html标签
     *
     * @param text
     * @return
     */
    public static String replace(String text) {
        if (isNotNull(text)) {
            return text.replaceAll("<p .*?>", "\r\n").replaceAll("<br\\s*/?>", "\r\n").replaceAll("\\<.*?>", "") + "\r\n";
        }
        return "";
    }

    /**
     * 获取UUID
     *
     * @return
     */
    public static String getShortUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取文件后缀名
     *
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * 生产编号后4位
     *
     * @param random
     * @return
     */
    public static String getRandom(Integer random) {
        String number = "";
        random++;
        if (random >= 0 && random < 10) {
            number = "000" + random;
        } else if (random >= 10 && random < 100) {
            number = "00" + random;
        } else if (random >= 100 && random < 1000) {
            number = "0" + random;
        } else {
            number = "" + random;
        }
        return number;
    }

    /**
     * 判断增减值
     *
     * @param context
     */
    public static boolean isReplace(String context) {
        String str = context.replaceAll(",", "").replaceAll("%", "").replaceAll("-", "");
        if (ObjectUtils.isNotNull(context.replaceAll(",", "").replaceAll("%", "").replaceAll("-", ""))) {
            if ("-".equals(context.charAt(1))) {
                return false;
            }
            if (Math.round(Double.valueOf(str)) < 0) {
                return false;
            }
            if (Math.round(Double.valueOf(str)) > 0) {
                return true;
            }
        }
        return false;
    }

    /*
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * @param sourceBean 被提取的对象bean
     * @param targetBean 用于合并的对象bean
     * @return targetBean 合并后的对象
     * @Title: combineSydwCore
     * @Description: 该方法是用于相同对象不同属性值的合并，如果两个相同对象中同一属性都有值，
     * 那么sourceBean中的值会覆盖tagetBean重点的值
     * @author: jlliu
     * @date: 2018-09-01 10:40:21
     * @return: Object
     */
    @SuppressWarnings("unused")
    private Object combineSydwCore(Object sourceBean, Object targetBean) {
        Class sourceBeanClass = sourceBean.getClass();
        Class targetBeanClass = targetBean.getClass();

        Field[] sourceFields = sourceBeanClass.getDeclaredFields();
        Field[] targetFields = sourceBeanClass.getDeclaredFields();
        for (int i = 0; i < sourceFields.length; i++) {
            Field sourceField = sourceFields[i];
            Field targetField = targetFields[i];
            sourceField.setAccessible(true);
            targetField.setAccessible(true);
            try {
                if (!(sourceField.get(sourceBean) == null)) {
                    targetField.set(targetBean, sourceField.get(sourceBean));
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return targetBean;
    }
    /**
     * 检查项目编号第五位为数字的正则表达式
     */
    public static boolean checkNumberByAmount(String projectNumber) {

        String reg = "^\\d{8,}[ABC][G]?$";
        if (projectNumber.matches(reg)) {
            return true;
        } else {
            return false;
        }

    }
    /**
     * 检查项目编号第五位为字母的正则表达式
     */
    public static boolean checkNumberByLetter(String projectNumber) {

        String reg = "^\\d{4}[JFKTY]\\d{3,}[ABC][G]?$";
        if (projectNumber.matches(reg)) {
            return true;
        } else {
            return false;

        }
    }
    
    public static String strToNull(Object obj) {
    	String objStr = String.valueOf(obj);
        if (objStr == null || "".equals(objStr) || "null".equals(objStr)) {
            return "";
        }
        return objStr;
    }

    public static String strToNullStr(Object obj) {
        String s = strToNull(obj);
        if(ObjectUtils.isNull(s)){
            return "";
        }
        return s;
    }

    /**
     * 过滤值后是空字符串,返回true
     * @param variableValue 值
     * @author songsl
     */
    public static boolean filterIsNullValue(String variableValue){
        return ObjectUtils.isNull(variableValue) || "0".equals(variableValue) || "0.0".equals(variableValue) || "0.00".equals(variableValue) || "�".equals(variableValue) || "null".equals(variableValue);
    }

    /**
     * 取map对象里的值,前提是map对象不为null，key不为null
     * @param objectMap map对象
     * @param key map的key
     * @author songsl
     */
    public static String getMapObject(Map<String,Object> objectMap,String key){
        if(ObjectUtils.isNotNull(objectMap)){
            if(ObjectUtils.isNotNull(objectMap.get(key))){
                return objectMap.get(key).toString();
            }
        }
        return  "";
    }

}
