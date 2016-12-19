package com.explore.wang.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 全站乱码过滤器
 * Created by 王兆琦  on 2016/12/7 16.22.
 */
@WebFilter(filterName = "EncodeingFilter", urlPatterns = "/*")
public class EncodeingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        //响应乱码的处理
        resp.setContentType("text/html;charset=utf-8");

        //进行全乱请求参数的正确设置
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;

        //使用包装类进行包装
        MyServletRequest myServletRequest = new MyServletRequest(httpServletRequest);

        //使用包装类进行处理，
        chain.doFilter(myServletRequest, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

class MyServletRequest extends HttpServletRequestWrapper {

    private HttpServletRequest request;
    private boolean needEncode = true;

    public MyServletRequest(HttpServletRequest request) {
        super(request);

        this.request = request;
    }

    @Override
    public String getParameter(String name) {
        return this.getParameterValues(name) == null ? null : this.getParameterValues(name)[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {

        // 这里进行参数的改造
        try {
            String method = request.getMethod();

            if ("POST".equalsIgnoreCase(method)) {

                request.setCharacterEncoding("utf-8");
                return request.getParameterMap();
            } else if ("GET".equalsIgnoreCase(method)) {

                Map<String, String[]> map = request.getParameterMap();
                if (needEncode) {
                    for (Map.Entry<String, String[]> entry : map.entrySet()) {
                        //只有参数会乱码
                        String[] values = entry.getValue();

                        for (int i = 0; i < values.length; i++) {
                            values[i] = new String(values[i].getBytes("iso-8859-1"), "utf-8");
                        }

                    }
                    needEncode = false;
                }
                return map;
            } else { // 不做处理
                return request.getParameterMap();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


    @Override
    public String[] getParameterValues(String name) {
        return this.getParameterMap().get(name);
    }
}
