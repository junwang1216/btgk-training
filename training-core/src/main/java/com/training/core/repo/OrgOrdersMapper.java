package com.training.core.repo;

import com.training.core.repo.po.OrgOrders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgOrdersMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgOrders record);

    OrgOrders selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrgOrders record);

    List<OrgOrders> queryAll(@Param("orderNo") String orderNo, @Param("orderType") Integer orderType, @Param("orderStatus") Integer orderStatus, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    List<OrgOrders> queryAllByDate(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("orderStatus") Integer orderStatus);
}