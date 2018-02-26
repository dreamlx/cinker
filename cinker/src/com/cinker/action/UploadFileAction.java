package com.cinker.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sun.istack.internal.logging.Logger;

@Controller
@RequestMapping("/maintain")
public class UploadFileAction {
	
	private static final Logger log = Logger.getLogger(UploadFileAction.class);
	
	@Value("${LoadUpFileMaxSize}")
    String LoadUpFileMaxSize; 
	
    private static String uploadPath = "d:/upload";
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody 
	public Map<String, String> aadd_worker(MultipartHttpServletRequest multipartRequest,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String result = "success!";
		Map<String, String> map = new HashMap<String, String>();
		for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
			String key = (String) it.next();// 文件名 
			log.info("key: " + key);
			MultipartFile multipartFile = multipartRequest.getFile(key);// 根据key得到文件 
			if (multipartFile.getOriginalFilename().length() > 0) {
				String originalFileName = multipartFile.getOriginalFilename(); 
				 // b-截取后缀, 重命名文件, 使用uuid+后缀的方式命名保存到服务器上的文件 
				String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
				 log.info("文件后缀: " + suffix);
				 String fileName = UUID.randomUUID() + suffix;
				 log.info("新文件名: " + fileName);
				 try {
					 String uploadFileUrl = uploadPath;
					 log.info("保存文件路径: " + uploadFileUrl);
					 File uploadFile = saveFileFromInputStream(multipartFile.getInputStream(), uploadFileUrl,fileName);
					 if (uploadFile.exists()) {
						 log.info(originalFileName + "上传成功");
					 }else{
						 log.info(originalFileName + "上传失败");
						 throw new FileNotFoundException("file write fail: "+ fileName);
					 }
				 }catch (Exception e) {
					 log.info(originalFileName + "上传失败");  
	                 e.printStackTrace();
				 }
				 result += fileName + "|";
			}
		}
		map.put("notice", result); 
		return map;
	}
	
	// 保存文件到指定路径 
	 private File saveFileFromInputStream(InputStream stream, String path,String filename){
		 File dirFile = new File(path); 
		 if (!dirFile.exists()) {
	        dirFile.mkdir();  
	     }
		 File file = null;
		 FileOutputStream fs = null;
		 try{			 
			 file = new File(path + "/" + filename);
			 fs = new FileOutputStream(file); 
			 byte[] buffer = new byte[1024 * 1024];
			 int bytesum = 0;
			 int byteread = 0;
			 while ((byteread = stream.read(buffer)) != -1) { 
				 bytesum += byteread;
				 fs.write(buffer, 0, byteread);
				 fs.flush();
			 }
			 fs.close();
			 stream.close();
			 
		 }catch(FileNotFoundException e){
			 e.printStackTrace();
		 } catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				if (fs != null) {
					fs.close();
				}
				if (stream != null) {
					stream.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		return file;
		 
	 }
			

}
