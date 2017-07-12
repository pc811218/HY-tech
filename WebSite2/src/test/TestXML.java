package test;

import website.common.ParseXml;

public class TestXML {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String sql = ParseXml.getSqlByName("Account.checkAccount");
			System.out.println(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println();
	}

}
