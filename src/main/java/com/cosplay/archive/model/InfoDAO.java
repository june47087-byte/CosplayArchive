package com.cosplay.archive.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cosplay.archive.util.DBManager;

public class InfoDAO {
	private InfoDAO() {}
	public static InfoDAO instance = new InfoDAO();
	public static InfoDAO getinstance() {
		return instance;
	}
	//　全体リスト用
	public List<InfoDTO> getTweet() {
		List<InfoDTO> list = new ArrayList<InfoDTO>(); 
		String sql = "SELECT * FROM INFO";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				InfoDTO dto = new InfoDTO();
				dto.setInfo_id(Integer.parseInt(rs.getString("info_id")));
				dto.setInfo_div(rs.getString("info_div"));
				dto.setInfo_name(rs.getString("info_name"));
				dto.setInfo_contents(rs.getString("info_contents"));
				dto.setInfo_url(rs.getString("info_url"));
				list.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	//　idポスト検索よう
	public InfoDTO getTweet(int info_id) {
		InfoDTO dto = new InfoDTO(); 
		String sql = "SELECT * FROM INFO WHERE INFO_ID=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, info_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto.setInfo_id(Integer.parseInt(rs.getString("info_id")));
				dto.setInfo_div(rs.getString("info_div"));
				dto.setInfo_name(rs.getString("info_name"));
				dto.setInfo_contents(rs.getString("info_contents"));
				dto.setInfo_url(rs.getString("info_url"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		return dto;
	}
	//　div索引リスト用
	public List<InfoDTO> getSelectedCategory(String info_div) {
		List<InfoDTO> list = new ArrayList<InfoDTO>();
		String sql = "SELECT * from info where info_div = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, info_div);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				InfoDTO dto = new InfoDTO();
				dto.setInfo_id(Integer.parseInt(rs.getString("info_id")));
				dto.setInfo_div(rs.getString("info_div"));
				dto.setInfo_name(rs.getString("info_name"));
				dto.setInfo_contents(rs.getString("info_contents"));
				dto.setInfo_url(rs.getString("info_url"));
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
	public int infoWrite(InfoDTO dto) {
		int result = 0;
		String sql = "insert into info(info_id, info_div, info_name, info_contents, info_url) values(seq_info.nextval, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getInfo_div());
			pstmt.setString(2, dto.getInfo_name());
			pstmt.setString(3, dto.getInfo_contents());
			pstmt.setString(4, dto.getInfo_url());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}
	//　	info_id指定登録用
	public int infoWrite(InfoDTO dto, int info_id) {
		int result = 0;
		String sql = "insert into info(info_id, info_div, info_name, info_contents, info_url) values(?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, info_id);
			pstmt.setString(2, dto.getInfo_div());
			pstmt.setString(3, dto.getInfo_name());
			pstmt.setString(4, dto.getInfo_contents());
			pstmt.setString(5, dto.getInfo_url());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}
	//　	修正用
	public int infoModify(InfoDTO info) {
		int result = 0;
		String sql = "update info set info_div=?, info_name=?, info_contents=?, info_url=? where info_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, info.getInfo_div());
			pstmt.setString(2, info.getInfo_name());
			pstmt.setString(3, info.getInfo_contents());
			pstmt.setString(4, info.getInfo_url());
			pstmt.setInt(5, info.getInfo_id());
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
		String sql = "delete from info where info_id=?";
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
}
