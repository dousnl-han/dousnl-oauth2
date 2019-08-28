package com.aiways.recovery.web.rest.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.UUID;

/**
 * 公共json响应bean
 *
 * @author hanliang
 *
 */
@JsonInclude(Include.NON_NULL)
public class JsonMsgBean implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Boolean success;
	private String code;
	private Object data; // 返回数据对象

	public JsonMsgBean() {
		super();
	}

	public JsonMsgBean(Boolean success) {
		this.success = success;
	}

	public JsonMsgBean(Boolean success, String code, Object data) {
		super();
		this.success = success;
		this.code = code;
		this.data = data;
	}

	public JsonMsgBean initSuccess() {
		this.success = true;
		return this;
	}

	public JsonMsgBean initSuccess(Object data) {
		this.success = true;
		this.data = data;
		return this;
	}

	public JsonMsgBean initSuccess(String code, Object data) {
		this.success = true;
		this.code = code;
		this.data = data;
		return this;
	}

	public JsonMsgBean initFailure() {
		this.success = false;
		return this;
	}

	public JsonMsgBean initFailure(Object data) {
		this.success = false;
		this.data = data;
		return this;
	}

	public JsonMsgBean initFailure(String code, Object data) {
		this.success = false;
		this.code = code;
		this.data = data;
		return this;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString().replace("-", "").length());
	}

}
