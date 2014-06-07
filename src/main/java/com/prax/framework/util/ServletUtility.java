/**
 * 
 */
package com.prax.framework.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Huanan
 *
 */
public class ServletUtility {
  private static Long currDate = null;

  /**
   * Global variable used by getWebAppRoot.
   */
  private static String webAppRoot = null;

  /**
   * Global variable used by getServletContext.
   * Holds the value as http://host:port/context
   */
  private static String serverURL = null;

  /**
   * Returns a string containing the 'root' path of the current web application
   * @author Martin Goulet (martin.goulet@sungard.com)
   * @return String containin the root path of the current web application
   */
  public String getWebAppRoot() {
    return getWebAppRoot(null);
  }

  /**
   * Returns a string containing the 'root' path of the current web application
   * @author Martin Goulet (martin.goulet@sungard.com)
   * @param className Name of the class to use in case the root of the context isn't set by the controller at startup.
   * @return String containin the root path of the current web application
   */
  public String getWebAppRoot(Class c) {
    URL url;
    String[] sv;
    String root;

    if (webAppRoot == null) {
      // Use old method when the global variable is not set. This can happen in the context of JUnit tests
      // (where the controller is not present to call the setWebAppRoot() method.

      // Private variable is not set, compute it and store it. We do this since we don't expect
      // the root of the web application to change WHILE it is running...
      if (c == null) {
        return null;
      }

      url = c.getResource(c.getSimpleName());

      if (url == null)
        url = c.getResource(c.getSimpleName() + ".class");

      if (url != null) {
        sv = url.getPath().split("/target/");
        root = sv[0].replaceAll("%20", " ");
        //
        // mgoulet: Special case on Linux. This is messy but no other work around has been found
        // so far. I'm open to suggestion. :)
        //
        if (root.substring(0, 5).equals("file:") == true) {
          root = root.substring(5);
        }
      } else {
        // Unexpected error, returning null
        return null;
      }
      webAppRoot = root;
    }

    return webAppRoot;
  }

  /**
   * Method to override manually the web application root directory variable. To be used only within '
   * test cases when the hom directory of the webb application is not so clear cut.
   */
  public void setWebAppRoot(String s) {
    webAppRoot = s;
  }

  public ServletUtility() {

  }

  public static java.util.Date getCurrDate() {
    return new java.util.Date(getCurrDateAsLong());
  }

  public static synchronized long getCurrDateAsLong() {
    if (currDate == null) {
      return System.currentTimeMillis();
    } else {
      return currDate.longValue();
    }
  }

  public static synchronized void setCurrDateAsLong(long date) {
    currDate = new Long(date);
  }

  public static void setCurrDate(java.util.Date date) {
    setCurrDateAsLong(date.getTime());
  }

  public boolean isParameterMember(ArrayList l, String n) {
    if (n.compareToIgnoreCase("debug") == 0) {
      return true;
    }

    if (l != null) {
      Iterator i = l.iterator();
      while (i.hasNext()) {
        if (((String) i.next()).compareToIgnoreCase(n) == 0) {
          return true;
        }
      }
    }
    return false;
  }

  public void addParameterList(ArrayList l, String n) {
    if (l != null) {
      if (!isParameterMember(l, n)) {
        l.add(n);
      }
    }
  }

  public String getParameter(ArrayList l, HttpServletRequest r, String n) {

    String v = r.getParameter(n);

    if (v != null) {
      addParameterList(l, n);
    }

    return v;
  }

  public String[] getParameterValues(ArrayList l, HttpServletRequest r, String n) {

    String[] v = r.getParameterValues(n);

    if (v == null) {
      String s = r.getParameter(n + "[0]"); // check for individual array elements
      if (s != null) {
        int i = 0;
        ArrayList t = new ArrayList();

        while (s != null) {
          t.add(s);
          addParameterList(l, n + "[" + i++ + "]");
          s = r.getParameter(n + "[" + i + "]");
        }

        v = (String[]) t.toArray(new String[0]);
        addParameterList(l, n);
      }
    } else {
      addParameterList(l, n);
    }

    return v;
  }

  public void setAttribute(ArrayList l, HttpServletRequest r, String n, String v, boolean t) {

    if (!isParameterMember(l, n)) {
      if (t) {
        r.setAttribute(n, v);
      }
    }
  }

  public void setAttribute(ArrayList l, HttpServletRequest r, String n, String v[], boolean t) {

    if (!isParameterMember(l, n)) {
      if (t) {
        r.setAttribute(n, v);
      }
    }
  }

  public void setAttribute(ArrayList l, HttpServletRequest r, String n, Object v, boolean t) {

    if (!isParameterMember(l, n)) {
      if (t) {
        r.setAttribute(n, v);
      }
    }
  }

  public void setAttribute(ArrayList l, HttpServletRequest r, String n, Object v[], boolean t) {

    if (!isParameterMember(l, n)) {
      if (t) {
        r.setAttribute(n, v);
      }
    }
  }

  public String extractFileName(String str) {
    if (str == null || str.length() == 0) {
      return "";
    }

    int sIndex = str.lastIndexOf('\\');

    if (sIndex == -1) {
      sIndex = str.lastIndexOf('/');

      if (sIndex == -1) {
        sIndex = 0;
      } else {
        sIndex++;
      }
    } else {
      sIndex++;
    }
    //int eIndex = str.lastIndexOf('.');
    //if (eIndex == -1) {
    //  eIndex = str.length();
    //}
    //if (eIndex <= sIndex) {
    //  return "";
    //}
    return str.substring(sIndex);//, eIndex);
  }

  public String sendRequestToRemote(String address, String postData, boolean isPost)
      throws Exception {
    StringBuffer ret = new StringBuffer();
    BufferedReader istream = null;
    InputStreamReader i = null;

    try {
      URL url = new URL(address);
      URLConnection uConn = url.openConnection();

      if (isPost) {
        uConn.setDoOutput(true);
        OutputStream ostream = uConn.getOutputStream();
        ostream.write(postData.getBytes());
      }

      i = new InputStreamReader(uConn.getInputStream());
      istream = new BufferedReader(i);
      String tmpLine;

      while ((tmpLine = istream.readLine()) != null) {
        ret.append(tmpLine);
      }
    } finally {

      if (i != null)
        i.close();

      if (istream != null)
        istream.close();
    }

    return ret.toString();
  }

  public String getFieldFromRemoteResponse(String res, String fieldName) {
    String ret = "";
    StringTokenizer st = new StringTokenizer(res, ",");
    String field;

    while (st.hasMoreTokens()) {
      field = st.nextToken();

      if (field.indexOf(fieldName + "=") != -1 && field.length() > fieldName.length() + 1) {
        ret = field.substring(fieldName.length() + 1);
      }
    }

    return ret;
  }

  /**
   * Converts a byte array into an hexadecimal string.
   * @param x Bytes array
   * @return Hexadecimal string
   */
  public static String bytesToHexa(byte[] x) {
    StringBuffer sb = new StringBuffer(x.length * 2);
    for (int i = 0; i < x.length; i++) {
      if ((x[i] & 0xff) < 16) {
        sb.append('0');
      }
      sb.append(Integer.toHexString(x[i] & 0xff).toUpperCase());
    }
    return sb.toString();
  }

  /**
   * Converts an hexadecimal string into a byte array into
   * @param s Hexadecimal string
   * @return Bytes array
   */
  public static byte[] hexaToBytes(String s) {
    if (s.length() % 2 != 0) {
      /* hexa string not good, don't even try */
      return null;
    }

    byte[] b = new byte[s.length() / 2];
    int x;
    for (int i = 0, j = 0; i < s.length(); i += 2, j++) {
      x = Character.digit(s.charAt(i), 16) * 16;
      x += Character.digit(s.charAt(i + 1), 16);
      b[j] = (byte) x;
    }
    return b;
  }

  public static String getServerURL() {
    return serverURL;
  }

  public static void setServerURL(String url) {
    serverURL = url;
  }
}
