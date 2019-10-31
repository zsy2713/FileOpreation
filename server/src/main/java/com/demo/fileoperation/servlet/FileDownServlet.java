package com.demo.fileoperation.servlet;

import com.demo.fileoperation.domain.Info;
import com.demo.fileoperation.security.SecrteKeyUtil;
import com.demo.fileoperation.service.FileService;
import com.demo.fileoperation.service.impl.FileServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileDownServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        String uuid = request.getParameter("uuid");
        //通过uuid从数据库中查出mkdir
        FileService service = new FileServiceImpl();
        Info info = service.getInfo(uuid);
        //拼接出文件的绝对路径
        String mkdir = info.getMkdir();
        String type = info.getType();
        String location = getServletContext().getRealPath("WEB-INF"+File.separator+mkdir)
                +File.separator+mkdir+uuid+"."+type;
        File file = new File(location);
        response.setHeader("filename",mkdir+uuid+"."+type);
        InputStream in = new FileInputStream(file);
        int len = 0;
        byte buff[] = new byte[1024];
        OutputStream os = response.getOutputStream();
        while ((len = in.read(buff))!=-1){
            os.write(buff,0,len);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
