import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Review05 {

    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "Bronze0613"
                );
            
            String sql = "SELECT * FROM kadaidb.person WHERE Id = ?";
            pstmt = con.prepareStatement(sql);
            
            System.out.print("検索キーワードを入力してください > ");
            int num1 = keyInNum();
            
            pstmt.setInt(1, num1);
            
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                String name = rs.getString("Name");
                int age = rs.getInt("Age");
                
                System.out.println(name);
                System.out.println(age);
            }
            
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                }catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if(pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.err.println("PreparedStatementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if( con != null) {
                try {
            con.close();
                } catch (SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }
        }
    }

    private static String keyIn() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        } catch (IOException e) {
            
        }
        return line;
    }
    
    private static int keyInNum() {
        int result = 0;
        try {
            result = Integer.parseInt(keyIn());
        } catch (NumberFormatException e) {
        }
        return result;
    }
}
