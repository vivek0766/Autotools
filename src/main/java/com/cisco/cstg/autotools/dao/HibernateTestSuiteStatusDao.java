
package com.cisco.cstg.autotools.dao;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cisco.cstg.autotools.domain.appdb.TestSuiteStatus;

@Repository
public class HibernateTestSuiteStatusDao extends AbstractHibernateDao<TestSuiteStatus> implements TestSuiteStatusDao {

    @Transactional(readOnly = true, value="txManager")
    public List<TestSuiteStatus> getAll() throws DataAccessException {
        return super.findAll("from TestSuiteStatus order by id");
    }

    @Override
    @Transactional(readOnly = false, value="txManager")
    public void save(TestSuiteStatus testSuiteStatus) throws DataAccessException {
    	logger.debug("INSIDE THE save Test Status "+ new Date());
        if (!testSuiteStatus.isIdSet()) {
            testSuiteStatus.setCreated(new Date());
            logger.debug("INSIDE CREATE DATE "+ new Date());
        }else{
        	testSuiteStatus.setUpdated(new Date());
            logger.debug("INSIDE UPDATE DATE "+ new Date());
        }
        super.save(testSuiteStatus);
    }

	@Override
	@Transactional(readOnly = true, value="txManager")
	public TestSuiteStatus getByTestSuiteId(Long testSuiteId) throws DataAccessException {
		return super.findOne("from TestSuiteStatus where testSuiteId=?", testSuiteId);
	}
}
