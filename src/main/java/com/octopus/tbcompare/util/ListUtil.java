package com.octopus.tbcompare.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-15
 * Time: 17:16
 */
public class ListUtil<T> {

    public List<T> getDiff(List<T> list1, List<T> list2) {
        Map<T,Integer> map = new HashMap<>(list1.size()+list2.size());
        List<T> diff = new ArrayList<>();
        List<T> maxList = list1;
        List<T> minList = list2;
        if(list2.size()>list1.size())
        {
            maxList = list2;
            minList = list1;
        }
        for (T T : maxList) {
            map.put(T, 1);
        }
        for (T T : minList) {
            Integer cc = map.get(T);
            if(cc!=null)
            {
                map.put(T, ++cc);
                continue;
            }
            map.put(T, 1);
        }
        for(Map.Entry<T, Integer> entry:map.entrySet())
        {
            if(entry.getValue()==1)
            {
                diff.add(entry.getKey());
            }
        }
        return diff;

    }
}
