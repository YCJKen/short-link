package com.nageoffer.shortlink.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.admin.dao.entity.UserDO;
import com.nageoffer.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDTO;

/*
* 用户接口层
* */
public interface UserService extends IService<UserDO> {
    /*
    *根据用户名查询用户信息，并返回实体，UserRespDTO,返回实体类，而不是从持久层直接返回
    **/
    UserRespDTO getUserByUsername(String username);

    /**
     * 查询用户名是否存在
     * @param username 用户名
     * @return 存在返回 True, 不存在返回False
     */
    Boolean hasUsername(String username);

    /**
     * @param requestParam 注册用户请求参数
     */
    void register(UserRegisterReqDTO requestParam);
}
