package com.bugbycode.dao.token;

public interface UserTokenDao {
	
	public String queryRefreshTokenByUserName(String userName);
	
	public void deleteTokenByUserName(String userName);
	
	public void deleteRefreshTokenByTokenId(String tokenId);
}
