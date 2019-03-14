package com.bluewhale.spring.boot.daily2weekly.mybatis.entity;

import java.util.Date;

/**
 * 日报信息表
 * @author 张晓睿
 * @version 创建时间   2019年3月14日 下午6:56:09
 */
public class DailyInfoEntity {

	//任务序号
	private Integer taskSeq;
	//需求号或bug号
	private String taskId;
	//bug分类
	private String bugType;
	//任务类型
	private String taskType;
	//任务描述
	private String taskDes;
	//任务状态
	private String taskStatus;
	//上班时间
	private Date startDate;
	//下班时间
	private Date endDate;
	//预计工作量
	private Double workTime;
	public Integer getTaskSeq() {
		return taskSeq;
	}
	public void setTaskSeq(Integer taskSeq) {
		this.taskSeq = taskSeq;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getBugType() {
		return bugType;
	}
	public void setBugType(String bugType) {
		this.bugType = bugType;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getTaskDes() {
		return taskDes;
	}
	public void setTaskDes(String taskDes) {
		this.taskDes = taskDes;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Double getWorkTime() {
		return workTime;
	}
	public void setWorkTime(Double workTime) {
		this.workTime = workTime;
	}
	
}
