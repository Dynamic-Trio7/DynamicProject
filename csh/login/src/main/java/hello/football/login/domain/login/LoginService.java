package hello.football.login.domain.login;

import hello.football.login.domain.member.Member;
import hello.football.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Member login(String loginId, String password) throws SQLException {
        Member member = memberRepository.findByLoginId(loginId);
        if (member == null) {
            return null;
        } else {
            if(bCryptPasswordEncoder.matches(password, member.getPassword())){
                return member;
            }
            else{
                return null;
            }
        }
    }
}


