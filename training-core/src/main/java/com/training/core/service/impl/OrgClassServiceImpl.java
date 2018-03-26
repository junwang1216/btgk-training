package com.training.core.service.impl;

import com.training.core.repo.OrgClassMapper;
import com.training.core.repo.po.OrgClass;
import com.training.core.service.OrgClassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgClassServiceImpl implements OrgClassService {

    @Resource
    private OrgClassMapper orgClassMapper;

    @Override
    public int addOrgClass(OrgClass orgClass) {
        return orgClassMapper.insert(orgClass);
    }

    @Override
    public int saveOrgClass(OrgClass orgClass) {
        return orgClassMapper.updateByPrimaryKey(orgClass);
    }

    @Override
    public int saveOrgClassStatus(OrgClass orgClass) {
        return orgClassMapper.updateStatusByPrimaryKey(orgClass);
    }

    @Override
    public List<OrgClass> queryOrgClassList(String className, Integer status, Integer start, Integer pageSize) {
        return orgClassMapper.queryAll(className, status, start, pageSize);
    }

    @Override
    public int queryOrgClassCount(String className, Integer status) {
        return orgClassMapper.queryAllCount(className, status);
    }

    @Override
    public int totalOrgClassCount(String startTime, String endTime, Integer status) {
        return orgClassMapper.totalAllCount(startTime, endTime, status);
    }

    @Override
    public OrgClass getOrgClass(Integer id) {
        return orgClassMapper.selectByPrimaryKey(id);
    }
}


