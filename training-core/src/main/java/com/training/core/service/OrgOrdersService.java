package com.training.core.service;

import com.training.core.repo.po.OrgOrders;

import java.util.List;

public interface OrgOrdersService {

    int addOrgOrders(OrgOrders orgOrders);

    OrgOrders getOrgOrders(int orderId);

    List<OrgOrders> queryOrders();
}
