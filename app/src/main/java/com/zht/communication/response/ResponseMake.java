package com.zht.communication.response;

import com.alibaba.fastjson.JSON;
import com.zht.communication.Request;
import com.zht.communication.Response;
import com.zht.communication.bean.RequestBean;
import com.zht.communication.core.TypeCenter;

/**
*
* @author zhanghaitao
* created at 2019/2/21  19:43
*/
public abstract class ResponseMake {
    protected Class<?> resultClass;
    protected TypeCenter typeCenter = TypeCenter.getInstance();

    protected abstract Object invokeMethod();
    protected abstract void setMethod(RequestBean requestBean);

    public Response makeResponse(Request request){
        RequestBean requestBean = JSON.parseObject(request.getData(),RequestBean.class);
        resultClass = typeCenter.getClassType(requestBean.getResultClassName());
        setMethod(requestBean);
        return null;
    }
}
