package com.intel.hpnl.core;

import java.util.List;
import java.util.ArrayList;

public class CqService {
  public CqService(EqService service, int num, long serviceNativeHandle) {
    this.eqService = service;
    this.num = num;
    this.serviceNativeHandle = serviceNativeHandle;
    init(serviceNativeHandle);
    cqThreads = new ArrayList<CqThread>();
    for (int i = 0; i < this.num; i++) {
      CqThread cqThread = new CqThread(this, i);
      cqThreads.add(cqThread);
    }
  }
  public void start() {
    for (CqThread cqThread : cqThreads) {
      cqThread.start();
    }
  }
  public void shutdown() {
    for (CqThread cqThread : cqThreads) {
      cqThread.iterrupt();
    }
  }

  public void join() {
    try {
      for (CqThread cqThread : cqThreads) {
        cqThread.join();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void handleCqCallback(long eq, int eventType, int blockId) {
    Connection connection = eqService.getConMap().get(eq);
    connection.handleCallback(eventType, blockId);
  }
  public native int wait_cq_event(int index);
  private native void init(long Service);
  public native void finalize();
  private long nativeHandle;
  private EqService eqService;
  private int num;
  private long serviceNativeHandle;
  private List<CqThread> cqThreads; 
}