package org.czx.spring.ioc.test.action;

import org.czx.spring.ioc.annotation.*;
import org.czx.spring.ioc.test.service.IQueryService;

/**
 * 公布接口url
 * @author Tom
 *
 */
@CCController
@CCRequestMapping("/web")
public class MyAction {

	@CCAutowired
	IQueryService queryService;

	@CCRequestMapping("/Hello")
	public void query(@CCRequestParam("name") String name){
		String result = queryService.query(name);
		System.out.println(result);
	}
	

}
