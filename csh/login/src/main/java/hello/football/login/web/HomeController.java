package hello.football.login.web;

import hello.football.board.domain.Board;
import hello.football.board.domain.BoardRepository;
import hello.football.login.domain.member.Member;
import hello.football.login.domain.member.MemberRepository;
import hello.football.login.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    @GetMapping("/")
    public String homeLoginV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                                    Model model,
                                    @ModelAttribute Board board) {


        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home";
        }


        Member member = memberRepository.findByLoginId(loginMember.getLoginId());

        List<Board> boards= boardRepository.findAll();
        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", member);
        model.addAttribute("board",boards);

        return "loginHome";
    }


}