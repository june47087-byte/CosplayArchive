package com.cosplay.archive.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cosplay.archive.util.DBManager;

public class PictureDAO {
	private PictureDAO() {}
	public static PictureDAO instance = new PictureDAO();
	public static PictureDAO getinstance() {
		return instance;
	}

	// 연도별 그룹(연도, 그 해 전체 건수) — archive.jsp 목록의 뼈대. pictures는 여기서
	// 안 채우고, 연도마다 getPictureListByYear로 따로 페이지네이션해서 채웁니다.
	public List<ArchiveYearGroupDTO> getYearGroups() {
		List<ArchiveYearGroupDTO> list = new ArrayList<ArchiveYearGroupDTO>();
		String sql = "SELECT pic_year, COUNT(*) AS cnt FROM picture GROUP BY pic_year ORDER BY pic_year DESC";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ArchiveYearGroupDTO dto = new ArchiveYearGroupDTO();
				dto.setYear(rs.getInt("pic_year"));
				dto.setCount(rs.getInt("cnt"));
				list.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}

	// 연도 하나의 게시물 목록, 페이지 단위로 (page는 1부터, pageSize는 한 페이지당 개수)
	public List<PictureDTO> getPictureListByYear(int year, int page, int pageSize) {
		List<PictureDTO> list = new ArrayList<PictureDTO>();
		int offset = (page - 1) * pageSize;
		String sql = "SELECT * FROM picture WHERE pic_year=? ORDER BY pic_id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PictureDTO dto = new PictureDTO();
				dto.setPic_id(rs.getInt("pic_id"));
				dto.setPic_name(rs.getString("pic_name"));
				dto.setPic_file(rs.getString("pic_file"));
				dto.setPhotographer(rs.getString("photographer"));
				dto.setPic_event(rs.getString("pic_event"));
				dto.setPic_year(rs.getInt("pic_year"));
				list.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}

	// id 단건 조회용 (rightbar.jsp의 picture 채울 때 사용)
	public PictureDTO getPicture(int pic_id) {
		PictureDTO dto = new PictureDTO();
		String sql = "SELECT * FROM PICTURE WHERE PIC_ID=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pic_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto.setPic_id(rs.getInt("pic_id"));
				dto.setPic_name(rs.getString("pic_name"));
				dto.setPic_file(rs.getString("pic_file"));
				dto.setPhotographer(rs.getString("photographer"));
				dto.setPic_event(rs.getString("pic_event"));
				dto.setPic_year(rs.getInt("pic_year"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		return dto;
	}

	// 등록용
	public int pictureWrite(PictureDTO dto) {
		int result = 0;
		String sql = "insert into picture(pic_id, pic_name, pic_file, photographer, pic_event, pic_year) "
				+ "values(seq_picture.nextval, ?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getPic_name());
			pstmt.setString(2, dto.getPic_file());
			pstmt.setString(3, dto.getPhotographer());
			pstmt.setString(4, dto.getPic_event());
			pstmt.setInt(5, dto.getPic_year());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}

	// 수정용 (새 파일 업로드된 경우 — pic_file도 갱신)
	public int pictureModify(PictureDTO dto) {
		int result = 0;
		String sql = "update picture set pic_name=?, pic_file=?, photographer=?, pic_event=?, pic_year=? where pic_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getPic_name());
			pstmt.setString(2, dto.getPic_file());
			pstmt.setString(3, dto.getPhotographer());
			pstmt.setString(4, dto.getPic_event());
			pstmt.setInt(5, dto.getPic_year());
			pstmt.setInt(6, dto.getPic_id());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}

	// 수정용 (파일 안 바꾼 경우 — pic_file은 그대로 둠)
	public int pictureModifyKeepFile(PictureDTO dto) {
		int result = 0;
		String sql = "update picture set pic_name=?, photographer=?, pic_event=?, pic_year=? where pic_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getPic_name());
			pstmt.setString(2, dto.getPhotographer());
			pstmt.setString(3, dto.getPic_event());
			pstmt.setInt(4, dto.getPic_year());
			pstmt.setInt(5, dto.getPic_id());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}

	// 삭제용
	public int pictureDelete(int pic_id) {
		int result = 0;
		String sql = "delete from picture where pic_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pic_id);
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return result;
	}
}

