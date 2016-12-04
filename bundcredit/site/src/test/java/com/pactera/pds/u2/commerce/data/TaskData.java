
package com.pactera.pds.u2.commerce.data;

import com.pactera.pds.u2.commerce.entity.Task;
import com.pactera.pds.u2.commerce.entity.User;

import org.springside.modules.test.data.RandomData;

/**
 * Task相关实体测试数据生成.
 * 
 */
public class TaskData {

	public static Task randomTask() {
		Task task = new Task();
		task.setTitle(randomTitle());
		User user = new User(1L);
		task.setUser(user);
		return task;
	}

	public static String randomTitle() {
		return RandomData.randomName("Task");
	}
}
