package com.lhr.milk;

import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author lhr
 * @Date:2022/5/25 15:44
 * @Version 1.0
 */
public class test {

    public static void main(String[] args) {
//        List<Map> arrayList = new ArrayList<>();
//        String detail = "[{\"name\": \"霸气榴莲王\", \"price\": 33, \"types\": \"正常温 少糖\", \"number\": 3, \"joinCartId\": 84}, {\"name\": \"草莓魔法棒\", \"price\": 13, \"types\": \"\", \"number\": 1, \"joinCartId\": 85}, {\"name\": \"蜜蜜芝士火龙果\", \"price\": 18, \"types\": \"\", \"number\": 1, \"joinCartId\": 86}]";
//        String[] numberList = detail.substring(0,detail.length()-2).replace("\"","").split("},");
//        Arrays.stream(numberList).forEach(list->{
//            String[] split = list.split(",");
////            for (int i = 0; i < split.length; i++) {
////                String name = split[i].split(":")[1];
////            }
//            HashMap<String, String> map = new HashMap<>();
//            String name = split[0].split(":")[1];
//            String price = split[1].split(":")[1];
//            String types = split[2].split(":")[1];
//            String number = split[3].split(":")[1];
//            String cart = split[4].split(":")[1];
//
//            map.put("name",name);
//            map.put("price",price);
//            map.put("types",types);
//            map.put("number",number);
//            map.put("cart",cart);
//            arrayList.add(map);
//        });
//
//        for (int i = 0; i < arrayList.size(); i++) {
//            Set set = arrayList.get(i).keySet();
//            for(Object ignored :set){
//                System.out.println(ignored +""+ arrayList.get(i).get(ignored));
//            }
//        }

//        Date date = new Date();
//        System.out.println(new DateTime());

        String cartId = "[97, 98, 99, 100, 101, 102]";
        String substring = cartId.split("]")[0].substring(1).replace(" ","");
        String[] split = substring.split(",");
        ArrayList<Integer> arrayList = new ArrayList<>();
        Arrays.stream(split).forEach(item->{
            arrayList.add(Integer.parseInt(item));
        });

        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i));
        }
    }
}
