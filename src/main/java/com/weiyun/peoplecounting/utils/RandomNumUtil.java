package com.weiyun.peoplecounting.utils;

import java.util.Random;


/**
 * 生成6位随机验证码字符串
 * @author Tobu
 *
 */

public class RandomNumUtil {
	
	public static String getRandomNum() {
		
		Random random = new Random();
		String smsCode = String.valueOf(random.nextInt(899999) + 100000);
		return smsCode;
		
	}
	
}
