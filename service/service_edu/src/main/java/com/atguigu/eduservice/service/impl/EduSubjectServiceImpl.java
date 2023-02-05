package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-02-04
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

	@Override
	public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
		try {
			InputStream in = file.getInputStream();

			EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<OneSubject> getAllOneTwoSubject() {
		QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
		wrapperOne.eq("parent_id", 0);
		List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

		QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
		wrapperOne.ne("parent_id", 0);
		List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

		List<OneSubject> finalSubjectList = new ArrayList<>();
		for (int i = 0; i < oneSubjectList.size(); i++) { //遍历oneSubjectList集合
			//得到oneSubjectList每个eduSubject对象
			EduSubject eduSubject = oneSubjectList.get(i);
			//把eduSubject里面值获取出来，放到OneSubject对象里面
			OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
			//eduSubject值复制到对应oneSubject对象里面
			BeanUtils.copyProperties(eduSubject,oneSubject);
			//多个OneSubject放到finalSubjectList里面
			finalSubjectList.add(oneSubject);

			//在一级分类循环遍历查询所有的二级分类
			//创建list集合封装每个一级分类的二级分类
			List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
			//遍历二级分类list集合
			for (int m = 0; m < twoSubjectList.size(); m++) {
				//获取每个二级分类
				EduSubject tSubject = twoSubjectList.get(m);
				//判断二级分类parentid和一级分类id是否一样
				if(tSubject.getParentId().equals(eduSubject.getId())) {
					//把tSubject值复制到TwoSubject里面，放到twoFinalSubjectList里面
					TwoSubject twoSubject = new TwoSubject();
					BeanUtils.copyProperties(tSubject,twoSubject);
					twoFinalSubjectList.add(twoSubject);
				}
			}
			//list.stream().filter(s -> "0".equals(s.getParentId())).collect(Collectors.toList());
			//把一级下面所有二级分类放到一级分类里面
			oneSubject.setChildren(twoFinalSubjectList);
		}
		return finalSubjectList;
	}
}
