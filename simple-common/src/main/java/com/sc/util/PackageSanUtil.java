package com.sc.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class PackageSanUtil {

    public      List<String> scanPackage(String basePackage) {

        List<String>  classNames =new ArrayList<String>();
        //扫描编译好的类路径下所有的类
        String dir= basePackage.replaceAll("\\.", "/");
    //    URL url = this.getClass().getClassLoader().getResource( "/"+dir);
        URL url = this.getClass().getClassLoader().getResource(basePackage.replaceAll("\\.", "/"));

        String fileStr = url.getFile();

        File file = new File(fileStr);
        String[] filesStr = file.list();

        for (String path : filesStr) {
            File filePath = new File(fileStr + "/" + path);//扫描com.enjoy.james下的所有class类

            //递归调用扫描,如果是路径,继续扫描
            if (filePath.isDirectory()) {
                // com.enjoy.james
                scanPackage(basePackage + "." + path);
            } else {

                String  packName=basePackage + "." + filePath.getName();
                System.out.println("basePackage="+basePackage);
                System.out.println("file="+filePath.getName());
                System.out.println("packName="+ packName);
                classNames.add((basePackage + "." + filePath.getName()).replace(".class",""));
            }
        }

        return   classNames ;
    }

    public static void main(String[] args) {

        PackageSanUtil  ps=new PackageSanUtil();
        List<String>  list=ps.scanPackage("com.sc.api");
        for (String string : list) {
            System.out.println(string);
        }

    }
}
