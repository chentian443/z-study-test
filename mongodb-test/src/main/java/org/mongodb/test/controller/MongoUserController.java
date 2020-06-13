package org.mongodb.test.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import org.mongodb.test.model.MongoUser;
import org.mongodb.test.model.Result;
import org.mongodb.test.service.MongoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("mongodb")
@Api("mongodb測試")
public class MongoUserController {

    private final MongoUserService service;

    @Autowired
    public MongoUserController(MongoUserService service) {
        this.service = service;
    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiIgnore()
    @ApiOperation(value = "重定向到api首页")
    public ModelAndView index() {
        return new ModelAndView("redirect:/swagger-ui.html");
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable("id") Long id) {
        MongoUser mongoUser = service.findById(id);
        return new Result<>(mongoUser);
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    public Result findAll() {
        return new Result<>(service.findAll());
    }


    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Result add(@RequestBody MongoUser user) {
        return new Result<>(service.add(user));
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public Result delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new Result("delete ok");
    }
    
    
    @GetMapping(value = "findByName/{name}")
    public Result findByName(@PathVariable("name") String name) {
        return new Result(service.findByName(name));
    }
    
    
    /**
     * 另一种方式：使用mongotemplate来连接查询
     */
    @Autowired
	private MongoTemplate mongotemplate;
    
    @GetMapping(value = "findByEmail/{email}")
	@ResponseBody
	public Result findByEmail(@PathVariable("email") String email) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));
		MongoUser mongoUser = mongotemplate.findOne(query, MongoUser.class);
		return new Result(mongoUser);
	}
    

}
