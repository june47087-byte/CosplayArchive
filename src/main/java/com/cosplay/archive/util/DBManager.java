package com.cosplay.archive.util;

import java.sql.*;

public class DBManager {
	
	public static Connection getConnection() {
		
			Connection conn = null;
			String myDriver="oracle.jdbc.OracleDriver";
			String myURL="jdbc:oracle:thin:@localhost:1521:xe";
			String myID="C##COSPLAY";
			String myPass="1234";
		
		
		try {//1 드라이버 로딩
			
			Class.forName(myDriver);
			conn = DriverManager.getConnection(myURL, myID, myPass);
		}catch(Exception e) {
			e.printStackTrace();
		}
			try {
				
			}catch(Exception e) {
			e.printStackTrace();
			}
			
		
	return conn;	
	}
	public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
	try {
		if(rs != null) rs.close();
		if(pstmt != null) pstmt.close();
		if(conn != null) conn.close();

	}catch(Exception e) {
		e.printStackTrace();

	}
	}

	public static void close(Connection conn, PreparedStatement pstmt) {
		close(conn, pstmt, null);
	}

}
