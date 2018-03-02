package com.training.core.common.util;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileUtil {
	
	private static Logger logger = Logger.getLogger(FileUtil.class);

	public static List<MultipartFile> getMultipartFiles(MultipartHttpServletRequest multipartRequest) {
		List<MultipartFile> multipartFiles = new ArrayList<MultipartFile>();
		if(multipartRequest != null){
            for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext(); ) {
                String key = it.next();
                MultipartFile file = multipartRequest.getFile(key);
                String originalFilename = file.getOriginalFilename();
                if (originalFilename.length() > 0) {
                	multipartFiles.add(file);
                }
            }
        }
		return multipartFiles;
	}

	public static StringBuilder saveFile(MultipartFile multipartFile, HttpServletRequest request) throws IOException {
		String originalFilename = multipartFile.getOriginalFilename();
		StringBuilder photoPath = new StringBuilder();

        String mimeType = multipartFile.getContentType();
        long size = multipartFile.getSize();

        if (!(mimeType.equals("image/gif") || mimeType.equals("image/jpeg") ||
                mimeType.equals("image/png") || mimeType.equals("image/jpg"))) {
            return photoPath.append("mime");
        }

        if (size >= 2 * 1024 * 1024) {
            return photoPath.append("size");
        }

		photoPath.append(CustomizedPropertyConfigurer.getPhotoPath().toString());
		photoPath.append(StrUtil.getUUID());
		photoPath.append(originalFilename.substring(originalFilename.indexOf(".")));

		File file = new File(request.getSession().getServletContext().getRealPath("") + photoPath.toString());
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if(!file.exists()) {
			file.createNewFile();
		}
		FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
		return photoPath;
	}

}
