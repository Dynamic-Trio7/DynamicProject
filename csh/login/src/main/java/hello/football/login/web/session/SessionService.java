package hello.football.login.web.session;

import hello.football.login.domain.member.Member;
import hello.football.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
