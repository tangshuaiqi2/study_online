package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {


	@Autowired
	private EduTeacherService eduTeacherService;

	@PostMapping("getTeacherFrontList/{page}/{limit}")
	public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit){
		Page<EduTeacher> pageTeacher = new Page<>(page, limit);

		Map<String, Object> map =  eduTeacherService.getTeacherFrontList(pageTeacher);
		return R.ok().data(map);
	}

}
