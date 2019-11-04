package com.dousnl.utils.execl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GlobalParameter {

    /**
     * 静态文件服务器IP地址
     */
    public static String ip;


    public static String adSign;

    /**
     * 登录服务器服务器IP地址
     */
    public static String loginHost;
    /**
     * 登录服务器接口端口
     */
    public static String loginPort;

    /**
     * 登录服务器名称
     */
    public static String loginServerName;

    /**
     * 登录服务路径
     */
    public static String loginServerPath;

    public static String messagesUrl;

    public static String micServiceUrl;

    public static String redis_host;

    public static Integer redis_port;

    public static String redis_pass;

    public static String redis_timeout;

    public static Integer redis_maxIdle;

    public static Integer redis_maxTotal;

    public static Integer redis_maxWait;

    public static boolean redis_testOnBorrow;

    public static Integer redis_database;

    public static List<Integer> caseIdList;

    public static String shareUrlHost;

    public static String wulaUrl;

    public static String wulaAppKey;

    public static String wulaAppSecret;

    /**
     * 第一信息接口地址
     */
    public static String oneXinxiUrl;
    /**
     * 第一信息用户账号
     */
    public static String oneXinxiName;
    /**
     * 第一信息接口密码
     */
    public static String oneXinxiPwd;

    public static String popularPassword;

    //配置文件中读取ip地址信息
    static {
        Properties props = new Properties();
        InputStream in = GlobalParameter.class.getResourceAsStream("/environment/application.properties");
        try {
            props.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ip = props.getProperty("server.ip");
        loginHost = props.getProperty("login.server.host");
        loginPort = props.getProperty("login.server.port");
        loginServerName = props.getProperty("login.server.name");
        loginServerPath = props.getProperty("login.server.path");
        shareUrlHost = props.getProperty("urm.share.host");


        adSign = props.getProperty("ad.sign");

        messagesUrl = props.getProperty("messages.url");

        micServiceUrl = props.getProperty("micservice.url");

        caseIdList = new ArrayList<Integer>();

        String caseIds = props.get("urm.caseIds").toString();

        String[] caseIdArr = caseIds.split(",");

        for (String caseId : caseIdArr) {
            caseIdList.add(Integer.parseInt(caseId));
        }


        Properties redis = new Properties();
        InputStream rin = GlobalParameter.class.getResourceAsStream("/environment/redis.properties");

        try {
            redis.load(rin);
            rin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        redis_host = redis.getProperty("redis.host");
        redis_port = Integer.valueOf(redis.getProperty("redis.port"));
        redis_pass = redis.getProperty("redis.pass");
        redis_timeout = redis.getProperty("redis.timeout");
        redis_maxIdle = Integer.valueOf(redis.getProperty("redis.maxIdle"));
        redis_maxTotal = Integer.valueOf(redis.getProperty("redis.maxTotal"));
        redis_maxWait = Integer.valueOf(redis.getProperty("redis.maxWait"));
        redis_testOnBorrow = Boolean.parseBoolean(redis.getProperty("redis.testOnBorrow"));
        redis_database = Integer.valueOf(redis.getProperty("redis.db.index"));

        wulaUrl = props.getProperty("wula.url");
        wulaAppKey = props.getProperty("wula.appKey");
        wulaAppSecret = props.getProperty("wula.appSecret");

        oneXinxiUrl = props.getProperty("oneXinXi.url");
        oneXinxiName = props.getProperty("oneXinXi.name");
        oneXinxiPwd = props.getProperty("oneXinXi.pwd");

        popularPassword = props.getProperty("popular.password");
    }

    /**
     * 跟进方式:1-上门;2-去电;3-来电;4-来人;5-短信;6-微信;7-其他
     */
    /**
     * 1-上门 *
     */
    public static final int FOLLOW_TYPE_1 = 1;
    /**
     * 2-去电*
     */
    public static final int FOLLOW_TYPE_2 = 2;
    /**
     * 3-来电*
     */
    public static final int FOLLOW_TYPE_3 = 3;
    /**
     * 4-来人*
     */
    public static final int FOLLOW_TYPE_4 = 4;
    /**
     * 5-短信 *
     */
    public static final int FOLLOW_TYPE_5 = 5;
    /**
     * 6-微信 *
     */
    public static final int FOLLOW_TYPE_6 = 6;
    /**
     * 7-其他 *
     */
    public static final int FOLLOW_TYPE_7 = 7;

    /**
     * 客户来源 来电
     */
    public static final String SOURCE_1 = "1";
    /**
     * 客户来源 来人
     */
    public static final String SOURCE_2 = "2";
    /**
     * 客户来源 其他
     */
    public static final String SOURCE_3 = "3";

    /**
     * 订单类型-认筹
     */
    public static final Integer PLEDGED = 1;

    /**
     * 订单类型-认购
     */
    public static final Integer SUBSCRIPTION = 2;

    /**
     * 订单类型-签约
     */
    public static final Integer SIGN = 3;

    /**
     * 待完善客户资料
     */
    public static final String TRANSACTIONFLAG_0 = "0";

    /**
     * 未交易客户
     */
    public static final String TRANSACTIONFLAG_1 = "1";
    /**
     * 已交易客户
     */
    public static final String TRANSACTIONFLAG_2 = "2";


    /**
     * 未交易客户
     */
    public static final Integer NOTTRANSACTION = 1;

    /**
     * 统计类型 1-本日;2-本周;3-本月;
     */
    /**
     * 1-本日   *
     */
    public static final int TYPE_TODAY = 1;
    /**
     * 2-本周   *
     */
    public static final int TYPE_THIS_WEEK = 2;
    /**
     * 3-本月   *
     */
    public static final int TYPE_THIS_MONTH = 3;
    /**
     * 3-本月   *
     */
    public static final int TYPE_THIS_YEAR = 4;


    /**
     * 小时区间 前面有空格
     */
    /**
     * 00:00:00   *
     */
    public static final String FIRST_TIME = " 00:00:00";
    /**
     * 23:59:59  *
     */
    public static final String LAST_TIME = " 23:59:59";

    //今日待回访
    public static final Integer VISITFORTODAY = 1;

    //逾期未跟进
    public static final Integer OVERTIMEFORFOLLOW = 2;

    //三日内回收
    public static final Integer TIMEFORRECYCLE = 3;

    //新分配客户
    public static final Integer NEWALLOCATED = 4;

    //资料待完善
    public static final Integer NEEDFILLDATA = 5;

    //三日内回访
    public static final Integer TIMEFORVISIT = 6;

    //限制查询数量
    public static final Integer LIMITCOUNT = 4;

    /**
     * 客户来源 转介
     */
    public static final Integer SOURCE_5 = 5;

    /**
     * 客户来源 引入
     */
    public static final Integer SOURCE_8 = 8;

    /**
     * 转介 佣金类型:A类型 *
     */
    public static final Integer REFERRAL_RULE_COMMISSION_TYPE_A = 1;
    /**
     * 转介 佣金类型:B类型 *
     */
    public static final Integer REFERRAL_RULE_COMMISSION_TYPE_B = 4;

    /**
     * 转介规则是否有效
     */

    /**
     * 转介 是否有效:1-是 *
     */
    public static final Byte REFERRAL_RULE_IS_VALID_YES = 1;
    /**
     * 转介 是否有效:0-否 *
     */
    public static final Byte REFERRAL_RULE_IS_VALID_NO = 0;


    /**
     * 1-未审核 *
     */
    public static final Integer AUDIT_INIT = 1;

    /**
     * 2-通过审核 *
     */
    public static final Integer AUDIT_ADOPT = 2;

    /**
     * 3-退回审核(审核不通过) *
     */
    public static final Integer AUDIT_RETURN = 3;

    /**
     * 统计纬度级别 案场
     */
    public static final Integer LEVEL_4 = 4;

    /**
     * 统计纬度级别 楼栋
     */
    public static final Integer LEVEL_5 = 5;

    /**
     * 意向级别 1-A
     */
    public static final int INTENTION_LEVEL_1 = 1;
    /**
     * 意向级别 3-C
     */
    public static final int INTENTION_LEVEL_3 = 3;

    /**
     * 审核流程，认购
     */
    public static final int AUDIT_STATUS_4 = 4;

    /**
     * 审核流程，退订
     */
    public static final int AUDIT_STATUS_6 = 6;

    /**
     * 首访
     */
    public static final int FOLLOW_STATUS_1 = 1;

    /**
     * 回笼
     */
    public static final int FOLLOW_STATUS_2 = 2;


    /**
     * 平台-首页
     */
    public static final int MAINBANNER = 1;

    /**
     * 买房管家-应用中心
     */
    public static final int APPLICATIONBANNER = 5;

    /**
     * 第一次来人形式 = 1.来人
     */
    public static final int ISFIRSTCOME = 1;

    /**
     * 第一次来人形式 = 2.电转访
     */
    public static final int ISFIRSTTELTOCOME = 2;

    /**
     * 第一次来人形式 = 3.其他转访
     */
    public static final int ISFIRSTOTHERTOCOME = 3;

    /**
     * 第一次来人形式 = 4.转介转来访
     */
    public static final int ISFIRSTREFERRALTOCOME = 4;
}
