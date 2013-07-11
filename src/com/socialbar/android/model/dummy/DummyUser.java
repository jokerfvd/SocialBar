package com.socialbar.android.model.dummy;

import java.io.Serializable;
import com.socialbar.android.model.User;
/**
 * Class <code>Usuario postico - serializavel</code>.
 */
public class DummyUser implements User,Serializable{
	private static final long serialVersionUID = 1L;
	private String username;
	private String token;
	private String id;
	
	
	public DummyUser() {
	}

	public DummyUser(String username, String pass) {
		this.username = username;		
		this.token =  username+pass;
		}
		
	@Override
	public String getName() {
		return this.username;
	}

	@Override
	public String getToken() {
		
		return this.token;
	}

	@Override
	public boolean IsAuthenticated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getLatitude() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getLongitude() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setName(String nome) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPassword(String senha) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getId() {
		
		return this.getId();
	}
	
	public void setId(String id){
		this.id = id;
	}
	
}
