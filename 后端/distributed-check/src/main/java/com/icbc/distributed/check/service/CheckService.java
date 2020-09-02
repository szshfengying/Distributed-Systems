package com.icbc.distributed.check.service;

import com.icbc.distributed.check.entity.NettingEntity;

import java.util.List;

public interface CheckService {
    List<NettingEntity> CheckResult();
}
