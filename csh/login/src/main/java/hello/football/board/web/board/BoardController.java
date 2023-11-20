package hello.football.board.web.board;


import hello.football.board.boardService.BoardIDService;
import hello.football.board.domain.Board;
import hello.football.board.domain.BoardRepository;
import hello.football.login.domain.member.Member;
import hello.football.login.domain.member.MemberRepository;
import hello.football.login.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board/{id}")
public class BoardController {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    private final BoardIDService boardIDService;

    @GetMapping
    public String board(
            @ModelAttribute Board board,
            @PathVariable String id,
            Model model) {
        Member member = memberRepository.findById(id);

        board.setMemberName(member.getName());
        board.setMemberId(member.getId());

        model.addAttribute("board", board);
        return "board/boardCreate";
    }

    @PostMapping
    public String createBoard(
            @ModelAttribute Board board,
            @PathVariable String id
    ) {
        Member member = memberRepository.findById(id);
        board.setMemberName(member.getName());
        board.setMemberId(member.getId());
        int n = boardIDService.addId(id);
        board.setBoardId(String.valueOf(n));
        boardRepository.save(board);
        return "redirect:/";
    }

    @GetMapping("/{boardId}")
    public String checkBoard(@PathVariable String id,
                             @PathVariable String boardId,
                             @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                             Model model) {
        List<Board> all = boardRepository.findAll();
        for (Board board : all) {
            if(board.getMemberId().equals(id) && board.getBoardId().equals(boardId)){
                model.addAttribute("member",member);
                model.addAttribute("board", board);
                return "board/board";
            }
        }

        return "error-page";
    }
    @DeleteMapping("/{boardId}")
    public String editDeleteBoard(@PathVariable String id,
                                  @PathVariable String boardId) {
        List<Board> all = boardRepository.findAll();
        for (Board board : all) {
            if(board.getMemberId().equals(id) && board.getBoardId().equals(boardId)){
                boardRepository.delete(board);
                return "redirect:/";
            }
        }

        return "error-page";
    }


    @GetMapping("/{boardId}/edit")
    public String editBoard(@PathVariable String id,
                             @PathVariable String boardId,
                             Model model) {
        List<Board> all = boardRepository.findAll();
        for (Board board : all) {
            if(board.getMemberId().equals(id) && board.getBoardId().equals(boardId)){
                model.addAttribute("board", board);
                return "board/boardEdit";
            }
        }

        return "error-page";
    }

    @PostMapping("/{boardId}/edit")
    public String editPostBoard(@PathVariable String id,
                            @PathVariable String boardId,
                            @ModelAttribute Board fboard) {
        List<Board> all = boardRepository.findAll();
        for (Board board : all) {
            log.info(board.getMemberId());
            log.info(board.getBoardId());
            if(board.getMemberId().equals(id) && board.getBoardId().equals(boardId)){
                log.info(board.getBoardId());
                board.setTitle(fboard.getTitle());
                board.setBoardContent(fboard.getBoardContent());
                boardRepository.updateV2(board);
                return "redirect:/board/{id}/{boardId}";
            }
        }

        return "error-page";
    }

}
