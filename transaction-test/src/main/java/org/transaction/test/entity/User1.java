package org.transaction.test.entity;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User1 implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
    private String name;
}
