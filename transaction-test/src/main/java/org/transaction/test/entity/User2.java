package org.transaction.test.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User2 implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
    private String name;
}
