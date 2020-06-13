package org.springevent.test.beanname;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class TestBeanname {

	/**
	 * 会启动报错，@Autowired默认注入bean的方式为bytype，然而TopService的实现有两个；
	 * 其次，在bytype有多个的情况下，@Autowired使用byname的方式。
	 * 	因为beanname与该属性变量名都不一样，所以报错
	 * 解决方式：
	 * 1）如下通过byname方式注入，即将变量名改为与需要注入的bean的name一致
	 * 2）@Qualifier("topServiceImpl2")，指定注入的beanname
	 * 3）在TopServiceImpl2类上添加@Primary，表示相同类型时，优先注入这个
	 * 
	 * 注意：@Bean的beanname默认为申明bean的方法名，
	 * 而@Component的beanname默认为首字母小写，特殊情况除外，类名称以两个大写字母开头，则beanname默认为类名；
	 * 
	 */
//	@Autowired
//	private TopService topService;
	
	/**
	 * 该种方式不报错，通过byname方式注入
	 */
	@Autowired 
	private TopService topServiceImpl2;
	
	/**
	 * 该种方式报错，TTopServiceImpl的benname直接为类名
	 */
//	@Autowired 
//	private TopService tTopServiceImpl;
	
	/**
	 * 该种方式不报错
	 */
	@Autowired 
	private TopService TTopServiceImpl;
	
	
	
}
