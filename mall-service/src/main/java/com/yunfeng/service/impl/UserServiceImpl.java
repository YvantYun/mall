package com.yunfeng.service.impl;

import com.yunfeng.enums.Sex;
import com.yunfeng.mapper.UsersMapper;
import com.yunfeng.pojo.Users;
import com.yunfeng.pojo.bo.UserBO;
import com.yunfeng.service.UserService;
import com.yunfeng.utils.DateUtil;
import com.yunfeng.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author yunfeng
 * @since 2019-11-14
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {

        Example userExample = new Example(Users.class);
        userExample.createCriteria().andEqualTo("username", username);
        Users result = usersMapper.selectOneByExample(userExample);
        return result != null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users createUser(UserBO userBO) {
        String userId = sid.nextShort();

        Users user = new Users();
        user.setId(userId);
        user.setUsername(userBO.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 默认昵称同用户名
        user.setNickname(userBO.getUsername());
        // 默认头像
        user.setFace(USER_FACE);
        // 默认生日
        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        // 默认性别为保密
        user.setSex(Sex.SECRET.type);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        usersMapper.insert(user);
        return user;

    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {


        //Example userExample = new Example(Users.class);
        //        //Example.Criteria userCriteria = userExample.createCriteria();
        //        //
        //        //userCriteria.andEqualTo("username", username);
        //        //userCriteria.andEqualTo("password", password);

        Example userExample = Example.builder(Users.class).where(
                WeekendSqls.<Users>custom()
                        .andEqualTo(Users::getUsername,username)
                        .andEqualTo(Users::getPassword, password)

        ).build();



        Users result = usersMapper.selectOneByExample(userExample);

        return result;
    }
}
