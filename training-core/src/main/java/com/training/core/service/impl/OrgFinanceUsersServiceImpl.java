package com.training.core.service.impl;

import com.training.core.repo.OrgFinanceUsersMapper;
import com.training.core.repo.po.OrgFinanceUsers;
import com.training.core.service.OrgFinanceUsersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgFinanceUsersServiceImpl implements OrgFinanceUsersService {

    @Resource
    private OrgFinanceUsersMapper orgFinanceUsersMapper;

    @Override
    public int addOrgFinanceUsers(OrgFinanceUsers orgFinanceVenues) {
        return orgFinanceUsersMapper.insert(orgFinanceVenues);
    }

    @Override
    public int saveOrgFinanceUsers(OrgFinanceUsers orgFinanceVenues) {
        return orgFinanceUsersMapper.updateByPrimaryKey(orgFinanceVenues);
    }

    @Override
    public List<OrgFinanceUsers> queryOrgFinanceUsersList(Integer start, Integer pageSize) {
        return orgFinanceUsersMapper.queryAll(start, pageSize);
    }

    @Override
    public int queryOrgFinanceUsersCount() {
        return orgFinanceUsersMapper.queryAllCount();
    }

    @Override
    public OrgFinanceUsers getOrgFinanceUsers(Integer userId) {
        return orgFinanceUsersMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<OrgFinanceUsers> getOrgFinanceUsersList(OrgFinanceUsers orgFinanceVenues) {
        return orgFinanceUsersMapper.selectAll(orgFinanceVenues);
    }

    @Override
    public int setOrgFinanceUsersStatus(OrgFinanceUsers orgFinanceVenues) {
        return orgFinanceUsersMapper.updateStatus(orgFinanceVenues);
    }
}
