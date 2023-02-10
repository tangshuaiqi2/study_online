package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.Date;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-02-09
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

	@Autowired
	private RedisTemplate<String,String> redisTemplate;


	@Override
	public String login(UcenterMember member){
		String mobile = member.getMobile();
		String password = member.getPassword();

		if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
			return "error";
		}



		QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
		wrapper.eq("mobile", mobile);

		UcenterMember ucenterMember = baseMapper.selectOne(wrapper);


		if(ucenterMember == null){
			return "error";
		}
		System.out.println(MD5.encrypt(password));
		if(!MD5.encrypt(password).equals(ucenterMember.getPassword())){
			return "error";
		}
		if(ucenterMember.getIsDeleted()){
			return "error";
		}

		String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
		return jwtToken;
	}

	@Override
	public String register(RegisterVo registerVo) {
		String code = registerVo.getCode();
		String mobile = registerVo.getMobile();
		String nickname = registerVo.getNickname();
		String password = registerVo.getPassword();

		if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
				|| StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)){
			return "error";
		}

		String redisCode = redisTemplate.opsForValue().get(mobile);

		//暂时的 TODO
		redisCode = "123";

		if(!code.equals(redisCode)){
			return "error";
		}

		QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
		wrapper.eq("mobile", mobile);

		Integer count = baseMapper.selectCount(wrapper);

		if(count > 0){
			return "有了";
		}
		UcenterMember ucenterMember = new UcenterMember();
		ucenterMember.setMobile(mobile);
		ucenterMember.setNickname(nickname);
		ucenterMember.setPassword(MD5.encrypt(password));
		ucenterMember.setIsDisabled(false);
		ucenterMember.setGmtCreate(new Date());
		ucenterMember.setGmtModified(new Date());
		ucenterMember.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
		baseMapper.insert(ucenterMember);
		return "true";
	}
}
