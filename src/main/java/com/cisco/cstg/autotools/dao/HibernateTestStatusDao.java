
package com.cisco.cstg.autotools.dao;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cisco.cstg.autotools.domain.appdb.TestStatus;

@Repository
public class HibernateTestStatusDao extends AbstractHibernateDao<TestStatus> implements TestStatusDao {

    @Transactional(readOnly = true, value="txManager")
    public List<TestStatus> getAll() throws DataAccessException {
        return super.findAll("from TestStatus order by id");
    }

    @Override
    @Transactional(readOnly = false, value="txManager")
    public void save(TestStatus testStatus) throws DataAccessException {
    	logger.debug("INSIDE THE save Test Status "+ new Date());
        if (!testStatus.isIdSet()) {
            testStatus.setCreated(new Date());
            logger.debug("INSIDE CREATE DATE "+ new Date());
        }else{
        	testStatus.setUpdated(new Date());
            logger.debug("INSIDE UPDATE DATE "+ new Date());
        }
        super.save(testStatus);
    }

	@Override
	@Transactional(readOnly = true, value="txManager")
	public TestStatus getByTestId(Long testId) throws DataAccessException {
		return super.findOne("from TestStatus where testId=?", testId);
	}
}
