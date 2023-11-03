package hello.login.domain.member;

import hello.login.jdbc.connection.DBConnectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;



@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final DataSource dataSource;
    private static Map<Long, Member> store=new HashMap<>();
    private static long sequence = 0L;

    public Member save(Member member) throws SQLException {
        member.setId(UUID.randomUUID().toString());
        String sql = "insert into member(id,loginID, password,name,gender) values(?,?,?,?,?)";

        Connection con = null;
        PreparedStatement pstmt = null; //DB에 쿼리날리기

        //중복 방지
        Member existMember = findByLoginId(member.getLoginId());
        if(existMember!=null&&existMember.getLoginId().equals(member.getLoginId())){
            return null;
        }


        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql); // 데이터베이스에 전달할 SQL과 파라미터로 전달할 데이터들을 준비한다
            pstmt.setString(1,member.getId());
            pstmt.setString(2, member.getLoginId()); //파라미터에 대한 값 바인딩
            pstmt.setString(3, member.getPassword());
            pstmt.setString(4,member.getName());
            pstmt.setString(5,member.getMemberType().getDescription());

            pstmt.executeUpdate(); //실행
            return member;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }

    }


    public Member findById(long id) {
        return store.get(id);
    }
    public Member findByLoginId(String loginId) throws SQLException {
        String sql = "select * from member where loginId=?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, loginId);
            rs = pstmt.executeQuery(); // 업데이트 아님 애는 찾아주는거

            if (rs.next()) {
                Member member = new Member();
                member.setLoginId(rs.getString("loginId"));
                member.setPassword(rs.getString("password"));
                member.setName(rs.getString("name"));
                log.info("password={}",member.getName());
                return member;
            } else {
                return null;
            }

        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, rs);
        }
    }

    public List<Member> findAll() {
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Member> members = new ArrayList<>();
            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getString("id"));
                member.setLoginId(rs.getString("loginId"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
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
