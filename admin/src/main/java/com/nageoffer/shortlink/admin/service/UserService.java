package com.nageoffer.shortlink.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.admin.dao.entity.UserDO;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDTO;

/*
* 用户接口层
* */
public interface UserService extends IService<UserDO> {
    /*
    *根据用户名查询用户信息，并返回实体，UserRespDTO,返回实体类，而不是从持久层直接返回
    **/
    UserRespDTO getUserByUsername(String username);
}
