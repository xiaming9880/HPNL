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

import java.util.concurrent.atomic.AtomicBoolean;

public class RdmThread extends Thread {
  public RdmThread(RdmService rdmService, int index) {
    this.rdmService = rdmService;
    this.index = index;
    running.set(true);
    this.setDaemon(true);
  }

  public void run() {
    while (running.get()) {
      if (this.rdmService.waitEvent(index) == -1) {
        shutdown();
      }
    }
    this.rdmService.free();
  }

  public void shutdown() {
    running.set(false); 
  }

  private RdmService rdmService;
  private int index;
  private final AtomicBoolean running = new AtomicBoolean(false);
}
