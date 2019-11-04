package com.dousnl.utils.execl;

import java.util.List;

public class SheetVO {
	
	public SheetVO(){
		
	}
	
	public SheetVO(List<String> header, List<?> content, List<String> fieldName, String sheetName){
		this.header = header;
		this.content = content;
		this.fieldName=fieldName;
		this.sheetName = sheetName;
	}
	
	List<String> header;
	
	List<?> content;
	
	List<String> fieldName;
	

	
	String sheetName;

	public List<String> getHeader() {
		return header;
	}

	public void setHeader(List<String> header) {
		this.header = header;
	}

	public List<?> getContent() {
		return content;
	}

	public void setContent(List<?> content) {
		this.content = content;
	}

	public List<String> getFieldName() {
		return fieldName;
	}

	public void setFieldName(List<String> fieldName) {
		this.fieldName = fieldName;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
	

}
