package com.weiyun.peoplecounting.utils;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.Base64;

public class BASE64DecodedMultipartFile implements MultipartFile {


    private final byte[] imgContent;
    private final String header;

    public BASE64DecodedMultipartFile(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header.split(";")[0];
    }

    @Override
    public String getName() {
        // TODO - implementation depends on your requirements
        return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
    }

    @Override
    public String getOriginalFilename() {
        // TODO - implementation depends on your requirements
        return System.currentTimeMillis() + (int)Math.random() * 10000 + "." + header.split("/")[1];
    }

    @Override
    public String getContentType() {
        // TODO - implementation depends on your requirements
        return header.split(":")[1];
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }


    //Base64数据解码成字节数组并写入文件
    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        FileOutputStream outputStream = new FileOutputStream(dest);
        outputStream.write(imgContent);
        outputStream.flush();
        outputStream.close();
    }


    //字节文件编码成Base64格式字符串
    public static String imgToBase64(File source) throws IOException, IllegalStateException {
        //BASE64Encoder encoder = new BASE64Encoder(); //这个编码器编码出来的base64存在换行,导致客户端解析不出来原图
        Base64.Encoder encoder = Base64.getEncoder();
        FileInputStream inputStream = new FileInputStream(source);
        byte[] content = new byte[inputStream.available()];
        inputStream.read(content);
        inputStream.close();
        String base64 = encoder.encodeToString(content);
        return "data:image/jpg;base64,"+base64;
    }

    public static MultipartFile base64ToMultipart(String base64) {
        try {
            String[] baseStrs = base64.split(",");

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            b = decoder.decodeBuffer(baseStrs[1]);

            for(int i = 0; i < b.length; ++i) {
                //处理异常数据
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new BASE64DecodedMultipartFile(b, baseStrs[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
