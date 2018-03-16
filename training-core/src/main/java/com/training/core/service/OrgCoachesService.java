package com.training.core.service;

import com.training.core.repo.po.OrgCoaches;

import java.util.List;

public interface OrgCoachesService {

    int addOrgCoaches(OrgCoaches orgCoaches);

    int saveOrgCoaches(OrgCoaches orgCoaches);

    int lockOrgCoaches(OrgCoaches orgCoaches);

    List<OrgCoaches> queryOrgCoachesList(String realName, String mobile, Integer start, Integer pageSize);

    int queryOrgCoachesCount(String realName, String mobile);

    OrgCoaches getOrgCoaches(Integer id);

}
