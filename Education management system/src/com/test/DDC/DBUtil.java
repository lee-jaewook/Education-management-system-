package com.test.DDC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 오라클 DB 연결해주는 클래스
 * @author DB1조
 *
 */
public class DBUtil {

	private Connection conn = null;
	

	public Connection open() {
		
//		String url = "jdbc:oracle:thin:@211.63.89.36:1521:xe";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "hr";
		String pw = "java1234";

		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(url, id, pw);
			
			return conn; //접속 성공
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null; //접속 실패
		
	}// open()

	public Connection open(String host, String id, String pw) {

		String url = "jdbc:oracle:thin:@" + host + ":1521:xe";

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(url, id, pw);
			
			return conn; // 접속 성공

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null; // 접속 실패

	}// open()
	

	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//close()
	
}

