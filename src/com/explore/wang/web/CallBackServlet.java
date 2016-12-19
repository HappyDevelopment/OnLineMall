package com.explore.wang.web;

import com.explore.wang.factory.CommonFactory;
import com.explore.wang.service.OrderService;
import com.explore.wang.utils.PaymentUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 第三方支付成功， 返回的处理控制层action
 * Created by 王兆琦  on 2016/12/17 23.16.
 */
@WebServlet(name = "CallBackServlet",urlPatterns = "/CallBackServlet")
public class CallBackServlet extends HttpServlet {
    private static Properties prop = null;
    static{
        prop = new Properties();
        String path = CallBackServlet.class.getClassLoader().getResource("merchantInfo.properties").getPath();
        try {
            prop.load(new FileInputStream(new File(path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        String keyValue = prop.getProperty("keyValue");
        //使用keyValue对以上数据进行加密,比较支付公司发回来的hmac
        boolean isVail = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
        if(isVail){//数据没有被修改
            //是转发还是重定向回来的
            if("1".equals(r9_BType)){//重定向过来的
                response.getWriter().write("付款操作完成，等待第三方支付平台进一步确认信息...");
                //测试使用，部署正式环境时删掉
				/*OrderService os = BasicFactory.getFactory().getInstance(OrderService.class);
				os.updatePaystate(r6_Order,1);*/
            }else if("2".equals(r9_BType)){//点对点通讯过来的
                System.out.println("付款成功");
                //修改订单的支付状态， 事务级别， 需要去通知发货了
                OrderService os = CommonFactory.getCommonFactory().getInstance(OrderService.class);
                os.updatePaystate(r6_Order,1);
                //恢复支付公司结果接收成功
                response.getWriter().write("success");
            }
        }else{
            System.out.println("数据被篡改！！！");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
