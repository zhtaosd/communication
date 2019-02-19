package com.zht.hermesuse;

import xiaofei.library.hermes.annotation.ClassId;
import xiaofei.library.hermes.annotation.MethodId;

@ClassId("UserManager")
public interface IUserManager {
    @MethodId("getName")
    public String getName();

    @MethodId("setName")
    public void setName(String name);
}
