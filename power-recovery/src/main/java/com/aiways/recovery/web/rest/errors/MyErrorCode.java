package com.aiways.recovery.web.rest.errors;


import com.connext.common.exception.ErrorCode;

/**
 * @Author: weilf
 * @Date 2018/8/25 9:48
 * @Description 错误编码及提示信息定义类
 * @Param
*/
public class MyErrorCode extends ErrorCode {

    public static final ErrorCode ERROR_CUSTOMER_NOT_EXIST = new MyErrorCode("error.prepareManage.car.not.exist", "the car not exist!");

    public static final ErrorCode ERROR_MODULE_DATA_UNDERACHIEVE = new MyErrorCode("error.module_data_underachieve","data underachieve");
    public static final ErrorCode ERROR_MODULE_IMPORT_EXCEL = new MyErrorCode("error.module.import.excel","wrong file parsing");

    public static final ErrorCode ERROR_DB_OPTFAIL = new MyErrorCode("error.energyassets.db.optfail", "database operation failed");
    public static final ErrorCode ERROR_FILE_TYPE = new MyErrorCode("error.file.type", "the type of file is error");
    public static final ErrorCode ERROR_EXPORT_TEMPLATE = new MyErrorCode("error.export.template","template export failure");
    /**
     *租户ID为空
     */
    public static final ErrorCode ERROR_TENANTID_IS_EMPTY = new MyErrorCode("error.tenant.id.isempty", "tenantid is empty or null!");
    //连接其他服务失败
    public static final ErrorCode ERROR_SERVICE_CONNECTION_FAILED = new MyErrorCode("error.service.connection.failed","Service connection failed");

    public static final ErrorCode ERROR_BATTERY_PACK_DOES_NOT_EXIST = new MyErrorCode("battery.pack.does.not.exist","battery pack does not exist");

    public static final ErrorCode ERROR_TEMPLATE_DATA_IS_EMPTY = new MyErrorCode("error.data.is.empty","the import template has no data");

    public static final ErrorCode ERROR_REC_SERVICE_DOT_EXIST = new MyErrorCode("error.rec.service.dot.exist","rec service dot is exist");
    public static final ErrorCode ERROR_REC_SERVICE_DOTUNIFIEDCODE_EXIST = new MyErrorCode("error.rec.service.dotunifiedcode.exist","rec service dotunifiedcode is exist");
    public static final ErrorCode ERROR_REC_SERVICE_DOT_NOT_EXIST = new MyErrorCode("error.rec.service.dot.not.exist","rec service dot is not exist");
    public static final ErrorCode ERROR_DELETE_REC_SERVICE_DOT_HAS_RECOVERY = new MyErrorCode("error.delete.rec.service.dot.has.recovery","rec service dot has.recovery");
    public static final ErrorCode ERROR_DB_FAILED = new MyErrorCode("error.db.failed", "database rec service dot failed");
    public static final ErrorCode ERROR_IMPORT_EXCEL = new MyErrorCode("error.import.excel","import failure");
    public static final ErrorCode ERROR_IMPORT_MESSAGE = new MyErrorCode("error.import.message","import failure");

    public static final ErrorCode ERROR_REC_RECOVERY_ENTITY = new MyErrorCode("error.rec.recovery.entity","the entity has no data");
    public static final ErrorCode ERROR_REC_RECOVERY_FAILED_TO_DEL = new MyErrorCode("error.rec.recovery.failed.to.del","failed to del");
    public static final ErrorCode ERROR_REC_RETIRE_ENTITY = new MyErrorCode("error.rec.retire.entity","the entity has no data");
    public static final ErrorCode ERROR_REC_RETIRE_FAILED_TO_DEL = new MyErrorCode("error.rec.retire.failed.to.del","failed to del");
    public static final ErrorCode ERROR_ACCEPT_DATA = new MyErrorCode("error.accept.data","accept data failure");
    public static final ErrorCode ERROR_PUSH_DATA = new MyErrorCode("error.push.data","push car status failure");
    public static final ErrorCode ERROR_REC_RECOVERY_DB_FAILED = new MyErrorCode("error.db.failed", "database rec recovery operation failed");


    public static final ErrorCode ERROR_REC_APPLY_LIST = new MyErrorCode("error.rec.apply.list","the entity has no data");
    public static final ErrorCode ERROR_REC_APPLY_EXIST = new MyErrorCode("error.rec.apply.exist","rec apply is exist");
    public static final ErrorCode ERROR_REC_APPLY_NOT_EXIST = new MyErrorCode("error.rec.apply.not.exist","rec apply is not exist");

    public static final ErrorCode ERROR_REC_BATTERY_EXIST = new MyErrorCode("error.rec.battery.exist","rec.battery.exist");
    public static final ErrorCode ERROR_REC_PUSH_REC = new MyErrorCode("error.rec.push.recovery","rec.push.recovery");
    public static final ErrorCode ERROR_REC_PUSH_RIR = new MyErrorCode("error.rec.push.retire","rec.push.retire");


    public MyErrorCode(String code, String message) {
        super(code, message);
    }
}
