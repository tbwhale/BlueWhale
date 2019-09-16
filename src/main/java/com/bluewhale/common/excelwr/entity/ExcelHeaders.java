package com.bluewhale.common.excelwr.entity;

/**
 * Created by curtin
 * User: curtin
 * Date: 2019/9/15
 * Time: 1:43 PM
 */
public class ExcelHeaders {
    /**
     * 模块周报上周完成任务头
     */
    public static final String[] module_wr_this_week_mission = {"需求/任务编号","任务类型","任务名称","实际开始日期","实际结束日期","实际工作量（人/天）","责任人","完成比率","任务拖延原因/客户评价"};
    /**
     * 模块周报下周计划头
     */
    public static final String[] module_wr_next_week_mission = {"需求/任务编号","类型","任务名称","计划开始日期","计划结束日期","工作量估计（人/天）","责任人","备注"};

    /**
     * 客户周报上周完成任务头
     */
    public static final String[] customer_wr_this_week_mission = {"需求/任务编号","任务类型","任务名称","实际开始日期","实际结束日期","实际工作量（人/天）","责任人","完成比率","任务拖延原因/客户评价"};
    /**
     * 客户周报下周计划头
     */
    public static final String[] customer_wr_next_week_mission = {"需求/任务编号","任务类型","任务名称","实际开始日期","计划结束日期","工作量估计（人/天）","责任人","备注"};



    /**
     * 项目周报上周完成任务头
     */
    public static final String[] project_wr_this_week_mission = {"任务编号","任务类型","任务名称","实际开始日期","实际结束日期","实际工作量（人/天）","责任人","完成比率","任务拖延原因/客户评价"};
    /**
     * 项目周报下周计划头
     */
    public static final String[] project_wr_next_week_mission = {"任务编号","任务类型","任务名称","实际开始日期","计划结束日期","工作量估计（人/天）","责任人","备注"};


}
