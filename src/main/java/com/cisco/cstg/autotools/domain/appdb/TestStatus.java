
package com.cisco.cstg.autotools.domain.appdb;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Past;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cisco.cstg.autotools.semantic.test.TestResult;


@Entity
@Table(name = "TABLE_TEST_STATUS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TestStatus extends IdentifiableEntity {

    private static final long serialVersionUID = 2993569267760500809L;
    public static final String RUNNING = "Running";
    public static final String PASSED = "Passed";
    public static final String FAILED = "Failed";
    public static final String EXECUTED = "Executed";
    
    
    /**
     * The TIMS or external test management system id 
     */
    private Long testId;
    
    private Test test;

    private String testStatus;

    private Date created;
    
    private Date updated;
    
    /**
     * stores the test result from the last execution. its a transient one and not persisted.
     */
    private TestResult testResult;
    
    private String reportName;
    
    @OneToOne(targetEntity=Test.class) 
    @JoinColumn(name = "TEST_ID", referencedColumnName="id")	
    public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

    @Column(name="TEST_ID", insertable=false, updatable=false)
    public Long getTestId() {
        return this.testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    @Column(name="TEST_STATUS")
    public String getTestStatus() {
        return this.testStatus;
    }

    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }

    @Past
    @Column(name="CREATE_DATE", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
    
    @Column(name="UPDATE_DATE", nullable = false, updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    
    @Column(name="REPORT_NAME")
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (this.getId() == null) {
            return false;
        } else if (o instanceof TestStatus) {
            TestStatus that = (TestStatus) o;
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

    @Transient
    public String getName() {
        return this.getId() + " " + this.getId();
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Transient
	public TestResult getTestResult() {
		return testResult;
	}
	public void setTestResult(TestResult testResult) {
		this.testResult = testResult;
	}
}