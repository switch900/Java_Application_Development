/**
 * Project: A00123456Lab7
 * File: CustomerDaoTester.java
 */

package a00123456;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00123456.data.customer.Customer;
import a00123456.data.customer.CustomerDao;

/**
 * @author Sam Cirka, A00123456
 *
 */
public class CustomerDaoTester {

	private static Logger LOG = LogManager.getLogger();
	private CustomerDao customerDao;

	public CustomerDaoTester(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public void test() {
		try {
			LOG.info("Getting the customer IDs");
			List<Long> ids = customerDao.getCustomerIds();
			LOG.info("Customer IDs: " + Arrays.toString(ids.toArray()));
			for (Long id : ids) {
				LOG.info(id);
				Customer customer = customerDao.getCustomer(id);
				LOG.info(customer);
			}
			long count = customerDao.countAllCustomers();
			LOG.info(count);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

	}

}
