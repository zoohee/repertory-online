package team.luckyturkey.projectservice.controller.configuration;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.websocket.server.WsContextListener;
import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;

public class TomcatWebSocketTestServer implements WebSocketTestServer{
    private final Tomcat tomcatServer;
    private final int port;
    private final File baseDir;
    private Context context;

    public TomcatWebSocketTestServer(int port) {

        this.port = port;
        this.baseDir = createBaseDir(port);

        Connector connector = new Connector(Http11NioProtocol.class.getName());
        connector.setPort(this.port);

        this.tomcatServer = new Tomcat();
        this.tomcatServer.setBaseDir(this.baseDir.getAbsolutePath());
        this.tomcatServer.setPort(this.port);
        this.tomcatServer.getService().addConnector(connector);
        this.tomcatServer.setConnector(connector);
    }

    private static File createBaseDir(int port) {
        try {
            File file = Files.createTempDirectory("tomcat." + "." + port).toFile();
            file.deleteOnExit();
            return file;
        }
        catch (IOException ex) {
            throw new RuntimeException("Unable to create temp directory", ex);
        }
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public void deployDispatcherServlet(WebApplicationContext cxt) {
        this.context = this.tomcatServer.addContext("", this.baseDir.getAbsolutePath());
        Tomcat.addServlet(context, "dispatcherServlet", new DispatcherServlet(cxt));
        this.context.addServletMappingDecoded("/", "project");
        this.context.addApplicationListener(WsContextListener.class.getName());
    }

    public void deployWithInitializer(Class<? extends WebApplicationInitializer>... initializers) {
        this.context = this.tomcatServer.addContext("", this.baseDir.getAbsolutePath());
        Tomcat.addServlet(this.context, "default", "org.apache.catalina.servlets.DefaultServlet");
        this.context.addApplicationListener(WsContextListener.class.getName());
        this.context.addServletContainerInitializer(new SpringServletContainerInitializer(),
                new HashSet<>(Arrays.asList(initializers)));
    }

    @Override
    public void undeployConfig() {
        if (this.context != null) {
            this.tomcatServer.getHost().removeChild(this.context);
        }
    }

    @Override
    public void start() throws Exception {
        this.tomcatServer.start();
    }

    @Override
    public void stop() throws Exception {
        this.tomcatServer.stop();
    }
}