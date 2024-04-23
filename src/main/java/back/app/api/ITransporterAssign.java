package back.app.api;

import back.infrastructure.websocket.ITransporter;

public interface ITransporterAssign {
    void useTransporter(ITransporter transporter);
    
}
