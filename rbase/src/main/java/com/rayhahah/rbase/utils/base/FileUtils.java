package com.rayhahah.rbase.utils.base;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/11
 *     desc  : 文件相关工具类
 * </pre>
 */
public class FileUtils {

    private FileUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    public static File getFileByPath(String filePath) {
        return StringUtils.isSpace(filePath) ? null : new File(filePath);
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isFileExists(String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    /**
     * 重命名文件
     *
     * @param filePath 文件路径
     * @param newName  新名称
     * @return {@code true}: 重命名成功<br>{@code false}: 重命名失败
     */
    public static boolean rename(String filePath, String newName) {
        return rename(getFileByPath(filePath), newName);
    }

    /**
     * 重命名文件
     *
     * @param file    文件
     * @param newName 新名称
     * @return {@code true}: 重命名成功<br>{@code false}: 重命名失败
     */
    public static boolean rename(File file, String newName) {
        // 文件为空返回false
        if (file == null) {
            return false;
        }
        // 文件不存在返回false
        if (!file.exists()) {
            return false;
        }
        // 新的文件名为空返回false
        if (StringUtils.isSpace(newName)) {
            return false;
        }
        // 如果文件名没有改变返回true
        if (newName.equals(file.getName())) {
            return true;
        }
        File newFile = new File(file.getParent() + File.separator + newName);
        // 如果重命名的文件已存在返回false
        return !newFile.exists()
                && file.renameTo(newFile);
    }

    /**
     * 判断是否是目录
     *
     * @param dirPath 目录路径
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isDir(String dirPath) {
        return isDir(getFileByPath(dirPath));
    }

    /**
     * 判断是否是目录
     *
     * @param file 文件
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isDir(File file) {
        return isFileExists(file) && file.isDirectory();
    }

    /**
     * 判断是否是文件
     *
     * @param filePath 文件路径
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isFile(String filePath) {
        return isFile(getFileByPath(filePath));
    }

    /**
     * 判断是否是文件
     *
     * @param file 文件
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isFile(File file) {
        return isFileExists(file) && file.isFile();
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param dirPath 目录路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(File file) {
        if (file == null) {
            return false;
        }
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断文件是否存在，存在则在创建之前删除
     *
     * @param filePath 文件路径
     * @return {@code true}: 创建成功<br>{@code false}: 创建失败
     */
    public static boolean createFileByDeleteOldFile(String filePath) {
        return createFileByDeleteOldFile(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在，存在则在创建之前删除
     *
     * @param file 文件
     * @return {@code true}: 创建成功<br>{@code false}: 创建失败
     */
    public static boolean createFileByDeleteOldFile(File file) {
        if (file == null) {
            return false;
        }
        // 文件存在并且删除失败返回false
        if (file.exists() && file.isFile() && !file.delete()) {
            return false;
        }
        // 创建目录失败返回false
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 复制或移动文件
     *
     * @param srcFilePath  源文件路径
     * @param destFilePath 目标文件路径
     * @param isMove       是否移动
     * @return {@code true}: 复制或移动成功<br>{@code false}: 复制或移动失败
     */
    private static boolean copyOrMoveFile(String srcFilePath, String destFilePath, boolean isMove) {
        return copyOrMoveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), isMove);
    }

    /**
     * 复制或移动文件
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @param isMove   是否移动
     * @return {@code true}: 复制或移动成功<br>{@code false}: 复制或移动失败
     */
    private static boolean copyOrMoveFile(File srcFile, File destFile, boolean isMove) {
        if (srcFile == null || destFile == null) {
            return false;
        }
        // 源文件不存在或者不是文件则返回false
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }
        // 目标文件存在且是文件则返回false
        if (destFile.exists() && destFile.isFile()) {
            return false;
        }
        // 目标目录不存在返回false
        if (!createOrExistsDir(destFile.getParentFile())) {
            return false;
        }
        try {
            return writeFileFromIS(destFile, new FileInputStream(srcFile), false)
                    && !(isMove && !deleteFile(srcFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移动文件
     *
     * @param srcFilePath  源文件路径
     * @param destFilePath 目标文件路径
     * @return {@code true}: 移动成功<br>{@code false}: 移动失败
     */
    public static boolean moveFile(String srcFilePath, String destFilePath) {
        return moveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath));
    }

    /**
     * 移动文件
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @return {@code true}: 移动成功<br>{@code false}: 移动失败
     */
    public static boolean moveFile(File srcFile, File destFile) {
        return copyOrMoveFile(srcFile, destFile, true);
    }

    /**
     * 删除目录
     *
     * @param dirPath 目录路径
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteDir(String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    /**
     * 删除目录
     *
     * @param dir 目录
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteDir(File dir) {
        if (dir == null) {
            return false;
        }
        // 目录不存在返回true
        if (!dir.exists()) {
            return true;
        }
        // 不是目录返回false
        if (!dir.isDirectory()) {
            return false;
        }
        // 现在文件存在且是文件夹
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!deleteFile(file)) {
                        return false;
                    }
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

    /**
     * 删除文件
     *
     * @param srcFilePath 文件路径
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteFile(String srcFilePath) {
        return deleteFile(getFileByPath(srcFilePath));
    }

    /**
     * 删除文件
     *
     * @param file 文件
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteFile(File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    /**
     * 删除目录下的所有文件
     *
     * @param dirPath 目录路径
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteFilesInDir(String dirPath) {
        return deleteFilesInDir(getFileByPath(dirPath));
    }

    /**
     * 删除目录下的所有文件
     *
     * @param dir 目录
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteFilesInDir(File dir) {
        if (dir == null) {
            return false;
        }
        // 目录不存在返回true
        if (!dir.exists()) {
            return true;
        }
        // 不是目录返回false
        if (!dir.isDirectory()) {
            return false;
        }
        // 现在文件存在且是文件夹
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!deleteFile(file)) {
                        return false;
                    }
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 获取目录下所有文件
     *
     * @param dirPath     目录路径
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<File> listFilesInDir(String dirPath, boolean isRecursive) {
        return listFilesInDir(getFileByPath(dirPath), isRecursive);
    }

    /**
     * 获取目录下所有文件
     *
     * @param dir         目录
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<File> listFilesInDir(File dir, boolean isRecursive) {
        if (!isDir(dir)) {
            return null;
        }
        if (isRecursive) {
            return listFilesInDir(dir);
        }
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            Collections.addAll(list, files);
        }
        return list;
    }

    /**
     * 获取目录下所有文件包括子目录
     *
     * @param dirPath 目录路径
     * @return 文件链表
     */
    public static List<File> listFilesInDir(String dirPath) {
        return listFilesInDir(getFileByPath(dirPath));
    }

    /**
     * 获取目录下所有文件包括子目录
     *
     * @param dir 目录
     * @return 文件链表
     */
    public static List<File> listFilesInDir(File dir) {
        if (!isDir(dir)) {
            return null;
        }
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                list.add(file);
                if (file.isDirectory()) {
                    list.addAll(listFilesInDir(file));
                }
            }
        }
        return list;
    }

    /**
     * 获取目录下所有后缀名为suffix的文件
     * <p>大小写忽略</p>
     *
     * @param dirPath     目录路径
     * @param suffix      后缀名
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<File> listFilesInDirWithFilter(String dirPath, String suffix, boolean isRecursive) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), suffix, isRecursive);
    }

    /**
     * 获取目录下所有后缀名为suffix的文件
     * <p>大小写忽略</p>
     *
     * @param dir         目录
     * @param suffix      后缀名
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<File> listFilesInDirWithFilter(File dir, String suffix, boolean isRecursive) {
        if (isRecursive) {
            return listFilesInDirWithFilter(dir, suffix);
        }
        if (dir == null || !isDir(dir)) {
            return null;
        }
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.getName().toUpperCase().endsWith(suffix.toUpperCase())) {
                    list.add(file);
                }
            }
        }
        return list;
    }

    /**
     * 获取目录下所有后缀名为suffix的文件包括子目录
     * <p>大小写忽略</p>
     *
     * @param dirPath 目录路径
     * @param suffix  后缀名
     * @return 文件链表
     */
    public static List<File> listFilesInDirWithFilter(String dirPath, String suffix) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), suffix);
    }

    /**
     * 获取目录下所有后缀名为suffix的文件包括子目录
     * <p>大小写忽略</p>
     *
     * @param dir    目录
     * @param suffix 后缀名
     * @return 文件链表
     */
    public static List<File> listFilesInDirWithFilter(File dir, String suffix) {
        if (dir == null || !isDir(dir)) {
            return null;
        }
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.getName().toUpperCase().endsWith(suffix.toUpperCase())) {
                    list.add(file);
                }
                if (file.isDirectory()) {
                    list.addAll(listFilesInDirWithFilter(file, suffix));
                }
            }
        }
        return list;
    }

    /**
     * 获取目录下所有符合filter的文件
     *
     * @param dirPath     目录路径
     * @param filter      过滤器
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<File> listFilesInDirWithFilter(String dirPath, FilenameFilter filter, boolean isRecursive) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, isRecursive);
    }

    /**
     * 获取目录下所有符合filter的文件
     *
     * @param dir         目录
     * @param filter      过滤器
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<File> listFilesInDirWithFilter(File dir, FilenameFilter filter, boolean isRecursive) {
        if (isRecursive) {
            return listFilesInDirWithFilter(dir, filter);
        }
        if (dir == null || !isDir(dir)) {
            return null;
        }
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file.getParentFile(), file.getName())) {
                    list.add(file);
                }
            }
        }
        return list;
    }

    /**
     * 获取目录下所有符合filter的文件包括子目录
     *
     * @param dirPath 目录路径
     * @param filter  过滤器
     * @return 文件链表
     */
    public static List<File> listFilesInDirWithFilter(String dirPath, FilenameFilter filter) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter);
    }

    /**
     * 获取目录下所有符合filter的文件包括子目录
     *
     * @param dir    目录
     * @param filter 过滤器
     * @return 文件链表
     */
    public static List<File> listFilesInDirWithFilter(File dir, FilenameFilter filter) {
        if (dir == null || !isDir(dir)) {
            return null;
        }
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file.getParentFile(), file.getName())) {
                    list.add(file);
                }
                if (file.isDirectory()) {
                    list.addAll(listFilesInDirWithFilter(file, filter));
                }
            }
        }
        return list;
    }

    /**
     * 获取目录下指定文件名的文件包括子目录
     * <p>大小写忽略</p>
     *
     * @param dirPath  目录路径
     * @param fileName 文件名
     * @return 文件链表
     */
    public static List<File> searchFileInDir(String dirPath, String fileName) {
        return searchFileInDir(getFileByPath(dirPath), fileName);
    }

    /**
     * 获取目录下指定文件名的文件包括子目录
     * <p>大小写忽略</p>
     *
     * @param dir      目录
     * @param fileName 文件名
     * @return 文件链表
     */
    public static List<File> searchFileInDir(File dir, String fileName) {
        if (dir == null || !isDir(dir)) {
            return null;
        }
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.getName().toUpperCase().equals(fileName.toUpperCase())) {
                    list.add(file);
                }
                if (file.isDirectory()) {
                    list.addAll(searchFileInDir(file, fileName));
                }
            }
        }
        return list;
    }

    /**
     * 将输入流写入文件
     *
     * @param filePath 路径
     * @param is       输入流
     * @param append   是否追加在文件末
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean writeFileFromIS(String filePath, InputStream is, boolean append) {
        return writeFileFromIS(getFileByPath(filePath), is, append);
    }

    /**
     * 将输入流写入文件
     *
     * @param file   文件
     * @param is     输入流
     * @param append 是否追加在文件末
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean writeFileFromIS(File file, InputStream is, boolean append) {
        if (file == null || is == null) {
            return false;
        }
        if (!createOrExistsFile(file)) {
            return false;
        }
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            byte data[] = new byte[1024];
            int len;
            while ((len = is.read(data, 0, 1024)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIO(is, os);
        }
    }

    /**
     * 将字符串写入文件
     *
     * @param filePath 文件路径
     * @param content  写入内容
     * @param append   是否追加在文件末
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean writeFileFromString(String filePath, String content, boolean append) {
        return writeFileFromString(getFileByPath(filePath), content, append);
    }

    /**
     * 将字符串写入文件
     *
     * @param file    文件
     * @param content 写入内容
     * @param append  是否追加在文件末
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean writeFileFromString(File file, String content, boolean append) {
        if (file == null || content == null) {
            return false;
        }
        if (!createOrExistsFile(file)) {
            return false;
        }
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIO(bw);
        }
    }

    /**
     * 读取文件到字符数组中
     *
     * @param filePath 文件路径
     * @return 字符数组
     */
    public static byte[] readFile2Bytes(String filePath) {
        return readFile2Bytes(getFileByPath(filePath));
    }

    /**
     * 读取文件到字符数组中
     *
     * @param file 文件
     * @return 字符数组
     */
    public static byte[] readFile2Bytes(File file) {
        if (file == null) {
            return null;
        }
        try {
            return ConvertUtils.inputStream2Bytes(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取文件最后修改的毫秒时间戳
     *
     * @param filePath 文件路径
     * @return 文件最后修改的毫秒时间戳
     */
    public static long getFileLastModified(String filePath) {
        return getFileLastModified(getFileByPath(filePath));
    }

    /**
     * 获取文件最后修改的毫秒时间戳
     *
     * @param file 文件
     * @return 文件最后修改的毫秒时间戳
     */
    public static long getFileLastModified(File file) {
        if (file == null) {
            return -1;
        }
        return file.lastModified();
    }

    /**
     * 简单获取文件编码格式
     *
     * @param filePath 文件路径
     * @return 文件编码
     */
    public static String getFileCharsetSimple(String filePath) {
        return getFileCharsetSimple(getFileByPath(filePath));
    }

    /**
     * 简单获取文件编码格式
     *
     * @param file 文件
     * @return 文件编码
     */
    public static String getFileCharsetSimple(File file) {
        int p = 0;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            p = (is.read() << 8) + is.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(is);
        }
        switch (p) {
            case 0xefbb:
                return "UTF-8";
            case 0xfffe:
                return "Unicode";
            case 0xfeff:
                return "UTF-16BE";
            default:
                return "GBK";
        }
    }

    /**
     * 获取目录大小
     *
     * @param dirPath 目录路径
     * @return 文件大小
     */
    public static String getDirSize(String dirPath) {
        return getDirSize(getFileByPath(dirPath));
    }

    /**
     * 获取目录大小
     *
     * @param dir 目录
     * @return 文件大小
     */
    public static String getDirSize(File dir) {
        long len = getDirLength(dir);
        return len == -1 ? "" : ConvertUtils.byte2FitMemorySize(len);
    }

    /**
     * 获取文件大小
     *
     * @param filePath 文件路径
     * @return 文件大小
     */
    public static String getFileSize(String filePath) {
        return getFileSize(getFileByPath(filePath));
    }

    /**
     * 获取文件大小
     *
     * @param file 文件
     * @return 文件大小
     */
    public static String getFileSize(File file) {
        long len = getFileLength(file);
        return len == -1 ? "" : ConvertUtils.byte2FitMemorySize(len);
    }

    /**
     * 获取目录长度
     *
     * @param dirPath 目录路径
     * @return 文件大小
     */
    public static long getDirLength(String dirPath) {
        return getDirLength(getFileByPath(dirPath));
    }

    /**
     * 获取目录长度
     *
     * @param dir 目录
     * @return 文件大小
     */
    public static long getDirLength(File dir) {
        if (!isDir(dir)) {
            return -1;
        }
        long len = 0;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    len += getDirLength(file);
                } else {
                    len += file.length();
                }
            }
        }
        return len;
    }

    /**
     * 获取文件长度
     *
     * @param filePath 文件路径
     * @return 文件大小
     */
    public static long getFileLength(String filePath) {
        return getFileLength(getFileByPath(filePath));
    }

    /**
     * 获取文件长度
     *
     * @param file 文件
     * @return 文件大小
     */
    public static long getFileLength(File file) {
        if (!isFile(file)) {
            return -1;
        }
        return file.length();
    }

    /**
     * 获取文件的MD5校验码
     *
     * @param filePath 文件路径
     * @return 文件的MD5校验码
     */
    public static String getFileMD5ToString(String filePath) {
        File file = StringUtils.isSpace(filePath) ? null : new File(filePath);
        return getFileMD5ToString(file);
    }

    /**
     * 获取文件的MD5校验码
     *
     * @param filePath 文件路径
     * @return 文件的MD5校验码
     */
    public static byte[] getFileMD5(String filePath) {
        File file = StringUtils.isSpace(filePath) ? null : new File(filePath);
        return getFileMD5(file);
    }

    /**
     * 获取文件的MD5校验码
     *
     * @param file 文件
     * @return 文件的MD5校验码
     */
    public static String getFileMD5ToString(File file) {
        return ConvertUtils.bytes2HexString(getFileMD5(file));
    }

    /**
     * 获取文件的MD5校验码
     *
     * @param file 文件
     * @return 文件的MD5校验码
     */
    public static byte[] getFileMD5(File file) {
        if (file == null) {
            return null;
        }
        DigestInputStream dis = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            dis = new DigestInputStream(fis, md);
            byte[] buffer = new byte[1024 * 256];
            while (dis.read(buffer) > 0) {
                ;
            }
            md = dis.getMessageDigest();
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(dis);
        }
        return null;
    }


    /**
     * 获取全路径中的文件名
     *
     * @param file 文件
     * @return 文件名
     */
    public static String getFileName(File file) {
        if (file == null) {
            return null;
        }
        return getFileName(file.getPath());
    }

    /**
     * 获取全路径中的文件名
     *
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String getFileName(String filePath) {
        if (StringUtils.isSpace(filePath)) {
            return filePath;
        }
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }

    /**
     * 获取全路径中的不带拓展名的文件名
     *
     * @param file 文件
     * @return 不带拓展名的文件名
     */
    public static String getFileNameNoExtension(File file) {
        if (file == null) {
            return null;
        }
        return getFileNameNoExtension(file.getPath());
    }

    /**
     * 获取全路径中的不带拓展名的文件名
     *
     * @param filePath 文件路径
     * @return 不带拓展名的文件名
     */
    public static String getFileNameNoExtension(String filePath) {
        if (StringUtils.isSpace(filePath)) {
            return filePath;
        }
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastSep == -1) {
            return (lastPoi == -1 ? filePath : filePath.substring(0, lastPoi));
        }
        if (lastPoi == -1 || lastSep > lastPoi) {
            return filePath.substring(lastSep + 1);
        }
        return filePath.substring(lastSep + 1, lastPoi);
    }


    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public static String getPathFromUri(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    /**
     * 判断是够有SD卡
     *
     * @return
     */
    public static boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            return false;
        }
        return true;
    }

    /**
     * 获取根目录
     *
     * @return
     */
    public static String getRootFilePath() {
        if (hasSDCard()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";// filePath:/sdcard/
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data/"; // filePath: /data/data/
        }
    }

}