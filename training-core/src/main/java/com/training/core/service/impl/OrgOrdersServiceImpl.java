package com.training.core.service.impl;

import com.training.core.repo.OrgOrdersMapper;
import com.training.core.repo.po.OrgOrders;
import com.training.core.service.OrgOrdersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgOrdersServiceImpl implements OrgOrdersService {

    @Resource
    private OrgOrdersMapper orgOrdersMapper;

    @Override
    public int addOrgOrders(OrgOrders orgOrders) {
        return orgOrdersMapper.insert(orgOrders);
    }

    @Override
    public OrgOrders getOrgOrders(int orderId) {
        return orgOrdersMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public List<OrgOrders> queryOrders() {
        return orgOrdersMapper.queryAll(null, null, null, 0, 10);
    }
}


