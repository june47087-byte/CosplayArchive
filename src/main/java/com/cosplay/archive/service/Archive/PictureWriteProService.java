package com.cosplay.archive.service.Archive;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.cosplay.archive.model.PictureDAO;
import com.cosplay.archive.model.PictureDTO;
import com.cosplay.archive.service.Action;

// PdsWrtieProService의 파일 업로드 구조(commons-fileupload)를 가져오되,
// FileItem을 순서(iterator.next())가 아니라 이름(getFieldName())으로 찾도록 바꿨습니다 —
// 폼에 필드가 추가/순서 변경돼도 안 깨지게 하기 위해서입니다.
public class PictureWriteProService implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// 서버상의 실제 경로 찾기
		ServletContext context = request.getServletContext();
		String path = context.getRealPath("Archive/upload/");
		File uploadDir = new File(path);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		String encType = "UTF-8";
		int sizeLimit = 2 * 1024 * 1024; // 최대 2MB

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(sizeLimit);
		factory.setRepository(new File(path));
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding(encType);
		upload.setSizeMax(sizeLimit);

		List<FileItem> items;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			throw new IOException(e);
		}

		PictureDTO dto = new PictureDTO();
		String filename = null;

		for (FileItem item : items) {
			String fieldName = item.getFieldName();
			if (item.isFormField()) {
				String value = getFieldValue(item, encType);
				if ("pic_name".equals(fieldName)) {
					dto.setPic_name(value);
				} else if ("photographer".equals(fieldName)) {
					dto.setPhotographer(value);
				} else if ("pic_event".equals(fieldName)) {
					dto.setPic_event(value);
				} else if ("pic_year".equals(fieldName)) {
					dto.setPic_year(value == null || value.isEmpty() ? 0 : Integer.parseInt(value));
				}
			} else if ("pic_file".equals(fieldName)) {
				filename = item.getName(); // 경로 제외한 원본 파일명
				if (filename != null && !filename.isEmpty()) {
					try {
						item.write(new File(path, filename));
					} catch (Exception e) {
						throw new IOException(e);
					}
				}
			}
		}
		dto.setPic_file(filename);

		PictureDAO dao = PictureDAO.getinstance();
		int row = dao.pictureWrite(dto);
		if (row == 1) {
			response.sendRedirect(request.getContextPath() + "/Archive?cmd=archiveList");
		} else {
			response.sendRedirect(request.getContextPath() + "/Archive?cmd=archiveList");
		}
	}

	private String getFieldValue(FileItem item, String encoding) throws IOException {
		try {
			return item.getString(encoding);
		} catch (java.io.UnsupportedEncodingException e) {
			throw new IOException(e);
		}
	}
}
