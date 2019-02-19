package com.zht.hermesuse;

import xiaofei.library.hermes.annotation.ClassId;
import xiaofei.library.hermes.annotation.MethodId;

@ClassId("UserManager")
public class UserManager implements IUserManager {
    private static UserManager instance = null;
    private static String name1 ="123";
    public static UserManager getInstance(){
        if(instance == null){
            instance = new UserManager();
        }
        return instance;
    }

    private  UserManager() {
    }

    private String name;

    @MethodId("getName")
    @Override
    public String getName() {
        return name+name1;
    }

    @MethodId("setName")
    @Override
    public void setName(String name) {
        this.name = name;
    }
}
