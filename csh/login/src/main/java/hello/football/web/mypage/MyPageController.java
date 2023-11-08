package hello.football.web.mypage;

import hello.football.board.domain.BoardRepository;
import hello.football.domain.member.Member;
import hello.football.domain.member.MemberRepository;
import hello.football.domain.mypage.MyPageService;
import hello.football.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/my-page")
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    private final MemberRepository memberRepository;

    private final MyPageService myPageService;

    @GetMapping("/{id}")
    public String myPageHome(
            @PathVariable String id,
            Model model,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {

        Member byLoginId = memberRepository.findByLoginId(loginMember.getLoginId());
        model.addAttribute("loginMember",byLoginId);

        Member fmember = memberRepository.findById(id);
        Member member = new Member();
        member.setName(fmember.getName());
        member.setDescription(fmember.getDescription());
        member.setId(id);
        model.addAttribute("member", member);

        return "mypage/my-page";

    }

    @GetMapping("/edit")
    public String editGetMyPageHome(
            Model model,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        Member member1 = memberRepository.findByLoginId(member.getLoginId());
        model.addAttribute("member", member1);
        return "mypage/my-page-edit";
    }

    @PostMapping("/edit")
    public String editMyPageHome(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            @ModelAttribute("member") Member member) {
        Member member1 = memberRepository.findByLoginId(loginMember.getLoginId());
        memberRepository.update(member1.getId(), member);
        myPageService.boardNameUpdate(member1.getId(), member);

        return "redirect:/";
    }
}
