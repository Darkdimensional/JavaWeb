package cn.pdsu.student.servlet;


import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UploadServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		//上传
		try {
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {//判断前台的form是否有Multipart属性
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				
			/*	//设置上传文件用的临时文件的大小 
				factory.setSizeThreshold(1024*10);//设置临时的缓冲文件大小
				factory.setRepository(new File("j:\\")); //设置临时文件目录
				//控制上传单个文件的大小 20kB 
				upload.setSizeMax(20*1024);*/
				
				
				//通过parseRequest解析form中的所有请求字段，并保存到items集合中
				//spicture此时就保存在items中
				List<FileItem> items = upload.parseRequest(request);
				Iterator<FileItem> iter = items.iterator();
				while(iter.hasNext()) {
					FileItem item = iter.next();
					String itemName = item.getFieldName();
					int sno = -1;
					String sname = null;
					
					//判断前台字段 是普通form表单字段，还是文件文件字段
					if (item.isFormField()) {
						if("sno".equals(itemName)) {
							sno = Integer.parseInt(item.getString("UTF-8"));
						}else if("sname".equals(itemName)){
							sname = item.getString("UTF-8");
						}else {
							System.out.println("其他字段。。。");
						}
					}else {
						// spicture 123
						// 文件 上传
						// 文件名 getFieldName是获取 普通表单字段的Name值
						// getName()是获取 文件名
						String fileName = item.getName();
						String ext = fileName.substring(fileName.indexOf(".")+1);
						if (!("png".equals(ext) || "gif".equals(ext) || "jpg".equals(ext))) {
							System.out.println("图片格式有误！！");
							return;
						}
						//获取文件内容 并上传
						//定义文件路径：指定上传的位置（服务器路径）
						//获取服务器路径String path =request.getSession().getServletContext().getRealPath("upload") ;
						//String path = request.getSession().getServletContext().getRealPath("upload");
						String path = "J://";
						File file = new File(path,"hh.png");
						
						item.write(file);
						System.out.println("上传成功");
						return;
						
						}
					}
				}
				
			} catch (FileUploadException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
