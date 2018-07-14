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
	     //判断用户是否登陆，没有登陆跳转到登陆页面
    	 HttpSession session = request.getSession();
 
    	 //获取购物车
    	 Cart cart =(Cart)session.getAttribute("cart");
    	 
    	 //封装order
    	 Order order = new Order();
    	 
    	 String oid=UUIDUtil.getId();
    	 
    	 //设置订单id
    	 order.setOid(oid);
    	 //设置订单时间
    	 order.setOrdertime(new Date());
    	 //设置订单金额
    	 order.setTotal(cart.getTotal());
    	 //设置订单状态
    	 order.setState(Constant.ORDER_IS_WEIFUKUAN);
    	 
    	 //获取用户
    	 User user =(User)session.getAttribute("user");
    	 
    	 order.setUid(user.getUid());
    	 
    	 //设置订单项
    	 Collection<CartItem> map = cart.getMap();
    	 
    	 //创建list集合存储订单项
    	 for (CartItem item : map) {
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(UUIDUtil.getId());
			//设置数量
			orderItem.setCount(item.getCount());
			//设置pid
			orderItem.setPid(item.getP().getPid());
			//设置产品
			orderItem.setProduct(item.getP());
			//设置oid
			orderItem.setOid(oid);
			//设置subtotal
			orderItem.setSubtotal(item.getSubTotal());
			
			//添加到list集合
			order.getItems().add(orderItem);
		}
    	
    	//调用sevice存入数据库
    	orderService.saveOrder(order);
    	
    	//清空购物车
    	cart.clear();
    	
    	//转发到jsp页面进行显示
    	request.getRequestDispatcher("/order?md=info&oid="+oid).forward(request, response);
    	 
	}
    
    //获取购物车
    private Cart getCart(HttpServletRequest request){
    	//判断是否是第一次购物
    	HttpSession session = request.getSession();
    	
    	Cart cart=(Cart) session.getAttribute("cart");
    	
    	if(session.getAttribute("cart") == null){
    		//如果没有，新建购物车
    		cart=new Cart();
    		//存入session
    		session.setAttribute("cart", cart);
    		
    		return cart;
    		
    	}else{
    		//如果有，直接取出
    		return cart;
    	}
    }
    
    
    //获取订单列表
    @Auth
    public void myOrdersWithPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//获取session,判断用户名是否存在
    	User user =(User)request.getSession().getAttribute("user");
  
    	//获取pageNumber
    	String str = request.getParameter("pageNumber");
    	if(str == null){
    		str="1";
    	}
    	//当前页
    	int pageNumber=Integer.parseInt(str);
    	//获取用户id
    	String uid = user.getUid();
    	//设置pageSize
    	int pageSize=3;
    	
    	PageBean pageBean=orderService.findMyOrdersByPage(uid,pageNumber,pageSize);
    	
    	//存入request域中
    	request.setAttribute("pb", pageBean);
    	
    	request.getRequestDispatcher("/WEB-INF/jsp/order/order_list.jsp").forward(request, response);
    	
    }
	
    //根据oid获取订单详情
    public void info(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//获取订单id
    	String oid = request.getParameter("oid");
    	
    	//获取订单信息
    	Order order=orderService.findOrderInfo(oid);
    	
    	request.setAttribute("order", order);
    	
    	request.getRequestDispatcher("WEB-INF/jsp/order/order_info.jsp").forward(request, response);
    
    }
    
    //准备订单付款
    public void toPay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 //获取订单id
    	 String oid = request.getParameter("oid");
    	 //获取所有参数
    	 Map<String, String[]> map = request.getParameterMap();
    	 
    	 //根据id获取当前订单
    	 Order order = orderService.findOrderInfo(oid);
    	 //将map集合封装入order中
    	 try {
			BeanUtils.populate(order, map);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	 
    	 //调用orderService更新订单信息
    	 orderService.updateOrder(order);
    	 
    	//跳转到第三方支付网站
 		// 组织发送支付公司需要哪些数据
 		String pd_FrpId = request.getParameter("pd_FrpId");
 		String p0_Cmd = "Buy";
 		String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
 		String p2_Order = oid;
 		String p3_Amt = "0.01";
 		String p4_Cur = "CNY";
 		String p5_Pid = "";
 		String p6_Pcat = "";
 		String p7_Pdesc = "";
 		// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
 		// 第三方支付可以访问网址
 		String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
 		String p9_SAF = "";
 		String pa_MP = "";
 		String pr_NeedResponse = "1";
 		// 加密hmac 需要密钥
 		String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
 		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
 				p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

 		// 发送给第三方
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
		// 身份校验 --- 判断是不是支付公司通知你
		String hmac = request.getParameter("hmac");
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");

		// 自己对上面数据进行加密 --- 比较支付公司发过来hamc
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid,
				r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
		if (isValid) {
			// 响应数据有效
			if (r9_BType.equals("1")) {
				// 浏览器重定向
				System.out.println("111");
				request.setAttribute("msg", "您的订单号为:" + r6_Order + ",金额为:" + r3_Amt + "已经支付成功,等待发货~~");
				request.getRequestDispatcher("/WEB-INF/jsp/info.jsp").forward(request, response);
			} else if (r9_BType.equals("2")) {
				// 服务器点对点 --- 支付公司通知你
				System.out.println("付款成功！222");
				// 修改订单状态 为已付款
				// 回复支付公司
				response.getWriter().print("success");
			}

			// 修改订单状态
			Order order = orderService.findOrderInfo(r6_Order);
			order.setState(Constant.ORDER_IS_YIFUKUAN);
			//更改订单状态
			orderService.updateOrder(order);
		} else {
			// 数据无效
			System.out.println("数据被篡改！");
		}
    }
    
    public void test(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  	}
}
