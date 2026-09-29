package com.cosplay.archive.service.Plan;

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

import com.cosplay.archive.model.PlanDAO;
import com.cosplay.archive.model.PlanDTO;
import com.cosplay.archive.service.Action;

// WishWriteProService와 같은 구조입니다.
public class PlanWriteProService implements Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		ServletContext context = request.getServletContext();
		String path = context.getRealPath("Plan/upload/");
		File uploadDir = new File(path);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		String encType = "UTF-8";
		int sizeLimit = 2 * 1024 * 1024;

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

		PlanDTO dto = new PlanDTO();
		String filename = null;

		for (FileItem item : items) {
			String fieldName = item.getFieldName();
			if (item.isFormField()) {
				String value = getFieldValue(item, encType);
				if ("plan_name".equals(fieldName)) {
					dto.setPlan_name(value);
				} else if ("plan_comment".equals(fieldName)) {
					dto.setPlan_comment(value);
				} else if ("plan_switch".equals(fieldName)) {
					dto.setPlan_switch(value);
				} else if ("plan_day".equals(fieldName)) {
					dto.setPlan_day(value);
				}
			} else if ("plan_file".equals(fieldName)) {
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
		dto.setPlan_file(filename);

		PlanDAO dao = PlanDAO.getinstance();
		dao.planWrite(dto);

		response.sendRedirect(request.getContextPath() + "/Plan?cmd=planList");
	}

	private String getFieldValue(FileItem item, String encoding) throws IOException {
		try {
			return item.getString(encoding);
		} catch (java.io.UnsupportedEncodingException e) {
			throw new IOException(e);
		}
	}
}
