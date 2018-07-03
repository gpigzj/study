package com.deep.study.test1;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

import java.util.HashMap;
import java.util.Map;

public class HelloWorld {

    public static void main(String[] args) {
        Range<Integer> k = Range.closed(0,9);
        Map<String, String> map = new HashMap<>();
        map.put("adfasdf", "adsfadf");
        map.put("adfasdfa", "adsfadf");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        for(int i : ContiguousSet.create(k,DiscreteDomain.integers())){
            System.out.println(i);
        }

    }

}
