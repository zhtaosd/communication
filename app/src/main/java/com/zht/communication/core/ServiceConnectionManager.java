package com.zht.communication.core;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.widget.TextView;

import com.zht.communication.EventBusService;
import com.zht.communication.Request;
import com.zht.communication.Response;
import com.zht.communication.Service.HermesService;

import java.util.concurrent.ConcurrentHashMap;

public class ServiceConnectionManager {
    private static final ServiceConnectionManager instance = new ServiceConnectionManager();
    private final ConcurrentHashMap<Class<? extends HermesService>,EventBusService> mHermesService = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Class<? extends HermesService>,HermesServiceConnection> mHermesServiceConnection = new ConcurrentHashMap<>();

    public static ServiceConnectionManager getInstance(){
        return instance;
    }

    private ServiceConnectionManager() {
    }

    public void bind(Context context,String packageName,Class<? extends HermesService> service){
        HermesServiceConnection connection = new HermesServiceConnection(service);
        mHermesServiceConnection.put(service,connection);
        Intent intent;
        if(TextUtils.isEmpty(packageName)){
            intent = new Intent(context,service);
        }else{
            intent = new Intent();
            intent.setClassName(packageName,service.getName());
        }
        context.bindService(intent,connection,Context.BIND_AUTO_CREATE);
    }

    public Response request(Class<HermesService> hermesServiceClass, Request request) {
        EventBusService eventBusService = mHermesService.get(hermesServiceClass);
        try {
            Response response = eventBusService.send(request);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class HermesServiceConnection implements ServiceConnection{

        private Class<? extends HermesService> mClass;

        public HermesServiceConnection(Class<? extends HermesService> mClass) {
            this.mClass = mClass;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            EventBusService hermesService = EventBusService.Stub.asInterface(service);
            mHermesService.put(mClass,hermesService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mHermesService.remove(mClass);
        }
    }
}
