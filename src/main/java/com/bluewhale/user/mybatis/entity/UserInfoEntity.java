package com.bluewhale.user.mybatis.entity;

import java.util.Date;

/**
 * 员工信息表
 * @author 张晓睿
 * @version 创建时间   2019年10月29日 下午16:25:01
 */
public class UserInfoEntity {

    private String id;
    private Integer empNo;

    private String empName;

    private String sexCode;

    private Date birthDate;
    private String phone;
    private String mobile;

    private String homeAddress;
    private String postcode;
    private Date hireDate;
    private Date regDate;
    private Date leaveDate;
    private String postition;

    private String isValid;

    public void setId(String id) {
        this.id = id;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public void setSexCode(String sexCode) {
        this.sexCode = sexCode;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public void setPostition(String postition) {
        this.postition = postition;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getId() {
        return id;
    }

    public Integer getEmpNo() {
        return empNo;
    }

    public String getEmpName() {
        return empName;
    }

    public String getSexCode() {
        return sexCode;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public String getMobile() {
        return mobile;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getPostcode() {
        return postcode;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public Date getRegDate() {
        return regDate;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public String getPostition() {
        return postition;
    }

    public String getIsValid() {
        return isValid;
    }
}
