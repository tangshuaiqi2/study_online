package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-02-09
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {
	@Autowired
	private UcenterMemberService memberService;

	@PostMapping("login")
	public R loginUser(@RequestBody UcenterMember member){
		String token = memberService.login(member);
		return R.ok().data("token", token);
	}

	@PostMapping("register")
	public R registerUser(@RequestBody RegisterVo registerVo){
		String message = memberService.register(registerVo);
		return R.ok().data("message", message);
	}

	@GetMapping("getMemberInfo")
	public R getMemberInfo(HttpServletRequest request){
		String jwtToken = JwtUtils.getMemberIdByJwtToken(request);
		UcenterMember byId = memberService.getById(jwtToken);
		return R.ok().data("userInfo", byId);
	}

	//查询某一天注册人数
	@GetMapping("countRegister/{day}")
	public R countRegister(@PathVariable String day) {
		Integer count = memberService.countRegisterDay(day);
		return R.ok().data("countRegister",count);
	}

}

