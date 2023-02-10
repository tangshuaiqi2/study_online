package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-02-09
 */
public interface UcenterMemberService extends IService<UcenterMember> {

	String login(UcenterMember member);

	String register(RegisterVo registerVo);
}
