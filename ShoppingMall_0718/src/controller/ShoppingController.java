package controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import model.BoardBean;
import model.MemberBean;
import model.ShoppingDAO;
import model.SuBean;
import model.SuCartBean;

public class ShoppingController {

	// 설명 : 
	// shopping-servlet.xml에 의존관계를 이용하여.. <bean>태그를?
	// <bean id="sujak" name="/sujak.do" class="controller.ShoppingController"
	// p:shoppingDAO-ref="shoppingDAO" /> 라고 해놓았기 때문에..
	// shoppingDAO 객체를 가져올 수 있다.
	ShoppingDAO shoppingDAO; // db와 연동된 db작업할 객체를 가져와서 저장할 변수 선언
	
	// 설정 취득
	public void setShoppingDAO(ShoppingDAO shoppingDAO){
		this.shoppingDAO = shoppingDAO;
	}
	
	
	
	@RequestMapping("/sujak.do")
	public ModelAndView suJak(String num){
		
		ModelAndView mav = new ModelAndView();
		
		if(num == null){ // left메뉴인 ? 신제품메뉴를 누르거나 top.jsp페이지의 수작업공구 메뉴를 눌렀을때
			
			// db에 접근하여.. 검색한 모든 수작업 공구를 가져와야 한다.
			List<SuBean> list = shoppingDAO.getAllSutool();
			// db로부터 검색한 모든 수작업공구들(arraylist에 담겨있는 subean객체들)을 modelandview객체에 담기
			mav.addObject("list", list);			
			
		} else { // left화면의 메뉴중.. 하나의 메뉴를 선택했을때..
			
			List<SuBean> list = shoppingDAO.getSelectSutool(num);

			mav.addObject("list", list);
			
		}
		
		// center화면으로 "SujakCenter.jsp"문자열을..
		// ShoppingMain.jsp로 보내주기 위해.. modelandview객체에 저장
		mav.addObject("center", "SujakCenter.jsp");
		mav.addObject("left", "SujakLeft.jsp");
		
		// 이동할 뷰페이지명 => ShoppingMain.jsp페이지명 셋팅
		mav.setViewName("ShoppingMain");
		
		return mav;
	} // sujak
	
	
	// SujakCenter.jsp에서 수작업공구 이미지 하나를 클릭했을때.. (상세보기 요청이 들어왔을때)
	// 공구번호를 전달받아.. 공구하나의 정보를 db로부터 검색해서 View화면에 보여주는 메서드를 실행하자
	@RequestMapping("/suinfo.do")
	public ModelAndView suInfo(int suno){
		
		// 상세볼 공구번호를 전달하여 db에서 검색한 하나의 수작업공구 정보를 담고 있는 subean객체를 리턴받음
		SuBean sbean = shoppingDAO.getOneSutool(suno);
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("sbean", sbean);
		
		// center화면으로 "SujakInfo.jsp"문자열을 shoppingmain페이지로 보내주기위해
		// mav객체 저장
		mav.addObject("center", "SujakInfo.jsp");
		
		mav.addObject("left", "SujakLeft.jsp");
		
		mav.setViewName("ShoppingMain");
		
		return mav;
	}
	
	@RequestMapping("/sutoolcart.do")
	public ModelAndView sutoolCart(SuCartBean cartbean, HttpSession session){
		
//		System.out.println(cartbean.getSuname());
		
		// 기존의 Cart객체를 사용하기 위해서 session에 저장되어있는 cart객체 꺼내오기
		// 참고! Cart클래스안에 ArrayList객체가 담겨있다.
		// 참고! 처음에는 session영역안에 Cart객체가 없어서 null을 리턴받는다
		Cart cart = (Cart)session.getAttribute("cart");
		
		// session에서 꺼내온 Cart객체가 없다면?
		if(cart == null){
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		
		// 매개변수로 넘어온 ArrayList에 추가할 상품을 Cart객체의 메서드를 호출하여 전달
		cart.push(cartbean);
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("msg", cartbean.getSuname() + "의 상품을 " + cartbean.getSuqty() + "개 카트에 추가했습니다.");
		// Cart객체에 저장되어 있는 ArrayList넘기기위해.. cart객체자체를 mav에 담기
		mav.addObject("cart", cart);
		// 카트상품추가 내용 확인 페이지명 담기 - center
		mav.addObject("center", "SuCartResult.jsp");
		// left
		mav.addObject("left", "SujakLeft.jsp");

		mav.setViewName("ShoppingMain");
		
		return mav;
	}
	
	
	@RequestMapping("/sucartdel.do")
	public ModelAndView sucartDel(int suno, HttpSession session){
		
		// 세션영역에 저장되어 있는 카트객체를 사용하기 위해 Cart객체를 꺼내옴
		Cart cart = (Cart)session.getAttribute("cart");
		
		// 카트에 상품 삭제
		cart.deleteCart(suno);
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("cart", cart);
		
		mav.addObject("center", "SuCartResult.jsp");
		mav.addObject("left", "SujakLeft.jsp");
		
		mav.setViewName("ShoppingMain");
		
		return mav;
	}
	
	@RequestMapping("/joinform.do")
	public ModelAndView joinForm(){
		ModelAndView mav = new ModelAndView();

		mav.addObject("center", "JoinForm.jsp");
		mav.addObject("left", "SujakLeft.jsp");
		
		mav.setViewName("ShoppingMain");
		
		return mav;
	}
	
	// 회원가입
	// 회원의 정보를 담고 있는 MemberBean객체를 매개변수로 전달받아..
	// memberbean객체를 이용하여 데이터베이스에 저장하는 메서드
	@RequestMapping("/joinproc.do")
	public ModelAndView joinProc(MemberBean mbean, HttpSession session){
		
		// 작성한 회원정보 데이터 + view페이지명을 저장하여 전달할  modelandview객체 생성
		ModelAndView mav = new ModelAndView();
		
		// db에 접근해서 해당 아이디가 존재하는지와 패스워드가 맞는지 비교를 위해
		// 리턴값 1 => 해당아이디가 존재 함.
		int result = shoppingDAO.getLogin(mbean);
		
		if(result == 1){
			mav.addObject("result", "1");
			mav.addObject("center", "JoinForm.jsp"); // <-- 가서 1을 받자~
			mav.addObject("left", "SujakLeft.jsp"); 
			mav.setViewName("ShoppingMain");
		} else {
			if(mbean.getMempasswd1().equals(mbean.getMempasswd2())){
				
				shoppingDAO.insertMember(mbean);
				
				// 세션영역에 가입한 회원정보 저장
				session.setAttribute("mbean", mbean);
				
				session.setMaxInactiveInterval(60*30); // 30분
				
				return new ModelAndView(new RedirectView("index.do"));
			} else {
				mav.addObject("result", "2");
				mav.addObject("center", "JoinForm.jsp"); // <-- 가서 2을 받자~
				mav.addObject("left", "SujakLeft.jsp");
				mav.setViewName("ShoppingMain");
			}
		}
		
		return mav;
	}
	
	@RequestMapping("/login.do")
	public ModelAndView login(){
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("center", "LoginForm.jsp");
		mav.addObject("left", "SujakLeft.jsp");
		mav.setViewName("ShoppingMain");
		
		return mav;
	}
	
	
	@RequestMapping("/loginproc.do")
	public ModelAndView loginProc(HttpSession session, MemberBean mbean){
		// 참고! 아이디, 패스워드는 따로따로 매개변수로 받아도 되지만,
		// MemberBean에 자동으로 MemberBean객체를 넘겨받음
		
//		System.out.println(mbean.getMemid());
//		System.out.println(mbean.getMempasswd1());
		
		// 데이터베이스에  접근하여 해당 아이디와 패스워드가 있는지를 알려주는 메서드 호출 후 결과를 저장
		int result = shoppingDAO.getLoinProc(mbean);
		
		ModelAndView mav = new ModelAndView();

		if(result == 1) { // 회원이 존재
			session.setAttribute("mbean", mbean); // 세션값 저장
			session.setMaxInactiveInterval(60*30); // 30분
			
			return new ModelAndView(new RedirectView("index.do"));
		} else { // 회원이 없음 .. LoginForm.jsp로 이동하면서 1을 전달
			mav.addObject("center", "LoginForm.jsp");
			mav.addObject("left", "SujakLeft.jsp");
			mav.setViewName("ShoppingMain");
			mav.addObject("login", "1");
			
			return mav;
		}
		
	}
	
	
	@RequestMapping("/logout.do")
	public ModelAndView logOut(HttpSession session) {
		
		MemberBean mbean = (MemberBean)session.getAttribute("mbean");
		
		session.setAttribute("mbean", null);
//		session.invalidate();
		
		
		return new ModelAndView(new RedirectView("index.do"));
	}
	
	
	@RequestMapping("/sutoolbuy.do")
	public ModelAndView sutoolBuy(HttpSession session, SuCartBean suBean) {
		// sujakinfo.jsp(구매화면)에서 구매하기 버튼을 눌렀을때
		// input태그의 hidden값들을 suBean에 자동으로 저장하여
		// 위의 파라미터로 sucartbean객체를 전달 받는다
		
		ModelAndView mav = new ModelAndView();
		
		MemberBean mbean = (MemberBean)session.getAttribute("mbean");
		
		if(mbean == null){ // 로그인이 되어있지 않으면
			mav.addObject("center", "LoginForm.jsp");
			mav.addObject("left", "SujakLeft.jsp");
			mav.setViewName("ShoppingMain");
		} else {
			mav.addObject("subean", suBean);
			mav.addObject("center", "SutoolBuy.jsp"); // 결제금액을 나타내는 화면
			mav.addObject("left", "SujakLeft.jsp");
			mav.setViewName("ShoppingMain");
		}
		
		return mav;
	}
	
	// 카트에 담겨진 모든 상품 구매하기
	@RequestMapping("/sucartbuy.do")
	public ModelAndView sucartBuy(HttpSession session, SuCartBean suBean){
		
		ModelAndView mav = new ModelAndView();
		
		// 로그인한 회원정보를 세션을 통해서 얻어옴
		MemberBean mbean = (MemberBean)session.getAttribute("mbean");
		
		if(mbean == null) { // 로그인이 되어있지 않으면
			mav.addObject("center", "LoginForm.jsp");
			mav.addObject("left", "SujakLeft.jsp");
			mav.setViewName("ShoppingMain");
		} else {
			// 카트에 있는 내용을 계산하기 위해서 세션을 통하여 카트객체를 가져옴
			Cart cart = (Cart)session.getAttribute("cart");
			mav.addObject("cart", cart);
			mav.addObject("center", "SuCartBuy.jsp");
			mav.addObject("left", "SujakLeft.jsp");
			mav.setViewName("ShoppingMain");
		}
		
		return mav;
	}
	
	
	@RequestMapping("/cartaldel.do")
	public ModelAndView cartalDel(HttpSession session) {
		
		// 세션에 있는 카트 객체를 얻어옴
		Cart cart = (Cart)session.getAttribute("cart");
		
		// 카트안에 있는 모든 상품을 삭제해주는 메서드를 호출하여.. 카트 내용 비우기
		cart.clearCart();
		
		return new ModelAndView(new RedirectView("index.do"));
	}
	
	
	@RequestMapping("/stanlyinfo.do")
	public ModelAndView stanlyInfo(String name) {
		// 스탠리 소개 메뉴의 하위 메뉴 카테고리 no값 받기
		// 소개 <-- name = 0
		// 연혁 <-- name = 1
		// 글로벌 네트워크 <-- name = 2
		// 찾아오시는 길 <-- name = 3
		
		// 소개, 연혁, 글로벌 네트워크, 찾아오시는 길  각각 하위 메뉴에 해당하는 이미지 이름을 배열에 저장
		String[] imgarr = {"stanlycenterinfo", "stanlycenterhistory1", 
				"stanlycenterglobal", "stanlycentercompany"};
		
		// 연혁 메뉴 <-- 에 있는 이미지 이름 4개 배열에 저장
		String[] history = {"stanlycenterhistory1", "stanlycenterhistory2",
				"stanlycenterhistory3", "stanlycenterhistory4"};
		
		// 스탠리 소개 메뉴의 하위 메뉴 4개중 하나라도 선택하지 않았다면
		if(name == null){
			name = "0";
		}
		
		// 스탠리 소개 메뉴의 하위메뉴 4개중 연혁메뉴를 선택했다면
		if(name == "1"){
			ModelAndView mav = new ModelAndView();
			mav.addObject("name", name); // 연혁메뉴 선택 키값 1저장
			mav.addObject("history", history); // 연혁메뉴의 하위메뉴 4개의 이미지명을 담고 있는 배열 저장
			mav.addObject("center", "StanlyInfoMain.jsp");
			mav.addObject("left", "StanlyInfoLeft.jsp");
			mav.setViewName("ShoppingMain");
			
			return mav;

		} else {	// 하위 메뉴 4개중.. 글로벌네트워크 또는 찾아오시는길 메뉴중 하나를 선택했다면..
					// 글로벌 네트워크 선택 카테고리의 name 값은 ? 2
					// 찾아오시는 길 선택 카테고리의 name 값은 ? 3
			ModelAndView mav = new ModelAndView();
			mav.addObject("imgname", imgarr[Integer.parseInt(name)]);
			mav.addObject("center", "StanlyInfoMain.jsp");
			mav.addObject("left", "StanlyInfoLeft.jsp");
			mav.setViewName("ShoppingMain");
			
			return mav;
		}
	}

	
	@RequestMapping("/download.do")
	public ModelAndView download(){
		
		ModelAndView mav = new ModelAndView();

		mav.addObject("center", "DownCenter.jsp");
		mav.addObject("left", "DownLeft.jsp");
		mav.setViewName("ShoppingMain");
		
		return mav;
	}
	
	
	@RequestMapping("/downfile.do")
	public ModelAndView downFile(int no) {
		// db에 있는 다운로드할 파일명 대신에..
		// 다운로드할 파일명을 직접 배열에 담기
		String[] filename = {"m0.pdf", "m1.pdf", "m2.pdf"};
		
		// 다운로드할 팔일이 있는 서버 경로 설정
		String path = "D:/Shop/ShoppingMall/WebContent/downfile/";
		
		// 다운로드할 파일에 접근하기 위한 파일 객체 생성
		File downloadfile = new File(path + filename[no]);
		
		// 참고
		// 스프링 파일 다운로드
		// 	- 파일 다운로드를 위한 view를 jsp가 아닌 클래스로 처리
		//	- 클래스로 view처리 하기 위해 BeanNameViewResolver추가 (xml)추가
		//	- 두개 이상의 viewResolver 처리를 위해 <property>태그의 name속성의 order로 우선순위를 설정
		//	- 파일 다운로드로 사용하는 클래스를 <bean>태그로 등록(객체 생성)
		
		
		// 다운로드작업할 Controller패키지에 downloadview.java를 만들어서.. ModelAndView객체에 저장하여
		// 디스팩쳐 서블릿으로 리턴한다.
		// 그 디스팩쳐 서블릿은 shopping-sevlet.xml의 BeanNameViewResolver를 인식하여
		// 해당 downloadview.java로 이동하게 된다.
		return new ModelAndView("downloadView", "downloadFile", downloadfile);
	}
	
	
	@RequestMapping("/tooluse.do")
	public ModelAndView toolUse(String name){
		// 공구 사용법 메뉴의 하위 메뉴 카테고리 name 받기
		// 제품안전사항 <-- name = 0
		// 줄자 <-- name = 1
		// 펀치 <-- name = 2
		// 블레이드 나이프 <-- name = 3
		// 칼 <-- name = 4
		// 망치 <-- name = 5
		// 대패 <-- name = 6
		// 등등
		
		// 배열에 이미지의 이름을 미리 담아두기
		String[] imgarr = {"tool1", "tool2", "tool3", "tool4", "tool5", "tool6", "tool7", "tool8-1", "tool9", "tool10"};
		
		// 공구 사용법 메뉴의 하위 메뉴 카테고리를 선택하지 않았을때..
		if(name == null) { // 공구 사용법 메뉴만 클릭 했을때..
			name = "0";
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("imgname", imgarr[Integer.parseInt(name)]);
		mav.addObject("center", "ToolUseCenter.jsp");
		mav.addObject("left", "ToolUseLeft.jsp");
		mav.setViewName("ShoppingMain");
		
		return mav;
	}
	
	
	// 전체 게시글을 검색하여 정보를 제공하는 메서드
	@RequestMapping("/board.do")
	public ModelAndView boardList(String pageNum) { // 현재 보여지는 페이지 넘버값 받기
													// 처음에는 없음 null
		ModelAndView mav = new ModelAndView();
		
		// 화면에 보여질 게시글의 갯수를 지정
		int pageSize = 10;
		
		// 전체 글의 갯수 저장
		int count = 0;
		// 페이지 넘버링수(현재 화면에 보고 있는 페이지 넘버값)
		int number = 0;
		
		// 고객센터 메뉴를 누르면 pageNum없기때문에 null값을 처리해주어야 합니다.
		if(pageNum == null){
			pageNum = "1";
		}
		// 현재 보여지는 페이지 넘버값
		int currentPage = Integer.parseInt(pageNum);
		
		// 게시글의 총글의 갯수 얻기
		count = shoppingDAO.getCount();
		
		// 현재 페이지에 보여줄 시작 번호를 설정 = 데이터 베이스에서 불러올 시작번호를 의미
		int startRow = (currentPage - 1) * pageSize + 1;
		int endRow = currentPage * pageSize;
		
		// 가변길이로 데이터를 저장
		List<BoardBean> vbean = null;
		
		// 게시글이 존재하면 ?
		if(count > 0){
			// 10개를 기준으로 데이터를 데이터베이스에서 읽어들임
			vbean = shoppingDAO.getAllContent(startRow-1, endRow);
			// 테이블에 표시할 번호를 설정
			number = count - (currentPage - 1) * pageSize;
		}
		// BoardList.jsp
		mav.addObject("vbean", vbean);
		mav.addObject("number", number);
		mav.addObject("pageSize", pageSize);
		mav.addObject("count", count);
		mav.addObject("currentPage", currentPage);
		mav.addObject("center", "BoardList.jsp");
		mav.addObject("left", "BoardLeft.jsp");
		mav.setViewName("ShoppingMain");
		
		return mav;
	}
	
	
	
	@RequestMapping("/index.do")
	public ModelAndView index(HttpSession session){ //회원가입정보를 사용하기 위하여 세션을 설정
		
		ModelAndView mav = new ModelAndView();
		
		//회원정보를 사용하기위해여 세션객체를 불러옴
		MemberBean mbean = (MemberBean)session.getAttribute("mbean");
		
		//세션을 이용하여 로그인 처리 
		if(mbean == null){ //top.jsp에서 로그인 정보를 처리 하기 위한 소스
			mav.addObject("mbean", null);
			mav.setViewName("ShoppingMain");
		}else {
			mav.addObject("mbean", mbean);
			mav.setViewName("ShoppingMain");	
		}
		return mav;
	}
	
	
	
}





