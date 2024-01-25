package team.luckyturkey.projectservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.test.util.TestSocketUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import team.luckyturkey.projectservice.controller.configuration.DispatcherServletInitializer;
import team.luckyturkey.projectservice.controller.configuration.TomcatWebSocketTestServer;
import team.luckyturkey.projectservice.controller.requestdto.ProjectUpdateRequestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;


@Slf4j
public class StompWebSocketTest {
    private static int port;

    private static TomcatWebSocketTestServer server;
    private static SockJsClient sockJsClient;
    private final static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

    @BeforeEach
    void setup() throws Exception {

        System.setProperty("spring.profiles.active", "test.tomcat");
        port = TestSocketUtils.findAvailableTcpPort();

        server = new TomcatWebSocketTestServer(port);

        server.deployWithInitializer(TestDispatcherServletInitializer.class);
        server.start();

        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        RestTemplateXhrTransport xhrTransport = new RestTemplateXhrTransport(new RestTemplate());
        transports.add(xhrTransport);

        sockJsClient = new SockJsClient(transports);
    }

    @Test
    public void pubSubTest() throws Exception {

        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicReference<Throwable> failure = new AtomicReference<>();
        final Long projectId = 1L;
        StompSessionHandler handler = new AbstractTestSessionHandler(failure) {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {


                /**
                 * @deprecated : 아래 코드는 사용자가 구독한 Topic에 대해 올바르게 subscribe가 되었는지 확인하는 코드입니다.
                 *          하지만, 현재 서비스에는 사용자가 구독하는 시나리오가 없기 때문에, 사용하지 않는 테스트 입니다.
                 * */
                /*
                session.subscribe("/consume/" + projectId, new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return ProjectUpdateRequestDto.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        ProjectUpdateRequestDto receivedData = (ProjectUpdateRequestDto) payload;
                        log.debug("Got {}", receivedData.toString());

                        try{
                            Assertions.assertThat(1L).isIn(receivedData.getSourceIdList());
                            Assertions.assertThat(2L).isIn(receivedData.getSourceIdList());
                            Assertions.assertThat(3L).isIn(receivedData.getSourceIdList());
                        }
                        catch (Throwable t){
                            failure.set(t);
                        }
                        finally {
                            session.disconnect();
                            latch.countDown();
                        }
                    }
                });
                */

                try {
                    ProjectUpdateRequestDto projectData = ProjectUpdateRequestDto.builder()
                            .sourceIdList(List.of(1L, 2L, 3L))
                            .build();
                    log.info("sending data with source id list = {}", projectData);
                    session.send("/produce/message/" + projectId, projectData);
                }
                catch (Throwable t) {
                    failure.set(t);
                    latch.countDown();
                }
                finally {
                    latch.countDown();
                }
            }
        };

        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        log.info("now connecting...");
        stompClient.connectAsync("ws://localhost:{port}/project", headers, handler, port);
        log.info("connecting done...");

        if (failure.get() != null) {
            throw new AssertionError("", failure.get());
        }
    }


    private static abstract class AbstractTestSessionHandler extends StompSessionHandlerAdapter {

        private final AtomicReference<Throwable> failure;


        public AbstractTestSessionHandler(AtomicReference<Throwable> failure) {
            this.failure = failure;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            log.error("STOMP ERROR frame: " + headers.toString());
            this.failure.set(new Exception(headers.toString()));
        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
            log.error("handle exception" , exception);
            this.failure.set(exception);
        }

        @Override
        public void handleTransportError(StompSession session, Throwable exception) {
            log.error("Transport Error" , exception);
            this.failure.set(exception);
        }
    }


    private static class TestDispatcherServletInitializer extends DispatcherServletInitializer {

    }
}
