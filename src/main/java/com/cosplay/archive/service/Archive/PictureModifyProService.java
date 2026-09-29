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

// 새 파일이 첨부됐을 때만 pic_file을 바꾸고, 안 바꿨으면 기존 파일을 유지합니다
// (수정 폼의 파일 input은 브라우저 보안상 기존 파일명으로 미리 채울 수 없어서 항상 비어있습니다).
public class PictureModifyProService implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

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
		String newFilename = null;

		for (FileItem item : items) {
			String fieldName = item.getFieldName();
			if (item.isFormField()) {
				String value = getFieldValue(item, encType);
				if ("id".equals(fieldName)) {
					dto.setPic_id(value == null || value.isEmpty() ? 0 : Integer.parseInt(value));
				} else if ("pic_name".equals(fieldName)) {
					dto.setPic_name(value);
				} else if ("photographer".equals(fieldName)) {
					dto.setPhotographer(value);
				} else if ("pic_event".equals(fieldName)) {
					dto.setPic_event(value);
				} else if ("pic_year".equals(fieldName)) {
					dto.setPic_year(value == null || value.isEmpty() ? 0 : Integer.parseInt(value));
				}
			} else if ("pic_file".equals(fieldName)) {
				String uploaded = item.getName();
				if (uploaded != null && !uploaded.isEmpty()) {
					newFilename = uploaded;
					try {
						item.write(new File(path, newFilename));
					} catch (Exception e) {
						throw new IOException(e);
					}
				}
			}
		}

		PictureDAO dao = PictureDAO.getinstance();
		if (newFilename != null) {
			dto.setPic_file(newFilename);
			dao.pictureModify(dto);
		} else {
			dao.pictureModifyKeepFile(dto);
		}

		response.sendRedirect(request.getContextPath() + "/Archive?cmd=archiveList");
	}

	private String getFieldValue(FileItem item, String encoding) throws IOException {
		try {
			return item.getString(encoding);
		} catch (java.io.UnsupportedEncodingException e) {
			throw new IOException(e);
		}
	}
}
