package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/eduservice/user")
@RestController
@CrossOrigin
public class EduLoginController {

	@PostMapping("login")
	public R login(){
		return R.ok().data("token","admin");
	}

	@GetMapping("info")
	public R info(){
		return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://cn.bing.com/images/search?view=detailV2&ccid=os1QlR4D&id=98B7954CBD0654EFD64016DFFC201D6BC6E5EA16&thid=OIP.os1QlR4D1Zls2CUpx26FuwAAAA&mediaurl=https%3a%2f%2ftupian.qqw21.com%2farticle%2fUploadPic%2f2020-9%2f2020920228286667.jpg&exph=450&expw=450&q=%e5%a4%b4%e5%83%8f&simid=607994243730914973&FORM=IRPRST&ck=B67838DAC4632F25F42733D1E9CAC018&selectedIndex=0&ajaxhist=0&ajaxserp=0");
	}
}
