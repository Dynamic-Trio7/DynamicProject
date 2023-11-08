package hello.football.board.domain;

import hello.football.domain.member.Member;
import hello.football.jdbc.ex.MyDBException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final DataSource dataSource;

    public Board save(Board board) {
        String sql = "insert into board(id,title,boardContent, memberId,memberName) values(?,?,?,?,?)";
        Connection con = null;
        PreparedStatement pstmt = null; //DB에 쿼리날리기

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql); // 데이터베이스에 전달할 SQL과 파라미터로 전달할 데이터들을 준비한다
            pstmt.setString(1, board.getId());
            pstmt.setString(2, board.getTitle());
            pstmt.setString(3, board.getBoardContent());
            pstmt.setString(4, board.getMemberId());
            pstmt.setString(5, board.getMemberName());

            pstmt.executeUpdate(); //실행
            return board;
        } catch (SQLException e) {
            throw new MyDBException(e);
        } finally {
            close(con, pstmt, null);
        }

    }

    public void update(String id, Member member) {
        String sql = "update board set memberName=? where memberId=?";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getName());
            pstmt.setString(2, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new MyDBException(e);
        } finally {
            close(con, pstmt, null);
        }
    }
    public Board findById(String id) {
        String sql = "select * from board where memberId=?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery(); // 업데이트 아님 애는 찾아주는거

            if (rs.next()) {
                Board board=new Board();
                board.setTitle(rs.getString("title"));
                board.setBoardContent(rs.getString("boardContent"));
                board.setMemberName(rs.getString("memberName"));
                return board;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new MyDBException(e);
        } finally {
            close(con, pstmt, rs);
        }
    }
    public List<Board> findAll() {
        String sql = "select * from board";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Board> boards = new ArrayList<>();
            while (rs.next()) {
                Board board = new Board();
                board.setTitle(rs.getString("title"));
                board.setBoardContent(rs.getString("boardContent"));
                board.setMemberName(rs.getString("memberName"));
                board.setMemberId(rs.getString("memberId"));
                boards.add(board);
            }
            return boards;
        } catch (Exception e) {
            throw new MyDBException(e);
        } finally {
            close(con, pstmt, rs);
        }
    }


    private void close(Connection cos, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(cos);
    }

    private Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        return con;
    }
}


