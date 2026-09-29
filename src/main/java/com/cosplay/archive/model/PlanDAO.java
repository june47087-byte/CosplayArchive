package com.cosplay.archive.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cosplay.archive.util.DBManager;

public class PlanDAO {
	private PlanDAO() {}
	public static PlanDAO instance = new PlanDAO();
	public static PlanDAO getinstance() {
		return instance;
	}

	// 전체 목록
	public List<PlanDTO> getPlanList() {
		List<PlanDTO> list = new ArrayList<PlanDTO>();
		String sql = "SELECT * FROM plan ORDER BY plan_id DESC";
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
	public List<PlanDTO> getPlanListByStatus(String plan_switch) {
		List<PlanDTO> list = new ArrayList<PlanDTO>();
		String sql = "SELECT * FROM plan WHERE plan_switch=? ORDER BY plan_id DESC";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, plan_switch);
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

	// id 단건 조회용 (rightbar.jsp의 plan 채울 때 사용)
	public PlanDTO getPlan(int plan_id) {
		PlanDTO dto = new PlanDTO();
		String sql = "SELECT * FROM plan WHERE plan_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, plan_id);
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
	public int planWrite(PlanDTO dto) {
		int result = 0;
		String sql = "insert into plan(plan_id, plan_name, plan_file, plan_comment, plan_switch, plan_day) "
				+ "values(seq_plan.nextval, ?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getPlan_name());
			pstmt.setString(2, dto.getPlan_file());
			pstmt.setString(3, dto.getPlan_comment());
			pstmt.setString(4, dto.getPlan_switch());
			pstmt.setString(5, dto.getPlan_day());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}

	// 수정용 (새 파일 업로드된 경우 — plan_file도 갱신)
	public int planModify(PlanDTO dto) {
		int result = 0;
		String sql = "update plan set plan_name=?, plan_file=?, plan_comment=?, plan_switch=?, plan_day=? where plan_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getPlan_name());
			pstmt.setString(2, dto.getPlan_file());
			pstmt.setString(3, dto.getPlan_comment());
			pstmt.setString(4, dto.getPlan_switch());
			pstmt.setString(5, dto.getPlan_day());
			pstmt.setInt(6, dto.getPlan_id());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}

	// 수정용 (파일 안 바꾼 경우 — plan_file은 그대로 둠)
	public int planModifyKeepFile(PlanDTO dto) {
		int result = 0;
		String sql = "update plan set plan_name=?, plan_comment=?, plan_switch=?, plan_day=? where plan_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getPlan_name());
			pstmt.setString(2, dto.getPlan_comment());
			pstmt.setString(3, dto.getPlan_switch());
			pstmt.setString(4, dto.getPlan_day());
			pstmt.setInt(5, dto.getPlan_id());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}

	// 삭제용
	public int planDelete(int plan_id) {
		int result = 0;
		String sql = "delete from plan where plan_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, plan_id);
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}

	private PlanDTO mapRow(ResultSet rs) throws Exception {
		PlanDTO dto = new PlanDTO();
		dto.setPlan_id(rs.getInt("plan_id"));
		dto.setPlan_name(rs.getString("plan_name"));
		dto.setPlan_file(rs.getString("plan_file"));
		dto.setPlan_comment(rs.getString("plan_comment"));
		dto.setPlan_switch(rs.getString("plan_switch"));
		dto.setPlan_day(rs.getString("plan_day"));
		return dto;
	}
}
