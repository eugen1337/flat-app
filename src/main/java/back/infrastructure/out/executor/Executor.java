package back.infrastructure.out.executor;

import jakarta.annotation.Resource;
import jakarta.enterprise.concurrent.ManagedExecutorService;


public class Executor implements IExecutor {
  
  @Resource
  private ManagedExecutorService mes;
  

  @Override
  public void execute(Runnable thread) {            
    mes.execute(thread);
  }  
      
}
