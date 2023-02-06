package com.atguigu.eduservice.utils;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;

import java.util.Date;

public class AddTime {
	public static void addChapter(EduChapter eduChapter){
		eduChapter.setGmtCreate(new Date());
		eduChapter.setGmtModified(new Date());
	}

	public static void addEduVideo(EduVideo eduVideo){
		eduVideo.setGmtCreate(new Date());
		eduVideo.setGmtModified(new Date());
	}
}
