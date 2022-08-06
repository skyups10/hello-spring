package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired // MemberController와 MemberService 연결
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // GET 방식 -> createMemberForm.html 이동 -> POST 방식
    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

//        System.out.println("member = " + member.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members); // "members" 키 값 안에 members 삽입
        return "members/memberList";
    }
}

// 메모리에 데이터를 넣으면 재실행시 데이터가 모두 사라지므로 데이터베이스가 필요!