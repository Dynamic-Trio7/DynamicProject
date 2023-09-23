package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

    private final SessionManger sessionManger;
    //@GetMapping("/")
    public String home() {

        return "home";
    }

    //@GetMapping("/")
    public String homeLogin(@CookieValue(value = "memberId",required = false
            /*false 이유가 로그인 안한 사람도 들어오게 할려고 또한 String 이여야 되는데
            * 스프링이 자동으로 타입 컨버터 해줌*/) Long memberId, Model model) {
        if(memberId==null){
            return "home";
        }
        //로그인
        Member loginMember = memberRepository.findById(memberId);
        if(loginMember==null){
            return "home"; //굳이 해야되나 싶지만 악용이 있기때문에 해야될듯..
        }
        model.addAttribute("member",loginMember);
        return "loginHome";
    }

    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {

        //세션 관리자에 저장된 회원 정보 조회
        Member member = (Member)sessionManger.getSession(request);
        //로그인
        if(member==null){
            return "home";
        }
        model.addAttribute("member",member);
        return "loginHome";
    }
}