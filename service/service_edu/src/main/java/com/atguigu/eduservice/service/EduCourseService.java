package com.atguigu.eduservice.service;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-02-05
 */
public interface EduCourseService extends IService<EduCourse> {

	R saveCourseInfo(CourseInfoVo courseInfoVo);

	CourseInfoVo getCourseInfo(String courseId);

	void updateCourseInfo(CourseInfoVo courseInfoVo);

	CoursePublishVo publishCourseInfo(String id);

	void removeCourse(String courseId);

	Map<String, Object> getCourseFrontList(Page<EduCourse> page1, CourseFrontVo courseFrontVo);

	CourseWebVo getBaseCourseInfo(String courseId);
}
