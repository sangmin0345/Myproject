package model;

import java.util.List;
import javax.security.auth.Subject;
import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

// 참고!
// 데이터베이스 연결은 이미 shopping-servlet.xml에서 DriverManagerDataSource객체가 연결해 놓았다.

// 클래스 역할 : 미리 연결된 데이터베이스에 쿼리를 실행하여 결과를 리턴받아주는 클래스

public class ShoppingDAO {
	
	// 쿼리를 실행시켜서 데이터를 가져올 수 있도록 해주는 객체를 담을 변수 선언 스프링용
	SimpleJdbcTemplate template;
	
	// 설명
	// shopping-servlet.xml에 의존관계를 이용하여..
	// <bean id="shoppingDAO" class="model.ShoppingDAO" p:dataSource-ref="dataSource"/>
	// 등록해놓았기때문에
	// 데이터베이스에 접근하여 데이터를 읽어 가져올 수 있는 커넥션풀 객체를 담을 참조변수 선언
	DataSource dataSource;
	// 참고! 변수선언시 p:dataSource-ref중!! dataSource이름과 같이 변수선언을 해줘야 한다.
	
	// setter메서드
	// 설정 취득객체를 생성하지않고 dataSource사용
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		
		// 커넥션풀을 JdbcTemplate객체를 생성할때 생성자를 이용하여 저장
		template = new SimpleJdbcTemplate(dataSource);
	}

	// db로부터 검색한 모든 수작업 공구 레코드 하나하나를 subean객체에 담아서.. 다시 -> arraylist에 저장하여..
	// arraylist자체를 리턴
	public List<SuBean> getAllSutool() {

		/*기존방식 모델1방식,모델2방식에서라면?.......*/
		
		//해야 했던일1. DB에서 검색한 결과레코드를  각각의 SuBean객체에 담고....ArrAyList객체에 SuBean객체를 각각 담아..
		//List형태로 리턴하기위해 ArrayList객체생성 해야 한다.
		
		//해야 했던일2. DB에서 검색한 결과레코드를 담을 SuBean객체 생성해야 함.	
		//해야 했던일 3.  커넥션 풀에서 DB연결 객체를 빌려와서 준비 ( DB연결)	
		//해야 했던일 4.  DB에서  모든 수작업공구 검색  select쿼리  만들어야 함.		
		//해야 했던일 5.  PreparedStatement객체를 이용하여 select실행 해야함.		
		//해야 했던일 6.   select한 결과를  테이블형식으로  ResultSet에 저장 해야함.		
		//해야 했던일 7.  while문을이용하여  rs.next()이용하여  하나씩 컬럼에 있는 데이터를 SuBean객체에 맵핑하여 저장 해야함.		
		//해야 했던일8.   select하여 검색한 결과를 저장한 SuBean객체를 다시.. ArrayList컬렉션객체에  add시키고....		
		//해야 했던일9.   모든 while문을 돌고난후  return list; 해야함.
		//---------------------------------------------------------------------------------------
		/*스프링 방식*/		
		
		
		String sql = "select * from sutool";
		
		// RowMapper : 쿼리 결과를 객체로 변환
		
		// BeanPropertyRowMapper클래스 역할 :
		// SuBean클래스의 변수명과 DB의 SUTOOL테이블의 컬럼명을 매칭한 객체 생성
		RowMapper<SuBean> rm = new BeanPropertyRowMapper<SuBean>(SuBean.class);
		
		// template객체를 이용하여 sql구문을 실행시킬때..
		// query()메서드에 실행할 select구문과, db의 sutool테이블의 컬럼명과 매칭한 객체를 전달하여
		// select한 결과 레코드 갯수만큼 list담아.. list자체를 리턴해준다.
		// 또한! 리턴 받은 list를 또다시~ getAllSutool()메서드를 호출한 곳으로 리턴함.
		return template.query(sql, rm); // list 리턴
		
	}
	
	// 왼쪽 카테고리 메뉴중 선택된 수작업 공구를 리턴해주는 메서드
	public List<SuBean> getSelectSutool(String num) { // 왼쪽 카테고리 메뉴중 선택된 메뉴 번호
		
//		String sql = "select * from sutool where sucate = "+num;
		String sql = "select * from sutool where sucate = ?";
		
		RowMapper<SuBean> rm = new BeanPropertyRowMapper<SuBean>(SuBean.class);
		
//		return template.query(sql, rm);
		return template.query(sql, rm, num);
	}

	public SuBean getOneSutool(int suno) {
		
		String sql = "select * from sutool where suno = " + suno;
		
		// template 객체를 이용하여 쿼리를 실행시킬때..
		// queryForObject메서드에 실행할 select문과 db의 sutool테이블의 컬럼명과 매칭한 객체를 전달하여
		// select 한 결과는 subean객체이지만 최고 조상클래스인 object타입으로 리턴받는다
		// 또한 리턴받은 subean객체를 또다시 getOneSutool메서드를 호출한 곳으로 리턴함
		return template.queryForObject(sql, new BeanPropertyRowMapper<SuBean>(SuBean.class));
	}

	public int getLogin(MemberBean mbean) {
		
		String sql = "select count(*) from member where memid = ?";
		
		int result = template.queryForInt(sql, mbean.getMemid());
		
		return result;
	}

	public void insertMember(MemberBean mbean) {

		String sql = "insert into member values (:memid, :mempasswd1, :mempasswd2,"
				+ ":memname, :memphone, :memdate)";
		
		// MemberBean클래스가 insert쿼리구문에 1:1맵핑되도록 자동으로 설정
		SqlParameterSource sqlSource = new BeanPropertySqlParameterSource(mbean);
		
		// insert
		template.update(sql, sqlSource);
	}

	// 로그인 처리시 아이디와 패스워드가 있는지 여부확인
	// - LoginForm.jsp에서 작성한 아이디와 패스워드를 매개변수로 받음
	// 회원수를 검색하는데 성공하면 1을 반환 실패하면 0을 반환
	public int getLoinProc(MemberBean mbean) {

		String sql = "select count(*) from member where memid = ? and mempasswd1 = ?";
		
		return template.queryForInt(sql, mbean.getMemid(), mbean.getMempasswd1());
	}

	
	// 게시판의 글의 갯수 리턴
	public int getCount() {
		
		return template.queryForInt("select count(*) from board");
	}

	public List<BoardBean> getAllContent(int startRow, int endRow) {
		
		String sql = "select * from (select A.*, Rownum Rnum from "
				+ "(select * from board order by ref desc, re_level asc)A) "
				+ "where Rnum > ? and Rnum <= ?";
		
		RowMapper<BoardBean> rm = new BeanPropertyRowMapper<BoardBean>(BoardBean.class);
		
		return template.query(sql, rm, startRow-1, endRow);
	}
	
	
	

	
	
	
	
	
}
