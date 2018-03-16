package com.training.core.service.impl;

import com.training.core.repo.OrgCoachesMapper;
import com.training.core.repo.po.OrgCoaches;
import com.training.core.service.OrgCoachesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgCoachesServiceImpl implements OrgCoachesService {

    @Resource
    private OrgCoachesMapper orgCoachesMapper;

    @Override
    public int addOrgCoaches(OrgCoaches orgCoaches) {
        return orgCoachesMapper.insert(orgCoaches);
    }

    @Override
    public int saveOrgCoaches(OrgCoaches orgCoaches) {
        return orgCoachesMapper.updateByPrimaryKey(orgCoaches);
    }

    @Override
    public int lockOrgCoaches(OrgCoaches orgCoaches) {
        return orgCoachesMapper.setCoachStatus(orgCoaches);
    }

    @Override
    public List<OrgCoaches> queryOrgCoachesList(String realName, String mobile, Integer start, Integer pageSize) {
        return orgCoachesMapper.queryAll(realName, mobile, null, start, pageSize);
    }

    @Override
    public int queryOrgCoachesCount(String realName, String mobile) {
        return orgCoachesMapper.queryAllCount(realName, mobile, null);
    }

    @Override
    public OrgCoaches getOrgCoaches(Integer id) {
        return orgCoachesMapper.selectByPrimaryKey(id);
    }
}


