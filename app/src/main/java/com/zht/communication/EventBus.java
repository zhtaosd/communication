package com.zht.communication;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
* 用来进行注册以及发送消息的控制类
* @author zhanghaitao
* created at 2019/2/13  16:54
*/
public class EventBus {

    private static EventBus instance = new EventBus();

    /**
     * 保存含有接受方法的的键值对
     */
    private Map<Object, List<SubScribeMethod>> cacheMap;

    /**
     * 用来进行执行子线程操作的线程池
     */
    private ExecutorService executorService;

    /**
     * 用来进行子线程与主线程的切换
     */
    private Handler handler;

    public static EventBus getInstance(){
        return instance;
    }

    private  EventBus() {
        cacheMap = new HashMap<>();
        executorService = Executors.newCachedThreadPool();
        handler = new Handler(Looper.getMainLooper());
    }

    public void register(Object activity){
        Class<?> clazz = activity.getClass();
        List<SubScribeMethod> list = cacheMap.get(activity);
        if(list == null){
            //获取该类中的list列表并添加道map中
            list = getSubScribeMethods(activity);
            cacheMap.put(activity,list);
        }
    }

    private List<SubScribeMethod> getSubScribeMethods(Object activity) {
        List<SubScribeMethod> list = new ArrayList<>();
        Class clazz = activity.getClass();
        while(clazz != null){
            String name = clazz.getName();
            if(name.startsWith("java.")||name.startsWith("javax.")||name.startsWith("android.")){
                break;
            }
            Method[] methods  = clazz.getDeclaredMethods();
            for (Method method : methods) {
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                if(subscribe  == null){
                    continue;
                }

                Class[] parameters = method.getParameterTypes();
                //只接受一个参数的方法
                if(parameters.length != 1){
                    throw new RuntimeException("eventbus只能接收到一个参数");
                }
                ThreadMode threadMode = subscribe.threadMode();
                SubScribeMethod subScribeMethod = new SubScribeMethod(method,threadMode,parameters[0]);
                list.add(subScribeMethod);
            }
            clazz = clazz.getSuperclass();
        }
        return list;
    }

    public void post(final Object friend){
        Set<Object> set = cacheMap.keySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            final Object activity = iterator.next();
            List<SubScribeMethod> list = cacheMap.get(activity);
            for (final SubScribeMethod subScribeMethod : list) {
                if(subScribeMethod.getEvenType().isAssignableFrom(friend.getClass())){
                    switch (subScribeMethod.getThreadMode()){
                        case Async://接受方法在子线程中执行
                            if(Looper.myLooper() == Looper.getMainLooper()){
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        //执行相关的方法
                                        invoke(subScribeMethod,activity,friend);
                                    }
                                });
                            }else{
                                invoke(subScribeMethod,activity,friend);
                            }
                            break;
                        case MainThread://接受方法在主线程中执行
                            if(Looper.myLooper() == Looper.getMainLooper()){
                                invoke(subScribeMethod,activity,friend);
                            }else{
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(subScribeMethod,activity,friend);
                                    }
                                });
                            }
                            break;
                        case PostThread://不做处理
                            break;
                    }
                }
            }
        }
    }
    private void invoke(SubScribeMethod subScribeMethod,Object activity,Object feiend){
        Method method = subScribeMethod.getMethod();
        try {
            method.invoke(activity,feiend);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
