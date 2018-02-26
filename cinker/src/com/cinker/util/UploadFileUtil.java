package com.cinker.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.cinker.action.UploadFileAction;
import com.sun.istack.internal.logging.Logger;

public class UploadFileUtil {
	
	private static final Logger log = Logger.getLogger(UploadFileAction.class);
		
	public static List<String> uploadFileList(HttpServletRequest request, MultipartFile multipartFiles[]) throws IllegalStateException, IOException{
        List<String> newFileNames = new ArrayList<String>();
        try {
            for(MultipartFile multipartFile:multipartFiles){
                //文件的原始名称
                String originalFilename = multipartFile.getOriginalFilename();
                log.info("文件名: " + originalFilename);
                String newFileName = null;
                if (multipartFile != null && originalFilename!= null && originalFilename.length()>0){

                    newFileName= UUID.randomUUID() + "&"+ originalFilename;
                    //存储图片的物理路径 
                    String pic_path = request.getSession().getServletContext().getRealPath("/upload");
                    //新图片路径
                    File targetFile = new File(pic_path, newFileName);
                    //内存数据读入磁盘
                    multipartFile.transferTo(targetFile);
                    newFileNames.add("/upload/" + newFileName);
                }
            }
        }
        catch (IOException e){
        	e.printStackTrace();
        }
        return newFileNames;
    }

}
