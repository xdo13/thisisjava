package ch20.oracle.sec06;

import java.sql.*;

public class BoardWithFileInsertExample {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            //JDBC Driver 등록
            Class.forName("oracle.jdbc.driver.OracleDriver");

            //연결하기
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521/xe",
                    "java",
                    "oracle"
            );

            String sql = "" +
                    "INSERT INTO boards (bno, btitle, bcontent, bwriter, bdate," +
                    "bfilename, bfiledata) " +
                    "VALUES ( SEQ_BNO.NEXTVAL, ?, ?, ?, SYSDATE, ?, ?)";

            //PreparedStatement 얻기 및 값 지정
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"bno"});
            pstmt.setString(1,"봄의 정원");
            pstmt.setString(2,"정원의 꽃이 예쁘네요.");
            pstmt.setString(3,"winter");
            pstmt.setString(4,"spring.jpg");
            pstmt.setBlob(5,BoardWithFileInsertExample.class.getResourceAsStream("snow.jpg"));

            //SQL문 실행
            int rows = pstmt.executeUpdate();
            System.out.println("저장된 행 수: " + rows);

            //bno 값 얻기
            if (rows == 1) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()){
                    int bno = rs.getInt(1);
                    System.out.println("저장된 bno: " + bno);
                }
                rs.close();
        }
            //PreparedStatement 닫기
            pstmt.close();
    }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (conn != null){
                try {
                    //연결 끊기
                    conn.close();
                }catch (SQLException e){}
            }
        }
    }
}
