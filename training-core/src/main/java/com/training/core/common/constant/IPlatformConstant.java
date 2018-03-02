package com.training.core.common.constant;

import com.training.core.common.util.CustomizedPropertyConfigurer;


/*
 * 自定义常量
 */
public interface IPlatformConstant {
	
	public static final String SUCCESS_CODE = "1";
	
	public static final String SUCCESS_MESSAGE = "操作成功";
	
	public static final String FAIL_CODE = "-1";
	
	public static final String FAIL_MESSAGE = "操作失败";
	
	public static final String REQUEST_JSON = "request_json";
	
	public final String time00 = " 00:00:00";
	
    public final String time24 = " 23:59:59";
    
    public final String LOGIN_USER = "loginUser";
    
    public final String ADMIN = "admin";
    
    public final String ADMIN_NAME = "管理员";
    
    public final String DOU_HAO = ",";
    
    public final String LOGIN_TYPE_PC = ""; //PC端
    
    public final String LOGIN_TYPE_STUDENT = "/student"; //学生端
    
    public final String LOGIN_TYPE_COACH = "/coach"; //教练端
    
    public final String ORDER_SITE_NAME = ""; //场地预定
    
    public final int SITE_ADVANCE_START_TIME = -20; //场地开场可以提前20分钟签到
    
    public final int SITE_LATE_START_TIME = 20; //场地开场可以晚20分钟签到
    
    public final String XIANJIN = "xianjin";
    public final String ZHIFUBAO = "zhifubao";
    public final String WEIXIN = "weixin";
    public final String YINLIAN = "yinlian";
    public final String ZHIPIAO = "zhipiao";
	
	public static final String APP_ID = (String) CustomizedPropertyConfigurer.getContextProperty("app_id"); //appid
	
	public static final String APP_SECRET = (String) CustomizedPropertyConfigurer.getContextProperty("appsecret"); //密钥
	
	
	public static final String EXCEL_EXTENSION = ".xls";
    public static final String EXCEL_EXTENSION_X = ".xlsx";
    
    public static final String WX_OPEN_ID_KEY = "openId";
	
}