package main;

import counter.Counter;

import java.io.*;
import java.util.Scanner;

public class WC {
    public static void main(String[] args) throws IOException {
        while(true){
            Scanner in = new Scanner(System.in);
            String path;

            System.out.println("请输入指令。");
            String command = in.next();
            char flag = '0';

            if (!command.equals("wc.exe")) {
                System.out.println("输入有误，请先输入 wc.exe");
            }
            command = in.next();
            switch (command){
                case "-c":flag = 'c';break;
                case "-w":flag = 'w';break;
                case "-l":flag = 'l';break;
                case "-a":flag = 'a';break;
                case "exit": System.exit(0);
                default: {
                    System.out.println("输入有误，可用命令为：-c, -w, -l, -a, exit");
                }
            }
            path = in.next();
            File f = new File(path);
            //判断输入的文件是否合法
            if(!f.exists()){
                System.out.println("文件不存在。");
            }
            else if(!f.isFile()){
                System.out.println("输入的不是合法文件。");
            }
            else if(!f.canRead()){
                System.out.println("文件无法读取。");
            }
            else{
                Counter wc = new Counter(path);
                switch (flag){
                    case 'c': wc.printChars();break;
                    case 'w': wc.printWords();break;
                    case 'l': wc.printLines();break;
                    case 'a':{
                        wc.printCodeLines();
                        wc.printCommentLines();
                        wc.printBlankLines();
                        break;
                    }
                    default:
                        System.out.println("失败。");
                }
            }
        }
    }
}
