package com.weiyun.peoplecounting.miaodiyun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Test
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// 获取开发者账号信息
//		 AccountInfo.execute();

		// 验证码通知短信接口
		String result = IndustrySMS.sendCode("13189883866", "123456", 3);
		JSONObject jsonObject = JSON.parseObject(result);
		String respCode = jsonObject.get("respCode").toString();
		String respDesc = jsonObject.get("respDesc").toString();
		System.out.println(respCode);
		System.out.println(respDesc);

		// 会员营销短信接口
		// AffMarkSMS.execute();

		// 语音验证码
		// VoiceCode.execute();

	}
}
