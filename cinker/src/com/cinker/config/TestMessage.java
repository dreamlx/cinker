package com.cinker.config;

import com.cinker.util.OrderNumUtil;

public class TestMessage {

	public static void main(String[] args) {
		String tel = "";
		AppConfig config = ConfigLoader.load(ConfigLoader.ConfigType.Message);
		MESSAGEXsend submail = new MESSAGEXsend(config);
		submail.addTo(tel);
		submail.setProject("iQFXd4");
		submail.addVar("code", OrderNumUtil.getRandomNumber());
		submail.xsend();
	}	
	
}
