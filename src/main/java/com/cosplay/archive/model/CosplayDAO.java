package com.cosplay.archive.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cosplay.archive.util.DBManager;

public class CosplayDAO {
	private CosplayDAO() {}
	public static CosplayDAO instance = new CosplayDAO();
	public static CosplayDAO getinstance() {
		return instance;
	}

	public IndexStatsDTO getIndexStats() {
		IndexStatsDTO dto = new IndexStatsDTO();
		String sql = "SELECT COUNT(*) AS totalPosts, "
				+ "NVL(MAX(pic_year) - MIN(pic_year) + 1, 0) AS totalYears, "
				+ "COUNT(DISTINCT pic_name) AS totalCosplays "
				+ "FROM picture";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto.setTotalPosts(rs.getInt("totalPosts"));
				dto.setTotalYears(rs.getInt("totalYears"));
				dto.setTotalCosplays(rs.getInt("totalCosplays"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return dto;
	}

}
