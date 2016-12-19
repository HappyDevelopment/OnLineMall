package com.explore.wang.web;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传
 * Created by 王兆琦  on 2016/12/8 22.52.
 */
@WebServlet(name = "FileUpLoadServlet", urlPatterns = "/FileUpLoadServlet")
public class FileUpLoadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1 ,首先进行提交校验，是不是多媒体封装
        // true  为是， 进行处理 ，  不是 返回 或 抛异常
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new RuntimeException("不是isMultipartContent");
        }

        // 2 ,创建DiskFileItemFactory对象，设置缓冲区大小与临时文件目录
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();

        // 2.1 设置缓冲区大小
        diskFileItemFactory.setSizeThreshold(200 * 1024);

        // 2.2 配置临时文件目录  ， 接受文件目录为参数
        //  为了安全， 放在 WEB-INF 目录下的的temp目录
        //内存缓冲区： 上传文件时，上传文件的内容优先保存在内存缓冲区中，
        // 上传文件大小超过缓冲区大小，就会在服务器端产生临时文件
        diskFileItemFactory.setRepository(new File(request.getServletContext().
                getRealPath("/WEB-INf/tmp/")));


        //3、ServletFileUpload类的相关操作
        //3.1创建ServletFileUpload类对象
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);

        //3.2处理文件名乱码问题
        servletFileUpload.setHeaderEncoding("utf-8");

        // 3.3 设置单个文件上传大小 与 总的上传大小
        servletFileUpload.setFileSizeMax(5 * 1024 * 1024);
        servletFileUpload.setSizeMax(6 * 1024 * 1024);


        //4准备工作做好了。现在得到数据进行处理
        // 得到所有的输入项
        try {
            List<FileItem> list = servletFileUpload.parseRequest(request);

            //4.1 遍历 list
            for (FileItem fileItem : list) {

                //4.2进行处理， 判断提交的是不是文件 , false为文件
                if (fileItem.isFormField()) {
                    //此处需要注意的就是获取文件名的乱码问题，
                    // 因为提交的方式的多媒体封装， 也是没有中文的 , 可以传入一个编码进去
                    System.out.println(fileItem.getFieldName() + "==" +
                            fileItem.getString("utf-8"));

                } else {  // 是文件

                    // 获取文件名
                    String fileName = fileItem.getName();

                    // 解决部分浏览器包含本地地址的问题
                    if (fileName.contains("\\")) {
                        fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
                    }

                    //防止文件名字重复，拼接UUID 加上文件名，  有甚多中方法等。。
                    fileName = UUID.randomUUID().toString() + "_" + fileName;

                    // 获得文件名的字符数组， 分隔后作为保存的路径
                    String path = "WEB-INF/upload";

                    // 获得文件名
                    String hash = Integer.toHexString(fileName.hashCode());
                    char[] chars = hash.toCharArray();

                    for (int i = 0; i < chars.length; i++) {
                        path = path + "/" + chars[i];
                    }

                    //7 创建目录 , 跑拼接上web应用的实际地址
                    File file = new File(getServletContext().getRealPath(path));
                    file.mkdirs();

                    // 8 , 进行文件的读取操作
                    // 读取流是 FileItem的读取流
                    InputStream inputStream = fileItem.getInputStream();

                    //输出流是这个文件目录 ，  害的加上文件名
                    OutputStream outputStream = new FileOutputStream(
                            new File(getServletContext().getRealPath(path + "/" + fileName)));


                    int len = -1;
                    byte[] bytes = new byte[1024 * 1024];

                    while ((len = inputStream.read(bytes)) != -1) {

                        outputStream.write(bytes, 0, len);
                    }

                    //9 ， 关闭资源
                    inputStream.close();
                    outputStream.close();

                    // 10 ， 将临时资源删除
                    fileItem.delete();

                }

            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}
