package com.cosplay.archive.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cosplay.archive.util.DBManager;

public class EventDAO {
	private EventDAO() {}
	public static EventDAO instance = new EventDAO();
	public static EventDAO getinstance() {
		return instance;
	}

	// 지역 하나의 행사 목록 (rightbar.jsp의 eventList 채울 때 사용)
	public List<EventDTO> getEventsByRegion(String event_region) {
		List<EventDTO> list = new ArrayList<EventDTO>();
		String sql = "SELECT * FROM event WHERE event_region=? ORDER BY event_day DESC";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, event_region);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				EventDTO dto = new EventDTO();
				dto.setEvent_id(rs.getInt("event_id"));
				dto.setEvent_name(rs.getString("event_name"));
				dto.setEvent_region(rs.getString("event_region"));
				dto.setEvent_day(rs.getString("event_day"));
				dto.setEvent_place(rs.getString("event_place"));
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
	public int eventWrite(EventDTO dto) {
		int result = 0;
		String sql = "insert into event(event_id, event_name, event_region, event_day, event_place) "
				+ "values(seq_event.nextval, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getEvent_name());
			pstmt.setString(2, dto.getEvent_region());
			pstmt.setString(3, dto.getEvent_day());
			pstmt.setString(4, dto.getEvent_place());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}

	// 수정용 (event_region은 안 바꿈 — 카드 소속을 옮기는 기능은 없음)
	public int eventModify(EventDTO dto) {
		int result = 0;
		String sql = "update event set event_name=?, event_day=?, event_place=? where event_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getEvent_name());
			pstmt.setString(2, dto.getEvent_day());
			pstmt.setString(3, dto.getEvent_place());
			pstmt.setInt(4, dto.getEvent_id());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}

	// 삭제용
	public int eventDelete(int event_id) {
		int result = 0;
		String sql = "delete from event where event_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, event_id);
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}
}
