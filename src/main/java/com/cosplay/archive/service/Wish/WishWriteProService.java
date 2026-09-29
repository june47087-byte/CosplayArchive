package com.cosplay.archive.service.Wish;

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

import com.cosplay.archive.model.WishDAO;
import com.cosplay.archive.model.WishDTO;
import com.cosplay.archive.service.Action;

// PictureWriteProService와 같은 구조입니다.
public class WishWriteProService implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		ServletContext context = request.getServletContext();
		String path = context.getRealPath("Wish/upload/");
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

		WishDTO dto = new WishDTO();
		String filename = null;

		for (FileItem item : items) {
			String fieldName = item.getFieldName();
			if (item.isFormField()) {
				String value = getFieldValue(item, encType);
				if ("wish_name".equals(fieldName)) {
					dto.setWish_name(value);
				} else if ("wish_comment".equals(fieldName)) {
					dto.setWish_comment(value);
				} else if ("wish_switch".equals(fieldName)) {
					dto.setWish_switch(value);
				}
			} else if ("wish_file".equals(fieldName)) {
				filename = item.getName();
				if (filename != null && !filename.isEmpty()) {
					try {
						item.write(new File(path, filename));
					} catch (Exception e) {
						throw new IOException(e);
					}
				}
			}
		}
		dto.setWish_file(filename);

		WishDAO dao = WishDAO.getinstance();
		dao.wishWrite(dto);

		response.sendRedirect(request.getContextPath() + "/Wish?cmd=wishList");
	}

	private String getFieldValue(FileItem item, String encoding) throws IOException {
		try {
			return item.getString(encoding);
		} catch (java.io.UnsupportedEncodingException e) {
			throw new IOException(e);
		}
	}
}
