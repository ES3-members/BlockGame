package com.example.demo.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;
@Repository
public class UserDaoImpl implements UserDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public UserDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void insertUser(User user) {
		// password繧偵ワ繝�繧ｷ繝･髢｢謨ｰ縺ｫ縺九￠縺ｦUser繝�繝ｼ繝悶Ν縺ｫ逋ｻ骭ｲ
		String password = user.getPassword();
		jdbcTemplate.update("INSERT INTO user(userName, password) VALUES(?, ?)",
				user.getUserName(), hashedPass(password));
	}
	
	@Override
	public void deleteUser(User user) {
		jdbcTemplate.update("DELETE FROM user WHERE userName=?",
				user.getUserName());
	}

	@Override
	public List<User> getAll() {
		// User繝�繝ｼ繝悶Ν縺ｮ繝�繝ｼ繧ｿ繧偵☆縺ｹ縺ｦ蜿悶ｊ蜃ｺ縺�
		String sql = "SELECT userName, password FROM user";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		List<User> list = new ArrayList<User>();
		for(Map<String, Object> result : resultList) {
			User user = new User();
			user.setUserName((String)result.get("userName"));
			user.setPassword((String)result.get("password"));
			list.add(user);
		}
		return list;
	}
	
	@Override
	public boolean userExist(String userName) {
		// 繝ｦ繝ｼ繧ｶ繝ｼ蜷阪′User繝�繝ｼ繝悶Ν�ｼ医ョ繝ｼ繧ｿ繝吶�ｼ繧ｹ�ｼ峨↓蟄伜惠縺吶ｋ縺狗｢ｺ隱�
		// 蟄伜惠縺吶ｌ縺ｰTrue�ｼ悟ｭ伜惠縺励↑縺代ｌ縺ｰFalse繧定ｿ斐☆
		String sql = "SELECT userName FROM user";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		List<String> list = new ArrayList<String>();
		for(Map<String, Object> result : resultList) {
			String user = (String)result.get("userName");
			list.add(user);
		}
		return list.contains(userName);
	}

	@Override
	public boolean certificate(String userName, String password) {
		// 繝ｭ繧ｰ繧､繝ｳ蜃ｦ逅�
		String sql = "SELECT password FROM user WHERE userName = '" + userName + "'";
		String pass;
		try {
			pass = jdbcTemplate.queryForObject(sql, String.class);
		}
		catch(Exception e){
			pass = null;
		}
		if(hashedPass(password).equals(pass))
			return true;
		return false;
	}

	@Override
	public String hashedPass(String password) {
		//縲�繝代せ繝ｯ繝ｼ繝峨ｒ繝上ャ繧ｷ繝･髢｢謨ｰ縺ｫ縺九￠繧�
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(password.getBytes());
			byte[] byteHash = md.digest();
			String sha1 = String.format("%040x", new BigInteger(1, byteHash));
				return sha1;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	

}
