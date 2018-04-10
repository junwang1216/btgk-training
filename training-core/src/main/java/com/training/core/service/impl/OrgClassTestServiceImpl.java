package com.training.core.service.impl;

import com.training.core.repo.OrgClassTestMapper;
import com.training.core.repo.po.OrgClassTest;
import com.training.core.service.OrgClassTestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgClassTestServiceImpl implements OrgClassTestService {

    @Resource
    private OrgClassTestMapper orgClassTestMapper;

    @Override
    public int addOrgClassTest(OrgClassTest orgClassTest) {
        return orgClassTestMapper.insert(orgClassTest);
    }

    @Override
    public int saveOrgClassTest(OrgClassTest orgClassTest) {
        return orgClassTestMapper.updateByPrimaryKey(orgClassTest);
    }

    @Override
    public int saveOrgClassTestStatus(OrgClassTest orgClassTest) {
        return orgClassTestMapper.updateStatusByPrimaryKey(orgClassTest);
    }

    @Override
    public List<OrgClassTest> queryOrgClassTestList(Integer classId, Integer status, Integer start, Integer pageSize) {
        return orgClassTestMapper.queryAll(classId, status, start, pageSize);
    }

    @Override
    public int queryOrgClassTestCount(Integer classId, Integer status) {
        return orgClassTestMapper.queryAllCount(classId, status);
    }

    @Override
    public OrgClassTest getOrgClassTest(Integer id) {
        return orgClassTestMapper.selectByPrimaryKey(id);
    }
}


