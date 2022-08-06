package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service
public class MemberService { // Ctrl + Shift + T : 테스트 코드 자동 생성

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

//    @Autowired // MemberService와 MemoryMemberRepository 연결
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원X
//        Optional<Member> result = memberRepository.findByName(member.getName()); // Optional 바로 권한하는 건 좋지 않음
//        result.ifPresent(m -> {
//           throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();

//        long start = System.currentTimeMillis();
//
//        try {
//            validateDuplicateMember(member); // 중복 회원 검증
//            memberRepository.save(member);
//            return member.getId();
//        } finally {
//            long finish = System.currentTimeMillis();
//            long timeMs = finish - start;
//            System.out.println("join = " + timeMs + "ms");
//        }
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()) // 생략
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {

        return memberRepository.findAll();

//        long start = System.currentTimeMillis();
//        try {
//            return memberRepository.findAll();
//        } finally {
//            long finish = System.currentTimeMillis();
//            long timeMs = finish - start;
//            System.out.println("findMembers = " + timeMs + "ms");
//        }
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
