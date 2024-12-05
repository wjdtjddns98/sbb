package com.mysite.sbb;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class SbbApplicationTests {
	@Autowired
	private QuestionRepository qRepo;

	@Test
	void testJpa() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.qRepo.save(q1); // 첫번째 질문 저장

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.qRepo.save(q2);  // 두번째 질문 저장
	}


	@Test
	void testFind() {
		List<Question> qList = this.qRepo.findAll();
		for (Question question : qList) {
			System.out.println(question);
		}
		Optional<Question> q1 = qRepo.findById(1);
		if (q1.isPresent()) {
			Question q = q1.get();
			System.out.println(q.getContent());
		}
	}

	@Test
	void testFindBy() {
		Question q = this.qRepo.findBySubject("sbb가 무엇인가요?");
		System.out.println(q.getContent());
	}

	@Test
	void testFindByContaining() {
		List<Question> qList = this.qRepo.findBySubjectContaining("sbb");
		for (Question q : qList) {
			System.out.println(q.getSubject());
		}
	}
	
	@Test 
	void testUpdate(){
		Optional<Question> oq = qRepo.findById(1);
		Question q = oq.get();
		q.setSubject("수정된 제목");
		this.qRepo.save(q); //입력과 수정은 같은 save 메서드 사용, 이떄 id가 있으면 수정
	}

}
