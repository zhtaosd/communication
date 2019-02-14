// EventBusService.aidl
package com.zht.communication;
import com.zht.communication.Request;
import com.zht.communication.Response;
interface EventBusService {

 Response send(in Request request);
}
