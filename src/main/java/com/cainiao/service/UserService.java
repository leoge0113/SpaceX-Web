package com.cainiao.service;

import com.cainiao.entity.User;

import java.util.List;

public interface UserService {

	List<User> getUserList(int offset, int limit);
	 
}
