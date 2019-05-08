package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import etc.EtcService;


public class EtcController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EtcController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length() + 1);
		
		EtcService etcService = new EtcService();
		if(command.equals("pwsend")) {
			etcService.pwSend(request);
		}else if(command.contentEquals("proxy")) {
			//System.out.println("proxy 요청");
			//서비스 메소드 호출
			String result = etcService.proxy(request);
			//데이터를 결과 페이지에 출력
			request.setAttribute("result", result);
			//결과 페이지로 포워딩
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher("proxy.jsp");
			dispatcher.forward(request, response);
		}else if(command.equals("push")){
			//푸시 메소드 호출
			etcService.push(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}





