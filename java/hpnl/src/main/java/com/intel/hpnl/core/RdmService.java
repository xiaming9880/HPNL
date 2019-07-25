// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package com.intel.hpnl.core;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.ArrayList;

public class RdmService {
  static {
    System.loadLibrary("hpnl");
  }

  public RdmService(int worker_num, int buffer_num, boolean is_server) {
    this.worker_num = worker_num;
    this.buffer_num = buffer_num;
    this.is_server = is_server;

    this.workers = new ArrayList<RdmThread>();
    conMap = new ConcurrentHashMap<Long, RdmConnection>();
  }

  public RdmService init() {
    int res = init(worker_num, buffer_num, is_server);
    if (res < 0)
      return null;
    for (int i = 0; i < this.worker_num; i++) {
      RdmThread rdmThread = new RdmThread(this, i);
      this.workers.add(rdmThread);
    }
    for (RdmThread rdmThread : this.workers) {
      rdmThread.start(); 
    }
    return this;
  }

  public int listen(String ip, String port) {
    return listen(ip, port, nativeHandle);
  }

  public void join() {
    try {
      for (RdmThread rdmThread : this.workers) {
        rdmThread.join();  
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
    }
  }

  public void shutdown() {
    for (RdmThread rdmThread : this.workers) {
      rdmThread.shutdown(); 
    }
  }

  public RdmConnection getConnection(String ip, String port) {
    return conMap.get(get_con(ip, port, nativeHandle));
  }

  public int waitEvent(int index) {
    return wait_event(index, this.nativeHandle);
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

  private native int init(int worker_num, int buffer_num, boolean is_server);
  private native int listen(String ip, String port, long nativeHandle);
  private native long get_con(String ip, String port, long nativeHandle);
  private native int wait_event(int index, long nativeHandle);
  private native void set_buffer1(ByteBuffer buffer, long size, int bufferId, long nativeHandle);
  private native void free(long nativeHandle);

  private String addr;
  private int port;
  private int worker_num;
  private int buffer_num;
  private boolean is_server;
  private List<RdmThread> workers;
  private ConcurrentHashMap<Long, RdmConnection> conMap;
  private RdmHandler recvCallback;
  private RdmHandler sendCallback;
  private MemPool bufferPool;

  private long nativeHandle;
}
