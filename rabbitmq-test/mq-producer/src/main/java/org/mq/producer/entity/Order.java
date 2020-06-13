package org.mq.producer.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
    private String name;
    private String messageId;

}
