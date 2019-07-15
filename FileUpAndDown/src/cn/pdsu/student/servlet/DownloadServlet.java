package cn.pdsu.student.servlet;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DownloadServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//��ȡ��Ҫ���ص��ļ���
		String fileName = request.getParameter("filename");
		
		//�����ļ�����Ҫ������Ϣͷ
		response.addHeader("content-Type", "application/octet-stream");//MIME����:�������ļ��������ļ���
		response.addHeader("content-Disposition","attachement;filename="+fileName );//fileName�������ļ���׺��abc.txt
		
		//Servletͨ���ļ��ĵ�ַ ���ļ�ת��Ϊ������   ����Servlet��
		InputStream in = getServletContext().getResourceAsStream("/res/MIME.png");
		
		//ͨ����������ղ���תΪ���������ļ�������û�
		ServletOutputStream out = response.getOutputStream();
		byte [] bs = new byte[10];
		int len = -1;
		while((len = in.read(bs)) != -1 ) {
			out.write(bs,0,len);
		}
		out.close();
		in.close();
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
