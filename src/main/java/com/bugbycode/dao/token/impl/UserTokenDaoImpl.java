package com.bugbycode.dao.token.impl;

import org.springframework.stereotype.Service;

import com.bugbycode.dao.base.BaseDao;
import com.bugbycode.dao.token.UserTokenDao;

@Service("userTokenDao")
public class UserTokenDaoImpl extends BaseDao implements UserTokenDao {

	@Override
	public String queryRefreshTokenByUserName(String userName) {
		return getSqlSession().selectOne("userToken.queryRefreshTokenByUserName", userName);
	}

	@Override
	public void deleteTokenByUserName(String userName) {
		getSqlSession().delete("userToken.deleteTokenByUserName", userName);
	}

	@Override
	public void deleteRefreshTokenByTokenId(String tokenId) {
		getSqlSession().delete("userToken.deleteRefreshTokenByTokenId", tokenId);
	}

}
