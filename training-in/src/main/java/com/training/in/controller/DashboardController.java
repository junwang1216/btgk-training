package com.training.in.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.enums.ClassStatusEnum;
import com.training.core.common.enums.StatusEnum;
import com.training.core.common.exception.MessageException;
import com.training.core.common.util.DateUtil;
import com.training.core.repo.po.OrgStudents;
import com.training.core.service.OrgClassService;
import com.training.core.service.OrgClassStudentsService;
import com.training.core.service.OrgStudentsService;
import org.springframework.stereotype.Controller;
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
 * Created by wangjun on 2017/4/25.
 */
@Controller
@RequestMapping("/admin/dashboard")
public class DashboardController extends BaseController {

    @Resource
    private OrgClassService orgClassService;

    @Resource
    private OrgStudentsService orgStudentsService;

    @Resource
    private OrgClassStudentsService orgClassStudentsService;

    private ModelAndView setModelAndView(ModelAndView modelAndView) {
        return modelAndView.addObject("Admin", getLoginUser());
    }

    @Desc("工作面板")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView renderDashboardIndex() {

        ModelAndView modelAndView = new ModelAndView("Dashboard/Index");

        return setModelAndView(modelAndView);
    }

    @Desc("统计班级数量")
    @ResponseBody
    @RequestMapping(value = "/class/total", method = RequestMethod.GET)
    public ResponseBean totalDashboardClass(String type) {
        try {
            Map<String, Object> map = new HashMap<>();

            String startTime = null, endTime = null;
            switch (type) {
                case "year":
                    startTime = DateUtil.getCurrentYearEndTime() + "-01-01 00:00:00";
                    endTime = DateUtil.getCurrentYearEndTime() + "-12-31 23:59:59";
                    break;
                case "total":
                    break;
                case "month":
                default:
                    startTime = DateUtil.getTimesMonthmorning();
                    endTime = DateUtil.getTimesMonthnight();
            }

            map.put("start", orgClassService.totalOrgClassCount(startTime, endTime, ClassStatusEnum.STATUS_START.getCode()));
            map.put("working", orgClassService.totalOrgClassCount(startTime, endTime, ClassStatusEnum.STATUS_WORKING.getCode()));
            map.put("end", orgClassService.totalOrgClassCount(startTime, endTime, ClassStatusEnum.STATUS_END.getCode()));

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("统计班级数量")
    @ResponseBody
    @RequestMapping(value = "/students/total", method = RequestMethod.GET)
    public ResponseBean totalDashboardStudents(String type) {
        try {
            Map<String, Object> map = new HashMap<>();

            String startTime, endTime;
            String startTimeShow, endTimeShow;
            int valueTotal = 0;
            List<OrgStudents> orgStudentsList;
            List<String> labelList = new ArrayList<>();
            List<Integer> valueList = new ArrayList<>();
            switch (type) {
                case "prev_day":
                case "day":
                    labelList.add("00时");
                    labelList.add("01时");
                    labelList.add("02时");
                    labelList.add("03时");
                    labelList.add("04时");
                    labelList.add("05时");
                    labelList.add("06时");
                    labelList.add("07时");
                    labelList.add("08时");
                    labelList.add("09时");
                    labelList.add("10时");
                    labelList.add("11时");
                    labelList.add("12时");
                    labelList.add("13时");
                    labelList.add("14时");
                    labelList.add("15时");
                    labelList.add("16时");
                    labelList.add("17时");
                    labelList.add("18时");
                    labelList.add("19时");
                    labelList.add("20时");
                    labelList.add("21时");
                    labelList.add("22时");
                    labelList.add("23时");

                    String dateDay;
                    if (type.equals("prev_day")) {
                        dateDay = DateUtil.getAddDay(DateUtil.getNowDate(), -1);
                    } else {
                        dateDay = DateUtil.getAddDay(DateUtil.getNowDate(), 0);
                    }

                    List<String> labelList1 = new ArrayList<>();

                    labelList1.add(dateDay + " 00:00");
                    labelList1.add(dateDay + " 01:00");
                    labelList1.add(dateDay + " 02:00");
                    labelList1.add(dateDay + " 03:00");
                    labelList1.add(dateDay + " 04:00");
                    labelList1.add(dateDay + " 05:00");
                    labelList1.add(dateDay + " 06:00");
                    labelList1.add(dateDay + " 07:00");
                    labelList1.add(dateDay + " 08:00");
                    labelList1.add(dateDay + " 09:00");
                    labelList1.add(dateDay + " 10:00");
                    labelList1.add(dateDay + " 11:00");
                    labelList1.add(dateDay + " 12:00");
                    labelList1.add(dateDay + " 13:00");
                    labelList1.add(dateDay + " 14:00");
                    labelList1.add(dateDay + " 15:00");
                    labelList1.add(dateDay + " 16:00");
                    labelList1.add(dateDay + " 17:00");
                    labelList1.add(dateDay + " 18:00");
                    labelList1.add(dateDay + " 19:00");
                    labelList1.add(dateDay + " 20:00");
                    labelList1.add(dateDay + " 21:00");
                    labelList1.add(dateDay + " 22:00");
                    labelList1.add(dateDay + " 23:00");

                    startTime = dateDay + " 00:00:00";
                    endTime = dateDay + " 23:59:59";

                    orgStudentsList = orgStudentsService.queryOrgStudentsListByDate(startTime, endTime);

                    for (String date : labelList1) {
                        String startDayTime = date + ":00";
                        String endDayTime = date.replace(":00", ":59") + ":59";

                        int total = 0;
                        for (OrgStudents orgStudents : orgStudentsList) {
                            if (orgStudents.getCreateTime().compareTo(startDayTime) > 0 && orgStudents.getCreateTime().compareTo(endDayTime) < 0) {
                                total++;
                            }
                        }
                        valueList.add(total);
                        valueTotal += total;
                    }
                    break;
                case "prev_month":
                case "month":
                    List<String> labelList3 = new ArrayList<>();

                    if (type.equals("prev_month")) {
                        labelList3 = DateUtil.getMonthDate(DateUtil.getAddMonth(-1) + "-01");
                    } else {
                        labelList3 = DateUtil.getMonthDate(DateUtil.getAddMonth(0) + "-01");
                    }

                    startTime = labelList3.get(0) + " 00:00:00";
                    endTime = labelList3.get(labelList3.size() - 1) + " 23:59:59";
                    orgStudentsList = orgStudentsService.queryOrgStudentsListByDate(startTime, endTime);

                    for (String date : labelList3) {
                        String startDayTime = date + " 00:00:00";
                        String endDayTime = date + " 23:59:59";

                        labelList.add(date.replaceAll("^(\\d{4})-(\\d{2})-(\\d{2})$", "$2月$3日"));

                        int total = 0;
                        for (OrgStudents orgStudents : orgStudentsList) {
                            if (orgStudents.getCreateTime().compareTo(startDayTime) > 0 && orgStudents.getCreateTime().compareTo(endDayTime) < 0) {
                                total++;
                            }
                        }
                        valueList.add(total);
                        valueTotal += total;
                    }

                    break;
                case "prev_year":
                case "year":
                default:
                    labelList.add("01月");
                    labelList.add("02月");
                    labelList.add("03月");
                    labelList.add("04月");
                    labelList.add("05月");
                    labelList.add("06月");
                    labelList.add("07月");
                    labelList.add("08月");
                    labelList.add("09月");
                    labelList.add("10月");
                    labelList.add("11月");
                    labelList.add("12月");

                    List<String> labelList2 = new ArrayList<>();

                    if (type.equals("prev_year")) {
                        labelList2.add(DateUtil.getAddYear(-1) + "-01");
                        labelList2.add(DateUtil.getAddYear(-1) + "-02");
                        labelList2.add(DateUtil.getAddYear(-1) + "-03");
                        labelList2.add(DateUtil.getAddYear(-1) + "-04");
                        labelList2.add(DateUtil.getAddYear(-1) + "-05");
                        labelList2.add(DateUtil.getAddYear(-1) + "-06");
                        labelList2.add(DateUtil.getAddYear(-1) + "-07");
                        labelList2.add(DateUtil.getAddYear(-1) + "-08");
                        labelList2.add(DateUtil.getAddYear(-1) + "-09");
                        labelList2.add(DateUtil.getAddYear(-1) + "-10");
                        labelList2.add(DateUtil.getAddYear(-1) + "-11");
                        labelList2.add(DateUtil.getAddYear(-1) + "-12");
                    } else {
                        labelList2.add(DateUtil.getAddYear(0) + "-01");
                        labelList2.add(DateUtil.getAddYear(0) + "-02");
                        labelList2.add(DateUtil.getAddYear(0) + "-03");
                        labelList2.add(DateUtil.getAddYear(0) + "-04");
                        labelList2.add(DateUtil.getAddYear(0) + "-05");
                        labelList2.add(DateUtil.getAddYear(0) + "-06");
                        labelList2.add(DateUtil.getAddYear(0) + "-07");
                        labelList2.add(DateUtil.getAddYear(0) + "-08");
                        labelList2.add(DateUtil.getAddYear(0) + "-09");
                        labelList2.add(DateUtil.getAddYear(0) + "-10");
                        labelList2.add(DateUtil.getAddYear(0) + "-11");
                        labelList2.add(DateUtil.getAddYear(0) + "-12");
                    }

                    startTime = labelList2.get(0) + "-01 00:00:00";
                    endTime = labelList2.get(labelList2.size() - 1) + "-31 23:59:59";
                    orgStudentsList = orgStudentsService.queryOrgStudentsListByDate(startTime, endTime);

                    for (String date : labelList2) {
                        String startDayTime = date + "-01 00:00:00";
                        String endDayTime = date + "-31 23:59:59";

                        int total = 0;
                        for (OrgStudents orgStudents : orgStudentsList) {
                            if (orgStudents.getCreateTime().compareTo(startDayTime) > 0 && orgStudents.getCreateTime().compareTo(endDayTime) < 0) {
                                total++;
                            }
                        }
                        valueList.add(total);
                        valueTotal += total;
                    }
            }

            map.put("labelList", labelList);
            map.put("valueList", valueList);
            map.put("startTime", startTime);
            map.put("endTime", endTime);

            map.put("totalCreateCurrent", orgClassStudentsService.totalAllStudentsCount(startTime, endTime));  // 当前新增分班学员数
            map.put("totalCurrent", valueTotal); // 当前学员数

            map.put("total", orgStudentsService.totalOrgStudentsCount(null, null));  // 学员总量
            map.put("totalCreate", orgClassStudentsService.totalAllStudentsCount(null, null)); // 分班学员总量

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

}
