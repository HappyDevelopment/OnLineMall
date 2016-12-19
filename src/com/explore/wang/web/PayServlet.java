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
 * 支付的action
 * Created by 王兆琦  on 2016/12/17 23.35.
 */
@WebServlet(name = "PayServlet" , urlPatterns = "/PayServlet")
public class PayServlet extends HttpServlet {
    private static Properties prop = null;
    static{
        prop = new Properties();
        String path = PayServlet.class.getClassLoader().getResource("merchantInfo.properties").getPath();
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
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //准备参数
        String p0_Cmd = "Buy";
        String p1_MerId = prop.getProperty("p1_MerId");
        String p2_Order = request.getParameter("orderid");
        OrderService os = CommonFactory.getCommonFactory().getInstance(OrderService.class);
        String p3_Amt = os.findOrderById(p2_Order).getMoney()+"";
        //测试使用，部署正式环境时删掉
        p3_Amt = "0.01";//0.01
        String p4_Cur = "CNY";
        String p5_Pid = "";
        String p6_Pcat ="";
        String p7_Pdesc = "";
        String p8_Url = prop.getProperty("responseURL");
        String p9_SAF = "";//送货地址
        String pa_MP = "";
        String pd_FrpId = request.getParameter("pd_FrpId");
        String pr_NeedResponse ="1";
        String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid,
                p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, prop.getProperty("keyValue"));
        //将以上参数保存到requst中
        request.setAttribute("pd_FrpId", pd_FrpId);
        request.setAttribute("p0_Cmd", p0_Cmd);
        request.setAttribute("p1_MerId", p1_MerId);
        request.setAttribute("p2_Order", p2_Order);
        request.setAttribute("p3_Amt", p3_Amt);
        request.setAttribute("p4_Cur", p4_Cur);
        request.setAttribute("p5_Pid", p5_Pid);
        request.setAttribute("p6_Pcat", p6_Pcat);
        request.setAttribute("p7_Pdesc", p7_Pdesc);
        request.setAttribute("p8_Url", p8_Url);
        request.setAttribute("p9_SAF", p9_SAF);
        request.setAttribute("pa_MP", pa_MP);
        request.setAttribute("pr_NeedResponse", pr_NeedResponse);
        request.setAttribute("hmac", hmac);
        //转发
        request.getRequestDispatcher("/confirm.jsp").forward(request, response);
    }
}
