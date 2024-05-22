package com.nageoffer.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.common.convention.result.Results;
import com.nageoffer.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.nageoffer.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserActualRespDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDTO;
import com.nageoffer.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制层
 * 遵循 restful 开发风格规范
 */


@RestController
//(responseBody + Controller)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /*
    * 根据用户名来查询用户
    * */
    @GetMapping("/api/short-link/v1/user/{username}")
    public Result<UserRespDTO> getUserByName(@PathVariable("username") String username){
        return Results.success(userService.getUserByUsername(username));
    }

    @GetMapping("/api/short-link/v1/actual/user/{username}")
    public Result<UserActualRespDTO> getActualUserByName(@PathVariable("username") String username){
        return Results.success(BeanUtil.toBean(userService.getUserByUsername(username), UserActualRespDTO.class));
    }

    /**
     * 查询用户名是否存在, hasUsername方法返回值类型为boolean, 所以在results中 T data的类型也会变为boolean 返回前端
     */
    @GetMapping("/api/short-link/v1/user/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username){
        return Results.success(userService.hasUsername(username));
    }

    /**
     * 注册用户
     * @param requestParam
     * @return
     */
    @PostMapping("/api/short-link/v1/user")
    // Result 中的返回值，表示返回给前端的 data的类型
    public Result<Void> register(@RequestBody UserRegisterReqDTO requestParam){
        userService.register(requestParam);
        return Results.success();
    }
}
