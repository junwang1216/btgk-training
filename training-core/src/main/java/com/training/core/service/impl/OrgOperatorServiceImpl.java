package com.training.core.service.impl;

import com.training.core.repo.OrgOperatorMapper;
import com.training.core.repo.po.OrgOperator;
import com.training.core.repo.query.OrgOperatorRequest;
import com.training.core.service.OrgOperatorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgOperatorServiceImpl implements OrgOperatorService {

    @Resource
    private OrgOperatorMapper orgOperatorMapper;

    @Override
    public OrgOperator selectByPrimaryKey(Integer id) {
        return orgOperatorMapper.selectByPrimaryKey(id);
    }

    @Override
    public OrgOperator innerLogin(OrgOperatorRequest orgOperatorRequest) {
        return orgOperatorMapper.innerLogin(orgOperatorRequest);
    }

    @Override
    public int setLastLoginTime(String lastTime, Integer id) {
        return orgOperatorMapper.setLastLoginTime(lastTime, id);
    }

    @Override
    public List<OrgOperator> queryOrgOperatorList(Integer orgId, Integer roleId) {
        return orgOperatorMapper.queryAll(orgId, roleId);
    }

    @Override
    public int addOrgOperator(OrgOperator orgOperator) {
        return orgOperatorMapper.insert(orgOperator);
    }

    @Override
    public int saveOrgOperator(OrgOperator orgOperator) {
        return orgOperatorMapper.updateByPrimaryKey(orgOperator);
    }
}


