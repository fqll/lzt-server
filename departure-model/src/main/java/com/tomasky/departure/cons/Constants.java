package com.tomasky.departure.cons;


/**
 * @author sam
 * 系统常量存放位置
 */
public class Constants {

    // 是与否的通用常量标示(0:否/禁用/无效 1:是/启用/有效)
    public final static int COMMON_NO = 0;
    public final static int COMMON_YES = 1;

    public final static String HTTP_REQUEST_TYPE_GET = "GET";
    public final static String HTTP_REQUEST_TYPE_POST = "POST";

    // http 相关
    public final static int HTTP_OK = 200;
    public final static int HTTP_400 = 400;
    public final static int HTTP_401 = 401;
    public final static int HTTP_403 = 403;
    public final static int HTTP_404 = 404;
    public final static int HTTP_500 = 500;
    public final static int HTTP_800 = 800;

    // 状态/返回key值
    public final static String SUCCESS = "success";
    public final static String FAILURE = "fail";
    public final static String CLOSED = "closed";
    public final static String COOKIE = "cookie";
    public final static String FLAG = "flag";
    public final static String STATUS = "status";
    public final static String PAGE = "page";
    public final static String MESSAGE = "message";
    public final static String ERRORS = "errors";
    public final static String RESULT = "result";
    public final static String NOTHING = "nothing";
    public final static String TYPE = "type"; //状态类型

    /**
     * PAGE
     */
    public final static int DEFAULT_PAGE_SIZE = 10;
    public final static int DEFAULT_PAGE_NO = 1;
    public final static int DO_NOT_PAGE = -1;

    // http获取响应类型(all:所有，responseStr:网页字符串，cookies：网页cookies)
    public final static String HTTP_GET_TYPE_ALL = "all";
    public final static String HTTP_GET_TYPE_STRING = "responseStr";
    public final static String HTTP_GET_TYPE_COOKIES = "cookies";

    // cache/session/cooikes存储时间:30天 (单位秒)
    public static final int CACHE_MAX_AGE = 2592000;
    public static final int CACHE_10_MINUTES = 600;
    public static final int CACHE_30_MINUTES = 1800;
    public static final int CACHE_ONE_HOUR = 3600;
    public static final int CACHE_TWO_HOUR = 7200;
    public static final int CACHE_ONE_DAY = 86400;


    //缓存KEY值
    public static final String CACHE_SYS_TAG = "_sys_wx_";
    public static final String CACHE_WEIXIN_TOKEN = "_weixin_token_";
    public static final String CACHE_WEIXIN_JSAPI = "_weixin_jsapi_";
    public static final String CACHE_WEIXIN_QRCODE = "_weixin_qrcode_";

    public static final String CACHE_MINIAPP_PREFIX = "_miniapp_";


    /**
     * 数据有效
     */
    public static final String DATA_VALID = "1";
    /**
     * 数据无效
     */
    public static final String DATA_INVALID = "0";

    /**
     * 数据已经逻辑删除
     */
    public static final String DATA_DELETE = "1";
    /**
     * 数据未逻辑删除
     */
    public static final String DATA_NOT_DELETE = "0";

    /**
     * 数据初始版本号1
     */
    public static final Integer DATA_DEFAULT_VERSION = 1;

    /**
     * 离职表单核验码长度
     */
    public static final Integer DEPARTURE_CODE_LENGTH = 6;
    /**
     * 创建离职表单操作的顺序码
     */
    public static final Integer DEPARTURE_CREATED_ORDER = 0;
    /**
     * 撤销离职表单操作的顺序码
     */
    public static final Integer DEPARTURE_CANCEL_ORDER = 999;
    /**
     * 第一审批顺位的顺序码
     */
    public static final Integer DEPARTURE_FIRST_ORDER = 1;

    /**
     * 员工通过
     */
    public static final String AUDIT_RESULT_PASS = "1";
    /**
     * 员工拒绝
     */
    public static final String AUDIT_RESULT_REFUSE = "2";

    /**
     * 不是主企业
     */
    public static final String NOT_DEFAULT = "0";
    /**
     * 是主企业
     */
    public static final String IS_DEFAULT = "1";

    /**
     * 预入职
     */
    public static final String DELAY_ENTRY = "0";
    /**
     * 已入职
     */
    public static final String ENTRY = "1";

    /**
     * 开具离职证明的权限ID
     */
    public static final Integer AUTHORITY_CREATE_DEPARTURE = 1;

    /**
     * 入职背调的权限ID
     */
    public static final Integer AUTHORITY_VERIFICATION_DEPARTURE = 3;

    /**
     * 保存草稿
     */
    public static final String SAVE_TYPE_DRAFT = "0";
    /**
     * 确认开具
     */
    public static final String SAVE_TYPE_CONFIRM = "1";

    /**
     * 开具离职证明
     */
    public static final String AUTHORITY_KEY_CREATE = "create_departure";
    /**
     * 我的审批
     */
    public static final String AUTHORITY_KEY_APPROVAL = "my_approval";
    /**
     * 入职背调
     */
    public static final String AUTHORITY_KEY_DEPARTURE = "verification_departure";
    /**
     * 离职员工库
     */
    public static final String AUTHORITY_KEY_EMPLOYEE = "employee_list";
    /**
     * 离职分析
     */
    public static final String AUTHORITY_KEY_ANALYSIS = "analysis_departure";
    /**
     * 员工权限
     */
    public static final String AUTHORITY_KEY_AUTHORITY = "employee_authority";
    /**
     * 企业专属编码
     */
    public static final String AUTHORITY_KEY_CREATED = "created_info";
    /**
     * 引导模式
     */
    public static final String MODE_GUIDE = "guide";

    /**
     * 背调类型：发起背调
     */
    public static final String CHECK_TYPE_SEND = "0";
    /**
     * 背调类型：参与背调
     */
    public static final String CHECK_TYPE_JOIN = "1";

}