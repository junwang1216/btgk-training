package com.training.in.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.bean.ResponseBean;
import com.training.core.common.config.WebConfig;
import com.training.core.common.constant.IPlatformConstant;
import com.training.core.common.enums.LogTypeEnum;
import com.training.core.common.enums.RoleEnum;
import com.training.core.common.enums.StatusEnum;
import com.training.core.common.exception.MessageException;
import com.training.core.common.util.DataCryptUtil;
import com.training.core.common.util.DateUtil;
import com.training.core.common.util.Page;
import com.training.core.repo.po.OrgOperator;
import com.training.core.repo.po.OrgSystemLog;
import com.training.core.service.OrgOperatorService;
import com.training.core.service.OrgSystemLogService;
import com.training.in.request.OrgSystemLogRequest;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

/**
 * Created by wangjun on 2017/5/1.
 */
@Controller
@RequestMapping("/admin/settings")
public class SettingsController extends BaseController {

    @Resource
    private OrgOperatorService orgOperatorService;

    @Resource
    private OrgSystemLogService orgSystemLogService;

    private ModelAndView setModelAndView(ModelAndView modelAndView) {
        return modelAndView.addObject("Admin", super.getRequest().getSession().getAttribute(IPlatformConstant.LOGIN_USER));
    }

    @Desc("管理员设置")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView renderSettingsAdmin() {
        ModelAndView modelAndView = new ModelAndView("Settings/Admin");

        int orgId = getLoginUser().getOrgId();
        int roleId = getLoginUser().getRoleId();

        modelAndView.addObject("RoleTypeEnum", EnumUtils.getEnumList(RoleEnum.class));
        modelAndView.addObject("RoleTypeStart", RoleEnum.ROLE_OPERATOR);

        List<OrgOperator> orgOperatorList = orgOperatorService.queryOrgOperatorList(orgId, roleId);
        modelAndView.addObject("orgOperatorList", orgOperatorList);

        return setModelAndView(modelAndView);
    }

    @Desc("查询某管理员")
    @ResponseBody
    @RequestMapping(value = "/getAdmin", method = RequestMethod.GET)
    public ResponseBean getSettingsAdmin(int adminId) {
        try {

            Map<String, Object> map = new HashMap<>();

            OrgOperator orgOperator = orgOperatorService.selectByPrimaryKey(adminId);
            map.put("orgOperator", orgOperator);

            return new ResponseBean(map);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("保存管理员")
    @ResponseBody
    @RequestMapping(value = "/saveAdmin", method = RequestMethod.POST)
    public ResponseBean saveSettingsAdmin(OrgOperator orgOperator) {
        try {

            int result;
            if (orgOperator.getId() != null) {
                orgOperator.setUpdateTime(DateUtil.getNowDate());
                result = orgOperatorService.saveOrgOperator(orgOperator);
            }
            else {
                int orgId = getLoginUser().getOrgId();

                orgOperator.setOrgId(orgId);
                orgOperator.setPassword(DataCryptUtil.encrypt("123456"));
                orgOperator.setStatus(StatusEnum.STATUS_OK.getCode());
                orgOperator.setCreateTime(DateUtil.getNowDate());
                orgOperator.setUpdateTime(DateUtil.getNowDate());
                result = orgOperatorService.addOrgOperator(orgOperator);
            }

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("我的信息修改")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String renderSettingsProfile() {
        return "Settings/Profile";
    }

    @Desc("系统日志")
    @RequestMapping(value = "/log", method = RequestMethod.GET)
    public ModelAndView renderSettingsLog(OrgSystemLogRequest orgSystemLogRequest) {

        ModelAndView modelAndView = new ModelAndView("Settings/Log");

        int total = orgSystemLogService.queryAllSystemLogCount(getLoginUser().getOrgId());
        int pageSize = 10;
        int start = orgSystemLogRequest.getPage() < 1 ?  0 : orgSystemLogRequest.getPage() - 1;
        List<OrgSystemLog> orgSystemLogList = orgSystemLogService.queryAllSystemLog(getLoginUser().getOrgId(), start * pageSize, pageSize);
        List<Map> orgSystemLogList2 = new ArrayList<>();
        for (OrgSystemLog orgSystemLog : orgSystemLogList) {
            Map map = new HashMap();

            map.put("logType", LogTypeEnum.forValue(orgSystemLog.getLogType()).getDesc());
            map.put("logContent", orgSystemLog.getLogContent());
            map.put("logNo", orgSystemLog.getLogNo());
            map.put("logCreateTime", orgSystemLog.getCreateTime());
            map.put("logIp", orgSystemLog.getIp());
            map.put("logMac", orgSystemLog.getMac());
            map.put("logAccount", orgSystemLog.getAccount());

            orgSystemLogList2.add(map);
        }
        modelAndView.addObject("orgSystemLogList", orgSystemLogList2);

        Page page = new Page(pageSize, total);
        page.setPage(orgSystemLogRequest.getPage());

        modelAndView.addObject("total", total);
        modelAndView.addObject("pageURL", "/admin/settings/log?startTime=&endTime=");
        modelAndView.addObject("page", page);

        return setModelAndView(modelAndView);
    }

    @Desc("清除日志")
    @ResponseBody
    @RequestMapping(value = "/clearLog", method = RequestMethod.POST)
    public ResponseBean clearSettingsLog() {
        try {

            int result;
            int orgId = getLoginUser().getOrgId();
            result = orgSystemLogService.clearSystemLogByOrgId(orgId, 30);

            return new ResponseBean(result > 0);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseBean(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false);
        }
    }

    @Desc("数据库备份")
    @RequestMapping(value = "/database", method = RequestMethod.GET)
    public ModelAndView renderSettingsDatabase() {

        ModelAndView modelAndView = new ModelAndView("Settings/Database");

        String backupPath = System.getProperty("user.home") + "/btgk-training-backup";
        List<File> fileList = new ArrayList<>();

        File directory = new File(backupPath);
        if (!directory.exists()) {
            boolean fileValid = directory.mkdir();
            if (!fileValid) {
                modelAndView.addObject("error", "创建备份目录失败！！请手动创建'btgk-training-backup'");
                return setModelAndView(modelAndView);
            }
        }

        File[] listFiles = directory.listFiles();// 获取目录下的所有文件或文件夹
        if (listFiles == null) {// 如果目录为空，直接退出
            modelAndView.addObject("fileList", fileList);
            return setModelAndView(modelAndView);
        }

        // 遍历，目录下的所有文件
        for (File f : listFiles) {
            if (f.isFile() && f.getName().contains("btgk-training")) {
                fileList.add(f);
            }
        }

        modelAndView.addObject("fileList", fileList);
        return setModelAndView(modelAndView);
    }

    @Desc("数据库备份")
    @ResponseBody
    @RequestMapping(value = "/backup", method = RequestMethod.POST)
    public ResponseBean backupSettingsDatabase() {
        try {
            Runtime rt = Runtime.getRuntime();
            String backupPath = System.getProperty("user.home") + "/btgk-training-backup";

            // 调用 调用mysql的安装目录的命令
            String dbName = WebConfig.getDatabaseName();
            String userName = WebConfig.getDatabaseUserName();
            String password = WebConfig.getDatabasePassword();
            Process child = rt.exec("mysqldump -h localhost -u" + userName + " -p" + password + " " + dbName);

            // 设置导出编码为utf-8。这里必须是utf-8
            // 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
            InputStream in = child.getInputStream();// 控制台的输出信息作为输入流
            // 设置输出流编码为utf-8。这里必须是utf-8，否则从流中读入的是乱码
            InputStreamReader xx = new InputStreamReader(in, "utf-8");

            String inStr;
            StringBuffer sb = new StringBuffer("");
            String outStr;
            // 组合控制台输出信息字符串
            BufferedReader br = new BufferedReader(xx);
            while ((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            outStr = sb.toString();

            // 要用来做导入用的sql目标文件：
            String currentDate = DateUtil.dateToString(new Date(), "yyyyMMddHHmmss");
            FileOutputStream fout = new FileOutputStream(backupPath + "/btgk-training_" + currentDate + ".sql");
            OutputStreamWriter writer = new OutputStreamWriter(fout, "utf-8");
            writer.write(outStr);
            writer.flush();
            in.close();
            xx.close();
            br.close();
            writer.close();
            fout.close();

            System.out.println("OK");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseBean(true);
    }

    @Desc("数据库备份")
    @ResponseBody
    @RequestMapping(value = "/restore", method = RequestMethod.POST)
    public ResponseBean restoreSettingsDatabase() {
        try {
            String backupPath = System.getProperty("user.home") + "/btgk-training-backup";
            String maxFileName = "";
            String maxFileAbsolute = "";

            File directory = new File(backupPath);
            if (!directory.exists()) {
                return new ResponseBean(false);
            }

            File[] listFiles = directory.listFiles();// 获取目录下的所有文件或文件夹
            if (listFiles == null) {// 如果目录为空，直接退出
                return new ResponseBean(false);
            }

            // 遍历，目录下的所有文件
            for (File f : listFiles) {
                if (f.isFile() && f.getName().contains("btgk-training") && f.getName().compareTo(maxFileName) > 0) {
                    maxFileName = f.getName();
                    maxFileAbsolute = f.getAbsolutePath();
                }
            }

            Runtime runtime = Runtime.getRuntime();
            String dbName = WebConfig.getDatabaseName();
            String userName = WebConfig.getDatabaseUserName();
            String password = WebConfig.getDatabasePassword();
            Process process = runtime.exec("mysql -h localhost -u" + userName + " -p" + password + " --default-character-set=utf8 " + dbName);
            OutputStream outputStream = process.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(maxFileAbsolute), "utf-8"));
            String str = null;
            StringBuffer sb = new StringBuffer();
            while ((str = br.readLine()) != null) {
                sb.append(str + "\r\n");
            }
            str = sb.toString();
            // System.out.println(str);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8");
            writer.write(str);
            writer.flush();
            outputStream.close();
            br.close();
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseBean(true);
    }

}
