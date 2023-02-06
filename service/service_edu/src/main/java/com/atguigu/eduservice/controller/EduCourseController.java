package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-02-05
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

	@Autowired
	private EduCourseService courseService;


	@GetMapping("{current}/{limit}")
	public R getCourseList(@PathVariable("current") long current, @PathVariable("limit") long limit){
		Page<EduCourse> eduCourseIPage = new Page<>(current,limit);

		IPage<EduCourse> page = courseService.page(eduCourseIPage, null);
		List<EduCourse> list = page.getRecords();
		return R.ok().data("list", list).data("total",page.getTotal());
	}
//	@GetMapping()
//	public R getCourseList(@PathVariable(){
//
//		List<EduCourse> list = courseService.list(null);
//		return R.ok().data("list", list);
//	}

	@PostMapping("addCourseInfo")
	public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
		R r = courseService.saveCourseInfo(courseInfoVo);
		return r;
	}

	@GetMapping("getCourseInfo/{courseId}")
	public R getCourseInfo(@PathVariable String courseId){
		CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
		return R.ok().data("courseInfoVo",courseInfoVo);
	}

	@PostMapping("updateCourseInfo")
	public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
		courseService.updateCourseInfo(courseInfoVo);
		return R.ok();
	}

	@GetMapping("getPublishCourseInfo/{id}")
	public R getPublishCourseInfo(@PathVariable String id){
		CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
		return R.ok().data("publishCourse", coursePublishVo);
	}

	@PostMapping("publishCourse/{id}")
	public R publishCourse(@PathVariable String id){
		EduCourse eduCourse = new EduCourse();
		eduCourse.setId(id);
		eduCourse.setStatus("Normal");
		courseService.updateById(eduCourse);
		return R.ok();
	}

	@DeleteMapping("{courseId}")
	public R deleteCourse(@PathVariable String courseId){
		courseService.removeCourse(courseId);
		
		return R.ok();
	}
}

