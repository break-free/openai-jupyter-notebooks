
package org.apache.fineract.integrationtests.support.instancemode;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
@Retention(RUNTIME)
@Target({ METHOD })
public @interface ConfigureInstanceMode {
    boolean readEnabled();
    boolean writeEnabled();
    boolean batchWorkerEnabled();
    boolean batchManagerEnabled();
}
