package org.shardingjdbc.manu.entity;

public enum GenderEnum {
	MALE(1), FEMALE(2);
	
	GenderEnum(Integer code) {
	    this.code = code;
	}
	private Integer code;
	
	public Integer getCode(){
		return code;
	}
}