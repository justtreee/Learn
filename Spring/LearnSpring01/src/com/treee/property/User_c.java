package com.treee.property;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by treee on -2018/3/2-
 */

public class User_c {
    public void setNames(String[] names) {
        this.names = names;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    private String[] names;
    private List<String> list;
    private Map<String, String> map;
    private Properties properties;

    public void test(){
        StringBuffer sb1 = new StringBuffer();
        for(String name : names){
            sb1.append(name).append(",");
        }

        StringBuffer sb2 = new StringBuffer();
        for(String l : list){
            sb2.append(l).append(",");
        }

        StringBuffer sb3 = new StringBuffer();
        Set<String> keySet = map.keySet();
        for(String ks : keySet){
            sb3.append("key: "+ ks + "  value: "+map.get(ks)).append(",");
        }

        System.out.println(sb1.toString()+"\n"+sb2.toString()+"\n"+sb3.toString());
        System.out.println(properties.getProperty("username"));

    }
}
