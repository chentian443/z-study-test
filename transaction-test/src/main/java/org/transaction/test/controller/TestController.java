package org.transaction.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.transaction.test.entity.User1;
import org.transaction.test.entity.User2;
import org.transaction.test.service.OuterService;
import org.transaction.test.service.User1Service;
import org.transaction.test.service.User2Service;

@RestController
@RequestMapping("test/")
public class TestController {
	
	@Autowired
	private OuterService outerService;
	
	@Autowired
	private User1Service user1Service;
	
	@Autowired
	private User2Service user2Service;
	
	@GetMapping("1")
	public String testUser1Service(){
		User1 user = User1.builder().name("tian").build();
		user1Service.addRequired(user);
		return "testUser1Service";
	}
	
	@GetMapping("2")
	public String testUser2Service(){
		User2 user = User2.builder().name("tian").build();
		user2Service.addRequired(user);
		return "testUser2Service";
	}
	
	@GetMapping("2e")
	public String testUser2ServiceException(){
		User2 user = User2.builder().name("tian-e").build();
		user2Service.addRequiredException(user);
		return "testUser2ServiceException";
	}
	
	
	
	@GetMapping("nterr")
	public String testOuterServiceNterr(){
		outerService.notransaction_exception_required_required();
		return "over";
	}
	@GetMapping("ntrre")
	public String testOuterServiceNtrre(){
		outerService.notransaction_required_required_exception();
		return "over";
	}
	
	
	
	@GetMapping("terr")
	public String testOuterServiceTerr(){
		outerService.transaction_exception_required_required();
		return "over";
	}
	@GetMapping("trre")
	public String testOuterServiceTrre(){
		outerService.transaction_required_required_exception();
		return "over";
	}
	@GetMapping("trretry")
	public String testOuterServiceTrretry(){
		outerService.transaction_required_required_exception_try();
		return "over";
	}
	
	
	
	
	@GetMapping("nternrn")
	public String nternrn(){
		outerService.notransaction_exception_requiresNew_requiresNew();
		return "over";
	}
	@GetMapping("ntrnrne")
	public String ntrnrne(){
		outerService.notransaction_requiresNew_requiresNew_exception();
		return "over";
	}
	
	
	
	@GetMapping("ternrn")
	public String ternrn(){
		outerService.transaction_exception_required_requiresNew_requiresNew();
		return "over";
	}
	@GetMapping("trnrne")
	public String trnrne(){
		outerService.transaction_required_requiresNew_requiresNew_exception();
		return "over";
	}
	@GetMapping("trnrnetry")
	public String trnrne_try(){
		outerService.transaction_required_requiresNew_requiresNew_exception_try();
		return "over";
	}
	
	
	
	
	@GetMapping("ntess")
	public String ntess(){
		outerService.notransaction_exception_nested_nested();
		return "over";
	}
	@GetMapping("ntsse")
	public String ntsse(){
		outerService.notransaction_nested_nested_exception();
		return "over";
	}
	
	
	
	@GetMapping("tess")
	public String tess(){
		outerService.transaction_exception_nested_nested();
		return "over";
	}
	@GetMapping("tsse")
	public String tsse(){
		outerService.transaction_nested_nested_exception();
		return "over";
	}
	@GetMapping("tssetry")
	public String tssetry(){
		outerService.transaction_nested_nested_exception_try();
		return "over";
	}
	
	
	
	
}
