package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public class HelloController {

	//hello.do라는 요청이 url를 통하여 들어왔을때.. 아래의 메소드를 실행 시키시오
	@RequestMapping("/hello.do")
	public ModelAndView printHello(){
		
		//MVC중에.. View페이지( HelloPrint.jsp)로 넘겨줄 응답값 만들기
		String data = "Hello World~";
		
		//View페이지로 데이터를 넘기기 위한 저장소 객체 생성
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("data", data);  //"Hello World~"데이터를 아래의 HelloPrint.jsp로 보내주기 위해..
									  // 모델 앤드 뷰 객체에 담기 
		mav.setViewName("HelloPrint");//확장자명 .jsp를 제외한 파일명 작성
		
		return mav; 
	}
	
	
	
	
}



