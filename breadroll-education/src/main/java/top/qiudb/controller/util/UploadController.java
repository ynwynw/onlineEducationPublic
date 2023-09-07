package top.qiudb.controller.util;

import cn.dev33.satoken.stp.StpUtil;
import java.net.URLEncoder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.qiudb.pojo.resource.ResourceList;
import top.qiudb.pojo.user.User;
import top.qiudb.service.resource.ResourceListService;
import top.qiudb.service.user.UserService;
import top.qiudb.util.tools.PropertiesUtil;
import top.qiudb.util.tools.ResultVO;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/4/22 9:26
 * @description 上传文件
 */

@RestController
@CrossOrigin
@RequestMapping("/upload")
@Api(tags = "上传信息接口")
public class UploadController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;
    @Autowired
    ResourceListService resourceListService;

    /**
     * 上传视频课程
     * @param dirName  路径名
     * @param file     文件
     */
    @PostMapping("/course")
    @ApiOperation("上传课程视频")
    public ResultVO courseUpload(@ApiParam("课程目录名") String dirName, @ApiParam("上传的文件") @RequestParam("file") MultipartFile file) {
        long duration;
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        String realPathDir = PropertiesUtil.getUploadUrl();
        String path ="course/"+ dirName + "/" + fileName;
        System.out.println("课程路径:"+realPathDir+path);
        try {
            //4.第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下uploaded文件夹下）
            File destFile = new File(realPathDir+path);
            destFile.getParentFile().mkdirs();
            //5.把浏览器上传的文件复制到希望的位置
            file.transferTo(destFile);
            Encoder encoder = new Encoder();
            MultimediaInfo info = encoder.getInfo(destFile);
            duration = info.getDuration();
            Map<String, Object> data = new HashMap<>();
            data.put("url","/upload/" + path);
            data.put("length",duration / 60000);
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
        System.out.println("资料路径:"+realPathDir+path);
        try {
            File destFile = new File(realPathDir+path);
            destFile.getParentFile().mkdirs();
            file.transferTo(destFile);
            Map<String, Object> data = new HashMap<>();
            data.put("url","/upload/" + path);
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
        System.out.println("头像路径:"+realPathDir+path);
        try {
            File destFile = new File(realPathDir+path);
            destFile.getParentFile().mkdirs();
            file.transferTo(destFile);
            Map<String, Object> data = new HashMap<>();
            path = "/upload/" + path;
            data.put("url",path);
            Integer userId  = StpUtil.getLoginIdAsInt();
            User user = new User();
            user.setUserId(userId);
            user.setAvatarUrl(path);
            Boolean isSuccess = userService.updateUser(user);
            if(isSuccess){
                return ResultVO.success("上传成功",data);
            }
            return ResultVO.error("上传失败");
        } catch (IOException e) {
            logger.error("上传用户头像失败->message:{}",e.getMessage());
            return ResultVO.error("上传失败",e.getMessage());
        }
    }

    /**
     * 上传用户背景图
     * @param file 文件
     */
    @PostMapping("/background")
    @ApiOperation("上传用户背景图")
    public ResultVO backgroundUpload(@ApiParam("上传的文件") @RequestParam("file") MultipartFile file) {
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        String realPathDir = PropertiesUtil.getUploadUrl();
        String path ="background/" + fileName;
        try {
            File destFile = new File(realPathDir+path);
            destFile.getParentFile().mkdirs();
            file.transferTo(destFile);
            Map<String, Object> data = new HashMap<>();
            path = "/upload/" + path;
            data.put("url",path);
            Integer userId  = StpUtil.getLoginIdAsInt();
            User user = new User();
            user.setUserId(userId);
            user.setBackgroundUrl(path);
            Boolean isSuccess = userService.updateUser(user);
            if(isSuccess){
                return ResultVO.success("上传成功",data);
            }
            return ResultVO.error("上传失败");
        } catch (IOException e) {
            logger.error("上传用户背景图失败->message:{}",e.getMessage());
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
        System.out.println("图片路径:"+realPathDir+path);
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
     * 下载文件
     * @param resourceId 资源id
     */
    @GetMapping("/download")
    @ApiOperation("下载文件")
    public void downloadFile(HttpServletResponse response,Integer resourceId) throws UnsupportedEncodingException {
        if(resourceId==null){ return;}
        ResourceList resourceList = resourceListService.queryResourceById(resourceId);
        if (resourceList.getFileUrl() != null) {
            //设置文件路径
            File file = new File(PropertiesUtil.getAdminPath() + resourceList.getFileUrl());
            String fileName = resourceList.getResourceName()+resourceList.getFileType();
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", URLEncoder.encode(fileName, "utf-8")));// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {bis.close();} catch (IOException e) {e.printStackTrace();}
                    }
                    if (fis != null) {
                        try {fis.close();}catch(IOException e){e.printStackTrace();}
                    }
                }
            }
        }
    }
}
