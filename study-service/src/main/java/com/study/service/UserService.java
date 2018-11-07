package com.study.service;

import java.util.List;
import java.util.Map;


public interface UserService {

	List<Map<String, Object>> queryUesr();
	
	Map<String, Object> queryByuserName(String userName);
}
