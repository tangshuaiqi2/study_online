package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-01-29
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

	@Autowired
	private EduTeacherService teacherService;

	@GetMapping("/findAll")
	public R findAllTeacher(){
		QueryWrapper<EduTeacher> Wrapper = new QueryWrapper<>();
		Wrapper.orderByAsc("gmt_create");
		List<EduTeacher> list = teacherService.list(Wrapper);
		return R.ok().data("items",list);
	}

	@DeleteMapping ("{id}")
	public R removeTeacher(@PathVariable String id){
		boolean b = teacherService.removeById(id);
		return R.ok().data("flag",b);
	}

	@GetMapping("pageTeacher/{current}/{limit}")
	public R pageListTeacher(@PathVariable("current") long current, @PathVariable("limit") long limit){
		Page<EduTeacher> pageTeacher = new Page<>(current,limit);

		teacherService.page(pageTeacher,null);

		long total = pageTeacher.getTotal();
		List<EduTeacher> records = pageTeacher.getRecords();

		Map map = new HashMap();
		map.put("total", total);
		map.put("rows", records);
		return R.ok().data("map",map);
	}

	@PostMapping("pageTeacherCondition/{current}/{limit}")
	public R pageTeacherCondition(@PathVariable("current") long current, @PathVariable("limit") long limit, @RequestBody TeacherQuery teacherQuery){
		Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);

		QueryWrapper<EduTeacher> eduTeacherQueryWrapper = new QueryWrapper<>();
		System.out.println(teacherQuery.toString());
		if(teacherQuery.getName() != null && teacherQuery.getName() != ""){
			eduTeacherQueryWrapper.like("name", "%" + teacherQuery.getName() + "%");
		}

		eduTeacherQueryWrapper.orderByDesc("gmt_create");
		teacherService.page(eduTeacherPage,eduTeacherQueryWrapper);

		long total = eduTeacherPage.getTotal();
		List<EduTeacher> records = eduTeacherPage.getRecords();


		Map map = new HashMap();
		map.put("total", total);
		map.put("records", records);
		return R.ok().data("rows", records).data("total", total);
	}

	@PostMapping("addTeacher")
	public R addTeacher(@RequestBody EduTeacher eduTeacher){
		System.out.println(eduTeacher.toString());
		eduTeacher.setGmtCreate(new Date());
		eduTeacher.setGmtModified(new Date());
		boolean save = teacherService.save(eduTeacher);
		if(save){
			return R.ok();
		}
		return R.error();
	}

	@GetMapping("getTeacher/{id}")
	public R getTeacher(@PathVariable String id)
	{
		EduTeacher eduTeacher = teacherService.getById(id);
		return R.ok().data("teacher", eduTeacher);
	}

	@PostMapping("updateTeacher")
	public R updateTeacher(@RequestBody EduTeacher eduTeacher){
		boolean update = teacherService.updateById(eduTeacher);
		return R.ok();
	}
}

