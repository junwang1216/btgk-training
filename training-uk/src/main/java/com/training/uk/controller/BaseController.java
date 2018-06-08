package com.training.uk.controller;

import com.training.core.common.constant.IPlatformConstant;
import com.training.core.common.enums.LogTypeEnum;
import com.training.core.common.servlet.HttpServlets;
import com.training.core.common.util.DateUtil;
import com.training.core.common.util.JsonUtils;
import com.training.core.repo.po.OrgOperator;
import com.training.core.repo.po.OrgSystemLog;
import com.training.core.service.OrgSystemLogService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BaseController {

    @Resource
    private OrgSystemLogService orgSystemLogService;

    public static final String REQUEST_HEAD = "text/html; charset=UTF-8";

    public static final String APPLICATION_JSON = "application/json";

    public static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    /**
     * 为AJAX往前端输出的方法
     *
     * @param o
     *            想要输出的对象
     * @return 是否成功
     */
    protected boolean AjaxJsonWrite(Object o, HttpServletResponse response) {
        return AjaxJsonWrite(o, false, response);
    }

    protected boolean AjaxJsonWrite(Object o, boolean println, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setContentType(REQUEST_HEAD);
            out = response.getWriter();
            String jsonStr = String.valueOf(o instanceof String || o instanceof Number || o instanceof Boolean ? o
                    : o instanceof List<?> || o instanceof Set<?> || o instanceof Object[] ? JSONArray.fromObject(o)
                    : JSONObject.fromObject(o));
            if (println)
                System.out.println("zhe json string is " + jsonStr);
            out.write(jsonStr);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("JSON\u683c\u5f0f\u5316\u5bf9\u8c61\u65f6\u9519\u8bef" + o.getClass().getName());
            return false;
        } finally {
            out.flush();
            out.close();
        }
    }

    protected boolean AjaxJsonWriteGson(Object o, boolean println, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setContentType(REQUEST_HEAD);
            out = response.getWriter();
            String jsonStr = JsonUtils.toJsonDF(o);
            if (println)
                System.out.println("zhe json string is " + jsonStr);
            out.write(jsonStr);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("JSON\u683c\u5f0f\u5316\u5bf9\u8c61\u65f6\u9519\u8bef" + o.getClass().getName());
            return false;
        } finally {
            out.flush();
            out.close();
        }
    }

    protected <T> T getData(String json, Class<T> clazz) {
        return JsonUtils.fromJson(json, clazz);
    }

    protected String redirect(String path) {
        return new StringBuilder(UrlBasedViewResolver.REDIRECT_URL_PREFIX).append(path).toString();
    }

    protected String forward(String path) {
        return new StringBuilder(UrlBasedViewResolver.FORWARD_URL_PREFIX).append(path).toString();
    }

    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    protected String getStreamResult(HttpServletRequest request, HttpServletResponse response) throws Exception{
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        InputStream in=request.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len=in.read(buffer))!=-1){
            out.write(buffer, 0, len);
        }
        out.close();
        in.close();
        return new String(out.toByteArray(),"utf-8");
    }

    protected OrgOperator getLoginUser() {
        return (OrgOperator)getRequest().getSession().getAttribute(IPlatformConstant.LOGIN_USER);
    }

    protected void setLoginUser(OrgOperator orgOperator) {
        orgOperator.setPassword(null);
        getRequest().getSession().setAttribute(IPlatformConstant.LOGIN_USER, orgOperator);
    }

    protected void log(LogTypeEnum logType, int orgId, String content) {
        OrgSystemLog orgSystemLog = new OrgSystemLog();
        HttpServletRequest httpServletRequest = HttpServlets.getRequest();

        String ipAddress = getIPAddress(httpServletRequest);
        ipAddress = (ipAddress != null) ? ipAddress : "127.0.0.1";

        orgSystemLog.setOrgId(orgId);
        orgSystemLog.setIp(ipAddress);
        orgSystemLog.setMac(getMacAddressByIp(ipAddress));
        orgSystemLog.setAccount(getLoginUser().getRealName());

        orgSystemLog.setCreateTime(DateUtil.getNowDate());
        orgSystemLog.setLogType(logType.getCode());
        orgSystemLog.setLogNo(UUID.randomUUID().toString());
        orgSystemLog.setLogContent(content != null ? content : logType.getDesc());

        orgSystemLogService.addSystemLog(orgSystemLog);
    }

    private String getIPAddress(HttpServletRequest request) {
        try {
            String ip = request.getHeader("X-Forwarded-For");

            if (ip != null) {
                if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
                    int index = ip.indexOf(",");
                    if (index != -1) {
                        return ip.substring(0, index);
                    } else {
                        return ip;
                    }
                }
            }

            ip = request.getHeader("X-Real-IP");
            if (ip != null) {
                if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
                    return ip;
                }
            }

            ip = request.getHeader("Proxy-Client-IP");
            if (ip != null) {
                if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
                    return ip;
                }
            }

            ip = request.getHeader("WL-Proxy-Client-IP");
            if (ip != null) {
                if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
                    return ip;
                }
            }

            ip =  request.getRemoteAddr();

            return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getMacAddressByIp(String ip) {
        String macAddress = "";

        try {
            Process process = Runtime.getRuntime().exec("nbtstat -A " + ip);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());

            LineNumberReader input = new LineNumberReader(ir);
            String str;
            while ((str = input.readLine()) != null) {
                str = str.toUpperCase();
                if (str.indexOf("MAC ADDRESS") > 1) {
                    int start = str.indexOf("=");
                    macAddress = str.substring(start + 1, str.length()).trim();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  macAddress;
    }

}
