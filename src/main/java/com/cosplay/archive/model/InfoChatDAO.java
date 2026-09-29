package com.cosplay.archive.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cosplay.archive.util.DBManager;

public class InfoChatDAO {
	private InfoChatDAO() {}
	public static InfoChatDAO instance = new InfoChatDAO();
	public static InfoChatDAO getinstance() {
		return instance;
	}
	//　id索引リスト用
	public List<InfoChatDTO> getTweet(int info_id) {
		List<InfoChatDTO> list = new ArrayList<InfoChatDTO>();
		String sql = "SELECT * FROM INFO_CHAT WHERE INFO_ID=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, info_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				InfoChatDTO dto = new InfoChatDTO();
				dto.setInfo_id(Integer.parseInt(rs.getString("info_id")));
				dto.setInfo_chat_id(Integer.parseInt(rs.getString("info_chat_id")));
				dto.setInfo_chat_comment(rs.getString("info_chat_comment"));
				dto.setInfo_chat_url(rs.getString("info_chat_url"));
				list.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	//　	登録用
	public int infoChatWrite(InfoChatDTO dto) {
		int result = 0;
		String sql = "insert into info_chat(info_chat_id, info_id, info_chat_comment, info_chat_url) values(seq_info_chat.nextval, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getInfo_id());
			pstmt.setString(2, dto.getInfo_chat_comment());
			pstmt.setString(3, dto.getInfo_chat_url());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}
	//削除用
	public int infoDelete(int info_id) {
		int result = 0;
		String sql = "delete from info_chat where info_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, info_id);
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}
	//댓글 1개 수정용
	public int infoChatModify(InfoChatDTO dto) {
		int result = 0;
		String sql = "update info_chat set info_chat_comment=?, info_chat_url=? where info_chat_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getInfo_chat_comment());
			pstmt.setString(2, dto.getInfo_chat_url());
			pstmt.setInt(3, dto.getInfo_chat_id());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}
	//댓글 1개 삭제용
	public int infoChatDelete(int info_chat_id) {
		int result = 0;
		String sql = "delete from info_chat where info_chat_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, info_chat_id);
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}
}
