package test;

import website.common.Encryption;

public class TestMD5 {

	public static void main(String[] args) throws Exception {
		String msg = "這是 MD5 加密的字串";
		String encStr = Encryption.encrypt("MD5", msg);
		System.out.println(encStr);
		
	}

}
