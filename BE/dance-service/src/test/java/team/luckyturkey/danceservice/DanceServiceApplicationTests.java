package team.luckyturkey.danceservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.luckyturkey.danceservice.repository.jpa.SourceTagRepository;
import team.luckyturkey.danceservice.repository.jpa.TagRepository;

import java.util.List;

@SpringBootTest
class DanceServiceApplicationTests {

	@Autowired
	SourceTagRepository sourceTagRepository;

	@Autowired
	TagRepository tagRepository;

	@Test
	void contextLoads() {
	}

}
