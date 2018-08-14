package com.player.act;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PlayerFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) 
			   throws ServletException, IOException {
		String uri = request.getRequestURI();
//		 System.out.println(uri);

		String contextPath = request.getContextPath();
		// System.out.println(contextPath.length());

		String command = uri.substring(contextPath.length());

		/* 주소 비교 */
		// 페이지 이동 방식 여부 값,이동페이지 경로 값 저장 하여 리턴 해주는 객체를 저장할 참조변수 선언
		ActionForward forward = null;

		// 자식 Action 객체들을 담을 인터페이스 타입의 참조변수 선언
		Action action = null;

		if (command.equals("/BatPlayerListAction.play")) {

			action = new BatPlayerListAction();

			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// forward.setPath("/playerStats.jsp");

		}else if (command.equals("/PitchPlayerListAction.play")) {
			action = new PitchPlayerListAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

		if(forward!=null){
			if(forward.isRedirect()){//true
				response.sendRedirect(forward.getPath());
			}else{//false
				RequestDispatcher dispatcher=request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}

	}

}
