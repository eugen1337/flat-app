package back.infrastructure.out.websocket;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.LocalBean;
import jakarta.enterprise.concurrent.ManagedScheduledExecutorService;
import jakarta.inject.Inject;
import java.util.concurrent.TimeUnit;

import back.app.api.IApp;
import back.infrastructure.utils.qualifiers.Built;


@Singleton
@Startup
@LocalBean
public class Timer {
    @Resource
    ManagedScheduledExecutorService scheduleMES;

    @Inject
    @Built
    IApp app;

    @PostConstruct
    public void init() {
        this.scheduleMES.scheduleAtFixedRate(this::run, 500, 30 * 1000, TimeUnit.MILLISECONDS);
    }

    public void run() {
        app.sendUpdate();
    }
}

