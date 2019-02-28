package com.netease.websocketclientdemo;

import com.netease.websocketclientdemo.component.HelloClientEndpoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebsocketClientDemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void socket() throws URISyntaxException, IOException, DeploymentException, InterruptedException {


        int num = 5000;
        List<Session> clientList = new ArrayList<>();
        for (int i = 0; i < num; ++i) {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            Session session = container.connectToServer(HelloClientEndpoint.class, new URI("ws://10.240.171.10:8085/api/v1/chat/1/client" + i));
            clientList.add(session);
        }
        while (true) {
            for (int i = 0; i < num; ++i) {
                Session session = clientList.get(i);
                session.getBasicRemote().sendText("client+" + i);
            }
            Thread.sleep(2000);
        }
    }

}
