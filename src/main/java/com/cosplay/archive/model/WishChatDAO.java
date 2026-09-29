package com.cosplay.archive.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cosplay.archive.util.DBManager;

public class WishChatDAO {
	private WishChatDAO() {}
	public static WishChatDAO instance = new WishChatDAO();
	public static WishChatDAO getinstance() {
		return instance;
	}

	// wish 하나의 샵 목록 (rightbar.jsp용)
	public List<WishChatDTO> getWishChatList(int wish_id) {
		List<WishChatDTO> list = new ArrayList<WishChatDTO>();
		String sql = "SELECT * FROM wish_chat WHERE wish_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, wish_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				WishChatDTO dto = new WishChatDTO();
				dto.setWish_chat_id(rs.getInt("wish_chat_id"));
				dto.setWish_id(rs.getInt("wish_id"));
				dto.setWish_chat_url(rs.getString("wish_chat_url"));
				dto.setWish_chat_contents(rs.getString("wish_chat_contents"));
				list.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}

	// 등록용
	public int wishChatWrite(WishChatDTO dto) {
		int result = 0;
		String sql = "insert into wish_chat(wish_chat_id, wish_id, wish_chat_url, wish_chat_contents) values(seq_wish_chat.nextval, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getWish_id());
			pstmt.setString(2, dto.getWish_chat_url());
			pstmt.setString(3, dto.getWish_chat_contents());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}

	// 샵 1개 수정용
	public int wishChatModify(WishChatDTO dto) {
		int result = 0;
		String sql = "update wish_chat set wish_chat_contents=?, wish_chat_url=? where wish_chat_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getWish_chat_contents());
			pstmt.setString(2, dto.getWish_chat_url());
			pstmt.setInt(3, dto.getWish_chat_id());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}

	// 샵 1개 삭제용
	public int wishChatDelete(int wish_chat_id) {
		int result = 0;
		String sql = "delete from wish_chat where wish_chat_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, wish_chat_id);
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}

	// wish 삭제할 때 딸린 샵들도 같이 삭제(info_chat과 동일 패턴)
	public int wishChatDeleteByWishId(int wish_id) {
		int result = 0;
		String sql = "delete from wish_chat where wish_id=?";
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
}
