
package com.pactera.pds.u2.commerce.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;

import com.pactera.pds.u2.commerce.entity.Task;
import com.pactera.pds.u2.commerce.repository.jpa.TaskDao;

import org.springside.modules.test.spring.SpringTransactionalTestCase;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class TaskDaoTest extends SpringTransactionalTestCase {

	@Autowired
	private TaskDao taskDao;

	@Test
	public void findTasksByUserId() throws Exception {
		Page<Task> tasks = taskDao.findByUserId(2L, new PageRequest(0, 100, Direction.ASC, "id"));
		assertThat(tasks.getContent()).hasSize(5);
		assertThat(tasks.getContent().get(0).getId()).isEqualTo(1);

		tasks = taskDao.findByUserId(99999L, new PageRequest(0, 100, Direction.ASC, "id"));
		assertThat(tasks.getContent()).isEmpty();
		assertThat(tasks.getContent()).isEmpty();
	}
}
