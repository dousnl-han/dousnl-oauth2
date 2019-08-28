package com.aiways.recovery.web.rest.util;

import liquibase.util.Validate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RegexUtil
 * @Description TODO 反射 工具类
 * @Author guo.lee
 * @Date 2019/2/28 10:22
 */
public class ReflexUtil {


//    public static void main(String[] args) throws Exception{
//        System.out.println(checkEmail("14_8@qw.df"));
//        System.out.println(checkMobileNumber("15071392085"));
//        EnBatteryPack ebp = new EnBatteryPack();
//        ebp.setBatteryPackCode("ssssssss");
//        try {
//            Object batteryPackCode = getGetMethod(ebp, "batteryPackCode");
//            System.out.println(batteryPackCode);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        EnBatteryPack ebp = new EnBatteryPack();
//        ebp.setId("222222222222222222");
//        ebp.setBatteryPackCode("AAA");
//        EnBatteryPack ebp2 = new EnBatteryPack();
////        System.out.println(getClassType(ebp2,"batteryPackCode"));
//        Map<String, Object> fields = getFields(ebp);
//        for (String s : fields.keySet()) {
//            System.out.println("字段名:"+ s+",值:"+fields.get(s));
//        }

//    }


    public static Map<String,Object> getFields(Object object)  {
        Field[] fields = getAllFields(object.getClass());
        Map<String,Object> map = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            Object obj = null;
            try {
                obj = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            map.put(field.getName(), obj);
        }
        return map;
    }


    public static Field[] getAllFields(final Class<?> cls) {
        final List<Field> allFieldsList = getAllFieldsList(cls);
        return allFieldsList.toArray(new Field[allFieldsList.size()]);
    }

    public static List<Field> getAllFieldsList(final Class<?> cls) {
        Validate.isTrue(cls != null, "The class must not be null");
        final List<Field> allFields = new ArrayList<Field>();
        Class<?> currentClass = cls;
        while (currentClass != null) {
            final Field[] declaredFields = currentClass.getDeclaredFields();
            for (final Field field : declaredFields) {
                allFields.add(field);
            }
            currentClass = currentClass.getSuperclass();
        }
        return allFields;
    }

}
