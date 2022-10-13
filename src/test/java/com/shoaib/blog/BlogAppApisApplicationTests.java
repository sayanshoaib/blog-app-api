package com.shoaib.blog;

import com.shoaib.blog.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogAppApisApplicationTests {

	@Autowired
	private UserRepo userRepo;

	@Test
	void contextLoads() {
	}

	@Test
	public void repoTest() {
		String className = userRepo.getClass().getName();
		String packName = userRepo.getClass().getPackageName();
		System.out.println(className);
		System.out.println(packName);
	}

}
