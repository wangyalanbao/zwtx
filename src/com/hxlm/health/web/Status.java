package com.hxlm.health.web;

/**
 * Created by dengyang on 15/6/16.
 */
public class Status {
//    成功
    public static final int SUCCESS = 100;
//    参数不正确，必要参数为空或非法值
    public static final int INVALID_PARAMS = 1;
//    手机号码不正确
    public static final int INVALID_MOBILE = 2;
//    注册码不正确
    public static final int INVALID_REGCODE = 3;
//    用户ID不正确
    public static final int INVALID_UID = 4;
//    身份证号码不正确
    public static final int INVALID_USER_ID = 5;
//    EMAIL格式不正确
    public static final int INVALID_USER_EMAIL = 6;
//    姓别不正确，0：女 1：男
    public static final int INVALID_USER_GENDER = 7;
//    生日不正确
    public static final int INVALID_USER_BIRTHDAY =8;
//    操作ID不正确
    public static final int INVALID_OPTID = 9;
//    客户端时间不正确(未用)
    public static final int INVALID_TIME = 10;
//    音乐ID不正确
    public static final int INVALID_MUSIC_ID = 11;
//    兑换次数不正确
    public static final int INVALID_EXCHANGE_COUNT = 12;
//    发送短信失败
    public static final int FAILED_SEND_SMS = 13;
//    注册失败
    public static final int FAILED_LOGON = 14;
//    登录失败
    public static final int FAILED_LOGIN = 15;
//    切换帐号失败
    public static final int FAILED_LOGOUT = 16;
//    切换帐号失败
    public static final int FAILED_ORDER = 17;
//    兑换失败
    public static final int FAILED_EXCHANGE = 18;
//    查询练习记录失败
    public static final int FAILED_QUERY_EXERCISERECORD = 19;
//     查询检查结果失败
    public static final int FAILED_QUERY_REPORT = 20;
 //    查询乐药列表失败
    public static final int FAILED_QUERY_MUSIC = 21;
 //    查询锻炼通知的天数失败
    public static final int FAILED_QUERY_EXERCISE = 22;
//    查询用户信息失败
    public static final int FAILED_QUERY_USERINFO = 23;
//    查询历史检查结果失败
    public static final int FAILED_QUERY_HIS_REPORT = 24;
//    修改用户信息失败
    public static final int FAILED_CHANGE_USERINFO = 25;
//    修改注册码失败
    public static final int FAILED_CHANGE_REGCODE = 26;
//    上传音频失败
    public static final int FAILED_UPLOAD_ARM = 27;
//   下载乐药失败
    public static final int FAILED_DOWNLOAD_MUSIC = 28;
//    服务次数不足
    public static final int NO_SERVICE_COUNT = 29;
//    查询价格失败
    public static final int FAILED_QUERY_PRICE = 30;
//    查询通知消息失败，类型不正确
    public static final int FAILED_QUERY_INVALIED_TYPES = 31;
//    已经存在的EMAIL
    public static final int EXISTS_EMAIL = 32;
//    已经存在的身份证号
    public static final int EXISTS_ID = 33;
//    AMR转码WAV失败
    public static final int FAILED_CONVERT_WAV = 34;
//    修改密码失败
    public static final int FAILED_CHANGE_PASSWORD = 35;
//    同步用户信息到健康空间失败
    public static final int FAILED_SYNC_HEALTH_SPACE_USER = 36;
//    保存健康空间用户信息失败
    public static final int FAILED_SAVE_HEALTH_SAPCE_USER = 37;
//    两次请求时间间隔太短
    public static final int INVALID_EXCHANGE_TIME= 38;
//    手机号码格式不正确
    public static final int INVALID_USER_MOBILE = 39;
//    密码输入有误，两次密码输入不同
    public static final int INVALID_PASSWORD = 40;
//    服务器超时
    public static final int SERVER_TIME_OUT = 41;
//    上传文件失败
    public static final int FAILED_UPLOAD_FILE = 42;
//    预约数超过限制
    public static final int RESERVE_OVER_LIMIT = 43;
//    未登录或者登陆超时，无权限
    public static final int UNLOGIN = 44;
    //    卡不存在
    public static final int CARD_UNEXIT = 45;
    //    卡已经绑定，不能重复绑定
    public static final int CARD_ISBIND = 46;
    //    卡已经过期，不能绑定
    public static final int CARD_ISEXPIRED = 47;
    //    卡还未激活，请先激活后再绑定
    public static final int CARD_IS_NOT_ACTIVE = 48;
    //    卡余额不足
    public static final int CARD_BALANCE_NOT_ENOUGH = 49;
    //    token错误
    public static final int TOKEN_ERROR = 50;
    //    账号不存在
    public static final int INVALID_MEMBER = 51;
    //    会员服务等级过低
    public  static  final int INVALID_LEVEL_LITTLE = 52;
    //    服务人数已满
    public  static  final int INVALID_FULL=53;
    // 现可更换私人医生replaceable
    public  static  final int DOCTOR_REPLACEABLE=54;
    //现金卡移库中不支持绑定
    public static final int CARD_ISMOVE = 55;
    // 已经预约
    public static final int SCHEDULE_RESERVE = 56;

}