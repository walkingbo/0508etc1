package etc;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

//1.모든 클라이언트의 정보를 저장할 List를 생성
//2.클라이언트가 접속할 때  그 클라이언트를 List에 추가
//3.클라이언트가접속해제 될 때 List에서 제거
//4.클라이언트가 메시지를 전송할 때 그 메시지를 List의 모든 클라이언트에 전


@ServerEndpoint("/websocket")
public class WebSocketService {
	//1.모든 클라이언트의 정보를 저장할 List를 생성
	private static List<Session>list = new ArrayList<>();
	//클라이 언트가 연결 되었을 때 호출되는 메소드
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("클라이언트가 접속되었습니다.");
		list.add(session);
	}
	//클라이 언트가 연결 해제되었을 때 호출되는 메소드
	@OnClose
	public void onClose(Session session) {
		System.out.println("클라이언트가 접속해제되었습니다.");
		list.remove(session);
		}
	//클라이언트가 메시지를 전송했을 때 호출되는 메소드
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("클라이언트가 보낸 메시지:"+ message);
		//메시지 되돌려 보내기
		try {
			for(Session s : list) {
				s.getBasicRemote().sendText(message);
			}
		}catch(Exception e) {
			System.out.println("전송실패:	" + e.getMessage());
			e.printStackTrace();
		}
	}
}
