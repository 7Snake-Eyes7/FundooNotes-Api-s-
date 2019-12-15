package com.bridgelabz.fundoo_notes.reddisrepository;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo_notes.Entity.NoteInformation;
import com.bridgelabz.fundoo_notes.Entity.UserInformation;

@Repository
public class RedisRepository {
    private static final String KEY = "notes";
	
	private RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, Long, Object> hashOperations;

    public RedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;

        hashOperations = redisTemplate.opsForHash();
    }
	 
	 public void save(NoteInformation note) {
		 System.out.println("note"+ "  "+note);
	        hashOperations.put("note", note.getId(), note);
	    }

	 public void save(UserInformation user) {
		 System.out.println("user"+ "  "+user);
	        hashOperations.put("note", user.getUserId(), user);
	    }
}
