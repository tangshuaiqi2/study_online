package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-02-05
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

	@Autowired
	private EduCourseDescriptionService courseDescriptionService;

	@Override
	public R saveCourseInfo(CourseInfoVo courseInfoVo) {
		courseInfoVo.setGmtCreate(new Date());
		courseInfoVo.setGmtModified(new Date());
		EduCourse eduCourse = new EduCourse();
		BeanUtils.copyProperties(courseInfoVo, eduCourse);

		int insert = baseMapper.insert(eduCourse);

		if(insert == 0){
			return R.error();
		}

		String cid = eduCourse.getId();

		EduCourseDescription courseDescription = new EduCourseDescription();
		BeanUtils.copyProperties(courseInfoVo, courseDescription);
		courseDescription.setId(cid);
		courseDescriptionService.save(courseDescription);

		return R.ok().data("CourseId", cid);
	}
}
