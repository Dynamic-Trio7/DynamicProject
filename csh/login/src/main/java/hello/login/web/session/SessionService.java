package hello.login.web.session;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionService {

    private final MemberRepository memberRepository;

    public void sessionSave(String session,String loginId){
        Member member = memberRepository.findByLoginId(loginId);
        log.info("session={}",session);
        memberRepository.updateSession(session,member.getId());
    }

}
