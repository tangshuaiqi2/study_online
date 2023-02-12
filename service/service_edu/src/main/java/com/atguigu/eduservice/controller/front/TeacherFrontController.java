package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {


	@Autowired
	private EduTeacherService eduTeacherService;

	@Autowired
	private EduCourseService courseService;

	@PostMapping("getTeacherFrontList/{page}/{limit}")
	public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit){
		Page<EduTeacher> pageTeacher = new Page<>(page, limit);

		Map<String, Object> map =  eduTeacherService.getTeacherFrontList(pageTeacher);
		return R.ok().data(map);
	}

	@GetMapping("getTeacherFrontInfo/{teacherId}")
	public R getTeacherFrontInfo(@PathVariable String teacherId){
		EduTeacher eduTeacher = eduTeacherService.getById(teacherId);

		QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

		wrapper.eq("teacher_id", teacherId);

		List<EduCourse> eduList = courseService.list(wrapper);

		return R.ok().data("teacher", eduTeacher).data("courseList", eduList);

	}

}
