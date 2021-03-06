package com.cisco.cstg.autotools.domain.appdb;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "TABLE_SCHEDULE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Schedule extends IdentifiableEntity {
    
	private static final long serialVersionUID = 7735334592256525008L;
	public static final String SCHEDULED = "Scheduled";
	public static final String STOPPED = "Stopped";
	
	public static final String SNTC_PROGRAM = "SNTC";
	public static final String PSS_PROGRAM = "PSS";
	
	
    private String scheduleName;
    
    private String programName;
    
    private String serviceName;

	private String status;
	
	private Date updated;

    @Column(name="SCHEDULE_NAME")
    public String getScheduleName() {
 		return scheduleName;
 	}

 	public void setScheduleName(String scheduleName) {
 		this.scheduleName = scheduleName;
 	}

    @Column(name="PROGRAM_NAME")
    public String getProgramName() {
 		return programName;
 	}

 	public void setProgramName(String programName) {
 		this.programName = programName;
 	}
 	
    @Column(name="SERVICE_NAME")
    public String getServiceName() {
 		return serviceName;
 	}

 	public void setServiceName(String serviceName) {
 		this.serviceName = serviceName;
 	} 	
    
    @Column(name="SCHEDULE_STATUS")
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Column(name="UPDATE_DATE", nullable = false, updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

      @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (this.getId() == null) {
            return false;
        } else if (o instanceof Schedule) {
            Schedule that = (Schedule) o;
            return this.getId().equals(that.getId());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.getId() == null ? System.identityHashCode(this) : 17 * this.getId()
                .hashCode();
    }

    @Override
    public String toString() {
        return "ID: "+this.getId().toString()+
        		"SCHEDULE NAME: "+this.getScheduleName()+
        		"STATUS: "+this.getStatus();
    }
}