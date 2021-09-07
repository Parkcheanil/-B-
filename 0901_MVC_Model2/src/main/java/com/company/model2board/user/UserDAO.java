package com.company.model2board.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.company.model2board.common.JDBCUtil;

public class UserDAO {
	
	//H2 DB 관련 변수 선언
		private Connection conn = null;
		private PreparedStatement pstmt = null;
		private ResultSet rs = null;
		
		//SQL 명령어
		private final String USER_GET 
					= "select id, password from users where id=? and password=?";
		
		//로그인 사용자 조회(select) 메소드 구현 => 로그인 인증처리
		public UserDO getUser(UserDO userObj) {
			//[중요] 인증에 '성공' 하면 user 객체 생성, '실패'하면 null이 되겠금 하기 위해서...
			UserDO user = null;
			
			try {
				System.out.println("===> JDBC로 getUser()로 기능처리");
				
				conn = JDBCUtil.getConnection();
				pstmt = conn.prepareStatement(USER_GET);
				pstmt.setString(1, userObj.getId());
				pstmt.setString(2, userObj.getPassword());
				rs = pstmt.executeQuery();	//결과는 레코드 하나이다.
				
				if(rs.next()) {
					user = new UserDO();
					user.setId(rs.getString("ID"));
					user.setPassword(rs.getString("PASSWORD"));
				}

			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				JDBCUtil.close(rs, pstmt, conn);
			}
			return user;
		}
}
