#ifndef FISTACK_H
#define FISTACK_H

#include <rdma/fabric.h>
#include <rdma/fi_domain.h>
#include <rdma/fi_endpoint.h>
#include <rdma/fi_cm.h>

#include <map>

#include "HPNL/BufMgr.h"
#include "HPNL/FIConnection.h"
#include "HPNL/ConMgr.h"
#include "HPNL/Handle.h"

class FIStack {
  public:
    FIStack(const char*, const char*, uint64_t, int);
    ~FIStack();
    HandlePtr bind();
    void listen();
    HandlePtr connect(BufMgr*, BufMgr*);
    HandlePtr accept(void*, BufMgr*, BufMgr*);
    uint64_t reg_rma_buffer(char*, uint64_t, int);
    void unreg_rma_buffer(int rdma_buffer_id);
    Chunk* get_rma_chunk(int);
    void shutdown();
    void reap(void*);
    FIConnection* get_connection(fid* id);
    fid_fabric* get_fabric();
    fid_cq** get_cqs();

  private:
    uint64_t seq_num;
    int buffer_num;
    int total_buffer_num;
    fid_fabric *fabric;
    fid_domain *domain;
    fi_info *hints, *info;
    fid_eq *peq;
    fid_pep *pep;

    std::map<fid*, FIConnection*> conMap;
    HandlePtr peqHandle;

    fid_cq *cqs[WORKERS];
    Handle *cqHandle[WORKERS];

    fid_wait *waitset;

    std::map<int, Chunk*> chunkMap;
    std::mutex mtx;
};

#endif
