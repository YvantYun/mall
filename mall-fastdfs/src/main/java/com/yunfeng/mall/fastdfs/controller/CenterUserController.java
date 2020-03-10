package com.yunfeng.mall.fastdfs.controller;

import com.yunfeng.mall.fastdfs.resource.FileResource;
import com.yunfeng.mall.fastdfs.service.FdfsService;
import com.yunfeng.pojo.Users;
import com.yunfeng.pojo.vo.UsersVO;
import com.yunfeng.service.center.CenterUserService;
import com.yunfeng.utils.CookieUtils;
import com.yunfeng.utils.DateUtil;
import com.yunfeng.utils.IMOOCJSONResult;
import com.yunfeng.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("fdfs")
public class CenterUserController extends BaseController{

    @Autowired
    private FdfsService fdfsService;

    @Autowired
    private FileResource fileResource;

    @Autowired
    private CenterUserService centerUserService;

    @PostMapping("/uploadFace")
    public IMOOCJSONResult uploadFace(
            String userId,
            MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        String path = "";

        // 开始文件上传
        if (file != null) {
            // 获得文件上传的文件名称
            String fileName = file.getOriginalFilename();

            if (StringUtils.isNotBlank(fileName)) {

                // 文件重命名  imooc-face.png -> ["imooc-face", "png"]
                String fileNameArr[] = fileName.split("\\.");

                // 获取文件的后缀名
                String suffix = fileNameArr[fileNameArr.length - 1];

                if (!suffix.equalsIgnoreCase("png") &&
                        !suffix.equalsIgnoreCase("jpg") &&
                        !suffix.equalsIgnoreCase("jpeg")) {
                    return IMOOCJSONResult.errorMsg("图片格式不正确！");
                }

                path = fdfsService.upload(file, suffix);

            } else {
                return IMOOCJSONResult.errorMsg("文件不能为空！");
            }

        }

        if(StringUtils.isNotBlank(path)) {

            String finalUserFaceUrl = fileResource.getHost() + path;

            Users userResult = centerUserService.updateUserFace(userId, finalUserFaceUrl);
            UsersVO usersVO = convertUsersVO(userResult);
            CookieUtils.setCookie(request, response, "user",
                    JsonUtils.objectToJson(usersVO), true);

        }else {
            return IMOOCJSONResult.errorMsg("上传头像失败！");
        }
        return IMOOCJSONResult.ok();


    }


}

