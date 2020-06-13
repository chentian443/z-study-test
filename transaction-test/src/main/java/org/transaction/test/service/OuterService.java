package org.transaction.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.transaction.test.entity.User1;
import org.transaction.test.entity.User2;

@Service
public class OuterService {
	
	@Autowired
	private User1Service user1Service;
	
	@Autowired
	private User2Service user2Service;
	
	/**----------------------- 外部方法无事务，内部方法required --------------------------*/
	
	/**
	 * 事务由service的aop开启，并在service执行结束时提交事务，所以：
	 * service之外抛出异常，不会回滚，这里
	 */
	public void notransaction_exception_required_required(){
        User1 user1=User1.builder().name("张三").build();
        user1Service.addRequired(user1);
        
        User2 user2=User2.builder().name("李四").build();
        user2Service.addRequired(user2);
        // 此处抛出异常不影响上面两个service事务
        throw new RuntimeException();
    }
	
	/**
	 * 事务提交后的异常不会导致回滚，只会回滚范围内的事务
	 */
	public void notransaction_required_required_exception(){
		User1 user1=User1.builder().name("张三").build();
        user1Service.addRequired(user1);
        
        User2 user2=User2.builder().name("李四").build();
        // 第一个事务已经结束，在第二个事务抛出异常，不影响第一个事务
        user2Service.addRequiredException(user2);
    }
	
	
	/**--------------------- 外部方法required，内部方法required -------------------------*/
	
	/**
	 * 当前service开启事务，内部两个事务沿用，事务的提交在当前service结束时执行，
	 * 当前方法抛出异常，全部回滚
	 * 张三、李四均插入失败
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void transaction_exception_required_required(){
        User1 user1=User1.builder().name("张三").build();
        user1Service.addRequired(user1);
        
        User2 user2=User2.builder().name("李四").build();
        user2Service.addRequired(user2);
        throw new RuntimeException();
    }
	
	/**
	 * 当前service开启事务，内部两个事务沿用，第二个方法抛出异常，全部回滚
	 * 张三、李四均插入失败
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void transaction_required_required_exception(){
		User1 user1=User1.builder().name("张三").build();
        user1Service.addRequired(user1);
        
        User2 user2=User2.builder().name("李四").build();
        user2Service.addRequiredException(user2);
    }
	
	/**
	 * 张三、李四均插入失败
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void transaction_required_required_exception_try(){
		User1 user1=User1.builder().name("张三").build();
        user1Service.addRequired(user1);
        
        User2 user2=User2.builder().name("李四").build();
        try {
        	user2Service.addRequiredException(user2);
		} catch (Exception e) {
			System.out.println("即使捕获异常，还是回滚所有插入操作，因为大家是同一个事务");
		}
    }
	
	
	/**--------------------外部方法无事务，内部方法requireds_new -------------------------*/
	
	/**
	 * 张三、李四均插入成功：
	 */
	public void notransaction_exception_requiresNew_requiresNew(){
		User1 user1=User1.builder().name("张三").build();
        user1Service.addRequiresNew(user1);
        
        User2 user2=User2.builder().name("李四").build();
        user2Service.addRequiresNew(user2);
        throw new RuntimeException();
        
    }
	
	/**
	 * 张三插入成功，李四插入失败；各自开启自己的事务
	 */
	public void notransaction_requiresNew_requiresNew_exception(){
		User1 user1=User1.builder().name("张三").build();
        user1Service.addRequiresNew(user1);
        
        User2 user2=User2.builder().name("李四").build();
        user2Service.addRequiresNewException(user2);
    }
	
	
	
	/**--------------------外部方法有REQUIRED事务，内部方法requireds_new -------------------------*/
	
	/**
	 * 张三插入失败，李四与王五都插入成功
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void transaction_exception_required_requiresNew_requiresNew(){
		// 插入张三  加入外围事务
		User1 user1=User1.builder().name("张三").build();
        user1Service.addRequired(user1);
        // 开启自己的新事务，并挂起外围事务，结束后提交自己的事务
        User2 user2=User2.builder().name("李四").build();
        user2Service.addRequiresNew(user2);
        // 同样，开启自己的新事务，并挂起外围事务，结束后提交自己的事务
        User2 user3=User2.builder().name("王五").build();
        user2Service.addRequiresNew(user3);
        
        throw new RuntimeException();
        
    }
	
	/**
	 * 张三插入失败，李四插入成功，王五插入失败
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void transaction_required_requiresNew_requiresNew_exception(){
		// 插入张三  加入外围事务
		User1 user1=User1.builder().name("张三").build();
        user1Service.addRequired(user1);
        // 开启自己的新事务，并挂起外围事务，结束后提交自己的事务
        User2 user2=User2.builder().name("李四").build();
        user2Service.addRequiresNew(user2);
        // 开启自己的新事务，并挂起外围事务，自己的事务出现异常，回滚自己的事务；同时异常影响外围方法，导致外围事务也回滚
        User2 user3=User2.builder().name("王五").build();
        user2Service.addRequiresNewException(user3);
    }
	
	/**
	 * 张三插入成功，李四插入成功，王五插入失败
	 */
	@Transactional(propagation = Propagation.REQUIRED)
    public void transaction_required_requiresNew_requiresNew_exception_try(){
		// 插入张三 ，加入外围事务
		User1 user1=User1.builder().name("张三").build();
        user1Service.addRequired(user1);
        // 开启自己的新事务，并挂起外围事务，结束后提交自己的事务
        User2 user2=User2.builder().name("李四").build();
        user2Service.addRequiresNew(user2);
        // 开启自己的新事务，并挂起外围事务，自己的事务出现异常，回滚自己的事务；但异常被捕获，不影响外围事务的提交
        User2 user3=User2.builder().name("王五").build();
        try {
            user2Service.addRequiresNewException(user3);
        } catch (Exception e) {
            System.out.println("回滚");
        }
    }
	
	
	
	/**--------------------外部方法无事务，内部方法nested -------------------------*/
	/**
	 * 张三、李四均插入成功
	 */
	public void notransaction_exception_nested_nested(){
		User1 user1=User1.builder().name("张三").build();
        user1Service.addNested(user1);
        
        User2 user2=User2.builder().name("李四").build();
        user2Service.addNested(user2);
        
        throw new RuntimeException();
    }
	/**
	 * 张三插入成功，李四插入失败
	 */
	public void notransaction_nested_nested_exception(){
		
		User1 user1=User1.builder().name("张三").build();
		user1Service.addNested(user1);
        
        User2 user2=User2.builder().name("李四").build();
        user2Service.addNestedException(user2);
        
    }
	
	
	/**--------------------外部方法开启事务，内部方法nested -------------------------*/
	// nested开启子事务，外围事务回滚，子事务也会回滚；但子事务回滚，外围事务继续
	/**
	 * 张三、李四均插入失败：外围方法开启事务，内部事务为外围事务的子事务，外围方法回滚，内部方法也要回滚。
	 */
	@Transactional
    public void transaction_exception_nested_nested(){
		// 开启事务，并作为外围事务的子事务
		User1 user1=User1.builder().name("张三").build();
        user1Service.addNested(user1);
        // 开启事务，并作为外围事务的子事务
        User2 user2=User2.builder().name("李四").build();
        user2Service.addNested(user2);
        // 外围方法抛出异常，导致回滚，子事务也要回滚
        throw new RuntimeException();
    }
	/**
	 * 张三、李四均插入失败
	 */
	@Transactional
    public void transaction_nested_nested_exception(){
		// 开启事务，并作为外围事务的子事务
		User1 user1=User1.builder().name("张三").build();
        user1Service.addNested(user1);
        // 开启事务，并作为外围事务的子事务，出现异常，被外围事务感知，导致外围事务也异常，进而导致第一个子事务回滚
        User2 user2=User2.builder().name("李四").build();
        user2Service.addNestedException(user2);
    }
	/**
	 * 张三插入成功，李四插入失败：子事务的回滚不会导致外围事务回滚，但外围事务的回滚会导致所有子事务回滚
	 */
	@Transactional
    public void transaction_nested_nested_exception_try(){
		User1 user1=User1.builder().name("张三").build();
        user1Service.addNested(user1);
        
        User2 user2=User2.builder().name("李四").build();
        try {
            user2Service.addNestedException(user2);
        } catch (Exception e) {
            System.out.println("方法回滚");
        }
    }
}
