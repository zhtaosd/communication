package com.zht.communication.core;

import android.content.Context;
import android.os.UserManager;

import com.alibaba.fastjson.JSON;
import com.zht.communication.Request;
import com.zht.communication.Response;
import com.zht.communication.Service.HermesService;
import com.zht.communication.annotion.ClassId;
import com.zht.communication.bean.RequestBean;
import com.zht.communication.bean.RequestParameter;
import com.zht.communication.utils.TypeUtils;

import java.lang.reflect.Method;


/**
 * @author zhanghaitao
 * created at 2019/2/20  14:18
 */
public class Hermes {
    private static final Hermes instance = new Hermes();
    private Context sContext;
    private ServiceConnectionManager serviceConnectionManager;
    private TypeCenter typeCenter;

    //得到对象
    public static final int TYPE_NEW = 0;
    //得到单例
    public static final int TYPE_GET = 1;

    public static Hermes getDefault() {
        return instance;
    }

    private Hermes() {
        serviceConnectionManager = ServiceConnectionManager.getInstance();
        typeCenter = TypeCenter.getInstance();
    }

    public void connect(Context context, Class<? extends HermesService> service) {
        connect(context, null, service);
    }

    private void connect(Context context, String packageName, Class<? extends HermesService> service) {
        init(context);
        serviceConnectionManager.bind(context.getApplicationContext(), packageName, service);
    }

    public void init(Context context) {
        sContext = context.getApplicationContext();
    }

    public void register(Class<?> clazz) {
        typeCenter.register(clazz);
    }


    public <T> Response getInstance(Class<T> clazz, Object... parameters) {
        Response response = sendRequest(HermesService.class, clazz, null, parameters);
        return null;
    }

    private <T> Response sendRequest(Class<HermesService> hermesServiceClass, Class<T> clazz,
                                     Method method, Object[] parameters) {
        RequestBean requestBean = new RequestBean();
        if (clazz.getAnnotation(ClassId.class) == null) {
            requestBean.setClassName(clazz.getName());
            requestBean.setResultClassName(clazz.getName());
        } else {
            requestBean.setClassName(clazz.getAnnotation(ClassId.class).value());
            requestBean.setResultClassName(clazz.getAnnotation(ClassId.class).value());
        }
        if (method != null) {
            requestBean.setMethodName(TypeUtils.getMethodId(method));
        }

        RequestParameter[] requestParameters = null;
        if (parameters != null && parameters.length > 0) {
            requestParameters = new RequestParameter[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Object parameter = parameters[i];
                String className = parameter.getClass().getName();
                String parameterValue = JSON.toJSONString(parameter);
                RequestParameter requestParameter = new RequestParameter(className,parameterValue);
                requestParameters[i] = requestParameter;
            }
        }
        if(requestParameters != null){
            requestBean.setRequestParameter(requestParameters);
        }

        Request request = new Request(JSON.toJSONString(requestBean),TYPE_GET);
        return serviceConnectionManager.request(hermesServiceClass,request);
    }
}
