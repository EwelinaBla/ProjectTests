package Helpers;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class StatusTest implements AfterTestExecutionCallback {
    public Boolean isField;
    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        if(extensionContext.getExecutionException().isPresent()){
            isField=true;
        }
        else isField=false;
    }
}
