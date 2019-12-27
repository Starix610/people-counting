package com.weiyun.peoplecounting.controller;


import com.weiyun.peoplecounting.error.BusinessException;
import com.weiyun.peoplecounting.error.EmBusinessError;
import com.weiyun.peoplecounting.pojo.Img;
import com.weiyun.peoplecounting.response.CommonReturnType;
import com.weiyun.peoplecounting.utils.BASE64DecodedMultipartFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Api(description = "图片上传识别接口")
public class UploadController extends BaseController {

    private final static Logger logger = Logger.getLogger(BaseController.class);


    @ApiOperation(value = "测试接口")
    @GetMapping("/test")
    public CommonReturnType test(){
        return CommonReturnType.create("自动部署测试2222");
    }


    @ApiOperation(value = "人数统计识别接口", notes = "只接收BASE64编码格式的图片数据")
    @ApiImplicitParam(name = "img", value = "图片详细实体", required = true, dataType = "Img")
    @PostMapping("/upload/base64")
    public CommonReturnType testUploadBase64(@RequestBody Img img, HttpServletRequest request) throws Exception {

        if (img == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION, "文件无效");
        }
        String randomFileName = UUID.randomUUID().toString().replaceAll("-", "");
        File file = new File("/usr/apps/upload_file/img/" + randomFileName + ".jpg");
        //将上传的图片写到指定位置
        BASE64DecodedMultipartFile.base64ToMultipart(img.getImgform()).transferTo(file);
        Process process = Runtime.getRuntime().exec("/usr/apps/computer_design_competition/test.sh" + " " + file.getAbsolutePath() + " /usr/apps/upload_file/img/output/" + randomFileName + "_done");
        int status = process.waitFor();
        //执行成功
        if (status == 0) {
            //返回识别后的结果以及图片给客户端
            InputStream inputStream = process.getInputStream();
            InputStreamReader inputReader = new InputStreamReader(inputStream);
            BufferedReader buffReader = new BufferedReader(inputReader);
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = buffReader.readLine())!=null){
                lines.add(line);
            }
            buffReader.close();
            inputReader.close();
            inputStream.close();

            Map<String, Object> map = new HashMap<>();
            String executeTime = lines.get(0);  //识别耗时
            String count  = lines.get(lines.size()-1); //统计人数
            executeTime = executeTime.substring(executeTime.indexOf("in") + 3, executeTime.indexOf("seconds") - 1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String handleTime = sdf.format(new Date());
            file = new File("/usr/apps/upload_file/img/output/" + randomFileName + "_done.jpg");
            String base64 = BASE64DecodedMultipartFile.imgToBase64(file);  //返回base64字符串
            map.put("executeTime", executeTime);
            map.put("count", count);
            map.put("handleTime", handleTime);
            map.put("img_orginal", "http://139.196.102.109/img/original/"+ randomFileName + ".jpg");
            map.put("img_detection", "http://139.196.102.109/img/detection/"+ randomFileName + "_done.jpg");
            map.put("base64", base64);
            logger.info("Execute success!");
            return CommonReturnType.create(map);
        } else {
            logger.info("Execute fail! code:" + status);
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR, "未知错误，请稍后重试! [code:"+status+"]");
        }
    }

    @ApiOperation(value = "普通表单图片文件上传", notes = "接收上传图片")
    @PostMapping("/upload")
    public CommonReturnType testUpload(MultipartFile pictureFile, HttpServletRequest request) throws Exception {

        if (pictureFile==null||pictureFile.getSize()<=0){ //是否有文件上传
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION,"文件无效");
        }
        //保存到项目路径
        //String realPath = request.getSession().getServletContext().getRealPath("/upload");
        String realPath = "/usr/apps/upload_file/img";
        //UUID替换文件名防止同名覆盖
        String randomName = UUID.randomUUID().toString().replaceAll("-", "");
        //获取文件后缀名(不带.)
        String ext = FilenameUtils.getExtension(pictureFile.getOriginalFilename());
        //图片全路径
        String filePathName = realPath + "/" + randomName + "." + ext;
        //保存图片
        pictureFile.transferTo(new File(filePathName));

        Process process = Runtime.getRuntime().exec("/usr/apps/computer_design_competition/test.sh" + " " + filePathName + " /usr/apps/upload_file/img/output/" + randomName + "_done");
        int status = process.waitFor();
        if (status == 0) {
            //返回识别后的结果以及图片给客户端
            InputStream inputStream = process.getInputStream();
            InputStreamReader inputReader = new InputStreamReader(inputStream);
            BufferedReader buffReader = new BufferedReader(inputReader);
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = buffReader.readLine())!=null){
                lines.add(line);
            }
            buffReader.close();
            inputReader.close();
            inputStream.close();

            Map<String, String> map = new HashMap<>();
            String executeTime = lines.get(0);  //识别耗时
            String count  = lines.get(lines.size()-1); //统计人数
            executeTime = executeTime.substring(executeTime.indexOf("in") + 3, executeTime.indexOf("seconds") - 1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String handleTime = sdf.format(new Date());
            map.put("executeTime", executeTime);
            map.put("count", count);
            map.put("handleTime", handleTime);
            map.put("img_orginal", "http://139.196.102.109/img/original/"+ randomName + ".jpg");
            map.put("img_detection", "http://139.196.102.109/img/detection/"+ randomName + "_done.jpg");
            logger.info("Execute success!");
            return CommonReturnType.create(map);
        } else {
            logger.info("Execute fail! code:" + status);
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR, "未知错误，请稍后重试! [code:"+status+"]");
        }
    }




}
