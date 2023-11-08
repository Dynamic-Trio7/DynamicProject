package hello.football.board.boardService;

import hello.football.board.domain.Board;
import hello.football.board.domain.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardIDService {
    private final BoardRepository boardRepository;

    public int addId(String id){
        List<Board> all = boardRepository.findAll();
        int cnt=1;
        if (all != null) {
            for (Board fboard : all) {
                log.info(fboard.getMemberName());
                log.info(fboard.getMemberId());
                if (fboard.getMemberId().equals(id)){
                    cnt++;
                }
            }
        }
        return cnt;
    }
}
