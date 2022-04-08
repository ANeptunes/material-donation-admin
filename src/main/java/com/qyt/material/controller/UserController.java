package com.qyt.material.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.qyt.material.api.Result;
import com.qyt.material.api.ResultCode;
import com.qyt.material.dto.UpdatePasswordDto;
import com.qyt.material.dto.UserLoginDto;
import com.qyt.material.dto.UserRegisterDto;
import com.qyt.material.dto.VerifyByEmailFormDto;
import com.qyt.material.pojo.User;
import com.qyt.material.service.UserService;
import com.qyt.material.util.MD5Util;
import com.qyt.material.util.MailServiceUtil;
import com.qyt.material.util.RedisUtil;
import com.qyt.material.vo.UserBaseInfoVo;
import com.qyt.material.vo.UserInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private MailServiceUtil mailServiceUtil;

    @Resource
    private MD5Util md5Util;

    /**
     * 登录
     *
     * @param userLoginDto 用户登录提交的数据
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDto userLoginDto) {
        User user = userService.getUserByName(userLoginDto.getUsername());
        JSONObject data = new JSONObject();
        if (user == null) {
            return Result.failed(ResultCode.NOTFOUND, "用户名不存在");
        } else {
            String token = userService.login(userLoginDto.getUsername(), userLoginDto.getPassword());
            if (token != null) {
                // 设置redis中的token有效期为两小时
                redisUtil.set(userLoginDto.getUsername(), token, 2 * 60 * 60);
                data.put("type", user.getType());
                data.put("token", token);
                return Result.success(ResultCode.SUCCESS, "登录成功", data);
            } else {
                return Result.failed(ResultCode.FORBIDDEN, "密码错误");
            }
        }
    }

    /**
     * 注册
     *
     * @param userRegisterDto 用户注册提交的数据
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterDto userRegisterDto) {
        User user = userService.getUserByName(userRegisterDto.getUsername());
        if (user == null) {
            int count = userService.register(userRegisterDto);
            return Result.success(ResultCode.SUCCESS, "注册成功");
        } else {
            return Result.failed(ResultCode.FAILED, "用户名已存在");
        }
    }

    /**
     * 获取当前用户信息
     *
     * @param username 根据用户名查询
     */
    @GetMapping("/info")
    public Result<UserInfoVo> getUserInfo(@RequestParam("username") String username) {
        User user = userService.getUserInfo(username);
        if (user != null) {
            UserInfoVo userInfo = new UserInfoVo();
            BeanUtils.copyProperties(user, userInfo);
            System.out.println(user);
            System.out.println(userInfo);
            return Result.success(ResultCode.SUCCESS, userInfo);
        } else {
            return Result.failed(ResultCode.NOTFOUND, "该用户不存在");
        }
    }

    /**
     * 获取地址信息
     *
     * @return 返回省市区地址信息
     */
    @GetMapping("/address")
    public Result getAddressInfo() {
        File file;
        StringBuilder sb = new StringBuilder();
        try {
            file = ResourceUtils.getFile("classpath:static/json/districts.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String readLine;
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String addressJsonStr = sb.toString();
        JSONObject addressObject = JSONObject.parseObject(addressJsonStr, Feature.OrderedField);
        // 从json数据中获取省份对象
        JSONObject provinceObj = addressObject.getJSONObject("100000");
        // 遍历省份对象
        Iterator provinceIter = provinceObj.entrySet().iterator();
        JSONArray addressArr = new JSONArray();
        while (provinceIter.hasNext()) {
            // 存储省份的对象
            JSONObject provinceAddrView = new JSONObject(true);
            Map.Entry provinceEntry = (Map.Entry) provinceIter.next();
            // 获取省份的键值
            String provinceKey = provinceEntry.getKey().toString();
            String provinceValue = provinceEntry.getValue().toString();
            // 根据省份获取市的对象
            JSONObject cityObj = addressObject.getJSONObject(provinceKey);
            if (ObjectUtils.isEmpty(cityObj)) {
                provinceAddrView.put("value", provinceKey);
                provinceAddrView.put("label", provinceValue);
                addressArr.add(provinceAddrView);
                continue;
            }
            // 遍历市对象
            Iterator cityIter = cityObj.entrySet().iterator();
            JSONArray cityArr = new JSONArray();
            while (cityIter.hasNext()) {
                // 存储市的对象
                JSONObject cityAddrView = new JSONObject(true);
                Map.Entry cityEntry = (Map.Entry) cityIter.next();
                // 获取市的键值
                String cityKey = cityEntry.getKey().toString();
                String cityValue = cityEntry.getValue().toString();
                // 根据市获取区的对象
                JSONObject areaObj = addressObject.getJSONObject(cityKey);
                if (ObjectUtils.isEmpty(areaObj)) {
                    cityAddrView.put("value", cityKey);
                    cityAddrView.put("label", cityValue);
                    cityArr.add(cityAddrView);
                    continue;
                }
                // 遍历区对象
                Iterator areaIter = areaObj.entrySet().iterator();
                JSONArray areaArr = new JSONArray();
                while (areaIter.hasNext()) {
                    // 存储区的对象
                    JSONObject areaAddrView = new JSONObject(true);
                    Map.Entry areaEntry = (Map.Entry) areaIter.next();
                    // 获取区的键值
                    String areaKey = areaEntry.getKey().toString();
                    String areaValue = areaEntry.getValue().toString();
                    areaAddrView.put("value", areaKey);
                    areaAddrView.put("label", areaValue);
                    areaArr.add(areaAddrView);
                }
                cityAddrView.put("value", cityKey);
                cityAddrView.put("label", cityValue);
                cityAddrView.put("children", areaArr);
                cityArr.add(cityAddrView);
            }
            provinceAddrView.put("value", provinceKey);
            provinceAddrView.put("label", provinceValue);
            provinceAddrView.put("children", cityArr);
            addressArr.add(provinceAddrView);
        }
        return Result.success(ResultCode.SUCCESS, "查询成功", addressArr);
    }

    /**
     * 获取个人中心用户基本信息
     *
     * @param uid 根据用户名查询
     */
    @GetMapping("/baseInfo")
    public Result<UserBaseInfoVo> getUserBaseInfo(@RequestParam("uid") String uid) {
        User user = userService.getUserBaseInfo(uid);
        System.out.println(user);
        if (user != null) {
            UserBaseInfoVo userBaseInfo = new UserBaseInfoVo();
            BeanUtils.copyProperties(user, userBaseInfo);
            return Result.success(ResultCode.SUCCESS, "查询成功", userBaseInfo);
        } else {
            return Result.failed(ResultCode.NOTFOUND, "该用户不存在");
        }
    }

    /**
     * 修改个人基本信息
     *
     * @param userBaseInfoVo 用户提交的信息
     * @return 返回修改结果
     */
    @PostMapping("/saveUserBaseInfo")
    public Result update(@RequestBody UserBaseInfoVo userBaseInfoVo) {
        System.out.println(userBaseInfoVo);
        if (userBaseInfoVo.getBirthday() == null) {
            userBaseInfoVo.setBirthday("");
        }
        int count = userService.update(userBaseInfoVo);
        if (count != 0) {
            return Result.success(ResultCode.SUCCESS, "信息修改成功");
        } else {
            return Result.failed(ResultCode.FAILED, "信息修改失败");
        }
    }

    /**
     * 发送验证码（修改密码）
     *
     * @param email
     * @return
     */
    @GetMapping("/sendVerifyCodeByEmail")
    public Result sendVerifyCodeByEmail(@RequestParam("email") String email) {
        String subject = "[物资捐赠系统] 统一身份认证-密码修改";
        // 生成验证码
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int verifyCode = (int) ((random.nextDouble() * 9 + 1) * 100000);
        String content = "【物资捐赠系统】您的验证码为 <span style='color:blue'>" + verifyCode + "</span>，此验证码5分钟内有效。本次操作用于尝试修改登录密码，请勿泄漏或转发他人。";
        System.out.println(content);
        String verifyCodeCache = (String) redisUtil.get(email + "_UPDATE_PASS");
        System.out.println(verifyCodeCache);
        if (StrUtil.isNotBlank(verifyCodeCache)) {
            long expireTime = redisUtil.getExpire(email + "_UPDATE_PASS");
            // 没有超过一分钟（不予许发送）
            long criticalValue = 240;
            if (expireTime < criticalValue) {
                boolean sendVerifyCodeFlag = mailServiceUtil.sendMimeMessge(email, subject, content);
                if (sendVerifyCodeFlag) {
                    redisUtil.set(email + "_UPDATE_PASS", String.valueOf(verifyCode), 60 * 5);
                    return Result.success(ResultCode.SUCCESS, "发送成功");
                }
            } else {
                return Result.failed(ResultCode.WARRING, "发送频繁，请稍后再试");
            }
        } else {
            boolean sendVerifyCodeFlag = mailServiceUtil.sendMimeMessge(email, subject, content);
            if (sendVerifyCodeFlag) {
                redisUtil.set(email + "_UPDATE_PASS", String.valueOf(verifyCode), 60 * 5);
                return Result.success(ResultCode.SUCCESS, "发送成功");
            }
        }
        return Result.failed(ResultCode.FAILED, "发送失败,请确认邮箱地址");
    }

    /**
     * 发送验证码（绑定邮箱）
     *
     * @param email
     * @return
     */
    @GetMapping("/sendVerifyCodeByBindEmail")
    public Result sendVerifyCodeByBindEmail(@RequestParam("email") String email) {
        String subject = "[物资捐赠系统] 统一身份认证-邮箱验证";
        // 生成验证码
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int verifyCode = (int) ((random.nextDouble() * 9 + 1) * 100000);
        String content = "【物资捐赠系统】您的验证码为 <span style='color:blue'>" + verifyCode + "</span>，此验证码5分钟内有效。本次操作用于尝试修改登录密码，请勿泄漏或转发他人。";
        System.out.println(content);
        String verifyCodeCache = (String) redisUtil.get(email + "_BIND_EMAIL");
        System.out.println(verifyCodeCache);
        if (StrUtil.isNotBlank(verifyCodeCache)) {
            long expireTime = redisUtil.getExpire(email + "_BIND_EMAIL");
            // 没有超过一分钟（不予许发送）
            long criticalValue = 240;
            if (expireTime < criticalValue) {
                boolean sendVerifyCodeFlag = mailServiceUtil.sendMimeMessge(email, subject, content);
                if (sendVerifyCodeFlag) {
                    redisUtil.set(email + "_BIND_EMAIL", String.valueOf(verifyCode), 60 * 5);
                    return Result.success(ResultCode.SUCCESS, "发送成功");
                }
            } else {
                return Result.failed(ResultCode.WARRING, "发送频繁，请稍后再试");
            }
        } else {
            boolean sendVerifyCodeFlag = mailServiceUtil.sendMimeMessge(email, subject, content);
            if (sendVerifyCodeFlag) {
                redisUtil.set(email + "_BIND_EMAIL", String.valueOf(verifyCode), 60 * 5);
                return Result.success(ResultCode.SUCCESS, "发送成功");
            }
        }
        return Result.failed(ResultCode.FAILED, "发送失败,请确认邮箱地址");
    }

    /**
     * 发送验证码（绑定邮箱）
     *
     * @param email
     * @return
     */
    @GetMapping("/sendVerifyCodeByBindNewEmail")
    public Result sendVerifyCodeByBindNewEmail(@RequestParam("email") String email) {
        String subject = "[物资捐赠系统] 统一身份认证-绑定新邮箱";
        // 生成验证码
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int verifyCode = (int) ((random.nextDouble() * 9 + 1) * 100000);
        String content = "【物资捐赠系统】您的校验码为 <span style='color:blue'>" + verifyCode + "</span>，此校验码5分钟内有效。本次操作用于尝试修改绑定邮箱，请勿泄漏或转发他人。";
        System.out.println(content);
        String verifyCodeCache = (String) redisUtil.get(email + "_BIND_NEW_EMAIL");
        System.out.println(verifyCodeCache);
        if (StrUtil.isNotBlank(verifyCodeCache)) {
            long expireTime = redisUtil.getExpire(email + "_BIND_NEW_EMAIL");
            // 没有超过一分钟（不予许发送）
            long criticalValue = 240;
            if (expireTime < criticalValue) {
                boolean sendVerifyCodeFlag = mailServiceUtil.sendMimeMessge(email, subject, content);
                if (sendVerifyCodeFlag) {
                    redisUtil.set(email + "_BIND_NEW_EMAIL", String.valueOf(verifyCode), 60 * 5);
                    return Result.success(ResultCode.SUCCESS, "发送成功");
                }
            } else {
                return Result.failed(ResultCode.WARRING, "发送频繁，请稍后再试");
            }
        } else {
            boolean sendVerifyCodeFlag = mailServiceUtil.sendMimeMessge(email, subject, content);
            if (sendVerifyCodeFlag) {
                redisUtil.set(email + "_BIND_NEW_EMAIL", String.valueOf(verifyCode), 60 * 5);
                return Result.success(ResultCode.SUCCESS, "发送成功");
            }
        }
        return Result.failed(ResultCode.FAILED, "发送失败,请确认邮箱地址");
    }

    /**
     * 邮箱验证身份（修改密码）
     *
     * @param verifyByEmailForm
     * @return
     */
    @PostMapping("/verifyAccountByEmail")
    public Result verifyAccountByEmail(@RequestBody VerifyByEmailFormDto verifyByEmailForm) {
        String email = verifyByEmailForm.getEmail();
        String verifyCode = verifyByEmailForm.getVerifyCode();
        String verifyCodeCach = (String) redisUtil.get(email + "_UPDATE_PASS");
        if (verifyCode.equals(verifyCodeCach)) {
            redisUtil.delete(email + "_UPDATE_PASS");
            return Result.success(ResultCode.SUCCESS, "身份验证成功");
        }
        return Result.failed(ResultCode.FAILED, "身份验证失败");
    }

    /**
     * 验证身份（绑定邮箱）
     *
     * @param verifyByEmailForm
     * @return
     */
    @PostMapping("/verifyAccountByBindEmail")
    public Result verifyAccountByBindEmail(@RequestBody VerifyByEmailFormDto verifyByEmailForm) {
        String email = verifyByEmailForm.getEmail();
        String verifyCode = verifyByEmailForm.getVerifyCode();
        String verifyCodeCach = (String) redisUtil.get(email + "_BIND_EMAIL");
        if (StrUtil.isNotBlank(verifyCode) && verifyCode.equals(verifyCodeCach)) {
            redisUtil.delete(email + "_BIND_EMAIL");
            return Result.success(ResultCode.SUCCESS, "身份验证成功");
        } else {
            return Result.failed(ResultCode.FAILED, "验证码不存在或已失效，请重新获取");
        }
    }

    /**
     * 验证身份（绑定邮箱）
     *
     * @param verifyByEmailForm
     * @return
     */
    @PostMapping("/verifyAccountByBindNewEmail")
    public Result verifyAccountByBindNewEmail(@RequestBody VerifyByEmailFormDto verifyByEmailForm) {
        String email = verifyByEmailForm.getEmail();
        String verifyCode = verifyByEmailForm.getVerifyCode();
        String verifyCodeCach = (String) redisUtil.get(email + "_BIND_NEW_EMAIL");
        if (StrUtil.isNotBlank(verifyCode) && verifyCode.equals(verifyCodeCach)) {
            redisUtil.delete(email + "_BIND_NEW_EMAIL");
            return Result.success(ResultCode.SUCCESS, "身份验证成功");
        }
        return Result.failed(ResultCode.FAILED, "验证码不存在或已失效，请重新获取");
    }

    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        String password = md5Util.md5Encryption(updatePasswordDto.getPassword());
        String checkPassword = md5Util.md5Encryption(updatePasswordDto.getCheckPassword());
        String uid = updatePasswordDto.getUid();
        if (!password.equals(checkPassword)) {
            return Result.failed(ResultCode.FAILED, "密码不一致");
        }
        int count = userService.updatePassword(password, uid);
        if (count > 0) {
            return Result.success(ResultCode.SUCCESS, "修改密码成功");
        }
        return Result.failed(ResultCode.FAILED, "修改密码失败");
    }

    /**
     * 修改邮箱
     *
     * @param verifyByEmailFormDto
     * @return
     */
    @PostMapping("/updateBindEmail")
    public Result updateBindEmail(@RequestBody VerifyByEmailFormDto verifyByEmailFormDto) {
        String email = verifyByEmailFormDto.getEmail();
        String verifyCode = verifyByEmailFormDto.getVerifyCode();
        String uid = verifyByEmailFormDto.getUid();
        String verifyCodeCach = (String) redisUtil.get(email + "_BIND_NEW_EMAIL");
        if (StrUtil.isNotBlank(verifyCode) && verifyCode.equals(verifyCodeCach)) {
            int count = userService.updateBindEmail(email, uid);
            if (count > 0) {
                redisUtil.delete(email + "_BIND_NEW_EMAIL");
                return Result.success(ResultCode.SUCCESS, "修改邮箱成功");
            }
        } else {
            return Result.failed(ResultCode.FAILED, "验证码不存在或已失效，请重新获取");
        }
        return Result.failed(ResultCode.FAILED, "修改失败");
    }

    /**
     * 修改邮箱
     *
     * @param params
     * @return
     */
    @PostMapping("/cancelAccount")
    public Result cancelAccount(@RequestBody() JSONObject params) {
        String uid = params.getString("uid");
        int count = userService.cancelAccount(uid);
        System.out.println(count);
        if (count > 0) {
            return Result.success(ResultCode.SUCCESS, "注销成功");
        }
        return Result.failed(ResultCode.FAILED, "注销失败");
    }
}
