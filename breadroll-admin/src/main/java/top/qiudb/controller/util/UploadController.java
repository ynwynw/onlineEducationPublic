package top.qiudb.controller.util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.qiudb.util.tools.PropertiesUtil;
import top.qiudb.util.tools.ResultVO;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/22 9:26
 * @description 上传文件
 */

@RestController
@RequestMapping("/upload")
@Api(tags = "上传信息接口")
public class UploadController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 上传视频课程
     * @param dirName  路径名
     * @param file     文件
     */
    @PostMapping("/course")
    @ApiOperation("上传课程视频")
    public ResultVO courseUpload(@ApiParam("课程目录名") String dirName,@ApiParam("上传的文件") @RequestParam("file") MultipartFile file) {
        long duration;
        if(dirName.trim().length()==0||"请选择课程名称".equals(dirName)){
            return ResultVO.error("课程名不能为空");
        }
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        String realPathDir = "/Users/macbookpro/Downloads/aaaaa_code/upload/";
        String path ="course/"+ dirName + "/" + fileName;
        try {
            //4.第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下uploaded文件夹下）
            File destFile = new File(realPathDir+path);
            destFile.getParentFile().mkdirs();
            //5.把浏览器上传的文件复制到希望的位置
            file.transferTo(destFile);
            String reg = "(mp4|flv|avi|rm|rmvb|wmv)";
            Pattern p = Pattern.compile(reg);
            Map<String, Object> data = new HashMap<>();
            if(p.matcher(fileName).find()){
                Encoder encoder = new Encoder();
                MultimediaInfo info = encoder.getInfo(destFile);
                duration = info.getDuration() / 1000;  //换成秒
                data.put("minute",duration / 60);      //分钟
                data.put("second",duration % 60);      //秒
            }
            data.put("url","/upload/" + path);
            //返回视频路径 URL：/upload/course/JavaScript/161917171406101_mclaren_senna_black_livery_2_resized.jpg
            //返回视频时长 单位：分
            return ResultVO.success("上传成功",data);
        } catch (Exception e) {
            logger.error("上传课程视频失败->message:{}",e.getMessage());
            return ResultVO.error("上传失败",e.getMessage());
        }
    }

    /**
     * 上传学习资料
     * @param file 文件
     */
    @PostMapping("/material")
    @ApiOperation("上传学习资料")
    public ResultVO materialUpload(@ApiParam("上传的文件") @RequestParam("file") MultipartFile file) {
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        String realPathDir = PropertiesUtil.getUploadUrl();
        String path ="material/" + fileName;
        try {
            File destFile = new File(realPathDir+path);
            String fileType=fileName.substring(fileName.lastIndexOf("."));
            destFile.getParentFile().mkdirs();
            file.transferTo(destFile);
            String size=(destFile.length()/1024)+"."+((int)((destFile.length()%1024)/10))+"KB";
            Map<String, Object> data = new HashMap<>();
            data.put("url", path);
            data.put("size",size);
            data.put("fileType",fileType);
            return ResultVO.success("上传成功",data);
        } catch (IOException e) {
            logger.error("上传学习资料失败->message:{}",e.getMessage());
            return ResultVO.error("上传失败",e.getMessage());
        }
    }

    /**
     * 上传用户头像
     * @param file 文件
     */
    @PostMapping("/avatar")
    @ApiOperation("上传用户头像")
    public ResultVO avatarUpload(@ApiParam("上传的文件") @RequestParam("file") MultipartFile file) {
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        String realPathDir = PropertiesUtil.getUploadUrl();
        String path ="avatar/" + fileName;
        try {
            File destFile = new File(realPathDir+path);
            destFile.getParentFile().mkdirs();
            file.transferTo(destFile);
            Map<String, Object> data = new HashMap<>();
            data.put("url","/upload/" + path);
            return ResultVO.success("上传成功",data);
        } catch (IOException e) {
            logger.error("上传用户头像失败->message:{}",e.getMessage());
            return ResultVO.error("上传失败",e.getMessage());
        }
    }

    /**
     * 上传宣传图片
     * @param file 文件
     */
    @PostMapping("/banner")
    @ApiOperation("上传宣传图片")
    public ResultVO bannerUpload(@ApiParam("上传的文件") @RequestParam("file") MultipartFile file) {
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        String realPathDir = PropertiesUtil.getUploadUrl();
        String path ="banner/" + fileName;
        try {
            File destFile = new File(realPathDir+path);
            destFile.getParentFile().mkdirs();
            file.transferTo(destFile);
            Map<String, Object> data = new HashMap<>();
            data.put("url","/upload/" + path);
            return ResultVO.success("上传成功",data);
        } catch (IOException e) {
            logger.error("上传宣传图片失败->message:{}",e.getMessage());
            return ResultVO.error("上传失败",e.getMessage());
        }
    }

    /**
     * 上传文章封面
     * @param file 文件
     */
    @PostMapping("/article")
    @ApiOperation("上传文章封面")
    public ResultVO articleUpload(@ApiParam("上传的文件") @RequestParam("file") MultipartFile file) {
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        String realPathDir = PropertiesUtil.getUploadUrl();
        String path ="article/" + fileName;
        try {
            File destFile = new File(realPathDir+path);
            destFile.getParentFile().mkdirs();
            file.transferTo(destFile);
            Map<String, Object> data = new HashMap<>();
            data.put("url","/upload/" + path);
            return ResultVO.success("上传成功",data);
        } catch (IOException e) {
            logger.error("上传文章封面失败->message:{}",e.getMessage());
            return ResultVO.error("上传失败",e.getMessage());
        }
    }
}
