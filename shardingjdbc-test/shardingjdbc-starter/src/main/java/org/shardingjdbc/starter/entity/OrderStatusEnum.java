package org.shardingjdbc.starter.entity;

public enum OrderStatusEnum {
	PROCESSING(1);
	
	OrderStatusEnum(Integer code) {
	    this.code = code;
	}
	private Integer code;
	
	public Integer getCode(){
		return code;
	}
}
