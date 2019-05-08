package etc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.MultiPartEmail;

public class EtcService {
	//랜덤한 문자 5개를 만들어서 메일로 보내주는 메소드
	public void pwSend(HttpServletRequest request) {
		//랜덤하게 생성할 문자
		char [] ar = {'A', 'a', '!', '1', 'S'};
		
		//위 배열에서 랜덤한 5개 문자열 만들기
		String password = "";
		Random r = new Random();
		for(int i=0; i<5; i=i+1) {
			password = password + ar[r.nextInt(ar.length)];
		}
		
		//메일 전송 객체 만들기 - 파일 전송도 가능한 메일 객체 생성
		MultiPartEmail email = new MultiPartEmail();
		//보내는 서버 설정
		email.setHostName("smtp.naver.com");
		email.setSmtpPort(587);
		email.setAuthentication("ggangpae3", "Dokki1111!");
		email.setSSLOnConnect(true);
		email.setStartTLSEnabled(true);
		email.setCharset("utf-8");
		try {
			email.setFrom("ggangpae3@naver.com", "관리자", "utf-8");
			//제목 설정
			email.setSubject("새로운 비밀번호");
			//본문설정
			email.setMsg(password);
			//받는 사람 설정
			email.addTo("ggangpae1@gmail.com", "박문석", "utf-8");
			//전송
			email.send();
			
		}catch(Exception e) {
			System.out.println("메일보내기 실패:" + e.getMessage());
		}
		
	}


	//요청하는 곳에서 addr 이라는 파라미터에 넘겨준 url의 문자열을 읽어서 리턴하는 메소드
	public String proxy(HttpServletRequest request) {

		String result = null;
		
		try {
			//데이터를 받아올 주소 가져오기
			String addr = request.getParameter("addr");
			//파라미터 전달이 제대로 되는지 확인
			//System.out.println(addr);
			
			//위의 주소를 URL로 만들기
			URL url = new URL(addr);
			//HttpURLConnection 만들기
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			//Connection 옵션 설정
			con.setConnectTimeout(30000);
			con.setUseCaches(false);
			
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
			while(true) {
				String line = br.readLine();
				if(line == null) {
					break;
				}
				sb.append(line + "\n");
			}
			br.close();
			con.disconnect();
			result = sb.toString();
			
		}catch(Exception e) {
			System.out.println("데이터 가져오기 예외:" + e.getMessage());
			e.printStackTrace();
		}
		//System.out.println(result);
		return result;
	}
	
	//푸시를 전송할 메소드 : 5초마다 1-100 사이의 내용을 전송
	public void push(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			Random r = new Random();
			int su = r.nextInt(3);
			Thread.sleep(1000);
			
			//푸시를 위한 설정
			response.setContentType("text/event-stream");
			response.setCharacterEncoding("utf-8");
			//메시지 작성
			PrintWriter pw = response.getWriter();
			pw.write("data:" + su + "\n\n");
			pw.close();
			
		}catch(Exception e) {
			System.out.println("푸시에러:" +e.getMessage());
		}
		
		
	}
	
}











