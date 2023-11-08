package hello.football.board.web;


import hello.football.board.boardService.BoardIDService;
import hello.football.board.domain.Board;
import hello.football.board.domain.BoardRepository;
import hello.football.domain.member.Member;
import hello.football.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    private final BoardIDService boardIDService;

    @GetMapping("/{id}")
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

    @PostMapping("/{id}")
    public String createBoard(
            @ModelAttribute Board board,
            @PathVariable String id
    ) {
        Member member = memberRepository.findById(id);
        board.setMemberName(member.getName());
        board.setMemberId(member.getId());
        int n = boardIDService.addId(id);
        board.setId(String.valueOf(n));
        boardRepository.save(board);
        return "redirect:/";
    }
}
