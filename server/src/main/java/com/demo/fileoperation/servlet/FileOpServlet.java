package com.demo.fileoperation.servlet;


import com.demo.fileoperation.domain.Info;
import com.demo.fileoperation.security.SecrteKeyUtil;
import com.demo.fileoperation.service.FileService;
import com.demo.fileoperation.service.impl.FileServiceImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FileOpServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String SID = request.getParameter("SID");
        String Signature = request.getParameter("Signature");
        String ifTrue = "";
        try {
            ifTrue = SecrteKeyUtil.pubdecrypt(SID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!ifTrue.equals(SID)){
            response.setStatus(403);
        }
        //创建工厂实例
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //创建解析器
        ServletFileUpload sfu = new ServletFileUpload(factory);
        sfu.setHeaderEncoding("UTF-8");
        boolean flag = false;
        String uuid = "";
        try {
            List<FileItem> items = sfu.parseRequest(request);
            for(int i=0;i<items.size();i++){
                FileItem item = items.get(i);
                if (item.isFormField()){//判断表单传过来值的类型
                    String name = item.getName();
                }else {//非正常值
                    //UUID
                    uuid = UUID.randomUUID().toString();
                    //按照yy-MM-dd格式获得日期的字符串
                    SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
                    Date date = new Date();
                    String date_str = sdf.format(date);
                    ServletContext sc = getServletContext();
                    //文件存放路径
                    String path = sc.getRealPath("WEB-INF"+File.separator+date_str);
                    File demo = new File(path);
                    if (!demo.exists()) demo.mkdir();
                    //文件大小，以字节为单位
                    Long length = item.getSize();
                    String length_str = Long.toString(length);
                    //源文件全名
                    String allName = item.getName();
                    //文件名
                    String name = allName.substring(0,allName.lastIndexOf("."));
                    //文件类型
                    String type = allName.substring(allName.lastIndexOf(".")+1);

                    String filename =path+ File.separator+date_str+uuid+"."+type;//文件全称
                    File file = new File(filename);//创建文件
                    item.write(file);//将item写入文件


                    Info info = new Info();
                    info.setDate(date_str);
                    info.setFilename(name);
                    info.setType(type);
                    info.setMkdir(date_str);
                    info.setSize(length_str);
                    info.setUuid(uuid);
                    FileService service = new FileServiceImpl();
                    flag = service.save(info);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag){
            //上传成功后设置response的响应
            response.setStatus(200);
            response.setHeader("uuid",uuid);
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        this.doPost(request,response);
    }
}
