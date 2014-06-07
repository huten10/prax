/**
 * 
 */
package com.prax.framework.util;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * @author Huanan
 *
 */
public class StringUtils {
  public static final String EMPTY_STRING = "";

  public StringUtils() {
  }

  public static String replaceString(String str, String target, String newStr) {
    int index;
    String temp;
    String firstPart;
    String lastPart;

    if (str == null || target == null || newStr == null) {
      return null;
    }

    temp = str;

    if ((index = temp.indexOf(target)) < 0) {
      /* Did not find the target */
      return null;
    }

    firstPart = temp.substring(0, index);
    lastPart = temp.substring(index + target.length(), temp.length());

    firstPart = firstPart.concat(newStr);
    firstPart = firstPart.concat(lastPart);

    return firstPart;
  }

  public static String replaceStringIgnoreCaps(String str, String target, String newStr) {
    return replaceString(str.toLowerCase(), target.toLowerCase(), newStr);
  }

  public static void addTabs(StringBuffer str, int numOfTabs) {
    for (int i = 0; i < numOfTabs; i++) {
      str = str.append('\t');
    }
  }

  public static String addTabs(int numOfTabs) {
    String str = "";

    for (int i = 0; i < numOfTabs; i++) {
      str = str.concat("\t");
    }

    return str;
  }

  public static String trimTrailingNulls(String str) {
    int index;
    if (str == null || str.length() == 0) {
      return str;
    }

    if ((index = str.indexOf(0)) > -1) {
      /* Found a null character! */
      return str.substring(0, index);
    }

    return str;
  }

  public static String trimTrailingNulls(char[] vc, int startingPos) {
    int i;
    char[] temp;

    if (vc == null || vc.length == 0) {
      return "";
    }

    for (i = startingPos; i < vc.length; i++) {
      if (vc[i] == 0) {
        break;
      }
    }

    /* Do not include the null character in this vector */
    if (i > 0) {
      temp = new char[i - startingPos];
    } else {
      /* the input string starts with a null char, so return an empty string */
      return "";
    }

    /* This is the second 'for' loop, this is the tradeoff for
       using the exact memory space for the previous vector */
    int n = 0;
    for (int k = startingPos; k < i; k++) {
      temp[n++] = vc[k];
    }

    return new String(temp);
  }

  public static String removeSurroundingQuotes(String str) {
    int startIndex;
    int endIndex;
    String quote = "'";

    if ((startIndex = str.indexOf(quote)) < 0) {
      /* No quotes of type "'" were found */

      /* Try double quotes ... */
      quote = "\"";
      if ((startIndex = str.indexOf(quote)) < 0) {
        /* No quotes of type """ were found, leave */
        return null;
      }
    }

    if ((endIndex = str.indexOf(quote, startIndex + 1)) < 0) {
      /* No quotes were found */
      return null;
    }

    return str.substring(startIndex + 1, endIndex);
  }

  public static boolean isValidNumber(String str) {
    if (str == null || str.length() == 0) {
      return false;
    }

    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) < '0' || str.charAt(i) > '9') {
        /* Discard automatically */
        return false;
      }
    }

    return true;
  }

  public static String extractFileFromPackage(String packageFile) {
    int index;
    if (packageFile == null || packageFile.length() == 0) {
      /* Nothing to do */
      return packageFile;
    }

    if ((index = packageFile.lastIndexOf(".")) < 0) {
      /* Not a package */
      return packageFile;
    }

    return packageFile.substring(++index);
  }

  public static String insertLeadingCharacters(String str, char c, int maxLength) {
    if (str == null || str.length() == 0 || maxLength <= str.length()) {
      /* Nothing to do */
      return str;
    }

    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < (maxLength - str.length()); i++) {
      sb.insert(i, c);
    }

    sb.append(str);

    return sb.toString();
  }

  public static String insertLeadingSpaces(String str, int maxLength) {
    return insertLeadingCharacters(str, ' ', maxLength);
  }

  public static String insertLeadingZeroes(String str, int maxLength) {
    return insertLeadingCharacters(str, '0', maxLength);
  }

  /* Verifies if the supplied class name is a preceded of a package name */
  public static boolean isClassNameAPackage(String className) {
    if (className == null || className.length() == 0) {
      return false;
    }

    if (className.indexOf('.') >= 0) {
      return true;
    }

    return false;
  }

  public static String insertTrailingNull(String str) {
    return str.concat("\0");
  }

  public static String removeTrailingWhiteSpaces(String str) {
    int i;
    char currentChar;

    /* This will remove trailing '\t', '\n', '\r', ' ' */

    i = str.length() - 1;
    currentChar = str.charAt(i);
    while (currentChar == '\t' || currentChar == '\n' || currentChar == '\r' || currentChar == ' ') {
      if (i <= 0) {
        /* The string contains only white spaces */
        return "";
      }

      --i;
      currentChar = str.charAt(i);
    }

    return str.substring(0, i + 1);
  }

  /**
   * Strips any string by removing all occurence of removeChar at the beginning
   * of the giving string.
   * @param str string to strip
   * @param removeChar char to remove in str.
   * @return string stripped
   */
  public static String stripStartingCharacter(String str, char removeChar) {
    int i;

    /* this will remove all removeChar from the beginning of str */
    i = 0;
    while (i < str.length()) {
      if (str.charAt(i) == removeChar) {
        i++;
      } else {
        return str.substring(i);
      }
    }
    /* string only contains occurence of removeChar */
    return "";

  }

  /**
   * Joins the elements of the provided array into a single String containing the provided list of elements.
   * 
   * @param array
   * @param delim
   * @return
   */
  public static String join(Object[] array, String delim) {
    StringBuffer sb = join(array, delim, new StringBuffer());
    return sb.toString();
  }

  private static StringBuffer join(Object[] array, String delim, StringBuffer sb) {
    if (array == null) {
      return sb;
    }

    for (int i = 0; i < array.length; i++) {
      if (i != 0)
        sb.append(delim);
      sb.append(array[i].toString());
    }
    return sb;
  }

  public static String[] joinArrays(String[] arr1, String[] arr2) {
    String[] ret;
    if (arr1 == null && arr2 == null) {
      return null;
    } else if (arr1 == null) {
      return (arr2 == null ? null : arr2.clone());
    } else if (arr2 == null) {
      return (arr1 == null ? null : arr1.clone());
    }

    ret = new String[arr1.length + arr2.length];
    System.arraycopy(arr1, 0, ret, 0, arr1.length);
    System.arraycopy(arr2, 0, ret, arr1.length, arr2.length);

    return ret;
  }

  public static String[] split(String s, char c) {
    if (s == null) {
      return null;
    }

    int ctChar = getOccurrenceCount(s, c);
    int ctSegments = ctChar + 1;
    String[] asSegments = new String[ctSegments];

    if (ctChar == 0) {
      // minimal case
      asSegments[0] = s;
      return asSegments;
    }

    int len = s.length();
    int xSegment = 0;
    int posStartOfSegment = 0;
    for (int pos = 0; pos < len; pos++) {
      if (s.charAt(pos) == c) {
        asSegments[xSegment] = s.substring(posStartOfSegment, pos);
        posStartOfSegment = pos + 1; // skip delimiter
        xSegment++;
      }
    }
    asSegments[xSegment] = s.substring(posStartOfSegment, len);
    return asSegments;
  }

  private static int getOccurrenceCount(String s, char c) {
    if (s == null) {
      return 0;
    }
    int len = s.length();
    if (len == 0) {
      return 0;
    }
    int iCount = 0;
    for (int pos = 0; pos < len; pos++) {
      if (s.charAt(pos) == c)
        iCount++;
    }
    return iCount;
  }

  /**
   * This function replaces all the occurences of string with the
   given string
   * Creation date: (3/13/01 3:47:13 PM)
   * @return java.lang.String
   * @param source java.lang.String
   * @param find java.lang.String
   * @param replace java.lang.String
   */
  public static String replaceAll(String source, String find, String replace) {

    StringBuffer result = new StringBuffer("");

    int maxlength = source.length();
    int start = 0;
    int findlength = find.length();
    int lastIndex;
    int i = source.indexOf(find);
    if (i < 0) {
      lastIndex = maxlength;
      result = new StringBuffer(source);
    } else {
      lastIndex = i + findlength;
    }
    while (lastIndex <= maxlength) {
      result.append(source.substring(start, i));
      result.append(replace);
      i = source.indexOf(find, lastIndex);
      start = lastIndex;
      if (i < 0) {
        result.append(source.substring(start, maxlength));
        break;
      }
      lastIndex = i + findlength;
    }
    return result.toString();
  }

  /**
   * This method tests if a string is not null and has at least one character.
   * @param string
   * @return
   */
  public static boolean isValidString(String string) {
    if (string != null && string.trim().length() > 0) {
      return true;
    } else {
      return false;
    }
  }

  public static String toTitleCase(String string) {
    if (isValidString(string)) {
      string = string.trim().toLowerCase();
      if (string.trim().length() > 0) {
        string = string.toUpperCase().charAt(0)
            + ((string.length() > 1) ? string.substring(1, string.length()) : "");
      }
    }
    return string;
  }

  public static String getfirstCharCaps(String string) {
    if (isValidString(string)) {
      if (string.trim().length() > 0) {
        string = string.toUpperCase().charAt(0)
            + ((string.length() > 1) ? string.substring(1, string.length()) : "");
      }
    }
    return string;
  }

  public static String getTrimmedValue(String str) {
    if (str != null) {
      str = str.trim();
    }
    return str;
  }

  /**
  * Maethod that parse the String array to boolean array.
  * @param stringArray Array of elements of type string
  * @return boolean[]
  */
  public static boolean[] parseBooleanArray(String[] stringArray) {
    boolean[] booleanArray = null;
    if (stringArray != null) {
      booleanArray = new boolean[stringArray.length];
      for (int i = 0; i < stringArray.length; i++) {
        booleanArray[i] = parseBoolean(stringArray[i]);
      }
    }
    return booleanArray;
  }

  /**
   * Method to parse the string to boolean
   * @param str String 
   * @return boolean true/false
   */
  public static boolean parseBoolean(String str) {
    if (str != null && (str.compareTo("1") == 0 || str.equalsIgnoreCase("true"))) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method to parse the boolean array to string array
   * @param booleanArray
   * @return String[]
   */
  public static String[] parseStringArray(boolean[] booleanArray) {
    String[] stringArray = null;
    if (booleanArray != null) {
      stringArray = new String[booleanArray.length];
      for (int i = 0; i < booleanArray.length; i++) {
        stringArray[i] = String.valueOf(booleanArray[i]);
      }
    }
    return stringArray;
  }

  /**
   * Method to parse the boolean array to string array of value as 1 for true and 0 for false
   * @param booleanArray
   * @return String[]
   */
  public static String[] parseBooleanToStringArray(boolean[] booleanArray) {
    String[] stringArray = parseStringArray(booleanArray);
    return convertStringAssignable(stringArray);
  }

  /**
   * Changed String value to 1 from 'true'. Search.js requires 1 instead of true
   * @param config
   * @return
   */
  public static String[] convertStringAssignable(String[] stringArray) {
    if (stringArray == null)
      return null;
    String[] newStringArr = new String[stringArray.length];
    int i = 0;
    for (String con : stringArray) {
      if ("true".compareTo(con) == 0) {
        newStringArr[i++] = "1";
      } else {
        newStringArr[i++] = "0";
      }
    }
    return newStringArr;
  }

  /**
   * Check if the percentage value is from 0 - 100 (inclusive)
   * @param str
   * @return
   */
  public static boolean isValidPercentage(String str) {
    if (isValidNumber(str)) {
      int percentage = Integer.parseInt(str);
      if (percentage < 0 || percentage > 100) {
        return false;
      }
    }
    return true;
  }

  public static String formatMessagePattern(String message, Object args[], Locale locale) {
    MessageFormat formatter = new MessageFormat(message);
    formatter.setLocale(locale);
    return formatter.format(args);
  }
}
