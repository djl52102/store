package com.itheima.store.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import com.itheima.store.constant.Constant;
import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.UUIDUtil;
import com.itheima.store.utils.UploadUtils;

/**
 * Servlet implementation class ProductAddServlet
 */
public class ProductAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService=BeanFactory.newInstance(ProductService.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductAddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡ���в���
				Map<String, String[]> map = parseRequest(request);
				
				//�����ݷ�װ��product
				Product product=new Product();
				
				try {
					BeanUtils.populate(product, map);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException();
				} 
				
				//����pid
				product.setPid(UUIDUtil.getId());
				// ����
				product.setPdate(new Date());
				// �Ƿ��ϼ�
				product.setPflag(Constant.PRODUCT_OFF);
				
				System.out.println(product);
				
				//����service������Ʒ
				productService.saveProduct(product);
	}

	private Map<String, String[]> parseRequest(HttpServletRequest request) {
		Map<String, String[]> map = new HashMap<>();
		//1.�����ļ����̹���
		 DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		 //2.ʹ���ļ����̹������� �����ļ��ϴ���������
		 FileUpload fileUpload = new FileUpload(diskFileItemFactory);
		 //3.ʹ�øö������request ����
		 try {
			List<FileItem> items = fileUpload.parseRequest(request);
			
			//����items
			for (FileItem fileItem : items) {
				//��ȡ������
				String fieldName = fileItem.getFieldName();
				//�ж�����ͨ�ֶ�
				if(fileItem.isFormField()){
					//����ֵ
					String value = fileItem.getString("utf-8");
					//����ͨ�ֶ�����ֵ����map����
					map.put(fieldName, new String[]{value});
				}else{
					//�ļ���
					//��ȡ�ϴ��ļ��ļ���
					String name = fileItem.getName();
					//��ȡ����·�����ļ���
					String realName = UploadUtils.getRealName(name);
					//��������ļ���
					String uuidName = UploadUtils.getUUIDName(realName);
					//��ȡ�ļ������λ��
					ServletContext sc = this.getServletContext();
					//����һ����Ŀ¼����Ŀ�����Ժ�� ��ʵĿ¼ ===tomcat��Ŀ¼+webapps/+��Ŀ��+/resources/products
					String realPath = sc.getRealPath("/resources/products");
					//��������ļ���
					String dir = UploadUtils.getDir();
					//Ŀ��·��
					String path=realPath+dir;
					
					File storePath=new File(path);
					
					if(!storePath.exists()){
						storePath.mkdirs();
					}
					
					//�ļ���Ӧ������
					InputStream is = fileItem.getInputStream();
					//�����
					FileOutputStream fos = new FileOutputStream(path+"/"+uuidName);
					
					//�����ļ�
					IOUtils.copy(is, fos);
					//�ر���
					fos.close();
					is.close();
					
					//ɾ�������ļ�
					fileItem.delete();
					
					//�洢��map��
					map.put(fieldName, new String[]{"resources/products"+dir+"/"+uuidName});
					
				}
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		} 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
