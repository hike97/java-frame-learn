package com.mashibing.spring.ioc;

import lombok.Data;

/**
 * @author hike97 2month
 * @create 2021-04-29 20:17
 * @desc
 **/
@Data
public class UserController {
	@Autowried
	private UserService userService;
}
