package com.icbc.distributed.accopen.service;

import com.icbc.distributed.accopen.entity.AccIdTableEntity;
import com.icbc.distributed.accopen.entity.AccInfoTableEntity;
import com.icbc.distributed.accopen.vo.request.AccLoginEntity;

public interface LoginAccService {
    String loginAcc(AccLoginEntity accLoginEntity);
}
