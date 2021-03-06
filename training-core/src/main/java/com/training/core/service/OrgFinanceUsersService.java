package com.training.core.service;

import com.training.core.repo.po.OrgFinanceUsers;

import java.util.List;

public interface OrgFinanceUsersService {

    int addOrgFinanceUsers(OrgFinanceUsers orgFinanceUsers);

    int saveOrgFinanceUsers(OrgFinanceUsers orgFinanceUsers);

    List<OrgFinanceUsers> queryOrgFinanceUsersList(Integer start, Integer pageSize);

    int queryOrgFinanceUsersCount();

    OrgFinanceUsers getOrgFinanceUsers(Integer userId);

    List<OrgFinanceUsers> getOrgFinanceUsersList(OrgFinanceUsers orgFinanceUsers);

    int setOrgFinanceUsersStatus(OrgFinanceUsers orgFinanceUsers);

    OrgFinanceUsers innerLogin(OrgFinanceUsers orgFinanceUsers);

    int modifyPassword(OrgFinanceUsers orgFinanceUsers);
}
