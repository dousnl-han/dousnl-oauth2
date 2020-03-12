package com.dousnl.domain;

import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/11/28 13:09
 */
@Data
public class EsbPush {

    private String repairOrderNo;

    private String repairNo;

    private Byte orderStatus;

    private Date realFinishTime;

    private String desc;

    private String operator;

    private String operatorMobile;
}
