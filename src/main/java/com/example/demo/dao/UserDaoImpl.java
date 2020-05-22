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
		// passwordをハッシュ関数にかけてUserテーブルに登録
		String password = user.getPassword();
		jdbcTemplate.update("INSERT INTO user(userName, password) VALUES(?, ?)",
				user.getUserName(), hashedPass(password));
	}

	@Override
	public List<User> getAll() {
		// Userテーブルのデータをすべて取り出し
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
		// ユーザー名がUserテーブル（データベース）に存在するか確認
		// 存在すればTrue，存在しなければFalseを返す
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
		// ログイン処理
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
		//　パスワードをハッシュ関数にかける
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
