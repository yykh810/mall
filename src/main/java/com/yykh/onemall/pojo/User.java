package com.yykh.onemall.pojo;

import lombok.Data;

/**
 * @Author：yykh
 * @Descripton:
 */

@Data
public class User {
    private int id;
    private String password;
    private String name;
    private String salt;
    private String anonymousName;

    public String getAnonymousName(){
        if(null!=anonymousName)
            return anonymousName;
        if(null==name)
            anonymousName= null;
        else if(name.length()<=1)
            anonymousName = "*";
        else if(name.length()==2)
            anonymousName = name.substring(0,1) +"*";
        else {
            char[] cs =name.toCharArray();
            for (int i = 1; i < cs.length-1; i++) {
                cs[i]='*';
            }
            anonymousName = new String(cs);
        }
        return anonymousName;
    }
}
