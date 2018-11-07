package com.study.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.dao.slave.SlaveDao;
import com.study.service.UserService;


@Service
public class UserImpl implements UserService{
	
	private static final String NAME_SPACE = "user.";	
	@Autowired
	private SlaveDao slaveDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryUesr() {
		List<Map<String, Object>> list = null;
		try {
			list = (List<Map<String, Object>>) slaveDao.findForList(NAME_SPACE.concat("queryUser"),null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public Map<String, Object> queryByuserName(String userName) {
		java.util.Map<String, Object> map = new HashMap<String, Object>();
		try {
			map =(Map<String, Object> ) slaveDao.findForObject(NAME_SPACE.concat("queryByuserName"), userName);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}
	
	
	

}
