package com.training.core.common.constant;

public interface IDBConstant {

	public static final int NULL_PARENT_KEY = -1;// 空父字典键
	
	public static final String LOGIC_STATUS = "LOGIC_STATUS";//逻辑状态

    public static final String LOGIC_STATUS_YES = "1";//是
    public static final String LOGIC_STATUS_NO = "2";//否
    public static final String LOGIC_STATUS_OTHER = "3"; //其他

    public static final String OPERATOR_TYPE_ADMIN = "1";//管理员
    public static final String OPERATOR_TYPE_WORK = "2";//操作员
    public static final String OPERATOR_TYPE_USER = "3";//会员
    
    public static final String STATUS = "STATUS";//状态

    public static final String STATUS_YES = "1";//有效
    public static final String STATUS_NO = "2";//无效
    
    public static final String SEX = "SEX";//性别

    public static final String SEX_NAN = "1";//男
    public static final String SEX_NV = "2";//女
    
    public static final String BALANCE_STYLE = "BALANCE_STYLE";//支付方式

    public static final String BALANCE_STYLE_XJ = "1";//现金
    public static final String BALANCE_STYLE_ZFB = "2";//支付宝
    public static final String BALANCE_STYLE_WX = "3";//微信
    public final String BALANCE_STYLE_YINLIAN = "4";//银联
    public final String BALANCE_STYLE_ZHIPIAO = "5";//支票
    
    public static final int ZERO = 0;
    
    public static final String BALANCE_TYPE = "BALANCE_TYPE";//流水类型
    public static final String BALANCE_TYPE_CZ = "1";//充值
    public static final String BALANCE_TYPE_XF = "2";//消费
    public static final String BALANCE_TYPE_OTHER = "3";//其他
    
    public static final String BALANCE_SERVICE_TYPE = "BALANCE_SERVICE_TYPE";//业务类型
    public static final String BALANCE_SERVICE_TYPE_REG = "10";//注册会员
    public static final String BALANCE_SERVICE_TYPE_CARD_CZ = "11";//会员充值
    public static final String BALANCE_SERVICE_TYPE_CARD_UP = "12";//升级会员卡
    public static final String BALANCE_SERVICE_TYPE_CARD_BUBAN = "13";//补办会员卡
    public static final String BALANCE_SERVICE_TYPE_CARD_BUBAN_TEACHER = "20";//教师补办会员卡
    public static final String BALANCE_SERVICE_TYPE_CARD_BUBAN_STUDENT = "30";//学生补办会员卡
    
    public static final String BALANCE_SERVICE_TYPE_SITE = "100";//场地预定
    public static final String BALANCE_SERVICE_TYPE_BLOCK_SITE = "200";//包场预订
    public static final String BALANCE_SERVICE_TYPE_SITE_RECEIVABLE = "220";//应收款
    public static final String BALANCE_SERVICE_TYPE_GOODS = "300";//商品
    
    
    
    public static final String BALANCE_STATUS = "BALANCE_STATUS";//流水状态
    public static final String BALANCE_STATUS_ALL = "1";//已收全款
    public static final String BALANCE_STATUS_NOT = "2";//未付款
    public static final String BALANCE_STATUS_ONE = "3";//部分收款
    
    public static final String GOOD_STATE = "GOOD_STATE";//支付方式

    public static final String GOOD_STATE_ING = "1";//在售
    public static final String GOOD_STATE_BOOKING = "2";//预售
    public static final String GOOD_STATE_OUT = "3";//下架
    
    public static final int ROLE_ADMIN = 1;//超级管理员
    public static final int ROLE_MEMBER = 2;//会员
    public static final int ROLE_COMMON = 3;//普通用户
    
    public static final String ORDER_SERVICE_TYPE = "ORDER_SERVICE_TYPE";//订单业务类型
    public static final String ORDER_SERVICE_TYPE_SITE = "100";//场地预定
    public static final String ORDER_SERVICE_TYPE_BLOCK_SITE = "200";//包场预订
    public static final String ORDER_SERVICE_TYPE_GOODS = "300";//商品消费

    public static final int ROLE_EMPLOYEE = 1;//员工角色最低主键值

    // 通知
    public static final String NOTIFICATIONS_SENDER_YES = "1";//已发送
    public static final String NOTIFICATIONS_SENDER_NO = "2";//未发送
    public static final String NOTIFICATIONS_SENDER_DEL = "3";//已删除

    public static final String NOTIFICATIONS_RECEIVER_YES = "1";//已读
    public static final String NOTIFICATIONS_RECEIVER_NO = "2";//未读
    public static final String NOTIFICATIONS_RECEIVER_DEL = "3";//已删除
    public static final String NOTIFICATIONS_RECEIVER_ALL = "4";//已读

    public static final String NOTIFICATIONS_TYPE_DRAFT = "1"; // 草稿
    public static final String NOTIFICATIONS_TYPE_SEND = "2"; // 发件箱
    public static final String NOTIFICATIONS_TYPE_RECEIVE = "3"; // 收件箱
    public static final String NOTIFICATIONS_TYPE_TRASH = "4"; // 垃圾箱

    public static final String NOTIFICATIONS_TYPE_RECEIVE_UNREAD = "31"; // 未读收件箱
    public static final String NOTIFICATIONS_TYPE_RECEIVE_READ = "32"; // 已读收件箱
    
    public static final String CARD_TEACHERS = "1";//教师卡
    public static final String CARD_TEAM = "2";//团体会员卡
    public static final String CARD_STUDENT = "3"; //学生卡
    
    public static final String SIGN_STATUS_IN = "1";//已签到
    public static final String SIGN_STATUS_OVER = "2";//已过期
    public static final String SIGN_STATUS_NORMAL = "3"; //未使用
    
    public static final String INVENTORY_OP_TYPE_ADD = "1";//添加
    public static final String INVENTORY_OP_TYPE_IN = "2";//入库
    public static final String INVENTORY_OP_TYPE_OUT = "3"; //销售
    public static final String INVENTORY_OP_TYPE_MINUS = "4"; //损耗

    public static final String INVOICE_TYPE_SPECIAL = "1";// 专票
    public static final String INVOICE_TYPE_GENERAL = "2"; //普票

    public static final int DATA_DATE_PRE_DAY = 10; // 昨天
    public static final int DATA_DATE_DAY = 11; // 今天
    public static final int DATA_DATE_PRE_WEEK = 20; // 上周
    public static final int DATA_DATE_WEEK = 21; // 本周
    public static final int DATA_DATE_NEXT_WEEK = 22; // 下周
    public static final int DATA_DATE_PRE_MONTH = 30; // 上月
    public static final int DATA_DATE_MONTH = 31; // 本月
    public static final int DATA_DATE_PRE_YEAR = 40; // 上年
    public static final int DATA_DATE_YEAR = 41; // 本年
}
