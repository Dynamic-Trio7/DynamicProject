package hello.login.web.member;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.domain.member.MemberType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
@Slf4j
public class MemberController {

    private final MemberRepository memberRepository;
    @ModelAttribute("memberType")
    public MemberType[] memberType(){
        MemberType[] values = MemberType.values();
        return values;
    }
    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member) {
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Validated @ModelAttribute Member member, BindingResult bindingResult) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "members/addMemberForm";
        }
        if (!Objects.equals(member.getPassword(), member.getPasswordCheck())) {
            bindingResult.reject("addFail", "비밀번호가 일치하지 않습니다.");
            return "members/addMemberForm";
        }
        Member addMember = memberRepository.save(member);
        if (addMember == null) {
            bindingResult.reject("addFail", "존재하는 아이디가 있습니다.");
            return "members/addMemberForm";
        }
        log.info("dd={}",member.getMemberType());
        return "redirect:/";
    }

}
