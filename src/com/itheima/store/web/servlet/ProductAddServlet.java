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
		//获取所有参数
				Map<String, String[]> map = parseRequest(request);
				
				//将数据封装入product
				Product product=new Product();
				
				try {
					BeanUtils.populate(product, map);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException();
				} 
				
				//设置pid
				product.setPid(UUIDUtil.getId());
				// 日期
				product.setPdate(new Date());
				// 是否上架
				product.setPflag(Constant.PRODUCT_OFF);
				
				System.out.println(product);
				
				//调用service保存商品
				productService.saveProduct(product);
	}

	private Map<String, String[]> parseRequest(HttpServletRequest request) {
		Map<String, String[]> map = new HashMap<>();
		//1.创建文件磁盘工厂
		 DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		 //2.使用文件磁盘工厂对象 创建文件上传解析对象
		 FileUpload fileUpload = new FileUpload(diskFileItemFactory);
		 //3.使用该对象解析request 请求
		 try {
			List<FileItem> items = fileUpload.parseRequest(request);
			
			//遍历items
			for (FileItem fileItem : items) {
				//获取参数名
				String fieldName = fileItem.getFieldName();
				//判断是普通字段
				if(fileItem.isFormField()){
					//参数值
					String value = fileItem.getString("utf-8");
					//将普通字段类型值放入map集合
					map.put(fieldName, new String[]{value});
				}else{
					//文件项
					//获取上传文件文件名
					String name = fileItem.getName();
					//获取不含路径的文件名
					String realName = UploadUtils.getRealName(name);
					//生成随机文件名
					String uuidName = UploadUtils.getUUIDName(realName);
					//获取文件输出流位置
					ServletContext sc = this.getServletContext();
					//返回一个此目录在项目发布以后的 真实目录 ===tomcat家目录+webapps/+项目名+/resources/products
					String realPath = sc.getRealPath("/resources/products");
					//生成随机文件夹
					String dir = UploadUtils.getDir();
					//目标路径
					String path=realPath+dir;
					
					File storePath=new File(path);
					
					if(!storePath.exists()){
						storePath.mkdirs();
					}
					
					//文件对应输入流
					InputStream is = fileItem.getInputStream();
					//输出流
					FileOutputStream fos = new FileOutputStream(path+"/"+uuidName);
					
					//复制文件
					IOUtils.copy(is, fos);
					//关闭流
					fos.close();
					is.close();
					
					//删除缓存文件
					fileItem.delete();
					
					//存储到map中
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
