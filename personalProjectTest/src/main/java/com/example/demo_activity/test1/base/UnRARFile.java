package com.example.demo_activity.test1.base;

/**
 * @Author: yongl
 * @DATE: 2020/6/15 16:30
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class UnRARFile {
    //系统安装的winRAR位置
    private static final String WINRAR_PATH = "C:\\Program Files\\WinRAR\\WinRAR.exe";
    /**
     * 解压方法
     * @param rarFilePath  rar压缩文件路径
     * @param unFilePath   要解压的到指定的路径
     * @throws IOException IO异常
     * @throws InterruptedException
     */
    public static void unRARFile(String rarFilePath,String unFilePath) throws IOException, InterruptedException
    {
        File file = new File(unFilePath);
        if(!file.exists())  //如果发现指定解压的路径不存在，创建目录
        {
            file.mkdirs();
        }


        String cmd = WINRAR_PATH + " x -r -o+ -ibck -y " + rarFilePath+"  "+ unFilePath;  //需要执行的命令
        Runtime runtime = Runtime.getRuntime();  //得到命令对象
        Process process = runtime.exec(cmd);   //获取执行命令过程中返回的流



        //下面是打印出流的内容，查看是否有异常

        InputStreamReader isr = new InputStreamReader(process.getInputStream(),"gbk");
        BufferedReader br = new BufferedReader(isr);
//        String line;
//        while((line = br.readLine()) != null){
//        	System.out.println(line);
//        }
//        br = new BufferedReader(new InputStreamReader(process.getErrorStream(),"gbk"));
//        while((line=br.readLine()) != null){
//        	System.out.println(line);
//        }
//        process.waitFor();
        String str = null;
        while((str=br.readLine())!=null)
        {
            if(!"".equals(str.trim())&&str!=null)  //如果当前行不为空
            {
                System.out.println(str);
            }
        }
        br.close();
    }
    public static void main(String args[]) throws InterruptedException{
        String rarPath = "C:\\Users\\zm\\Desktop\\Sping3.1.1.rar";
        String unRarPath = "C:\\Users\\zm\\Desktop\\test";
        try {
            UnRARFile.unRARFile(rarPath, unRarPath);
        } catch (IOException e) {
            System.out.println("出现异常....");
            e.printStackTrace();
        }
    }
}

