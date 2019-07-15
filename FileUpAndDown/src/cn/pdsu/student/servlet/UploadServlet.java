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
		
		//�ϴ�
		try {
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {//�ж�ǰ̨��form�Ƿ���Multipart����
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				
			/*	//�����ϴ��ļ��õ���ʱ�ļ��Ĵ�С 
				factory.setSizeThreshold(1024*10);//������ʱ�Ļ����ļ���С
				factory.setRepository(new File("j:\\")); //������ʱ�ļ�Ŀ¼
				//�����ϴ������ļ��Ĵ�С 20kB 
				upload.setSizeMax(20*1024);*/
				
				
				//ͨ��parseRequest����form�е����������ֶΣ������浽items������
				//spicture��ʱ�ͱ�����items��
				List<FileItem> items = upload.parseRequest(request);
				Iterator<FileItem> iter = items.iterator();
				while(iter.hasNext()) {
					FileItem item = iter.next();
					String itemName = item.getFieldName();
					int sno = -1;
					String sname = null;
					
					//�ж�ǰ̨�ֶ� ����ͨform���ֶΣ������ļ��ļ��ֶ�
					if (item.isFormField()) {
						if("sno".equals(itemName)) {
							sno = Integer.parseInt(item.getString("UTF-8"));
						}else if("sname".equals(itemName)){
							sname = item.getString("UTF-8");
						}else {
							System.out.println("�����ֶΡ�����");
						}
					}else {
						// spicture 123
						// �ļ� �ϴ�
						// �ļ��� getFieldName�ǻ�ȡ ��ͨ���ֶε�Nameֵ
						// getName()�ǻ�ȡ �ļ���
						String fileName = item.getName();
						String ext = fileName.substring(fileName.indexOf(".")+1);
						if (!("png".equals(ext) || "gif".equals(ext) || "jpg".equals(ext))) {
							System.out.println("ͼƬ��ʽ���󣡣�");
							return;
						}
						//��ȡ�ļ����� ���ϴ�
						//�����ļ�·����ָ���ϴ���λ�ã�������·����
						//��ȡ������·��String path =request.getSession().getServletContext().getRealPath("upload") ;
						//String path = request.getSession().getServletContext().getRealPath("upload");
						String path = "J://";
						File file = new File(path,"hh.png");
						
						item.write(file);
						System.out.println("�ϴ��ɹ�");
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
