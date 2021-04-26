package com.tcu.library.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tcu.library.entity.User;
import com.tcu.library.service.UserService;
import com.tcu.library.uitls.ResultCode;
import com.tcu.library.uitls.ResultEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yjn
 * @since 2020-09-29
 */
@CrossOrigin
@RestController
@RequestMapping("/library/user")
@Api(description = "用户的api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;


//    @ApiOperation("添加用户")
//    @PostMapping("/addUser")
//    public ResultEntity addUser(@RequestBody User user){
//        User getUser = userService.getById(user.getBorrowNum());
//        if (getUser!=null){
//            return ResultEntity.error().code(ResultCode.ID_EXIST).message("借阅号已存在");
//        }
//        boolean save = userService.save(user);
//        if (save){
//            return ResultEntity.ok();
//        }else {
//            return ResultEntity.error();
//        }
//    }

    @Transactional
    @ApiOperation("添加用户")
    @GetMapping("/addUser/{appid}/{secret}/{jsCode}")
    public ResultEntity addWxUser(@ApiParam("小程序appId") @PathVariable("appid") String appid,@ApiParam("小程序appSecret")@PathVariable("secret") String secret,@ApiParam("登录时获取的code")@PathVariable("jsCode") String jsCode) throws URISyntaxException {
        //拼接url
        StringBuilder url = new StringBuilder("https://api.weixin.qq.com/sns/jscode2session?");
        url.append("appid=");//appid设置
        url.append(appid);
        url.append("&secret=");//secret设置
        url.append(secret);
        url.append("&js_code=");//code设置
        url.append(jsCode);
        url.append("&grant_type=authorization_code");
        Map<String, Object> map = new HashMap<>();
        try {
            HttpClient client = HttpClientBuilder.create().build();//构建一个Client
            HttpGet get = new HttpGet(url.toString());    //构建一个GET请求
            HttpResponse response = client.execute(get);//提交GET请求
            HttpEntity result = response.getEntity();//拿到返回的HttpResponse的"实体"
            String content = EntityUtils.toString(result);
            JSONObject res = JSONObject.parseObject(content);//把信息封装为json
            String openid = (String) res.get("openid");
            QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
            userQueryWrapper.eq("openid", openid);
            User one = userService.getOne(userQueryWrapper);
            if (one==null){
                User user = new User();
                user.setOpenid(openid);
                userService.save(user);
            }
            User one1 = userService.getOne(userQueryWrapper);
            //把信息封装到map
            map.put("openid", openid);
            map.put("borrowNum", one1.getBorrowNum());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultEntity.ok().data(map);
    }

    /**
     * 查询所有用户
     * @return 所有用户信息
     */
    @ApiOperation("查询所有用户")
    @GetMapping("/getUserList")
    public ResultEntity getUserList(){
        List<User> userList = userService.list();
        return ResultEntity.ok().data("userList",userList);
    }

    /**
     * 根据id删除用户
     * @param id 要删除的用户id
     * @return 删除的状态
     */
    @ApiOperation("根据借阅号删除用户")
    @GetMapping("/delete/by/{id}")
    public ResultEntity deleteById(@ApiParam("用户借阅号") @PathVariable String id){
        boolean isRemove = userService.removeById(id);
        if (isRemove){
            return ResultEntity.ok();
        }else {
            return ResultEntity.error().message("删除失败");
        }
    }

    /**
     * 修改用户信息
     * @param user 要修改的用户
     * @return 修改的状态
     */
    @ApiOperation("修改用户信息")
    @PostMapping("/update/user")
    public ResultEntity updateUser(@RequestBody User user){
        User updateUser = userService.getById(user.getBorrowNum());
        if (updateUser==null){
            return ResultEntity.error().message("该用户不存在");
        }
        boolean isUpdate = userService.updateById(user);
        if (isUpdate){
            return ResultEntity.ok();
        }else {
            return ResultEntity.error();
        }
    }

    /**
     * 根据openid查询用户
     * @param openId 用户的openid
     * @return 用户信息
     */
    @ApiOperation("根据openid查询用户")
    @GetMapping("/getUser/by/{openId}")
    public ResultEntity getUserByBorrowNum(@ApiParam("用户的openid")@PathVariable String openId){
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("openid", openId);
        User user = userService.getOne(wrapper);
        if(user==null){
            return ResultEntity.error().code(ResultCode.ID_NOT_EXIST).message("借阅号不存在");
        }
        return ResultEntity.ok().data("user",user);
    }
}

