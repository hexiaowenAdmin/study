package com.study.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysService {
	Set<String> queryMentId(Integer userId);
	List<Map<String, Object>> getMentForList();
}
