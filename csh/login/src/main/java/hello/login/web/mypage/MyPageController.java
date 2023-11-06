package hello.login.web.mypage;

import hello.login.domain.member.Member;
import hello.login.web.session.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/my-page")
public class MyPageController {

    @GetMapping("/{memberId}")
    public String myPageHome(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                             Model model){
        model.addAttribute("member",loginMember);
        return "mypage/my-page";
    }
}
