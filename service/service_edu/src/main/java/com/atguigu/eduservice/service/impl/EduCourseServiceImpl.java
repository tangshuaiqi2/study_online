package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Autowired
	private EduVideoService eduVideoService;

	@Autowired
	private EduChapterService chapterService;


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

		return R.ok().data("courseId", cid);
	}

	@Override
	public CourseInfoVo getCourseInfo(String courseId) {

		EduCourse eduCourse = baseMapper.selectById(courseId);
		CourseInfoVo courseInfoVo = new CourseInfoVo();
		BeanUtils.copyProperties(eduCourse, courseInfoVo);


		EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
		BeanUtils.copyProperties(courseDescription, courseInfoVo);

		return courseInfoVo;
	}

	@Override
	public void updateCourseInfo(CourseInfoVo courseInfoVo) {
		EduCourse eduCourse = new EduCourse();

		BeanUtils.copyProperties(courseInfoVo, eduCourse);

		int update = baseMapper.updateById(eduCourse);

		EduCourseDescription courseDescription = new EduCourseDescription();
		BeanUtils.copyProperties(courseInfoVo, courseDescription);
		courseDescriptionService.updateById(courseDescription);
	}

	@Override
	public CoursePublishVo publishCourseInfo(String id) {
		CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
		return publishCourseInfo;
	}

	@Override
	public void removeCourse(String courseId) {
		eduVideoService.removeVideoByCourseId(courseId);

		chapterService.removeChapterByCourseId(courseId);

		courseDescriptionService.removeById(courseId);

		int i = baseMapper.deleteById(courseId);
	}

	@Override
	public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {

		//2 根据讲师id查询所讲课程
		QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
		//判断条件值是否为空，不为空拼接
		if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) { //一级分类
			wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
		}
		if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级分类
			wrapper.eq("subject_id",courseFrontVo.getSubjectId());
		}
		if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度
			wrapper.orderByDesc("buy_count");
		}
		if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
			wrapper.orderByDesc("gmt_create");
		}

		if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
			wrapper.orderByDesc("price");
		}

		baseMapper.selectPage(pageParam,wrapper);

		List<EduCourse> records = pageParam.getRecords();
		long current = pageParam.getCurrent();
		long pages = pageParam.getPages();
		long size = pageParam.getSize();
		long total = pageParam.getTotal();
		boolean hasNext = pageParam.hasNext();//下一页
		boolean hasPrevious = pageParam.hasPrevious();//上一页

		//把分页数据获取出来，放到map集合
		Map<String, Object> map = new HashMap<>();
		map.put("items", records);
		map.put("current", current);
		map.put("pages", pages);
		map.put("size", size);
		map.put("total", total);
		map.put("hasNext", hasNext);
		map.put("hasPrevious", hasPrevious);

		//map返回
		return map;
	}

	@Override
	public CourseWebVo getBaseCourseInfo(String courseId) {

		return baseMapper.getBaseCourseInfo(courseId);
	}
}
