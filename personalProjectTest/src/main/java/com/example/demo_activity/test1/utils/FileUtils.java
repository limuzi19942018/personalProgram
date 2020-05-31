package com.example.demo_activity.test1.utils;

import cn.hutool.core.io.FileUtil;
import org.apache.http.client.utils.DateUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipFile;

/**
 * @author Administrator 文件管理操作实用类
 */
public class FileUtils {

    /**
     * 新建指定的目录
     *
     * @param filepath 指定目录的路径
     * @return {@code true} 成功
     * @see File#mkdirs()
     */
    public static boolean mkdirs(String filepath) {
        return !StringUtils.isEmpty(filepath) && new File(filepath).mkdirs();
    }

    /**
     * 新建指定的文件<br>
     * 如果指定的文件的父目录不存在，则先创建父目录
     *
     * @param filepath 指定文件的路径
     * @return {@code true} 创建成功
     * @throws IOException java.io.IOException
     * @see FileUtils#createFile(File)
     */
    public static boolean createFile(String filepath) throws IOException {
        return createFile(new File(filepath));
    }

    /**
     * 新建指定的文件<br>
     * 如果指定的文件的父目录不存在，则先创建父目录
     *
     * @param file 指定文件
     * @return {@code true} 创建成功
     * @throws IOException java.io.IOException
     * @see FileUtils#createFile(String)
     */
    public static boolean createFile(File file) throws IOException {
        boolean create = true;
        if (!file.exists()) {
            File parent = file.getParentFile();
            if (!parent.exists()) {
                create = parent.mkdirs();
            }
            if (create) {
                create = file.createNewFile();
            }
        }
        return create;
    }

    /**
     * 删除文件
     *
     * @param filePath
     */
    public static void deleteFile(String filePath) throws Exception {
        try {
            File myDelFile = new File(filePath);
            if (myDelFile.exists())
                myDelFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("删除文件错误!");
        }
    }

    /**
     * 删除文件
     *
     * @param filePath
     */
    public static void deleteFileByPath(String filePath) {
        try {
            File myDelFile = new File(filePath);
            if (myDelFile.exists()) {
                myDelFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定的文件是否存在
     *
     * @param filename 文件名
     * @return {@code true}存在
     * @since 2016-12-08
     */
    public static boolean exists(String filename) {
        return new File(filename).exists();
    }

    /**
     * 文件拷贝
     *
     * @param sourceFilePath
     * @param distFilePath
     */
    @SuppressWarnings("unused")
    public static void copyFile(String sourceFilePath, String distFilePath) throws Exception {
        try {
            int bytesum = 0;
            int byteread = 0;
            InputStream inStream = new FileInputStream(sourceFilePath);
            FileOutputStream fs = new FileOutputStream(distFilePath);
            byte[] buffer = new byte[1444];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                fs.write(buffer, 0, byteread);
            }
            inStream.close();
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("文件拷贝错误!");
        }
    }

    /**
     * 文件拷贝
     *
     * @param file
     * @param distFilePath
     * @param fileName
     */
    @SuppressWarnings("unused")
    public static void copyFile(File file, String distFilePath, String fileName) throws Exception {
        try {
            File f = new File(distFilePath);
            if (!f.exists()) {
                f.mkdirs();
            }
            int bytesum = 0;
            int byteread = 0;
            InputStream inStream = new FileInputStream(file);
            FileOutputStream fs = new FileOutputStream(distFilePath + fileName);
            byte[] buffer = new byte[1444];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                fs.write(buffer, 0, byteread);
            }
            inStream.close();
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("文件拷贝错误!");
        }
    }

    /**
     * 文件夹拷贝
     *
     * @param sourceFilePath
     * @param distFilePath
     */
    public static void copyFilePath(String sourceFilePath, String distFilePath) throws Exception {
        try {
            (new File(distFilePath)).mkdirs();
            File[] file = (new File(sourceFilePath)).listFiles();
            for (int i = 0; i < file.length; i++) {
                if (file[i].isFile()) {
                    file[i].toString();
                    FileInputStream input = new FileInputStream(file[i]);
                    FileOutputStream output = new FileOutputStream(distFilePath
                            + "/" + (file[i].getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("文件夹拷贝错误!");
        }
    }

    /**
     * 读到流中
     *
     * @param filePath
     * @return
     */
    public static InputStream fileToStream(String filePath) throws Exception {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                return new FileInputStream(file);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("文件转化输出流错误!");
        }

    }

    /**
     * 读文件到字节数组中
     *
     * @param file
     * @throws Exception
     */
    public static byte[] fileToByte(File file) throws Exception {
        FileInputStream is = null;
        try {
            byte[] dist = null;
            if (file.exists()) {
                is = new FileInputStream(file);
                dist = new byte[is.available()];
                is.read(dist);
            }
            return dist;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("文件转化字节数组错误!");
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    /**
     * 读文件到字节数组中
     *
     * @param filePath
     * @throws Exception
     */
    public static byte[] fileToByte(String filePath) throws Exception {
        FileInputStream is = null;
        try {
            File file = new File(filePath);
            byte[] dist = null;
            if (file.exists()) {
                is = new FileInputStream(file);
                dist = new byte[is.available()];
                is.read(dist);
            }
            return dist;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("文件转化字节数组错误!");
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    /**
     * 文件的写入
     *
     * @param filePath 文件路径
     * @param fileName 文件名
     * @param args     []
     * @throws IOException java.io.IOException
     */
    public static void writeFile(String filePath, String fileName, String[] args) throws IOException {
        FileWriter fw = new FileWriter(filePath + fileName);
        PrintWriter out = new PrintWriter(fw);
        for (int i = 0; i < args.length; i++) {
            out.write(args[i]);
            out.println();
            out.flush();
        }
        fw.close();
        out.close();
    }

    /**
     * 文件的写入
     *
     * @param filePath(文件路径)
     * @param fileName(文件名)
     * @param args
     * @throws IOException
     */
    public static void writeFile(String filePath, String fileName, String args) throws IOException {
        FileWriter fw = new FileWriter(filePath + fileName);
        fw.write(args);
        fw.close();
    }

    /**
     * 文件的写入
     *
     * @param filePath(文件路径+文件名)
     * @param args
     * @throws IOException
     */
    public static void writeFile(String filePath, String args) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        fw.write(args);
        fw.close();
    }

    /**
     * 文件的写入
     *
     * @param filePath(文件路径+文件名)
     * @param args               要写入的内容
     * @param isUTF8             是否以UTF-8的文件编码写入文件
     * @throws IOException
     */
    public static void writeFile(String filePath, String args, boolean isUTF8) throws IOException {
        if (isUTF8) {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
            out.write(args);
            out.flush();
            out.close();
        } else {
            FileWriter fw = new FileWriter(filePath);
            fw.write(args);
            fw.close();
        }
    }

    /**
     * 文件的写入
     *
     * @param filePath 文件路径
     * @param fileName 文件名
     * @param args     要写入的内容
     * @param isUTF8   是否以UTF-8的文件编码写入文件
     * @throws IOException
     */
    public static void writeFile(String filePath, String fileName, String args, boolean isUTF8) throws IOException {
        File f = new File(filePath);
        if (!f.exists()) {
            f.mkdirs();
        }
        if (isUTF8) {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filePath + fileName), "UTF-8");
            out.write(args);
            out.flush();
            out.close();
        } else {
            FileWriter fw = new FileWriter(filePath + fileName);
            fw.write(args);
            fw.close();
        }
    }

    /**
     * 创建与删除文件
     *
     * @param filePath
     * @param fileName
     * @return 创建成功返回true
     * @throws IOException
     */
    public static boolean createAndDeleteFile(String filePath, String fileName) throws IOException {
        boolean result = false;
        File file = new File(filePath, fileName);
        if (file.exists()) {
            file.delete();
            result = true;
        } else {
            file.createNewFile();
            result = true;
        }
        return result;
    }

    /**
     * 创建和删除目录
     *
     * @param folderName
     * @param filePath
     * @return 删除成功返回true
     */
    public static boolean createAndDeleteFolder(String folderName, String filePath) {
        boolean result = false;
        try {
            File file = new File(filePath + folderName);
            if (file.exists()) {
                file.delete();
                System.out.println("目录已经存在，已删除!");
                result = true;
            } else {
                file.mkdir();
                System.out.println("目录不存在，已经建立!");
                result = true;
            }
        } catch (Exception ex) {
            result = false;
            System.out.println("CreateAndDeleteFolder is error:" + ex);
        }
        return result;
    }

    /**
     * 输出目录中的所有文件及目录名字
     *
     * @param filePath
     */
    public static void readFolderByFile(String filePath) {
        File file = new File(filePath);
        File[] tempFile = file.listFiles();
        for (int i = 0; i < tempFile.length; i++) {
            if (tempFile[i].isFile()) {
                System.out.println("File : " + tempFile[i].getName());
            }
            if (tempFile[i].isDirectory()) {
                System.out.println("Directory : " + tempFile[i].getName());
            }
        }
    }

    /**
     * 检查文件中是否为一个空
     *
     * @param filePath
     * @param fileName
     * @return 为空返回true
     * @throws IOException
     */
    public static boolean fileIsNull(String filePath, String fileName) throws IOException {
        boolean result = false;
        FileReader fr = new FileReader(filePath + fileName);
        if (fr.read() == -1) {
            result = true;
            System.out.println(fileName + " 文件中没有数据!");
        } else {
            System.out.println(fileName + " 文件中有数据!");
        }
        fr.close();
        return result;
    }

    /**
     * 读取文件中的所有内容
     *
     * @param filePath
     * @param fileName
     * @throws IOException
     */
    public static void readAllFile(String filePath, String fileName) throws IOException {
        FileReader fr = new FileReader(filePath + fileName);
        int count = fr.read();
        while (count != -1) {
            count = fr.read();
            if (count == 13) {
                fr.skip(1);
            }
        }
        fr.close();
    }

    /**
     * 一行一行的读取文件中的数据
     *
     * @param filePath
     * @param fileName
     * @throws IOException
     */
    public static void readLineFile(String filePath, String fileName) throws IOException {
        FileReader fr = new FileReader(filePath + fileName);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        while (line != null) {
            line = br.readLine();
        }
        br.close();
        fr.close();
    }

    /**
     * 一行一行的读取文件中的数据,转换成字符串
     *
     * @param filePath
     * @throws IOException
     */
    public static String readLineFile(String filePath) throws IOException {
        StringBuffer sb = new StringBuffer();
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            line = br.readLine();
        }
        br.close();
        fr.close();
        return sb.toString();
    }

    /**********************************/


    /**
     * 读取文件已byte流的形式返回,并删除临时文件;
     *
     * @param fileName :文件在服务器上的绝对路径;
     * @return
     * @throws Exception
     */
    public static ByteArrayInputStream file2ByteArrayInputStream(String fileName)
            throws Exception {
        try {
            File file = new File(fileName);
            return file2ByteArrayInputStream(file);
        } catch (Exception e) {
            throw new Exception("将文件转换为流的过程中出现错误!");
        }
    }

    /**
     * 读取文件已byte流的形式返回,并删除临时文件;
     *
     * @param file :文件
     * @throws Exception
     */
    public static ByteArrayInputStream file2ByteArrayInputStream(File file)
            throws Exception {
        try {
            FileInputStream is = new FileInputStream(file);
            byte[] b = new byte[is.available()];
            is.read(b);
            is.close();
//			file.delete();
            return new ByteArrayInputStream(b);
        } catch (Exception e) {
            throw new Exception("将文件转换为流的过程中出现错误!");
        }
    }

    /**
     * 从url读取文本
     *
     * @param strURL String url地址
     * @return String
     */
    public String readFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            String rtnStr = "";
            while ((str = in.readLine()) != null) {
                rtnStr = rtnStr + new String(str.getBytes(), "GB2312");
            }
            in.close();
            return rtnStr;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从输入流中读取内容
     *
     * @param is InputStream 输入流对象
     * @return String
     * @throws Exception
     */
    public String readFromIS(InputStream is) throws Exception {
        try {
            String strRtn = "";
            int length = is.available();
            byte[] buf = new byte[length];
            while ((is.read(buf, 0, length)) != -1) {
                strRtn = strRtn + new String(buf, 0, length, "GB2312");
            }
            return strRtn;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            is.close();
        }
    }

    /**
     * 将文件夹清空
     *
     * @param staticPath
     */
    public static void clearFile(String staticPath) {
        File file = new File(staticPath);
        deleteFile(file);
        file.mkdirs();
    }


    /**
     * 删除文件夹及以下的文件
     *
     * @param staticPath
     */
    public static void delFolder(String staticPath) {
        File file = new File(staticPath);
        if (file.exists()) {
            deleteFile(file);
            file.delete();
        }
    }

    /**
     * 递归删除文件夹下内容
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            System.out.println("所删除的文件不存在！" + '\n');
        }
    }

    /**
     * 传送文件到oss中
     *
     * @param filePathList
     * @param investorId
     */
    public static void transferFile(List<String> filePathList, Integer investorId, String fileType) throws Exception {
        if (ObjectUtils.isNotNull(filePathList)) {
            String pathUrl = filePathList.get(1);
            String filePath = filePathList.get(2);
            //上传oss
            if ("1".equals(filePathList.get(0))) {
                AliYunUploadFile.uploadFile(pathUrl, filePath);
            } else {
                AliYunUploadFile.downloadFile(pathUrl, filePath);
                //redisCache.remove(CacheConstants.JUDGMENTDOCUMENT + fileType + investorId);
            }
        }
    }

    /**
     * 获取文件路径
     *
     * @param proCreateTime 项目创建时间
     * @param projectId     项目ID
     * @param investorId    投资方ID
     */
/*    public static String getFileUrl(Date proCreateTime, Integer projectId, Integer investorId) {
        if (proCreateTime != null) {
            //目录规则：report\年月\项目id\投资方id
            String yyyyMmStr = DateUtils.formatDate(proCreateTime, "yyyy-MM");
            if (ObjectUtils.isNotNull(projectId)) {
                if (investorId != null && investorId != 0) {
                    return "report" + File.separator + yyyyMmStr + File.separator + "project_" + projectId + File.separator + "investor_" + investorId;
                } else {
                    return "report" + File.separator + yyyyMmStr + File.separator + "project_" + projectId;
                }
            }
        }
        //当前时间
        String yyyyMmStr = DateUtils.formatDate(new Date(), "yyyy-MM");
        if (investorId != null && investorId != 0) {
            return "report" + File.separator + yyyyMmStr + File.separator + "investor_" + investorId + File.separator + ObjectUtils.getShortUuid();
        } else {
            return "report" + File.separator + yyyyMmStr + File.separator + "document" + File.separator + ObjectUtils.getShortUuid();
        }
    }*/

    /**
     * 临时文件路径存放压缩包路径
     * @return
     */
  /*  public static String getTempZipFileUrl(){
        String yyyyMmStr = DateUtils.formatDate(new Date(), "yyyy-MM");
        return "tem_zip"+File.separator+yyyyMmStr;
    }*/
    /**
     * 临时文件路径，存放解压完以后的文件路径
     * @return
     */
  /*  public static String getTempDestinationFileUrl(){
        String yyyyMmStr = DateUtils.formatDate(new Date(), "yyyy-MM");
        return "tem_des"+File.separator+yyyyMmStr;
    }*/

    /**
     * 下载文件
     *
     * @param fileUrl  存储文件路径
     * @param fileName 文件名称
     */
    public static void downloadFileByFlow(HttpServletResponse response, String fileUrl, String fileName) throws Exception {
        InputStream fis = new BufferedInputStream(new FileInputStream(fileUrl));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        // 清空response
        response.reset();
        // 设置response的Header
        response.setHeader("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(fileName, "utf-8"));
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
    }

/*    public static void downloadExcel(HttpServletRequest request, HttpServletResponse response, Workbook workbook, String fileName, int saveFormat) throws Exception {
        response.setCharacterEncoding("utf-8");
        String firefox = "firefox";
        if(firefox.equals(getExplorerType(request))){
            //处理火狐浏览器在下载的excel文件后再次加一个.xls后缀的问题
            response.setContentType("application/x-excel");
            //火狐浏览器自己会对URL进行一次URL转码所以区别处理
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("GB2312"), StandardCharsets.ISO_8859_1));
        }else{
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "utf-8"));
        }
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.save(outputStream, saveFormat);
        outputStream.close();
    }*/

/*    public static String getExplorerType(HttpServletRequest request){
        String agent = request.getHeader("USER-AGENT");
        if(agent != null && agent.toLowerCase().indexOf("firefox") > 0){
            return "firefox";
        }else if(agent != null && agent.toLowerCase().indexOf("msie") > 0){
            return "ie";
        }else if(agent != null && agent.toLowerCase().indexOf("chrome") > 0){
            return "chrome";
        }else if(agent != null && agent.toLowerCase().indexOf("opera") > 0){
            return "opera";
        }else if(agent != null && agent.toLowerCase().indexOf("safari") > 0){
            return "safari";
        }
        return "others";
    }*/

/*    public static void transferFile(List<String> filePathList, Long valueOf, String fileType) throws MqttException, IOException {
        if (ObjectUtils.isNotNull(filePathList)) {
            String pathUrl = filePathList.get(1);
            String filePath = filePathList.get(2);
            System.out.println("pathUrl===" + pathUrl);
            System.out.println("filePath====" + filePath);
            //上传oss
            if ("1".equals(filePathList.get(0))) {
                AliYunUploadFile.uploadFile(pathUrl, filePath);
            } else {
                AliYunUploadFile.downloadFile(pathUrl, filePath);
                //   redisCache.remove(CacheConstants.JUDGMENTDOCUMENT + fileType + investorId);
            }
        }

    }*/

    /**
     * Java文件操作 获取不带扩展名的文件名
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /*    public static void copyFile(HttpServletRequest request, String fileUrl, String fileName, String tmpdir) {
            //判断数据库文件是否存在，不存在去阿里云下载
            String pathUrl = AliYunUploadFile.isExitPathUrl(request, fileUrl);
            if (org.apache.commons.lang3.StringUtils.isNotBlank(pathUrl)) {
                //将根路径里“\\”转换为"/"
                pathUrl = pathUrl.replaceAll("\\\\", "/");
                File file = new File(pathUrl);
                if (file.exists()) {
                    File newFile = new File(tmpdir + File.separator + fileName);
                    FileUtil.copy(file, newFile, true);
                    //删除本地文件
                    if(file.exists()){
                        file.delete();
                    }
                }
            }
        }*/


    public static void zipDecompression(MultipartFile file, List<File> list)throws Exception {
        //创建临时文件夹解压文件
        String fileName = file.getOriginalFilename();
        //创建临时路径存放解压后的文件
        String tempDestinationFileUrl = getTempDestinationFileUrl();
        //拼接盘符路径+后缀路径
        tempDestinationFileUrl = AliYunUploadFile.getRealPath(tempDestinationFileUrl);
        //tempDestinationFileUrl="C:\\Users\\Lenovo\\Desktop\\home_zlpg\\tem_des";
        //String path = "d:/zip/";
        File dir = new File(tempDestinationFileUrl);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //创建临时路径存放压缩包
        String tempZipFileUrl = getTempZipFileUrl();
        tempZipFileUrl = AliYunUploadFile.getRealPath(tempZipFileUrl);
        //tempZipFileUrl="C:\\Users\\Lenovo\\Desktop\\home_zlpg\\tem_zip";
        //String filePath = "d:/test/";
        File fileDir = new File(tempZipFileUrl);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File saveFile = new File(fileDir, fileName);//将压缩包解析到指定位置
        try {
            file.transferTo(saveFile);
            String newFilePath = tempZipFileUrl + File.separator + fileName;
            File zipFile = new File(newFilePath);
            //unZipFiles(zipFile, tempDestinationFileUrl, list);//解压文件，获取文件路径
            unZipFiles(saveFile, tempDestinationFileUrl, list);//解压文件，获取文件路径
            //删除存放压缩包的临时文件夹
            //FileUtils.deleteFile(tempZipFileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("解压执行失败");
            throw new IOException("解压执行失败");
        }
        //程序结束时，删除临时文件
        deleteFiles(tempZipFileUrl);//删除压缩包文件夹
        //deleteFiles(tempDestinationFileUrl);//删除解压文件夹**
    }
    public static String getTempZipFileUrl() {
        String yyyyMmStr = DateUtils.formatDate(new Date(), "yyyy-MM");
        return "tem_zip" + File.separator + yyyyMmStr;
    }
    public static String getTempDestinationFileUrl() {
        String yyyyMmStr = DateUtils.formatDate(new Date(), "yyyy-MM");
        return "tem_des" + File.separator + yyyyMmStr;
    }

    private static void unZipFiles(File srcFile, String destDirPath, List<File> list) {
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile.getPath() + "所指文件不存在");
        }
        // 开始解压
        ZipFile zipFile = null;
        try {
            //编码不设置的话，报java.lang.IllegalArgumentException: MALFORMED异常
            zipFile = new ZipFile(srcFile, Charset.forName("GBK"));
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                java.util.zip.ZipEntry entry = (java.util.zip.ZipEntry) entries.nextElement();
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    String dirPath = destDirPath + "/" + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                    File[] files = dir.listFiles();
                    for (File file : files) {
                        //System.out.println("名称" + file.getName() + "---路径" + file.getAbsolutePath());
                        list.add(file);
                    }
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(destDirPath + "/" + entry.getName());
                    // 保证这个文件的父文件夹必须要存在
                    //System.out.println("名称" + targetFile.getName() + "---路径" + targetFile.getAbsolutePath());
                    list.add(targetFile);
                   /* if(!targetFile.getParentFile().exists()){
                    }*/
                    targetFile.createNewFile();
                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[1024];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    // 关流顺序，先打开的后关闭
                    fos.close();
                    is.close();
                }
            }
        } catch (Exception e) {
            //throw new RuntimeException("unzip error from ZipUtils", e);
            e.printStackTrace();
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void deleteFiles(String filePath) {
        File file = new File(filePath);
        if ((!file.exists()) || (!file.isDirectory())) {
            System.out.println("file not exist");
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (filePath.endsWith(File.separator)) {
                temp = new File(filePath + tempList[i]);
            } else {
                temp = new File(filePath + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                deleteFiles(filePath + "/" + tempList[i]);
            }
        }
        // 空文件的删除
        file.delete();
    }

}
