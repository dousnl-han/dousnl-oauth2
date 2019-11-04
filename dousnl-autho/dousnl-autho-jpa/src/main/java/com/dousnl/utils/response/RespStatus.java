package com.dousnl.utils.response;

public class RespStatus {

	public static final long CODE_SUCCESSFUL = 0L;

	public static final String MSG_SUCCESSFUL = "ok";

	public static final long CODE_FAILED = 1L;

	public static final String MSG_FAILED = "failed";

	public static final RespStatus SUCCESSFUL = new RespStatus(String.valueOf(CODE_SUCCESSFUL), MSG_SUCCESSFUL);

	public static final RespStatus FAILED = new RespStatus(String.valueOf(CODE_FAILED), MSG_FAILED);

	private String code;

	private String msg;

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
	public RespStatus() {
		super();
	}

	public RespStatus(String code) {
		super();
		this.code = code;
	}

	public RespStatus(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static long getCodeSuccessful() {
		return CODE_SUCCESSFUL;
	}

	public static String getMsgSuccessful() {
		return MSG_SUCCESSFUL;
	}

	public static long getCodeFailed() {
		return CODE_FAILED;
	}

	public static String getMsgFailed() {
		return MSG_FAILED;
	}

	public static RespStatus getSUCCESSFUL() {
		return SUCCESSFUL;
	}

	public static RespStatus getFAILED() {
		return FAILED;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
