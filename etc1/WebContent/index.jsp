<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
</head>
<body>
	<!-- 채팅 작성자를 구분하기 위한 입력장치 상자  - 로그인이 있으면 필요 없음 -->
	닉네임:<input type="text" id="nickname" size="30"/><br/>
	<!-- 전송할 메시지를 입력하고 전송에 이용할 버튼 영역 -->
	메시지:<input type="text" id="msg" size="30"/>
	<input type="button" value="전송" id="sendbtn"/><br/>
	<!-- 전송받은 메시지를 출력할 영역 -->
	받은 메시지<textarea id="disp" rows="20" cols="50"></textarea>

	<p><a href="pwsend"><img src="img/aqua.jpg" width="100px" height="30px" />
	</a></p>
	<!-- a 태그에서 href에 #을 사용하면 페이지 이동이 아니고 클릭이벤트를 사용  -->
	<p><a href="#" id="proxylink">proxy 요청</a></p>
	<p><input type="button" id="proxybtn" value="proxy 요청" /></p>
	
	<p><input type="button" id="pushbtn" value="web push 요청" /></p>
	<img id="pushdisp"></img>
	
	<script>
	var ar = ["피카추1.png","피카추2.png","피카추3.jpg"]
	
	
		document.getElementById("pushbtn").addEventListener("click",function(e){
			//푸시 이벤트 등록
			var eventSource = new EventSource("push")
			//서버로부터 메시지가 왔을 때 처리
			eventSource.addEventListener('message',function(e1){
				document.getElementById('pushdisp').src = ar[parseInt(e1.data)]
			})
			
			for(var attr in eventSource){
				console.log(attr)
			}
			
		})
		
	</script>
	
	
	<!-- 외부 스크립트를 사용하기 위한 설정 -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
	<!-- 내부 스크립트 -->
	<script>
		document.getElementById("proxylink").addEventListener(
				'click', function(e){	
			//ajax요청 - jquery 이용
			$.ajax({
				url:'proxy',
				data:{'addr': 'http://www.kma.go.kr/weather/forecast/mid-term-xml.jsp?stnId=109'},
				dataType:'xml',
				success:function(data){
					//data 태그를 찾아와서 순회
					$(data).find('data').each(function(idx,item){
						output = '<p>'
						output += '날짜:' + $(this).find('tmEf').text() + '<br/>'
						output += '날씨:' + $(this).find('wf').text() + '<br/>'
						output += '최고온도:' + $(this).find('tmx').text() + '<br/>'
						output += '최저온도:' + $(this).find('tmn').text()
						output += '</p>'
						$('body').html($('body').html() + output)
					})
				}
			})
		})
	</script>
	
	<script>
		//웹소켓 서버에 접속
		var webSocket = new WebSocket("ws://192.168.0.118:8080/etc1/websocket")
		
		//서버와 연결 되었을 때 수행할 내용
		webSocket.addEventListener("open",function(e){
			//전송 버튼을 누를 때 
			document.getElementById("sendbtn").addEventListener("click",function(e){
				//msg에 입력된 내용을 웹 소케서버에 전송
				webSocket.send(document.getElementById('nickname').value + ':' +
						document.getElementById('msg').value)
			})
		})
		//서버에게서 메시지를 받았을 때 수행할 내용
		webSocket.addEventListener("message",function(e){
		//새로 전송되어온 메시지를 출력하고 그 아래에 기존 메시지를 출력
		document.getElementById("disp").value = e.data + "\n" + document.getElementById("disp").value
		})
	
	</script>
	
	
	
	
</body>
</html>






