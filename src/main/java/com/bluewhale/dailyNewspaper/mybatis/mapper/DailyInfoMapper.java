package com.bluewhale.dailyNewspaper.mybatis.mapper;

import com.bluewhale.dailyNewspaper.mybatis.entity.DailyInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by curtin
 * User: curtin
 * Date: 2019/10/7
 * Time: 9:20 PM
 */
@Repository
public interface DailyInfoMapper {

    /**
     * 根据id获取日报信息
     * @param id
     * @return
     */
    public DailyInfo getDailyInfoById(Integer id);

}
