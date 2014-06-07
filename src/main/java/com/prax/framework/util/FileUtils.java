/**
 * 
 */
package com.prax.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Huanan
 *
 */
public class FileUtils {

  private static final String filePathSeparator = "/";
  private static final String resultsSeparator = ",";

  /**
   * The method will return all direct child directories for a specified directory.
   * The method does not return sub-child directories. Only direct child directories are returned.
   * 
   * @param directoryName - directory to search for child directories
   * @param returnFullPath - whether childs are required with full path or relative path
   * 
   * @return String array containing all directories under the given directory
   * 
   */

  public static String[] findDirectories(String directoryName, boolean returnFullPath)
      throws FileNotFoundException {
    final String fullDirectoryName = new ServletUtility().getWebAppRoot() + filePathSeparator
        + directoryName;

    File dir = null;
    String[] children = null;
    String[] fullPathDirectories = null;

    try {
      dir = new File(fullDirectoryName);

      if (dir.isDirectory()) {
        // Filter for all directories
        FilenameFilter filter = new FilenameFilter() {
          public boolean accept(File dir, String name) {

            // Skip over "core" file. They are loaded explicitely
            if (name.compareTo("core") == 0)
              return false;

            File file = new File(fullDirectoryName + filePathSeparator + name);
            return (file.isDirectory());
          }
        };

        children = dir.list(filter);
      }
    } catch (Exception e) {
      throw new FileNotFoundException(String.format("Can not find directory: %s", directoryName));
    }

    fullPathDirectories = new String[(null == children) ? 0 : children.length + 1];
    if (null != children) {
      for (int i = 0; i < children.length; i++) {
        if (returnFullPath) {
          fullPathDirectories[i] = new ServletUtility().getWebAppRoot() + filePathSeparator
              + directoryName + filePathSeparator + children[i];
        } else {
          fullPathDirectories[i] = directoryName + filePathSeparator + children[i];
        }
      }
    }
    return (null == fullPathDirectories) ? (new String[0]) : fullPathDirectories;
  }

  /**
   * The method will search all files of the 
   * 
   * @param directory - list of directories to search,
   * @param String fileNamesLike - file names matching this pattern.
   * @param boolean returnFullPath - return full path or relative path indicator,
   * @param String fileExtension - files that match this extension only.
   * 
   * @return String - All files found seperated by the fileSeparator constant declared are returned.
   */

  public static String findFiles(String[] directories, final String fileNamesLike,
      boolean returnFullPath, final String fileExtension) {
    StringBuffer x = new StringBuffer("");
    String result = null;
    for (int i = 0; i < directories.length; i++) {
      try {
        result = findFiles(directories[i], fileNamesLike, returnFullPath, fileExtension);
      } catch (Exception e) {
        /* Silent catch - if any directory out of the list passed is missing - eat exception and continue for other directories */
      }
      if ((null != result) && (result.trim().length() > 0)) {
        x.append(result + resultsSeparator);
      }
    }

    /* remove the last resultsSeparator */
    if (x.toString().endsWith(resultsSeparator)) {
      return x.substring(0, x.lastIndexOf(resultsSeparator));
    } else {
      return x.toString();
    }
  }

  /**
   * @param directory - directory where to search
   * @param filesLike - pattern for file search - support only for * wild character, not others like ?, ^ etc.
   *  patterns could be *xyz* - files containing xyz in file name 
   *  patterns could be *xyz - files ending with xyz as file name
   *  patterns could be xyz* - files starts with xyz as file name
   * @param fileExtension - files of this extension would only be searched
   * @param fullPathRequired - if false will return relative path only for all files found else full path
   * @return String[] - String array for all files found matching extension and pattern specified.
   *  
   */

  public static String findFiles(String directory, final String filenameLike,
      boolean fullPathRequired, final String fileExtension) throws FileNotFoundException {
    final String fullDirectoryName = new ServletUtility().getWebAppRoot() + filePathSeparator
        + directory;

    File dir = null;
    try {
      dir = new File(fullDirectoryName);
    } catch (Exception e) {
      throw new FileNotFoundException(String.format("Can not find directory: %s", directory));
    }

    String[] children = null;
    StringBuffer filesWithFullRelativePath = new StringBuffer("");

    if (dir.isDirectory()) {

      // Filter the list of files to be returned, based on filename criteria and file extension
      FilenameFilter filter = new FilenameFilter() {
        /** callback method - overridden to check our criteria */
        public boolean accept(File dir, String name) {
          if ((null != name) && fileNameMatchesPattern(name)) {
            if (null != fileExtension)
              return name.toLowerCase().endsWith(fileExtension);
            else
              return true;
          }
          return false;
        }

        /** filname pattern match - startsWith, endsWith and contains support added */
        private boolean fileNameMatchesPattern(String name) {
          if ((null == filenameLike) || (filenameLike.trim().length() == 0)) {
            return true;
          } else if (filenameLike.startsWith("*")) {
            return name.endsWith(filenameLike.substring(1, filenameLike.length()));
          } else if (filenameLike.startsWith("*") && filenameLike.endsWith("*")) {
            return (name.indexOf(filenameLike) >= 0 ? true : false);
          } else if (filenameLike.endsWith("*")) {
            return name.startsWith(filenameLike.substring(0, filenameLike.length() - 1));
          } else {
            return (name.indexOf(filenameLike) >= 0 ? true : false);
          }
        }
      };

      /* filter the files required based on filter created above */
      children = dir.list(filter);
      if (null != children) {
        for (int i = 0; i < children.length; i++) {
          if (fullPathRequired) {
            filesWithFullRelativePath.append(new ServletUtility().getWebAppRoot()
                + filePathSeparator + directory + filePathSeparator + children[i]
                + ((i + 1 == children.length) ? "" : resultsSeparator));
          } else {
            filesWithFullRelativePath.append(filePathSeparator + directory + filePathSeparator
                + children[i] + ((i + 1 == children.length) ? "" : resultsSeparator));
          }
        }
      }
    }

    return filesWithFullRelativePath.toString();
  }

  // Returns the contents of the file in a byte array.
  public static byte[] getBytesFromFile(File file) throws IOException {
    InputStream is = null;
    try {
      is = new FileInputStream(file);

      // Get the size of the file
      long length = file.length();

      // You cannot create an array using a long type.
      // It needs to be an int type.
      // Before converting to an int type, check
      // to ensure that file is not larger than Integer.MAX_VALUE.
      if (length > Integer.MAX_VALUE) {
        // File is too large
      }

      // Create the byte array to hold the data
      byte[] bytes = new byte[(int) length];

      // Read in the bytes
      int offset = 0;
      int numRead = 0;
      while (offset < bytes.length
          && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
        offset += numRead;
      }

      // Ensure all the bytes have been read in
      if (offset < bytes.length) {
        //throw new IOException("Could not completely read file "+file.getName());
      }

      // Close the input stream and return bytes
      is.close();
      is = null;
      return bytes;
    } finally {
      if (is != null)
        is.close();
    }
  }
}
