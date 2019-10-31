package com.demo.fileoperation.controller;

import com.demo.fileoperation.security.SecrteKeyUtil;
import com.demo.fileoperation.util.Info;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

@Controller
public class FileOpController {
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    CloseableHttpClient httpClient2 = HttpClientBuilder.create().build();
    CloseableHttpClient httpClient3 = HttpClientBuilder.create().build();
    CloseableHttpResponse httpResponse = null;

    @RequestMapping("/fileOp")
    public String FileOp(@RequestParam("file") MultipartFile multipartFile) throws IOException{
        File file = new File(multipartFile.getOriginalFilename());
        OutputStream os = new FileOutputStream(file);
        byte byt[] = multipartFile.getBytes();
        InputStream in = new ByteArrayInputStream(byt);
        int len = 0;
        byte buff[] = new byte[1024];
        while ((len=in.read(buff))!=-1){
            os.write(buff,0,len);
        }
        //------------------权限验证信息
        String SID = Double.toString(Math.random()*100000);
        String Signature = SecrteKeyUtil.priEncrypt(SID);

        //-------------------

        //通过服务器实现文件的上传，需要将请求发到文件服务器
        HttpPost httpPost = new HttpPost("http://localhost:8080/upload?SID="+SID+"&Signature="+Signature);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.addBinaryBody("file",file);
        HttpEntity httpEntity = multipartEntityBuilder.build();
        httpPost.setEntity(httpEntity);
        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity responseEntity = httpResponse.getEntity();
        int statusCode= httpResponse.getStatusLine().getStatusCode();
        if(statusCode == 200){
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
            StringBuffer buffer = new StringBuffer();
            String str = "";
            while(!StringUtil.isEmpty(str = reader.readLine())) {
                buffer.append(str);
            }
            //System.out.println(buffer.toString());
            //System.out.println(httpResponse.getFirstHeader("uuid"));
        }
        httpClient.close();
        if(httpResponse!=null){
            httpResponse.close();
        }
        return "fileOp";
    }

    @RequestMapping("/down")
    @ResponseBody
    public String down(HttpServletRequest request,HttpServletResponse response) throws IOException{
        HttpGet httpGet = new HttpGet("http://localhost:8080/list");
        HttpEntity entity = null;
        httpResponse = httpClient2.execute(httpGet);
        OutputStream os = response.getOutputStream();
        InputStream in = httpResponse.getEntity().getContent();
        byte buff[] = new byte[1024];
        int len = 0;
        while ((len=in.read(buff))!=-1){
            os.write(buff,0,len);
        }
        os.close();
    /*entity = httpResponse.getEntity();
        String ans =  EntityUtils.toString(entity);
        System.out.println(ans);*/
        return "fileOp";
    }

    @RequestMapping("/down/{uuid}")
    public void downFile(@PathVariable String uuid,HttpServletResponse response) throws IOException {

        String SID = Double.toString(Math.random()*100000);
        String Signature = SecrteKeyUtil.priEncrypt(SID);

        HttpPost httpPost = new HttpPost("http://localhost:8080/down?uuid="+uuid+"&SID="+SID+"&Signature="+Signature);
        httpResponse = httpClient3.execute(httpPost);
        HttpEntity entity = httpResponse.getEntity();
        String filename = httpResponse.getFirstHeader("filename").toString();
        String path = filename.substring(filename.lastIndexOf(" "));
        System.out.println(path);
        File file = new File(path);
        if (!file.exists()) file.createNewFile();
        OutputStream os = new FileOutputStream(file);
        if (entity != null){
            InputStream in = entity.getContent();
            int len = 0;
            byte bytes[] = new byte[1024];
            while ((len = in.read(bytes))!= -1){
                os.write(bytes,0,len);
            }
        }
        response.setHeader("content-disposition",
                "attachment;filename="
                        +URLEncoder.encode(path,"UTF-8"));
        FileInputStream fis = new FileInputStream(path);
        OutputStream out = response.getOutputStream();
        byte []byt = new byte[1024];
        int len = 0;
        while ((len=fis.read(byt))>0){
            out.write(byt,0,len);
        }
        fis.close();
        out.close();
        if (file.exists()) file.delete();
    }
}
