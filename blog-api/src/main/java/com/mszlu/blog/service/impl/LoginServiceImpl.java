package com.mszlu.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.service.LoginService;
import com.mszlu.blog.service.SysUserService;
import com.mszlu.blog.util.JWTUtils;
import com.mszlu.blog.vo.ErrorCode;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.LoginParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Author Peekaboo
 * Date 2022/2/10 14:31
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    private static final String salt = "mszlu!@#";

    @Override
    public Result login(LoginParam loginParam) {
        /**
         * 1.检查参数是否合法
         * 2.根据用户名和密码取user表中查询 是否存在
         * */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        //利用DigestUtils.md5Hex直接对参数进行加密
        password = DigestUtils.md5Hex(password+salt);
        SysUser user = sysUserService.findUser(account,password);
        //3.如果不存在 登陆失败
        if (user == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        //4.如果存在 使用jwt根据用户主键id生成token 返回给前端
        String token = JWTUtils.createToken(user.getId());

        //5.token放入redis当中  redis  token：user信息 设置过期时间(静态，后期可以作为参数动态地传进来,或者范进application.yml配置文件中)
        //（登录认证的时候 先认证token字符串是否合法  去redis认证是否存在）
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(user),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    /**
     * 校验Token
     */
    @Override
    public SysUser checkToken(String token) {
        //1、检查是否为空字符串或str.length = 0
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null){
            return null;
        }
        //3、在Redis中根据Token获取UserJson对象
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;

    }

    /**
     * 退出登录
     * token字符串没法更改掉，只能由前端进行清除，后端能做的就是把redis进行清除
     * @param token
     */
    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }
}
