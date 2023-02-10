package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-02-05
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

	@Autowired
	private VodClient vodClient;

	@Override
	public void removeVideoByCourseId(String courseId) {
		QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
		eduVideoQueryWrapper.eq("course_id", courseId);
		eduVideoQueryWrapper.select("video_source_id");
		List<EduVideo> eduVideos = baseMapper.selectList(eduVideoQueryWrapper);

		List<String> collect = eduVideos.stream().map(EduVideo::getVideoSourceId).filter(a -> {
			return !StringUtils.isEmpty(a);
		}).collect(Collectors.toList());
		vodClient.deleteBatch(collect);


		QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
		wrapper.eq("course_id", courseId);
		baseMapper.delete(wrapper);
	}
}
