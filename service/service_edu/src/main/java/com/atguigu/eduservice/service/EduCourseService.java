package com.atguigu.eduservice.service;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
