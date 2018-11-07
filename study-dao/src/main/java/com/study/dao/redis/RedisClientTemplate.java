package com.study.dao.redis;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;

@SuppressWarnings("all")
@Repository("redisClientTemplate")
public class RedisClientTemplate {

    @Autowired
    private RedisTemplate redisTemplate;

    /** 
     * 添加redis的map缓存，设置超时时间 
     * 此超时时间会覆盖掉前一个超时时间 
     * @param redisKey 缓存key 
     * @param hashKey  
     * @param object 缓存对象 
     * @param timeout 超时时间 
     * @param unit 超时单位 
     */  
    public void hset(String redisKey, String hashKey, Object object, long timeout, TimeUnit unit){  
    	try {  
    		BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(redisKey);  
    		boundHashOperations.put(hashKey, JSON.toJSONString(object));  
    		boundHashOperations.expire(timeout, unit);  
        } catch (Throwable e) {
            e.printStackTrace();
       }  
    }  
	
	
	/**
	 * 缓存objct对象，最终转换json格式到缓存服务器。
	 * @param redisKey
	 * @param hashKey
	 * @param object
	 */
	public void hsetNoExpire(String redisKey, String hashKey, Object object) {  
		try {  
	        BoundHashOperations<String,String,String> boundHashOperations = redisTemplate.boundHashOps(redisKey);  
	        boundHashOperations.put(hashKey, JSON.toJSONString(object)); 
	    } catch (Throwable e) {  
	        e.printStackTrace();
	   }  
	}
	
	
	/**
	 * 获取缓存数据
	 * @param redisKey
	 * @param hashKey
	 * @param clazz
	 * @return
	 */
    public <T> T hget(String redisKey, String hashKey, Class<T> clazz){  
	    try {  
	        String objectJson = (String) redisTemplate.opsForHash().get(redisKey, hashKey);  
	        if(StringUtils.isBlank(objectJson)){  
	            return null;  
	        }  
	        return JSON.parseObject(objectJson, clazz);  
	    } catch (Throwable e) {  
	        e.printStackTrace();
	  }  
        return null;  
   }
    
    /**
     * 删除缓存数据
     * @param redisKey
     * @param hashKeys
     */
	public void hdelete(String redisKey, String... hashKeys){  
		try {  
			redisTemplate.opsForHash().delete(redisKey, hashKeys);  
         } catch (Throwable e) {  
            e.printStackTrace();  
      }  
    } 
	
	/**
	 * 根据大key删除全部内容
	 * @param redisKey
	 */
	public void hdelete(String redisKey) {
		try {
			Set<String> resultMapSet = redisTemplate.opsForHash().keys(redisKey);
			if (0 == resultMapSet.size())
				return;
			redisTemplate.opsForHash().delete(redisKey,resultMapSet.toArray());
		} catch (Exception e) {
			e.printStackTrace();  
		}
	}
	
	/**
	 * 缓存数字数据
	 * @param redisKey
	 * @param hashKey
	 * @param score
	 */
	public void zsetNoExpire(String redisKey, String hashKey, double score) {
    	try {
			redisTemplate.opsForZSet().add(redisKey, hashKey, score);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * 删除数字缓存数据
	 * @param redisKey
	 * @param min
	 * @param max
	 */
	public void zremoveRangeByScore(String redisKey, double min, double max) {
    	try {
			redisTemplate.opsForZSet().removeRangeByScore(redisKey, min, max);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * 删除缓存
	 * @param redisKey
	 * @param hashKeys
	 */
	public void zrmove(String redisKey, String... hashKeys) {
		try {
			redisTemplate.opsForZSet().remove(redisKey, hashKeys);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断缓存中是否有对应的value
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
	    return redisTemplate.hasKey(key);
	}
	
	/**
	 * 读取缓存
	 * @param key
	 * @return
	 */
	public String get(final String key) {
	    Object result = null;
	    redisTemplate.setValueSerializer(new StringRedisSerializer());
	    ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
	    result = operations.get(key);
	    if(result==null){
	       return null;
	    }
	    return result.toString();
	}
	
	/**
	 * 写入缓存
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value) {
	    boolean result = false;
	    try {
	    	ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
	    	operations.set(key, value);
	    	result = true;
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return result;
	}
}