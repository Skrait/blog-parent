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
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Author Peekaboo
 * Date 2022/2/10 14:31
 * 登录和注册逻辑
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Resource
    private SysUserService sysUserService;


    @Resource
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 密码盐
     * 只要密码盐不被泄露理论上是安全的
     */
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
        //利用DigestUtils.md5Hex直接对参数进行加密,因为数据库存的是加密密码。
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
     * 常用方法_校验Token并返回User对象信息
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

    /**
     * 注册
     * @param loginParam
     * @return
     */
    @Override
    public Result register(LoginParam loginParam) {

        //1、判断参数是否合法
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)
        ){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        //2、判断账户是否存在，若存在，则返回账户已注册
        SysUser sysUser =  sysUserService.findUserByAcount(account);
        if (sysUser != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), "账号已存在");
        }
        //如果账户不存在，则注册用户，生成Token，存入redis并返回
        //注意加上事务，一旦中间出现任何问题，注册的用户数据，需要回滚，保证事务的一致性
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+salt));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        this.sysUserService.save(sysUser);
        
        //生成Token并注册
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token,JSON.toJSONString(sysUser),1,TimeUnit.DAYS);
        return Result.success(token);
    }
}
