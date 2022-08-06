package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional // 데이터 롤백 기능
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
//    @Commit // db에 반영
    void 회원가입() {
        // given == 머리
        Member member = new Member();
        member.setName("spring");

        // when == 가슴
        Long saveId = memberService.join(member);

        // then == 배
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2)); // IllegalStateException 예외 터져야 함

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        try{
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e) { // 정상 동작
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); // 성공
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.123123"); // 실패
//        }


        // then
    }
}