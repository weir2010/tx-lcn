package com.lorne.tx.thread;

import com.lorne.tx.Constants;

/**
 * create by lorne on 2017/8/9
 */
public abstract class HookRunnable implements Runnable {

    private volatile boolean hasOver;

    @Override
    public void run() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                Constants.hasExit = true;
                while (!hasOver){}
            }
        };
        if(!Constants.hasExit) {
            Runtime.getRuntime().addShutdownHook(thread);
        }else{
            System.out.println("jvm has exit..");
            return;
        }
        try {
            run0();
        }finally {

            hasOver = true;

            if (!thread.isAlive()) {
                Runtime.getRuntime().removeShutdownHook(thread);
            }
        }
    }

    public abstract void run0();

}
