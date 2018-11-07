package com.study.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.dao.slave.SlaveDao;
import com.study.service.SysService;
@Service
public class SysServiceImpl implements SysService{
	
	private static final String NAME_SPACE = "sys.";	
	
	@Autowired
	private SlaveDao slaveDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<String> queryMentId(Integer userId) {
		Set<String> set = new HashSet<String>();
		try {
			List<Integer>  list = (List<Integer>) slaveDao.findForList(NAME_SPACE.concat("queryMentId"),userId);
			for(Integer id:list){
				set.add(String.valueOf(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return set;
	}

	@Override
	public List<Map<String, Object>> getMentForList() {
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
		try {
			List<Map<String, Object>> listSysMap = (List<Map<String, Object>>) slaveDao.findForList(NAME_SPACE.concat("getMentForList"), null);
			if(listSysMap!=null){
				for(int i=0;i<listSysMap.size();i++){
					Map<String, Object> map = new HashMap<String, Object>();
					Map<String, Object> mapSys= listSysMap.get(i);
					map.put(String.valueOf(mapSys.get("menu_id")), mapSys.get("menu_url"));
					listMap.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listMap;
	}

}
