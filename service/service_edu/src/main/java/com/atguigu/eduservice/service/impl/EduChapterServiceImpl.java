package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-02-05
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

	@Autowired
	private EduVideoService eduVideoService;

	@Override
	public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

		QueryWrapper<EduChapter> wrapperCharpter = new QueryWrapper<>();
		wrapperCharpter.eq("course_id", courseId);
		List<EduChapter> eduChapterList = baseMapper.selectList(wrapperCharpter);

		QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
		wrapperVideo.eq("course_id", courseId);
		List<EduVideo> eduVideoList = eduVideoService.list(wrapperVideo);

		List<ChapterVo> finalList = new ArrayList<>();

		for(int i = 0; i < eduChapterList.size(); i++){
			EduChapter eduChapter = eduChapterList.get(i);
			ChapterVo chapterVo = new ChapterVo();
			BeanUtils.copyProperties(eduChapter, chapterVo);



			List<VideoVo> videoVoList = new ArrayList<>();

			for (int m = 0; m < eduVideoList.size(); m++) {
				EduVideo eduChapter1 = eduVideoList.get(m);
				if(eduChapter1.getChapterId().equals(eduChapter.getId())){
					VideoVo videoVo = new VideoVo();
					BeanUtils.copyProperties(eduChapter1, videoVo);

					videoVoList.add(videoVo);
				}
			}

			chapterVo.setChildren(videoVoList);
			finalList.add(chapterVo);
		}

		return finalList;
	}

	@Override
	public boolean deleteChapter(String chapterId) {
		QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
		eduVideoQueryWrapper.eq("chapter_id", chapterId);

		int count = eduVideoService.count(eduVideoQueryWrapper);

		if(count > 0){
			return false;
		}else{
			baseMapper.deleteById(chapterId);
		}
		return true;
	}

	@Override
	public void removeChapterByCourseId(String courseId) {
		QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
		wrapper.eq("course_id", courseId);

		baseMapper.delete(wrapper);
	}
}
