package com.company.MVC_FW_Board.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.company.MVC_FW_Board.common.JDBCUtil;


public class BoardDAO {

	//H2 DB 관련 변수 선언
		private Connection conn = null;
		private PreparedStatement pstmt = null;
		private ResultSet rs = null;
			
		//전체 게시글 목록 조회 메소드 구현  => [핵심] 
		public List<BoardDO> getBoardList(String searchField, String searchText){
			System.out.println("===> getBoardList() 기능 처리");
			
			List<BoardDO> boardList = new ArrayList<BoardDO>();
			
			try {
				conn = JDBCUtil.getConnection();
				
				//[핵심]
				String where = "";
				
				if(searchField !=  null && searchText != null) {
					where = "where " +  searchField + " like '%"+searchText+"%'";
				}
				String Condition_SQL = "select*from board " + where + " order by seq desc";
				pstmt = conn.prepareStatement(Condition_SQL);
				
				rs = pstmt.executeQuery();		//결과 레코드가 여러개이다.
				
				while(rs.next()){
					//BoardDo 객체 생성
					BoardDO board = new BoardDO();
					board.setSeq(rs.getInt("SEQ"));
					board.setTitle(rs.getString("TITLE"));
					board.setWriter(rs.getString("WRITER"));	
					board.setContent(rs.getString("CONTENT"));
					board.setRegdate(rs.getDate("REGDATE"));
					board.setCnt(rs.getInt("CNT"));
					
					boardList.add(board);
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				JDBCUtil.close(rs, pstmt, conn);
			}
			return boardList;
		}	//end getBoardList()===========================
		
		//게시글 상세보기 메소드 구현
		public BoardDO getBoard(BoardDO boardDO) {
			System.out.println("===> JDBC로 getBoard() 메소드 기능 처리함.");
		
			BoardDO board = null;
			
			try {
				conn = JDBCUtil.getConnection();
				
				//1. select 작업전 '조회수' 증가 작업.
				String UPDATE_CNT = "update board set cnt=cnt+1 where seq=?";
				pstmt = conn.prepareStatement(UPDATE_CNT);
				pstmt.setInt(1, boardDO.getSeq());
				pstmt.executeUpdate();
				
				//2. select 작업 - 게시글 가져오기
				String BOARD_GET = "select * from board where seq=?";
				pstmt = conn.prepareStatement(BOARD_GET);
				pstmt.setInt(1, boardDO.getSeq());
				
				rs = pstmt.executeQuery();
				if(rs.next()) {
					board = new BoardDO();
					board.setSeq(rs.getInt("seq"));
					board.setTitle(rs.getString("title"));
					board.setWriter(rs.getString("writer"));
					board.setContent(rs.getString("content"));
					board.setRegdate(rs.getDate("regdate"));
					board.setCnt(rs.getInt("cnt"));
				}
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				JDBCUtil.close(rs, pstmt, conn);
			}
			return board;
		}
		
		//게시글 수정 메소드
		public void updateBoard(BoardDO boardDO) {
			System.out.println("===> JDBC로 updateBoard() 기능 처리");
			
			try {
				conn = JDBCUtil.getConnection();
				
				String BOARD_UPDATE = "update board set title=?, content=? where seq=?";
				pstmt = conn.prepareStatement(BOARD_UPDATE);
				pstmt.setString(1, boardDO.getTitle());
				pstmt.setString(2, boardDO.getContent());
				pstmt.setInt(3, boardDO.getSeq());
				pstmt.executeUpdate();
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				JDBCUtil.close(rs, pstmt, conn);
			}
		}

		//게시글 업데이트 메소드
		public void insertBoard(BoardDO boardDO) {
			System.out.println("===> JDBC로 insertBoard() 기능 처리");
			
			try {
				conn = JDBCUtil.getConnection();
				
				String BOARD_INSERT = "insert into board(seq, title, writer, content)"
									+ " values((select nvl(max(seq),0)+1 from board),?,?,?)";
				pstmt = conn.prepareStatement(BOARD_INSERT);
				pstmt.setString(1, boardDO.getTitle());
				pstmt.setString(2, boardDO.getWriter());
				pstmt.setString(3, boardDO.getContent());
				pstmt.executeUpdate();
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				JDBCUtil.close(pstmt, conn);
			}
		}
		
		//게시글 삭제 메소드
		public void delectBoard(BoardDO boardDO) {
			System.out.println("===> JDBC로 delectBoard() 기능 처리");
			
			try {
				conn = JDBCUtil.getConnection();
				
				String BOARD_DELETE = "delete from board where seq=?";
				pstmt = conn.prepareStatement(BOARD_DELETE);
				pstmt.setInt(1, boardDO.getSeq());
				pstmt.executeUpdate();
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				JDBCUtil.close(pstmt, conn);
			}
		}
}
