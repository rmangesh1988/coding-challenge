package io.bankbridge.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * Api helper methods
 */
public class ApiHelper {

    private static final Logger logger = LoggerFactory.getLogger(ApiHelper.class);

    /**
     * Method to soften the checked exception into unchecked exception
     * @param callable
     * @param <T>
     * @return
     */
    public static <T> T propagateExceptionSoftly(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            logger.error("Exception during a method call[propogateExceptionSoftly] ",e);
            throw new RuntimeException(e);
        }
    }

}
