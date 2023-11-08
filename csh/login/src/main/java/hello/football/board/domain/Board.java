package hello.football.board.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Board {

    String id;

    @NotEmpty
    String title;

    @NotEmpty
    String boardContent;

    String memberName;

    String memberId;

}
