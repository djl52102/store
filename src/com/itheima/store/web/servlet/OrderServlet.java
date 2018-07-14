package com.itheima.store.web.servlet;

import com.itheima.store.constant.Constant;
import com.itheima.store.domain.Cart;
import com.itheima.store.domain.CartItem;
import com.itheima.store.domain.Order;
import com.itheima.store.domain.OrderItem;
import com.itheima.store.domain.PageBean;
import com.itheima.store.domain.User;
import com.itheima.store.service.OrderService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.PaymentUtil;
import com.itheima.store.utils.UUIDUtil;
import com.itheima.store.web.annotation.Auth;
import com.itheima.store.web.base.BaseServlet;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    
	private OrderService orderService=BeanFactory.newInstance(OrderService.class);
	
	@Auth
    public void createOrder(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
	     //�ж��û��Ƿ��½��û�е�½��ת����½ҳ��
    	 HttpSession session = request.getSession();
 
    	 //��ȡ���ﳵ
    	 Cart cart =(Cart)session.getAttribute("cart");
    	 
    	 //��װorder
    	 Order order = new Order();
    	 
    	 String oid=UUIDUtil.getId();
    	 
    	 //���ö���id
    	 order.setOid(oid);
    	 //���ö���ʱ��
    	 order.setOrdertime(new Date());
    	 //���ö������
    	 order.setTotal(cart.getTotal());
    	 //���ö���״̬
    	 order.setState(Constant.ORDER_IS_WEIFUKUAN);
    	 
    	 //��ȡ�û�
    	 User user =(User)session.getAttribute("user");
    	 
    	 order.setUid(user.getUid());
    	 
    	 //���ö�����
    	 Collection<CartItem> map = cart.getMap();
    	 
    	 //����list���ϴ洢������
    	 for (CartItem item : map) {
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(UUIDUtil.getId());
			//��������
			orderItem.setCount(item.getCount());
			//����pid
			orderItem.setPid(item.getP().getPid());
			//���ò�Ʒ
			orderItem.setProduct(item.getP());
			//����oid
			orderItem.setOid(oid);
			//����subtotal
			orderItem.setSubtotal(item.getSubTotal());
			
			//��ӵ�list����
			order.getItems().add(orderItem);
		}
    	
    	//����sevice�������ݿ�
    	orderService.saveOrder(order);
    	
    	//��չ��ﳵ
    	cart.clear();
    	
    	//ת����jspҳ�������ʾ
    	request.getRequestDispatcher("/order?md=info&oid="+oid).forward(request, response);
    	 
	}
    
    //��ȡ���ﳵ
    private Cart getCart(HttpServletRequest request){
    	//�ж��Ƿ��ǵ�һ�ι���
    	HttpSession session = request.getSession();
    	
    	Cart cart=(Cart) session.getAttribute("cart");
    	
    	if(session.getAttribute("cart") == null){
    		//���û�У��½����ﳵ
    		cart=new Cart();
    		//����session
    		session.setAttribute("cart", cart);
    		
    		return cart;
    		
    	}else{
    		//����У�ֱ��ȡ��
    		return cart;
    	}
    }
    
    
    //��ȡ�����б�
    @Auth
    public void myOrdersWithPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//��ȡsession,�ж��û����Ƿ����
    	User user =(User)request.getSession().getAttribute("user");
  
    	//��ȡpageNumber
    	String str = request.getParameter("pageNumber");
    	if(str == null){
    		str="1";
    	}
    	//��ǰҳ
    	int pageNumber=Integer.parseInt(str);
    	//��ȡ�û�id
    	String uid = user.getUid();
    	//����pageSize
    	int pageSize=3;
    	
    	PageBean pageBean=orderService.findMyOrdersByPage(uid,pageNumber,pageSize);
    	
    	//����request����
    	request.setAttribute("pb", pageBean);
    	
    	request.getRequestDispatcher("/WEB-INF/jsp/order/order_list.jsp").forward(request, response);
    	
    }
	
    //����oid��ȡ��������
    public void info(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//��ȡ����id
    	String oid = request.getParameter("oid");
    	
    	//��ȡ������Ϣ
    	Order order=orderService.findOrderInfo(oid);
    	
    	request.setAttribute("order", order);
    	
    	request.getRequestDispatcher("WEB-INF/jsp/order/order_info.jsp").forward(request, response);
    
    }
    
    //׼����������
    public void toPay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 //��ȡ����id
    	 String oid = request.getParameter("oid");
    	 //��ȡ���в���
    	 Map<String, String[]> map = request.getParameterMap();
    	 
    	 //����id��ȡ��ǰ����
    	 Order order = orderService.findOrderInfo(oid);
    	 //��map���Ϸ�װ��order��
    	 try {
			BeanUtils.populate(order, map);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	 
    	 //����orderService���¶�����Ϣ
    	 orderService.updateOrder(order);
    	 
    	//��ת��������֧����վ
 		// ��֯����֧����˾��Ҫ��Щ����
 		String pd_FrpId = request.getParameter("pd_FrpId");
 		String p0_Cmd = "Buy";
 		String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
 		String p2_Order = oid;
 		String p3_Amt = "0.01";
 		String p4_Cur = "CNY";
 		String p5_Pid = "";
 		String p6_Pcat = "";
 		String p7_Pdesc = "";
 		// ֧���ɹ��ص���ַ ---- ������֧����˾����ʡ��û�����
 		// ������֧�����Է�����ַ
 		String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
 		String p9_SAF = "";
 		String pa_MP = "";
 		String pr_NeedResponse = "1";
 		// ����hmac ��Ҫ��Կ
 		String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
 		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
 				p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

 		// ���͸�������
 		StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
 		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
 		sb.append("p1_MerId=").append(p1_MerId).append("&");
 		sb.append("p2_Order=").append(p2_Order).append("&");
 		sb.append("p3_Amt=").append(p3_Amt).append("&");
 		sb.append("p4_Cur=").append(p4_Cur).append("&");
 		sb.append("p5_Pid=").append(p5_Pid).append("&");
 		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
 		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
 		sb.append("p8_Url=").append(p8_Url).append("&");
 		sb.append("p9_SAF=").append(p9_SAF).append("&");
 		sb.append("pa_MP=").append(pa_MP).append("&");
 		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
 		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
 		sb.append("hmac=").append(hmac);

 		response.sendRedirect(sb.toString());
    	 
    }
    
    public void callback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");
		// ���У�� --- �ж��ǲ���֧����˾֪ͨ��
		String hmac = request.getParameter("hmac");
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");

		// �Լ����������ݽ��м��� --- �Ƚ�֧����˾������hamc
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid,
				r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
		if (isValid) {
			// ��Ӧ������Ч
			if (r9_BType.equals("1")) {
				// ������ض���
				System.out.println("111");
				request.setAttribute("msg", "���Ķ�����Ϊ:" + r6_Order + ",���Ϊ:" + r3_Amt + "�Ѿ�֧���ɹ�,�ȴ�����~~");
				request.getRequestDispatcher("/WEB-INF/jsp/info.jsp").forward(request, response);
			} else if (r9_BType.equals("2")) {
				// ��������Ե� --- ֧����˾֪ͨ��
				System.out.println("����ɹ���222");
				// �޸Ķ���״̬ Ϊ�Ѹ���
				// �ظ�֧����˾
				response.getWriter().print("success");
			}

			// �޸Ķ���״̬
			Order order = orderService.findOrderInfo(r6_Order);
			order.setState(Constant.ORDER_IS_YIFUKUAN);
			//���Ķ���״̬
			orderService.updateOrder(order);
		} else {
			// ������Ч
			System.out.println("���ݱ��۸ģ�");
		}
    }
    
    public void test(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  	}
}
