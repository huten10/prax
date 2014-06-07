/**
 * 
 */
package com.prax.framework.util;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Huanan
 *
 */
public class ExceptionUtils {
  
  public static RuntimeException wrapThrow(Throwable e) {
    if (e instanceof RuntimeException)
      return (RuntimeException) e;
    if (e instanceof InvocationTargetException)
      return wrapThrow(((InvocationTargetException) e).getTargetException());
    return new RuntimeException(e);
  }

}
