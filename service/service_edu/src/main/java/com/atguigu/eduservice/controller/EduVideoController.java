package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.eduservice.utils.AddTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-02-05
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

	@Autowired
	private EduVideoService eduVideoService;

	@PostMapping("addVideo")
	public R addVideo(@RequestBody EduVideo eduVideo){
		AddTime.addEduVideo(eduVideo);
		eduVideoService.save(eduVideo);
		return R.ok();
	}

	// TODO 后面需要完善
	@DeleteMapping("{id}")
	public R deleteVideo(@PathVariable String id){
		eduVideoService.removeById(id);
		return R.ok();
	}
}

