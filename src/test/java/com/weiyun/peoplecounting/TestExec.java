package com.weiyun.peoplecounting;


import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TestExec {


    public static void main(String[] args) throws Exception {
        Process process = Runtime.getRuntime().exec("/usr/apps/test.sh");
        InputStream inputStream = process.getInputStream();
        byte[] b = new byte[1024];
        int len;
        InputStreamReader inputReader = new InputStreamReader(process.getInputStream());
        BufferedReader buffReader = new BufferedReader(inputReader);
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = buffReader.readLine())!=null){
            lines.add(line);
        }
        System.out.println(lines.get(lines.size()-1));
//        while ((len=inputStream.read(b))!=-1){
//            System.out.print(new String(b));
//        }
        int status = process.waitFor();
        System.out.println(status);
    }

    @Test
    public void testFile(){

        File file = new File("F:\\IdeaProjects\\cas-client-demo\\pom.xml");
        System.out.println(file.getName());
    }

}
