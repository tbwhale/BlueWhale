package com.bluewhale.dailyNewspaper.service.Impl;

import java.util.List;

import com.bluewhale.dailyNewspaper.mybatis.entity.DailyInfo;
import com.bluewhale.dailyNewspaper.mybatis.mapper.DailyInfoMapper;
import com.bluewhale.dailyNewspaper.service.DailyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class DailyInfoServiceImpl implements DailyInfoService {
	
	@Autowired
	private DailyInfoMapper dailyInfoMapper;

	@Override
	public DailyInfo getDailyInfoById(Integer id) {
		return dailyInfoMapper.getDailyInfoById(id);
	}


}
