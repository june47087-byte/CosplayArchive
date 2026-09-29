package com.cosplay.archive.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cosplay.archive.util.DBManager;

public class WishDAO {
	private WishDAO() {}
	public static WishDAO instance = new WishDAO();
	public static WishDAO getinstance() {
		return instance;
	}

	// 전체 목록
	public List<WishDTO> getWishList() {
		List<WishDTO> list = new ArrayList<WishDTO>();
		String sql = "SELECT * FROM wish ORDER BY wish_id DESC";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(mapRow(rs));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}

	// 완료/미완료 필터 목록
	public List<WishDTO> getWishListByStatus(String wish_switch) {
		List<WishDTO> list = new ArrayList<WishDTO>();
		String sql = "SELECT * FROM wish WHERE wish_switch=? ORDER BY wish_id DESC";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, wish_switch);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(mapRow(rs));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}

	// id 단건 조회용 (rightbar.jsp의 wish 채울 때 사용)
	public WishDTO getWish(int wish_id) {
		WishDTO dto = new WishDTO();
		String sql = "SELECT * FROM wish WHERE wish_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, wish_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = mapRow(rs);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		return dto;
	}

	// 등록용
	public int wishWrite(WishDTO dto) {
		int result = 0;
		String sql = "insert into wish(wish_id, wish_name, wish_file, wish_comment, wish_switch) "
				+ "values(seq_wish.nextval, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getWish_name());
			pstmt.setString(2, dto.getWish_file());
			pstmt.setString(3, dto.getWish_comment());
			pstmt.setString(4, dto.getWish_switch());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}

	// 수정용 (새 파일 업로드된 경우 — wish_file도 갱신)
	public int wishModify(WishDTO dto) {
		int result = 0;
		String sql = "update wish set wish_name=?, wish_file=?, wish_comment=?, wish_switch=? where wish_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getWish_name());
			pstmt.setString(2, dto.getWish_file());
			pstmt.setString(3, dto.getWish_comment());
			pstmt.setString(4, dto.getWish_switch());
			pstmt.setInt(5, dto.getWish_id());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}

	// 수정용 (파일 안 바꾼 경우 — wish_file은 그대로 둠)
	public int wishModifyKeepFile(WishDTO dto) {
		int result = 0;
		String sql = "update wish set wish_name=?, wish_comment=?, wish_switch=? where wish_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getWish_name());
			pstmt.setString(2, dto.getWish_comment());
			pstmt.setString(3, dto.getWish_switch());
			pstmt.setInt(4, dto.getWish_id());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}

	// 삭제용
	public int wishDelete(int wish_id) {
		int result = 0;
		String sql = "delete from wish where wish_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, wish_id);
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}

	private WishDTO mapRow(ResultSet rs) throws Exception {
		WishDTO dto = new WishDTO();
		dto.setWish_id(rs.getInt("wish_id"));
		dto.setWish_name(rs.getString("wish_name"));
		dto.setWish_file(rs.getString("wish_file"));
		dto.setWish_comment(rs.getString("wish_comment"));
		dto.setWish_switch(rs.getString("wish_switch"));
		return dto;
	}
}
