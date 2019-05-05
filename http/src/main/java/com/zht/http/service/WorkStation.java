package com.zht.http.service;

import android.support.annotation.NonNull;

import com.zht.http.HttpRequestProvider;
import com.zht.http.network.HttpRequest;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkStation {
    private static final int MAX_REQUEST_SIZE = 60;
    private static ThreadPoolExecutor sThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
        private AtomicInteger index = new AtomicInteger();
        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("http thread name is " + index.getAndIncrement());
            return thread;
        }
    });

    private Deque<MoocRequest> running = new ArrayDeque<>();
    private Deque<MoocRequest> cache = new ArrayDeque<>();
    private HttpRequestProvider requestProvider;

    public WorkStation() {
        requestProvider = new HttpRequestProvider();
    }

    public WorkStation(HttpRequestProvider requestProvider) {
        this.requestProvider = requestProvider;
    }

    public void add(MoocRequest request){
        if(running.size()>MAX_REQUEST_SIZE){
            cache.add(request);
        }else{
            doHttpRequest(request);
            running.add(request);
        }
    }

    private void doHttpRequest(MoocRequest request) {
        HttpRequest httpRequest = null;
        try {
            httpRequest = requestProvider.getHttpRequest(URI.create(request.getUrl()),request.getMethod());
        } catch (IOException e) {
            e.printStackTrace();
        }
        sThreadPool.execute(new HttpRunnable(httpRequest,request,this));
    }

    public void finish(MoocRequest request){
        running.remove(request);
        if(running.size()>MAX_REQUEST_SIZE){
            return ;
        }

        if(cache.size() == 0){
            return;
        }

        Iterator<MoocRequest> iterator = cache.iterator();
        while (iterator.hasNext()){
            MoocRequest next = iterator.next();
            running.add(next);
            iterator.remove();
            doHttpRequest(next);
        }
    }
}
