package org.spring.groupAir.chat;

import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@ClientEndpoint
@ServerEndpoint(value = "/chat")
public class ChatConfig {

    // 클라이언트 정보나 메세지를 set 설정
    // 채팅창에 메세지를 반환
    // 클라이언트 세션 정보를 가지고 있다, 접속(1), 해제 삭제
    private static Set<Session> clientInfo =
            Collections.synchronizedSet(new HashSet<Session>()); // 세션을 적절히 관리

    @OnOpen // 접속 시 // 접속자 세션 설정
    public void onOpen(Session session) {
        System.out.println(" Session Start : " + session.toString());
        if (!clientInfo.contains(session)) {
            // 저장되어 있는 세션 정보가 없다면
            // 세션 설정
            clientInfo.add(session);
            System.out.println(" Session open : " + session);
        } else {
            System.out.println(" Pre Session ! ");
        }
    }

    @OnMessage // 클라이언트 메세지를 수신할 때 // 접속자 session 에 대한 메시지
    public void onMessage(String message, Session session) throws Exception {

        System.out.println(" Receive Message : " + message);
        for (Session session1 : clientInfo) {
            System.out.println(" Trans Data : " + message);
            // .getBasicRemote() -> 전송자 URL
            session1.getBasicRemote().sendText(message);
        }
    }

    @OnClose // 접속 해제시 // 접속자 : 해제 설정
    public void onClose(Session session) {
        System.out.println(" Session Close : " + session);
        clientInfo.remove(session); // 세션 삭제
    }

    @OnError // 에러 발생 시
    public void handleError(Throwable throwable) {
        System.out.println(" WebSocket Error ! : ");
        throwable.printStackTrace();
    }

}
