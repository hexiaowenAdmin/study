package com.study.dao.slave;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
@Repository("slaveDao")
public class SlaveDao {
	
	@Resource(name = "slaveSqlSessionTemplate")
	private SqlSessionTemplate slaveSqlSessionTemplate;
	
	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForObject(String str, Object obj) throws Exception {
		return slaveSqlSessionTemplate.selectOne(str, obj);
	}

	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForList(String str,Object obj) throws Exception {
		return slaveSqlSessionTemplate.selectList(str,obj);
	}
	
	public Object findForMap(String str, Object obj, String key) throws Exception {
		return slaveSqlSessionTemplate.selectMap(str, obj, key);
	}
}
