package com.training.in.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.enums.LogTypeEnum;
import com.training.core.common.enums.RoleEnum;
import com.training.core.common.enums.StatusEnum;
import com.training.core.common.exception.MessageException;
import com.training.core.common.util.DateUtil;
import com.training.core.common.util.Page;
import com.training.core.repo.po.*;
import com.training.core.service.*;
import com.training.in.request.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设置场馆、课程、教练等基础信息
 * Created by wangjun on 2017/5/1.
 */
@Controller
@RequestMapping("/admin/venue")
public class VenueController extends BaseController {

    @Resource
    private OrgInformationService orgInformationService;

    @Resource
    private OrgVenuesService orgVenuesService;

    @Resource
    private OrgSportsService orgSportsService;

    @Resource
    private OrgSportsSkillsService orgSportsSkillsService;

    @Resource
    private OrgCoursesService orgCoursesService;

    @Resource
    private OrgCoachesService orgCoachesService;

    @Resource
    private OrgSportsCoachesService orgSportsCoachesService;

    @Resource
    private OrgVenueCoachesService orgVenueCoachesService;

    @Resource
    private OrgCoachesRolesService orgCoachesRolesService;

    private ModelAndView setModelAndView(ModelAndView modelAndView) {
        return modelAndView.addObject("Admin", getLoginUser());
    }

    /** Settings **/

    @Desc("基础资料")
    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public ModelAndView renderVenueSettings() {
        ModelAndView modelAndView = new ModelAndView("Venue/Settings");

        int orgId = getLoginUser().getOrgId();

        OrgInformation orgInformation = orgInformationService.selectByPrimaryKey(orgId);
        modelAndView.addObject("information", orgInformation);

        return setModelAndView(modelAndView);
    }

    @Desc("保存基础资料")
    @ResponseBody
    @RequestMapping(value = "/saveSettings", method = RequestMethod.POST)
    public ResponseBean saveVenueSettings(OrgInformation orgInformation) {
        try {

            orgInformation.setUpdateTime(DateUtil.getNowDate());
            int result = orgInformationService.updateByPrimaryKey(orgInformation);

            log(LogTypeEnum.LOG_TYPE_VENUE_SETTINGS, getLoginUser().getOrgId(), "修改机构[" + orgInformation.getOrgName() + "]基础资料");

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    /** Venue **/

    @Desc("分馆列表")
    @RequestMapping(value = "/branch", method = RequestMethod.GET)
    public ModelAndView renderVenueBranch(String venueId) {
        ModelAndView modelAndView = new ModelAndView("Venue/Branch");

        int orgId = getLoginUser().getOrgId();

        List<OrgVenues> orgVenuesList = orgVenuesService.queryOrgVenuesList(orgId);
        modelAndView.addObject("orgVenuesList", orgVenuesList);

        if (venueId != null) {
            OrgVenues orgVenues = orgVenuesService.getOrgVenues(Integer.parseInt(venueId));
            modelAndView.addObject("orgVenues", orgVenues);
        }
        else {
            modelAndView.addObject("orgVenues", new HashMap());
        }

        return setModelAndView(modelAndView);
    }

    @Desc("保存分馆信息")
    @ResponseBody
    @RequestMapping(value = "/saveBranch", method = RequestMethod.POST)
    public ResponseBean saveVenueBranch(OrgVenues orgVenues) {
        try {
            int result;
            int orgId = getLoginUser().getOrgId();
            String updateTime = DateUtil.getNowDate();

            orgVenues.setOrgId(orgId);
            orgVenues.setUpdateTime(updateTime);
            if (orgVenues.getId() != null) {
                result = orgVenuesService.saveOrgVenues(orgVenues);
            } else {
                orgVenues.setCreateTime(updateTime);
                orgVenues.setStatus(StatusEnum.STATUS_ERROR.getCode());
                result = orgVenuesService.addOrgVenues(orgVenues);
            }

            OrgVenues orgVenues1 = orgVenuesService.getOrgVenues(orgVenues.getId());
            log(LogTypeEnum.LOG_TYPE_VENUE_SETTINGS, getLoginUser().getOrgId(), "添加或者修改场馆[" + orgVenues1.getVenueName() + "]信息");

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("关闭体育场馆")
    @ResponseBody
    @RequestMapping(value = "/closeBranch", method = RequestMethod.POST)
    public ResponseBean closeVenueBranch(int venueId) {
        try {

            OrgVenues orgVenues = new OrgVenues();

            orgVenues.setUpdateTime(DateUtil.getNowDate());
            orgVenues.setStatus(StatusEnum.STATUS_ERROR.getCode());
            orgVenues.setId(venueId);

            int result = orgVenuesService.closeOrgVenues(orgVenues);

            OrgVenues orgVenues1 = orgVenuesService.getOrgVenues(orgVenues.getId());
            log(LogTypeEnum.LOG_TYPE_VENUE_SETTINGS, getLoginUser().getOrgId(), "禁用场馆[" + orgVenues1.getVenueName() + "]运营");

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("启用体育场馆")
    @ResponseBody
    @RequestMapping(value = "/openBranch", method = RequestMethod.POST)
    public ResponseBean openVenueBranch(int venueId) {
        try {

            OrgVenues orgVenues = new OrgVenues();

            orgVenues.setUpdateTime(DateUtil.getNowDate());
            orgVenues.setStatus(StatusEnum.STATUS_OK.getCode());
            orgVenues.setId(venueId);

            int result = orgVenuesService.closeOrgVenues(orgVenues);

            log(LogTypeEnum.LOG_TYPE_VENUE_SETTINGS, getLoginUser().getOrgId(), "启用场馆[" + orgVenues.getVenueName() + "]运营");

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    /** Sports **/

    @Desc("项目设置")
    @RequestMapping(value = "/sports", method = RequestMethod.GET)
    public ModelAndView renderVenueSports(String sportId) {

        ModelAndView modelAndView = new ModelAndView("Venue/Sports");

        int orgId = getLoginUser().getOrgId();

        List<OrgSports> orgSportsList = orgSportsService.queryOrgSportsList(orgId);
        modelAndView.addObject("orgSportsList", orgSportsList);

        if (sportId == null) {
            sportId = orgSportsList.get(0).getId().toString();
        }
        modelAndView.addObject("sportId", sportId);

        List<OrgSportsSkills> orgSportsSkillsList = orgSportsSkillsService.queryOrgSportsSkillsList(Integer.parseInt(sportId));
        modelAndView.addObject("orgSportsSkillsList", orgSportsSkillsList);


        return setModelAndView(modelAndView);
    }

    // 当期不能添加项目
    // TODO

    @Desc("删除项目评测技能")
    @ResponseBody
    @RequestMapping(value = "/deleteSportsSkills", method = RequestMethod.POST)
    public ResponseBean deleteVenueSportsSkills(@RequestBody OrgSportsSkills orgSportsSkills) {
        try {
            int result = orgSportsSkillsService.deleteOrgSportsSkills(orgSportsSkills.getId());

            log(LogTypeEnum.LOG_TYPE_VENUE_SETTINGS, getLoginUser().getOrgId(), "删除技能[" + orgSportsSkills.getSkillName() + "]的评测技能");

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("保存项目评测技能")
    @ResponseBody
    @RequestMapping(value = "/saveSportsSkills", method = RequestMethod.POST)
    public ResponseBean saveVenueSportsSkills(@RequestBody OrgSportsSkillsRequest orgSportsSkillsRequest) {
        try {

            //orgSportsSkillsService.clearAllBySportId(orgSportsSkillsRequest.getSportId());

            int result = 0;
            List<OrgSportsSkills> orgSportsSkillsList = new ArrayList<>();

            for (OrgSportsSkills orgSportsSkills : orgSportsSkillsRequest.getOrgSportsSkillsList()) {
                if (orgSportsSkills.getId() != null) {
                    continue;
                }

                orgSportsSkills.setCreateTime(DateUtil.getNowDate());
                orgSportsSkills.setUpdateTime(DateUtil.getNowDate());
                orgSportsSkills.setSkillNote("无");
                orgSportsSkills.setStatus(StatusEnum.STATUS_OK.getCode());

                orgSportsSkillsList.add(orgSportsSkills);
            }
            if (orgSportsSkillsList.size() > 0) {
                result = orgSportsSkillsService.addOrgSportsSkillsBatch(orgSportsSkillsList);
            }

            OrgSports orgSports = orgSportsService.getOrgSports(orgSportsSkillsRequest.getSportId());
            log(LogTypeEnum.LOG_TYPE_VENUE_SETTINGS, getLoginUser().getOrgId(), "设置项目[" + orgSports.getSportName() + "]的评测技能");

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    /** Course **/

    @Desc("课程管理")
    @RequestMapping(value = "/course", method = RequestMethod.GET)
    public ModelAndView renderVenueCourse(OrgCourseRequest orgCourseRequest) {

        ModelAndView modelAndView = new ModelAndView("Venue/Course");

        int orgId = getLoginUser().getOrgId();

        modelAndView.addObject("courseName", orgCourseRequest.getCourseName());
        modelAndView.addObject("sportId", orgCourseRequest.getSportId());

        List<OrgSports> orgSportsList = orgSportsService.queryOrgSportsList(orgId);
        modelAndView.addObject("orgSportsList", orgSportsList);

        int total = orgCoursesService.queryOrgCoursesCount(orgCourseRequest.getCourseName(), orgCourseRequest.getSportId());
        int start = orgCourseRequest.getPage() < 1 ? 0 : orgCourseRequest.getPage() - 1;
        int pageSize = 10;
        List<OrgCourses> orgCoursesList = orgCoursesService.queryOrgCoursesList(orgCourseRequest.getCourseName(), orgCourseRequest.getSportId(), start * pageSize, pageSize);
        modelAndView.addObject("orgCoursesList", orgCoursesList);

        Page page = new Page(pageSize, total);
        page.setPage(orgCourseRequest.getPage());

        modelAndView.addObject("total", total);
        modelAndView.addObject("pageURL", "/admin/venue/course?courseName=" + orgCourseRequest.getCourseName() + "&sportId=" + orgCourseRequest.getSportId());
        modelAndView.addObject("page", page);

        return setModelAndView(modelAndView);
    }

    @Desc("查询某课程")
    @ResponseBody
    @RequestMapping(value = "/getCourse", method = RequestMethod.GET)
    public ResponseBean getVenueCourse(int courseId) {
        try {

            OrgCourses orgCourses = orgCoursesService.getOrgCourses(courseId);

            Map map = new HashMap();
            map.put("orgCourses", orgCourses);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("保存课程")
    @ResponseBody
    @RequestMapping(value = "/saveCourse", method = RequestMethod.POST)
    public ResponseBean saveVenueCourse(OrgCourses orgCourses) {
        try {

            int result;
            if (orgCourses.getId() != null) {
                orgCourses.setUpdateTime(DateUtil.getNowDate());
                result = orgCoursesService.saveOrgCourses(orgCourses);
            }
            else {
                orgCourses.setStatus(StatusEnum.STATUS_ERROR.getCode());
                orgCourses.setCreateTime(DateUtil.getNowDate());
                orgCourses.setUpdateTime(DateUtil.getNowDate());
                result = orgCoursesService.addOrgCourses(orgCourses);
            }

            log(LogTypeEnum.LOG_TYPE_VENUE_SETTINGS, getLoginUser().getOrgId(), "添加或者修改课程[" + orgCourses.getCourseName() + "]信息");

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("锁定课程")
    @ResponseBody
    @RequestMapping(value = "/lockCourse", method = RequestMethod.POST)
    public ResponseBean lockVenueCourse(int courseId) {
        try {

            OrgCourses orgCourses = new OrgCourses();

            orgCourses.setUpdateTime(DateUtil.getNowDate());
            orgCourses.setStatus(StatusEnum.STATUS_ERROR.getCode());
            orgCourses.setId(courseId);

            int result = orgCoursesService.lockOrgCourses(orgCourses);

            OrgCourses orgCourses1 = orgCoursesService.getOrgCourses(courseId);
            log(LogTypeEnum.LOG_TYPE_VENUE_SETTINGS, getLoginUser().getOrgId(), "锁定课程[" + orgCourses1.getCourseName() + "]");

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("解锁课程")
    @ResponseBody
    @RequestMapping(value = "/unlockCourse", method = RequestMethod.POST)
    public ResponseBean unlockVenueCourse(int courseId) {
        try {

            OrgCourses orgCourses = new OrgCourses();

            orgCourses.setUpdateTime(DateUtil.getNowDate());
            orgCourses.setStatus(StatusEnum.STATUS_OK.getCode());
            orgCourses.setId(courseId);

            int result = orgCoursesService.lockOrgCourses(orgCourses);

            OrgCourses orgCourses1 = orgCoursesService.getOrgCourses(courseId);
            log(LogTypeEnum.LOG_TYPE_VENUE_SETTINGS, getLoginUser().getOrgId(), "解锁课程[" + orgCourses1.getCourseName() + "]");

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    /** Coaches **/

    // TODO 检索有问题
    @Desc("教练管理")
    @RequestMapping(value = "/coaches", method = RequestMethod.GET)
    public ModelAndView renderVenueCoaches(OrgCoachesQueryRequest orgCoachesQueryRequest) {

        ModelAndView modelAndView = new ModelAndView("Venue/Coaches");

        modelAndView.addObject("realName", orgCoachesQueryRequest.getRealName());
        modelAndView.addObject("mobile", orgCoachesQueryRequest.getMobile());

        int orgId = getLoginUser().getOrgId();

        List<OrgSports> orgSportsList = orgSportsService.queryOrgSportsList(orgId);
        modelAndView.addObject("orgSportsList", orgSportsList);

        List<OrgVenues> orgVenuesList = orgVenuesService.queryOrgVenuesList(orgId);
        modelAndView.addObject("orgVenuesList", orgVenuesList);

        List<RoleEnum> roleEnumList = RoleEnum.getCoachList();
        modelAndView.addObject("orgRolesList", roleEnumList);

        int total = orgCoachesService.queryOrgCoachesCount(orgCoachesQueryRequest.getRealName(), orgCoachesQueryRequest.getMobile());
        int start = orgCoachesQueryRequest.getPage() < 1 ? 0 : orgCoachesQueryRequest.getPage() - 1;
        int pageSize = 10;
        List<OrgCoaches> orgCoachesList = orgCoachesService.queryOrgCoachesList(orgCoachesQueryRequest.getRealName(), orgCoachesQueryRequest.getMobile(), start * pageSize, pageSize);
        List<Map<String, Object>> responseList = new ArrayList<>();
        for (OrgCoaches orgCoaches : orgCoachesList) {
            Map<String, Object> response = new HashMap<>();

            response.put("orgCoaches", orgCoaches);

            List<OrgSportsCoaches> orgSportsCoachesList = orgSportsCoachesService.queryOrgSportsCoachesList(0, orgCoaches.getId());
            String sportNames = "";
            for (OrgSportsCoaches orgSportsCoaches : orgSportsCoachesList) {
                OrgSports orgSports = orgSportsService.getOrgSports(orgSportsCoaches.getSportId());
                sportNames += orgSports.getSportName() + " ";
            }
            response.put("sportNames", sportNames.trim());

            List<OrgVenueCoaches> orgVenueCoachesList = orgVenueCoachesService.queryOrgVenueCoachesList(0, orgCoaches.getId());
            String venueNames = "";
            for (OrgVenueCoaches orgVenueCoaches : orgVenueCoachesList) {
                OrgVenues orgVenues = orgVenuesService.getOrgVenues(orgVenueCoaches.getVenueId());
                venueNames += orgVenues.getVenueName() + " ";
            }
            response.put("venueNames", venueNames.trim());

            List<OrgCoachesRoles> orgCoachesRolesList = orgCoachesRolesService.queryOrgCoachesRolesList(orgCoaches.getId());
            String roleNames = "";
            for (OrgCoachesRoles orgCoachesRoles : orgCoachesRolesList) {
                if (RoleEnum.ROLE_COACH.getCode() == orgCoachesRoles.getRoleId()) {
                    roleNames += RoleEnum.ROLE_COACH.getDesc() + " ";
                }
                if (RoleEnum.ROLE_CHIEF.getCode() == orgCoachesRoles.getRoleId()) {
                    roleNames += RoleEnum.ROLE_CHIEF.getDesc() + " ";
                }
                if (RoleEnum.ROLE_STUDENT.getCode() == orgCoachesRoles.getRoleId()) {
                    roleNames += RoleEnum.ROLE_STUDENT.getDesc() + " ";
                }
            }
            response.put("roleNames", roleNames.trim());

            responseList.add(response);
        }
        modelAndView.addObject("orgCoachesList", responseList);

        Page page = new Page(pageSize, total);
        page.setPage(orgCoachesQueryRequest.getPage());

        modelAndView.addObject("total", total);
        modelAndView.addObject("pageURL", "/admin/venue/coaches?realName=" + orgCoachesQueryRequest.getRealName() + "&mobile=" + orgCoachesQueryRequest.getMobile());
        modelAndView.addObject("page", page);

        return setModelAndView(modelAndView);
    }

    @Desc("查询某教练")
    @ResponseBody
    @RequestMapping(value = "/getCoach", method = RequestMethod.GET)
    public ResponseBean getVenueCoach(int coachId) {
        try {

            Map<String, Object> map = new HashMap<>();

            OrgCoaches orgCoaches = orgCoachesService.getOrgCoaches(coachId);
            map.put("orgCoaches", orgCoaches);

            List<OrgSportsCoaches> orgSportsCoachesList = orgSportsCoachesService.queryOrgSportsCoachesList(0, coachId);
            map.put("orgSportsCoachesList", orgSportsCoachesList);

            List<OrgVenueCoaches> orgVenueCoachesList = orgVenueCoachesService.queryOrgVenueCoachesList(0, orgCoaches.getId());
            map.put("orgVenueCoachesList", orgVenueCoachesList);

            List<OrgCoachesRoles> orgCoachesRolesList = orgCoachesRolesService.queryOrgCoachesRolesList(orgCoaches.getId());
            map.put("orgCoachesRolesList", orgCoachesRolesList);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("保存教练")
    @ResponseBody
    @RequestMapping(value = "/saveCoach", method = RequestMethod.POST)
    public ResponseBean saveVenueCoach(@RequestBody OrgCoachesRequest orgCoachesRequest) {
        try {

            int result;
            if (orgCoachesRequest.getOrgCoaches().getId() != null) {
                orgCoachesRequest.getOrgCoaches().setUpdateTime(DateUtil.getNowDate());
                orgCoachesService.saveOrgCoaches(orgCoachesRequest.getOrgCoaches());
            }
            else {
                orgCoachesRequest.getOrgCoaches().setCreateTime(DateUtil.getNowDate());
                orgCoachesRequest.getOrgCoaches().setUpdateTime(DateUtil.getNowDate());
                orgCoachesRequest.getOrgCoaches().setStatus(StatusEnum.STATUS_ERROR.getCode());
                orgCoachesService.addOrgCoaches(orgCoachesRequest.getOrgCoaches());
            }
            result = orgCoachesRequest.getOrgCoaches().getId();

            // 设置角色
            orgCoachesRolesService.clearAllByCoachId(result);
            List<OrgCoachesRoles> orgCoachesRolesList = new ArrayList<>();
            for (String roleId : orgCoachesRequest.getRoleIds()) {
                OrgCoachesRoles orgCoachesRoles = new OrgCoachesRoles();

                orgCoachesRoles.setCoachId(result);
                orgCoachesRoles.setRoleId(Integer.parseInt(roleId));
                orgCoachesRoles.setCreateTime(DateUtil.getNowDate());
                orgCoachesRoles.setUpdateTime(DateUtil.getNowDate());

                orgCoachesRolesList.add(orgCoachesRoles);
            }
            orgCoachesRolesService.addOrgCoachesRolesBatch(orgCoachesRolesList);

            // 设置项目
            List<OrgSportsCoaches> sportsCoachesList = new ArrayList<>();
            for (String sportId : orgCoachesRequest.getSportIds()) {
                OrgSportsCoaches orgSportsCoaches = new OrgSportsCoaches();

                orgSportsCoaches.setCoachId(result);
                orgSportsCoaches.setSportId(Integer.parseInt(sportId));
                orgSportsCoaches.setCreateTime(DateUtil.getNowDate());
                orgSportsCoaches.setUpdateTime(DateUtil.getNowDate());

                sportsCoachesList.add(orgSportsCoaches);
            }
            orgSportsCoachesService.clearAllByCoachId(result);
            orgSportsCoachesService.addOrgSportsCoachesBatch(sportsCoachesList);

            // 设置场馆
            List<OrgVenueCoaches> orgVenueCoachesList = new ArrayList<>();
            for (String venueId : orgCoachesRequest.getVenueIds()) {
                OrgVenueCoaches orgVenueCoaches = new OrgVenueCoaches();

                orgVenueCoaches.setCoachId(result);
                orgVenueCoaches.setVenueId(Integer.parseInt(venueId));
                orgVenueCoaches.setCreateTime(DateUtil.getNowDate());
                orgVenueCoaches.setUpdateTime(DateUtil.getNowDate());

                orgVenueCoachesList.add(orgVenueCoaches);
            }
            orgVenueCoachesService.clearAllByCoachId(result);
            orgVenueCoachesService.addOrgVenueCoachesBatch(orgVenueCoachesList);

            log(LogTypeEnum.LOG_TYPE_VENUE_SETTINGS, getLoginUser().getOrgId(), "添加或者修改教练[" + orgCoachesRequest.getOrgCoaches().getRealName() + "]信息");

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("锁定教练")
    @ResponseBody
    @RequestMapping(value = "/lockCoaches", method = RequestMethod.POST)
    public ResponseBean lockVenueCoaches(int coachId) {
        try {

            OrgCoaches orgCoaches = new OrgCoaches();

            orgCoaches.setUpdateTime(DateUtil.getNowDate());
            orgCoaches.setStatus(StatusEnum.STATUS_ERROR.getCode());
            orgCoaches.setId(coachId);

            int result = orgCoachesService.lockOrgCoaches(orgCoaches);

            OrgCoaches orgCoaches1 = orgCoachesService.getOrgCoaches(coachId);
            log(LogTypeEnum.LOG_TYPE_VENUE_SETTINGS, getLoginUser().getOrgId(), "锁定教练[" + orgCoaches1.getRealName() + "]授课权限");

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("解锁教练")
    @ResponseBody
    @RequestMapping(value = "/unlockCoaches", method = RequestMethod.POST)
    public ResponseBean unlockVenueCoaches(int coachId) {
        try {

            OrgCoaches orgCoaches = new OrgCoaches();

            orgCoaches.setUpdateTime(DateUtil.getNowDate());
            orgCoaches.setStatus(StatusEnum.STATUS_OK.getCode());
            orgCoaches.setId(coachId);

            int result = orgCoachesService.lockOrgCoaches(orgCoaches);

            OrgCoaches orgCoaches1 = orgCoachesService.getOrgCoaches(coachId);
            log(LogTypeEnum.LOG_TYPE_VENUE_SETTINGS, getLoginUser().getOrgId(), "解锁教练[" + orgCoaches1.getRealName() + "]授课权限");

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

}
