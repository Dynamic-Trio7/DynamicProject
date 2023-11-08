package hello.football.domain.mypage;

import hello.football.board.domain.Board;
import hello.football.board.domain.BoardRepository;
import hello.football.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final BoardRepository boardRepository;
    public void boardNameUpdate(String id, Member member){
        Board board = boardRepository.findById(id);
        if(board!=null){
            boardRepository.update(id, member);
        }
    }
}
