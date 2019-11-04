package com.dousnl.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常返回信息枚举
 *
 * @author 赵海涛
 * @version $ID:ErrorEnums, v0.1 2018/3/30 赵海涛 Exp $
 */
@Getter
@AllArgsConstructor
public enum ErrorEnums {

    //权限异常
    SYSTEM_NOT_AUTHENTICATION_ERROR("401", "用户暂未认证"),
    SYSTEM_UNAUTHORIZED_ERROR("403", "用户无权限"),
    SYSTEM_TOKEN_VALIDATOR_ERROR("998", "TOKEN验证错误"),
    SYSTEM_SQL_INJECT_ERROR("999", "参数存在sql注入风险"),
    SYSTEM_ALREADY_LOGIN("error_login","您的账号已在其他终端登录，如不是您本人操作，请及时联系后台"),

    //==================通用错误码定义====================
    SUCCESS("000000", "交易成功"),
    SYSTEM_BEAN_COPY_ERROR("000001", "对象拷贝异常"),
    SYSTEM_STRING_TO_DATE_ERROR("000002", "字符串转日期失败"),
    SYSTEM_PARAM_VALIDATOR_ERROR("000003", "参数校验失败"),

    DB_ERROR("000010", "数据库查询异常"),
    DB_INSERT_ERROR("000011", "数据库插入异常"),
    DB_UPDATE_ERROR("000012", "数据库更新异常"),
    DB_SELECT_ERROR("000013", "数据库查询异常"),

    LOGIN_ERROR("000020", "登录失败"),
    LOGIN_USER_NOT_EXIST_ERROR("000021", "用户不存在"),
    LOGIN_AUTHENTICATION_FAILURE("000022", "身份验证失败"),
    LOGIN_AD_CONNECT_ERROR("000023", "AD域连接失败"),
    LOGIN_TOKEN_CREATE_ERROR("000024", "token生成失败"),
    LOGIN_USER_DISABLED("000025", "用户状态为无效状态"),

	EXPORT_FOUR_BANK_ERROR("000030", "四大银行报表导出失败"),
	NO_PERMISSION_TO_DOWNLOAD("000031","没有权限下载或文件不存在"),
    NO_QUALIFICATION_UPDATE_TO_PROJECTTYPE("000032","B级卡信息未完善,请完善B级卡信息"),
    NO_DATA_SHIRO("000034","没有资格更新项目银行类型"),
    EXPORT_Daily_Monitoring_ERROR("000035", "日常监控报表导出失败"),
	RONG_CLOUD_REST_ERROR("000408", "融云服务端请求失败"),
    ;
	
	
    private final String code;
    private final String desc;

}
