package com.alan.springbootbase.utils;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @description: Socket基本工具类
 * @author: Alan
 * @create: 2019-08-07 16:21
 **/
public class SocketUtil {

    public static String sendSocket(String url,int port,String msg){
        String resInfo="";
        try {
            //创建Socket对象
            Socket socket=new Socket(url,port);

            //根据输入输出流和服务端连接
            //获取一个输出流，向服务端发送信息
            OutputStream outputStream=socket.getOutputStream();
            //将输出流包装成打印流
            PrintWriter printWriter=new PrintWriter(outputStream);
            printWriter.print(msg);
            printWriter.flush();
            socket.shutdownOutput();//关闭输出流

            //获取一个输入流，接收服务端的信息
            InputStream inputStream=socket.getInputStream();

            //包装成字符流，提高效率
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            //缓冲区
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            //临时变量
            String temp=null;
            System.out.println("客户端接收服务端发送信息："+bufferedReader);
            while((temp=bufferedReader.readLine())!=null){
                resInfo+=temp;
                System.out.println("客户端接收服务端发送信息："+resInfo);
            }
            //关闭相对应的资源
            bufferedReader.close();
            inputStream.close();
            printWriter.close();
            outputStream.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
        return resInfo;
    }
}
