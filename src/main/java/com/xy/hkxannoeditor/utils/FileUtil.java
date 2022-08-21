package com.xy.hkxannoeditor.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * This class manages I/O operations with files.
 *
 * @author Marco Meschieri - Logical Objects
 * @version 4.0
 */
public class FileUtil {
    static final int BUFF_SIZE = 100000;

    static final byte[] buffer = new byte[BUFF_SIZE];

    protected static Logger log = LoggerFactory.getLogger(FileUtil.class);

    public static void writeFile(InputStream in, String filepath) {
        try (OutputStream os = new FileOutputStream(filepath); in) {
            while (true) {
                synchronized (buffer) {
                    int amountRead = in.read(buffer);
                    if (amountRead == -1) break;
                    os.write(buffer, 0, amountRead);
                }
            }
            os.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } else {
            System.out.println("创建目录" + destDirName + "失败！");
            return false;
        }
    }

    public static void writeFile(String text, String filepath) {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filepath));) {
            bos.write(text.getBytes(StandardCharsets.UTF_8));
            bos.flush();
        } catch (Throwable e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public static String readFile(File file) throws IOException {
        try (FileInputStream fisTargetFile = new FileInputStream(file)) {
            return IOUtils.toString(fisTargetFile, StandardCharsets.UTF_8);
        }
    }

    public static String readFile(String filePath) throws IOException {
        return readFile(new File(filePath));
    }

    public static void appendFile(String text, String filepath) {
        try (OutputStream bos = new FileOutputStream(filepath, true)) {
            bos.write(text.getBytes());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public static String computeDigest(InputStream is) {
        String digest;
        MessageDigest sha;
        try (is) {
            sha = MessageDigest.getInstance("SHA-1");
            byte[] message = new byte[BUFF_SIZE];
            int len;
            while ((len = is.read(message)) != -1) {
                sha.update(message, 0, len);
            }
            byte[] messageDigest = sha.digest();
            // convert the array to String
            StringBuilder buf = new StringBuilder();
            int unsignedValue;
            String strUnsignedValue;
            for (byte b : messageDigest) {
                // convert each messageDigest byte to unsigned
                unsignedValue = ((int) b) & 0xff;
                strUnsignedValue = Integer.toHexString(unsignedValue);
                // at least two letters
                if (strUnsignedValue.length() == 1) buf.append("0");
                buf.append(strUnsignedValue);
            }
            digest = buf.toString();
            log.debug("Computed Digest: " + digest);

            return digest;
        } catch (Throwable io) {
            log.error("Error generating digest: ", io);
        }
        return null;
    }

    /**
     * This method calculates the digest of a file using the algorithm SHA-1.
     *
     * @param file The file for which will be computed the digest
     * @return digest
     */
    public static String computeDigest(File file) {
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(file), BUFF_SIZE);
            return computeDigest(is);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * This method calculates the digest of a string using the algorithm SHA-1.
     *
     * @param src The string for which will be computed the digest
     * @return digest
     */
    public static String computeDigest(String src) {
        InputStream is = IOUtils.toInputStream(src, "UTF-8");
        return computeDigest(is);
    }

    /**
     * This method calculates the digest of a file using the algorithm SHA-1.
     *
     * @param file The file for which will be computed the digest
     * @return digest
     */
    public static byte[] computeSha1Hash(File file) {
        try (InputStream is = new BufferedInputStream(new FileInputStream(file), BUFF_SIZE)) {
            return computeSha1Hash(is);
        } catch (IOException io) {
            log.error(io.getMessage(), io);
        }
        return null;
    }

    /**
     * This method calculates the digest of a inputStram content using the
     * algorithm SHA-1.
     *
     * @param is The file for which will be computed the digest
     * @return digest
     */
    public static byte[] computeSha1Hash(InputStream is) {
        try {
            if (is != null) {
                MessageDigest sha = MessageDigest.getInstance("SHA-1");
                byte[] message = new byte[BUFF_SIZE];
                int len;
                while ((len = is.read(message)) != -1) {
                    sha.update(message, 0, len);
                }
                return sha.digest();
            }
        } catch (Throwable io) {
            log.error("Error generating SHA-1: ", io);
        }
        return null;
    }

    /**
     * Writes the specified classpath resource into a file
     *
     * @param resourceName Fully qualified resource name
     * @param out          The output file
     * @throws IOException
     */
    public static void copyResource(String resourceName, File out) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            try {
                is = new BufferedInputStream(FileUtil.class.getResource(resourceName).openStream());
            } catch (Exception e) {
                is = new BufferedInputStream(Thread.currentThread().getContextClassLoader().getResource(resourceName).openStream());
            }
            os = new BufferedOutputStream(new FileOutputStream(out));

            for (; ; ) {
                int b = is.read();
                if (b == -1) break;
                os.write(b);
            }
        } finally {
            if (is != null) is.close();
            if (os != null) os.close();
        }
    }

    /**
     * Computes the folder size as the sum of all files directly and indirectly
     * contained.
     */
    public static long getFolderSize(File folder) {
        long folderSize = 0;

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    folderSize += getFolderSize(file);
                } else {
                    folderSize += file.length();
                }
            }
        }
        return folderSize;
    }

    /**
     * Renders a file size in a more readable behaviour taking into account the
     * user locale. Depending on the size, the result will be presented in the
     * following measure units: GB, MB, KB or Bytes
     *
     * @param size     Size to be rendered
     * @param language The language for the format symbols
     * @return
     */
    public static String getDisplaySize(long size, String language) {
        String displaySize;
        Locale locale = new Locale("en");
        if (StringUtils.isNotEmpty(language)) locale = new Locale(language);
        NumberFormat nf = new DecimalFormat("###,###,###.0", new DecimalFormatSymbols(locale));
        if (size > 1000000000) {
            displaySize = nf.format((double) size / 1024 / 1024 / 1024) + " GB";
        } else if (size > 1000000) {
            displaySize = nf.format((double) size / 1024 / 1024) + " MB";
        } else if (size > 1000) {
            displaySize = nf.format((double) size / 1024) + " KB";
        } else {
            displaySize = size + " Bytes";
        }
        return displaySize;
    }

    /**
     * Renders a file size in a more readable behaviour taking into account the
     * user locale. The size is always rendered in the KB(kilobyte) measure
     * unit.
     *
     * @param size     Size to be rendered
     * @param language The language for the format symbols
     * @return
     */
    public static String getDisplaySizeKB(long size, String language) {
        String displaySize;
        Locale locale = new Locale("en");
        if (StringUtils.isNotEmpty(language)) locale = new Locale(language);
        NumberFormat nf = new DecimalFormat("###,###,##0.0", new DecimalFormatSymbols(locale));
        displaySize = nf.format((double) size / 1024) + " KB";
        return displaySize;
    }

    /**
     * Check if a given filename matches the <code>includes</code> and not the
     * <code>excludes</code>
     *
     * @param filename The filename to consider
     * @param includes list of includes expressions (eg. *.doc,*dummy*)
     * @param excludes list of excludes expressions (eg. *.doc,*dummy*)
     * @return true only if the passed filename matches the includes and not the
     * excludes
     */
    public static boolean matches(String filename, String[] includes, String[] excludes) {
        // First check if the filename must be excluded
        if (excludes != null) for (String s : excludes)
            if (Pattern.matches(s, filename)) return false;

        // Then check if the filename must, can be included
        if (includes != null) for (String s : includes)
            if (Pattern.matches(s, filename)) return true;

        return includes == null || includes.length == 0;
    }

    /**
     * Check if a given filename matches the <code>includes</code> and not the
     * <code>excludes</code>
     *
     * @param filename The filename to consider
     * @param includes comma-separated list of includes expressions (eg.
     *                 *.doc,*dummy*)
     * @param excludes comma-separated list of excludeses expressions (eg.
     *                 *.doc,*dummy*)
     * @return true only if the passed filename matches the includes and not the
     * excludes
     */
    public static boolean matches(String filename, String includes, String excludes) {
        List<String> inc = new ArrayList<>();
        List<String> exc = new ArrayList<>();

        StringTokenizer st;

        if (StringUtils.isNotEmpty(excludes)) {
            st = new StringTokenizer(excludes, ",", false);
            while (st.hasMoreTokens()) exc.add(st.nextToken().trim());
        }

        if (StringUtils.isNotEmpty(includes)) {
            st = new StringTokenizer(includes, ",", false);
            while (st.hasMoreTokens()) inc.add(st.nextToken().trim());
        }

        return matches(filename, inc.toArray(new String[0]), exc.toArray(new String[0]));
    }

    public static void writeUTF8(String content, File file, boolean append) {
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), StandardCharsets.UTF_8))) {
            out.write(content);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] toByteArray(File file) {
        try (InputStream is = new BufferedInputStream(new FileInputStream(file), 2048)) {
            return IOUtils.toByteArray(is);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public static byte[] toByteArray(File file, long start, long length) throws IOException {

        try (RandomAccessFile input = new RandomAccessFile(file, "r"); ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            // Open streams.
            copy(input, output, start, length);
            output.flush();
            return output.toByteArray();
        }
    }

    /**
     * Copy the given byte range of the given input to the given output.
     *
     * @param input  The input to copy the given range to the given output for.
     * @param output The output to copy the given range from the given input
     *               for.
     * @param start  Start of the byte range.
     * @param length Length of the byte range.
     * @throws IOException If something fails at I/O level.
     */
    public static void copy(RandomAccessFile input, OutputStream output, long start, long length) throws IOException {
        byte[] buffer = new byte[BUFF_SIZE];
        int read;

        if (input.length() == length) {
            // Write full range.
            while ((read = input.read(buffer)) > 0) {
                output.write(buffer, 0, read);
            }
        } else {
            // Write partial range.
            input.seek(start);
            long toRead = length;

            while ((read = input.read(buffer)) > 0) {
                if ((toRead -= read) > 0) {
                    output.write(buffer, 0, read);
                } else {
                    output.write(buffer, 0, (int) toRead + read);
                    break;
                }
            }
        }
    }

    public static void replaceInFile(String sourcePath, String token, String newValue) throws Exception {
        boolean windows = System.getProperty("os.name").toLowerCase().contains("win");

        try {
            File tmp = new File(sourcePath + ".tmp");
            File file = new File(sourcePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            StringBuilder oldtext = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                oldtext.append(line.replaceAll(token, newValue.replaceAll("\\\\", "\\\\\\\\")));
                if (windows && !sourcePath.endsWith(".sh")) oldtext.append("\r");
                oldtext.append("\n");
            }
            reader.close();

            // To replace a line in a file
            String newtext = oldtext.toString().replaceAll(token, newValue.replaceAll("\\\\", "\\\\\\\\"));

            FileWriter writer = new FileWriter(tmp);
            writer.write(newtext);
            writer.close();

            file.delete();
            tmp.renameTo(file);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        try (FileChannel source = new FileInputStream(sourceFile).getChannel();
             FileChannel destination = new FileOutputStream(destFile).getChannel()) {
            destination.transferFrom(source, 0, source.size());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


    public static void strongDelete(File file) {
        if (file == null || !file.exists()) return;

        // Make sure the temp file is deleted
        for (int i = 0; i < 20; i++) {
            try {
                FileUtils.forceDelete(file);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            if (!file.exists()) break;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public static boolean isDirEmpty(final Path directory) throws IOException {
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
            return !dirStream.iterator().hasNext();
        }
    }

    public static void moveFile(File sourceFile, File destFile) throws IOException {
        copyFile(sourceFile, destFile);
        strongDelete(sourceFile);
    }

    /**
     * Deletes all files and subdirectories under "dir".
     *
     * @param dir Directory to be deleted
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String child : children) {
                    boolean success = deleteDir(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
        }

        // The directory is now empty so now it can be smoked
        return dir.delete();
    }

    public static boolean deleteDir(String path) {
        File file = new File(path);
        return deleteDir(file);
    }

    public static String getNewTypeFileName(String sFile, String sType) {
        int nIndex = sFile.indexOf(".");
        if (nIndex > -1) {
            return sFile.substring(0, nIndex) + "." + sType;
        } else {
            return sFile;
        }
    }

    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if (file.exists()) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
            return false;
        }
        //判断目标文件所在的目录是否存在  
        if (!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录  
            System.out.println("目标文件所在目录不存在，准备创建它！");
            if (!file.getParentFile().mkdirs()) {
                System.out.println("创建目标文件所在目录失败！");
                return false;
            }
        }
        //创建目标文件  
        try {
            if (file.createNewFile()) {
                System.out.println("创建单个文件" + destFileName + "成功！");
                return true;
            } else {
                System.out.println("创建单个文件" + destFileName + "失败！");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
            return false;
        }
    }
}