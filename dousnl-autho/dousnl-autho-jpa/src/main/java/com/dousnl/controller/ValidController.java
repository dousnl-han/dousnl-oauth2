package com.dousnl.controller;

import com.alibaba.fastjson.JSON;
import com.dousnl.domain.AdviceCanel;
import com.dousnl.domain.EsbPush;
import com.dousnl.domain.TUserEntity;
import com.dousnl.repository.TUserRepository;
import com.dousnl.utils.enums.ErrorEnums;
import com.dousnl.utils.exception.MyException;
import com.dousnl.utils.response.Resp;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * 模板控制器
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/10/23 15:11
 */
@Slf4j
@RestController
@RequestMapping("/valid")
public class ValidController {

    @Autowired
    private TUserRepository tUserRepository;
    /**
     * 车主取消订单接口
     * @return
     */
    @ApiOperation(value = "车主取消订单接口", notes = "车主取消订单接口")
    @PostMapping(value = "/cancel")
    public Resp tmsPush(@Valid @Validated @RequestBody AdviceCanel canel, BindingResult bindingResult) {
        Resp resp = Resp.success();
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            return Resp.failed(fieldError.getField() + ":" + fieldError.getDefaultMessage());
        }
//        try{
//            //JsonMsgBean msgBean = _adviceService.carOwnerCancelAdviceNo(canel);
//            if (!msgBean.getSuccess()) {
//                return Resp.failed((String) msgBean.getData());
//            }
//        } catch (PushException e) {
//            logger.error(e.getMessage(), e);
//            return Resp.failed(e.getMessage());
//        } catch (Exception e) {
//            logger.error("维修订单终止失败...", e);
//            return Resp.failed("维修订单终止失败...");
//        }
        return resp;
    }

    /**
     * 用户信息接口
     * @return
     */
    @ApiOperation(value = "用户信息接口", notes = "用户信息接口")
    @GetMapping(value = "/{id}")
    public Resp getUser(@PathVariable("id") Integer id) {
        Resp resp=Resp.success();
        TUserEntity byId = tUserRepository.findById(id);
        TUserEntity byId1 = tUserRepository.findById(id);
        TUserEntity byId2 = tUserRepository.findById(id);
        TUserEntity byId3 = tUserRepository.findById(id);
        tUserRepository.findById(id);
        tUserRepository.findById(id);
        resp.setData(byId);
        return resp;
    }
    /**
     * 车主取消订单接口
     * @return
     */
    @ApiOperation(value = "车主取消订单接口", notes = "车主取消订单接口")
    @GetMapping(value = "/update/{roleId}")
    @Transactional
    public void updateUser(@PathVariable("roleId") Integer roleId) {
        TUserEntity u=new TUserEntity(1,roleId);
        tUserRepository.updateUser(u);
        //int i=1/0;
    }
    /**
     * 车主取消订单接口
     * @return
     */
    @ApiOperation(value = "车主取消订单接口", notes = "车主取消订单接口")
    @GetMapping(value = "/id/{id}")
    public TUserEntity getUserId(@PathVariable("id") Integer id) {
        return tUserRepository.findTUserEntityId(id);
    }
    /**
     * 车主取消订单接口
     * @return
     */
    @ApiOperation(value = "全局接口", notes = "全局接口")
    @GetMapping(value = "/error")
    public Resp updateUser() {
        try{
            int i=1/0;
        }catch (Exception e){
            throw new MyException(ErrorEnums.LOGIN_TOKEN_CREATE_ERROR);
        }
        return Resp.success();
    }
    /**
     * 车主取消订单接口
     * @return
     */
    @ApiOperation(value = "全局接口", notes = "全局接口")
    @GetMapping(value = "/error2")
    public Resp updateUser2() {
        int i=1/0;
        return Resp.success();
    }
    /**
     * 自定义异常message接口，默认失败code=1
     * @return
     */
    @ApiOperation(value = "自定义异常message接口", notes = "自定义异常message接口")
    @GetMapping(value = "/error3/{id}")
    public Resp updateUser3(@PathVariable("id") Integer id) {
        try{
            tUserRepository.findByRoleId(id);
        }catch (Exception e){
            log.error("维修订单终止失败..." + e.getMessage(), e);
            return Resp.failed("维修订单终止失败...");
        }
        return Resp.success();
    }
    /**
     * 车主取消订单接口
     * @return
     */
    @ApiOperation(value = "全局接口", notes = "全局接口",response = TUserEntity.class)
    @GetMapping(value = "/error4/{id}")
    public Resp updateUser4(@PathVariable("id") Integer id) {
        Resp resp = Resp.success();
        resp.setData(tUserRepository.findByRoleId(id));
        return resp;
    }

    /**
     * 车主取消订单接口
     * @return
     */
    @ApiOperation(value = "全局接口", notes = "全局接口",response = TUserEntity.class)
    @PostMapping(value = "/push")
    public Resp updatePush(@RequestBody EsbPush push) {
        Resp resp = Resp.success();
        log.info(">>>>>>>>>>>esb push:{}", JSON.toJSONString(push));
        return resp;
    }

    /**
     * 车主取消订单接口
     * @return
     */
    @ApiOperation(value = "车主取消订单接口", notes = "车主取消订单接口")
    @GetMapping(value = "/ids")
    public List<TUserEntity> getUserId() {
        List<Integer> list= Lists.newArrayList();
        list.add(1);list.add(2);list.add(3);
        List<Object[]> tUserByRoleId = tUserRepository.findTUserByRoleId(list);
        List<TUserEntity> entities=Lists.newArrayList();
        for (Object[] o:tUserByRoleId){
            TUserEntity entity=new TUserEntity();
            entity.setId((Integer) o[0]);
            entity.setUsername((String) o[1]);
            entities.add(entity);
        }
        System.out.println(JSON.toJSONString(entities));
        return entities;
    }
}
