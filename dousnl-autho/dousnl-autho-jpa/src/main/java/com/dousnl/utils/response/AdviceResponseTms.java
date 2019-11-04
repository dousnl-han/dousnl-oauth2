package com.dousnl.utils.response;

import lombok.Data;

/**
 * 派单新增TMS返回信息
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/10/22 15:35
 */
@Data
public class AdviceResponseTms {

    //状态:字典表（0成功1失败）
    Integer status;
    //错误原因
    String errorMsg;
    //派单号（派单系统 订单编码）
    Object data;

    public AdviceResponseTms(Integer status, String errorMsg) {
        this.status = status;
        this.errorMsg = errorMsg;
    }

    public AdviceResponseTms(Integer status, String errorMsg, Object data) {
        this.status = status;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public static AdviceResponseTms failed(String msg) {
        return new AdviceResponseTms(1, msg);
    }
}
