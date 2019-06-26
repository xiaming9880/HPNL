package com.intel.hpnl.core;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;

public class RdmService {
  static {
    System.loadLibrary("hpnl");
  }

  public RdmService(int buffer_num, boolean is_server) {
    this.buffer_num = buffer_num;
    this.is_server = is_server;

    conMap = new ConcurrentHashMap<Long, RdmConnection>();
  }

  public RdmService init() {
    int res = init(buffer_num, is_server);
    if (res < 0)
      return null;
    this.worker = new RdmThread(this);
    this.worker.start();
    return this;
  }

  public int listen(String ip, String port) {
    return listen(ip, port, nativeHandle);
  }

  public void join() {
    try {
      this.worker.join(); 
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
    }
  }

  public void shutdown() {
    this.worker.shutdown();
  }

  public RdmConnection getConnection(String ip, String port) {
    return conMap.get(get_con(ip, port, nativeHandle));
  }

  public int waitEvent() {
    return wait_event(this.nativeHandle);
  }

  private void handleCallback(long handle, int eventType, int blockId, int blockSize) {
    RdmConnection connection = conMap.get(handle);
    if (connection == null) {
      throw new NullPointerException("connection is NULL when handling " + eventType + " event.");
    }
    connection.handleCallback(eventType, blockId, blockSize);
  }

  public void free() {
    free(this.nativeHandle);
  }

  private void establishConnection(long con_handle) {
    RdmConnection con = new RdmConnection(con_handle, this);
    con.setRecvCallback(recvCallback);
    con.setSendCallback(sendCallback);
    conMap.put(con_handle, con);
  }

  public void initBufferPool(int initBufferNum, int bufferSize, int nextBufferNum) {
    this.bufferPool = new MemPool(this, initBufferNum, bufferSize, nextBufferNum);
  }

  public void reallocBufferPool() {
    this.bufferPool.realloc();
  }

  public void setRecvCallback(RdmHandler callback) {
    recvCallback = callback;
  }

  public void setSendCallback(RdmHandler callback) {
    sendCallback = callback;
  }

  public void pushSendBuffer(long handle, int bufferId) {
    RdmConnection connection = conMap.get(handle);
    if (connection == null) {
      throw new NullPointerException("connection is null when putting " + bufferId + " bufferId"); 
    }
    connection.pushSendBuffer(bufferPool.getBuffer(bufferId));
  }

  public HpnlBuffer getSendBuffer(int bufferId) {
    return this.bufferPool.getBuffer(bufferId); 
  }

  public HpnlBuffer getRecvBuffer(int bufferId) {
    return this.bufferPool.getBuffer(bufferId);
  }

  public void set_buffer(ByteBuffer buffer, long size, int bufferId) {
    set_buffer1(buffer, size, bufferId, this.nativeHandle);
  }

  private native int init(int buffer_num, boolean is_server);
  private native int listen(String ip, String port, long nativeHandle);
  private native long get_con(String ip, String port, long nativeHandle);
  private native int wait_event(long nativeHandle);
  private native void set_buffer1(ByteBuffer buffer, long size, int bufferId, long nativeHandle);
  private native void free(long nativeHandle);

  private String addr;
  private int port;
  private int buffer_num;
  private boolean is_server;
  private RdmThread worker;
  private ConcurrentHashMap<Long, RdmConnection> conMap;
  private RdmHandler recvCallback;
  private RdmHandler sendCallback;
  private MemPool bufferPool;

  private long nativeHandle;
}
