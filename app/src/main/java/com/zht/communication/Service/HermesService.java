package com.zht.communication.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.zht.communication.EventBusService;
import com.zht.communication.Request;
import com.zht.communication.Response;

public class HermesService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private EventBusService.Stub  mBinder  = new EventBusService.Stub(){

        @Override
        public Response send(Request request) throws RemoteException {
            return null;
        }
    };

    public static class HermesService0 extends HermesService {}

    public static class HermesService1 extends HermesService {}

    public static class HermesService2 extends HermesService {}

    public static class HermesService3 extends HermesService {}

    public static class HermesService4 extends HermesService {}

    public static class HermesService5 extends HermesService {}

    public static class HermesService6 extends HermesService {}

    public static class HermesService7 extends HermesService {}

    public static class HermesService8 extends HermesService {}

    public static class HermesService9 extends HermesService {}
}
