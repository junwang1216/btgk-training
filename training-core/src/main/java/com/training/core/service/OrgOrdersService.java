package com.training.core.service;

import com.training.core.repo.po.OrgOrders;

import java.util.List;

public interface OrgOrdersService {

    int addOrgOrders(OrgOrders orgOrders);

    int saveOrgOrders(OrgOrders orgOrders);

    OrgOrders getOrgOrders(int orderId);

    List<OrgOrders> queryOrders();

    List<OrgOrders> queryOrdersByDate(String startTime, String endTime, Integer status);
}
