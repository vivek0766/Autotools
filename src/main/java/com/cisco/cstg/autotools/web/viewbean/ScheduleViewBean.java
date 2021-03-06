 package com.cisco.cstg.autotools.web.viewbean;

import java.util.Date;

import com.cisco.cstg.autotools.domain.appdb.Schedule;

public class ScheduleViewBean {
	
	private Long id;
	
    private int version;

    private String scheduleName;
    
    private String programName;
    
    private String serviceName;

	private String status;
	
	private Date updated;
    
	private String userName;
	private String password;
	
    public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getScheduleName() {
		return scheduleName;
	}
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public ScheduleViewBean scheduleToScheduleViewBean(Schedule schedule){

		if(schedule.getId()!=null)
			this.id = schedule.getId();

		this.version = schedule.getVersion();

		if(schedule.getScheduleName()!=null)
			this.scheduleName = schedule.getScheduleName();

		if(schedule.getProgramName()!=null)
			this.programName = schedule.getProgramName();
	    
		if(schedule.getServiceName()!=null)
			this.serviceName = schedule.getServiceName();

		if(schedule.getStatus()!=null)
			this.status = schedule.getStatus();

		if(schedule.getUpdated()!=null)
			this.updated = schedule.getUpdated();
	  return this;
	}
	@Override
	public String toString() {
		return "ScheduleViewBean [id=" + id + ", version=" + version
				+ ", scheduleName=" + scheduleName + ", programName="
				+ programName + ", serviceName=" + serviceName + ", status="
				+ status + ", updated=" + updated + ", userName=" + userName
				+ ", password=" + password + "]";
	}
	
}
