/**
 * 
 */
package easytools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;


/**
 * 这是对文件操作的工具类
 * @author jf320
 * @date 2017/10/20
 * 
 */
public class EasyFile {
	/**
	 * 复制文件
	 * @param src 原文件
	 * @param dest 目标文件
	 * @throws IOException
	 */
	public static void copyFile(String src,String dest){
		try {
			FileInputStream in;
			in = new FileInputStream(src);
			File file=new File(dest);
	        if(!file.exists())
	        {	
	        	file.createNewFile();
	        }
	        FileOutputStream out=new FileOutputStream(file);
	        int c;
	        byte buffer[]=new byte[1024];
	        while((c=in.read(buffer))!=-1){
	            for(int i=0;i<c;i++){
	            	out.write(buffer[i]); 
	            }       
	        }
	        in.close();
	        out.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
        
    }
	/**
	 * 获取目标目录下的文件夹数量
	 * @param path 目标目录 
	 * @param isrecursive 是否递归
	 * @return 文件夹数量
	 */
	public static int getchildfoldnumber(String path,boolean isrecursive){
		int foldnumber=0;
		File f = new File(path);
		File[] fl = null;
		//判断是不是目录
		if(f.isDirectory())
		{ 
			fl = f.listFiles();
			for (int i=0; i < fl.length; i++)
			{
			    File f2 = fl[i];
			    if (f2.isDirectory())
			    {
			    	foldnumber = foldnumber + 1;
			    }   
			}
		}
		return foldnumber;
	}
	/**
	 * 创建文件夹
	 * @param path 文件夹
	 */
	public static void createDir(String path){
        File dir=new File(path);
        if(!dir.exists()){
        	dir.mkdir();
        }
    }
	/**
	 * 获取目标目录下所有文件名(不包含dir)
	 * @param dir 目标目录
	 * @return
	 */
	public static ArrayList<String> getAllFile(String dir){
		ArrayList<String> result=new ArrayList<String>();
		//路径
		String path = dir;
        File f = new File(path);
        if (!f.exists()) {
            return result;
        }
        File[] fa = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (!fs.isDirectory()) {
                result.add(fs.getName());
            }
        }
        return result;
	}
	/**
	 * 创建新文件
	 * @param filename 含有完整路径的文件名
	 * @throws IOException
	 */
	public static void createFile(String filename) throws IOException{
        File file=new File(filename);
        if(!file.exists()){
        	file.createNewFile();
        }
    }
	/**
	 * 创建新文件
	 * @param path 文件所在目录
	 * @param filename 仅含文件名
	 * @throws IOException
	 */
	public static void createFile(String path,String filename) throws IOException{
        File file=new File(path+"/"+filename);
        if(!file.exists()){
        	file.createNewFile();
        }
    }
	/**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return 是否返回成功
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return 是否返回成功
     */
    public static boolean deleteDir(String dir) {
    	return deleteDir(new File(dir));
    }
	/**
	 * 删除文件
	 * @param filename 完整路径的文件名
	 */
	public static void delFile(String filename){
        File file=new File(filename);
        if(file.exists()&&file.isFile()){
        	file.delete();
        }
    }
	/**
	 * 删除文件
	 * @param path 文件所在目录
	 * @param filename 仅含文件名
	 */
	public static void delFile(String path,String filename){
        File file=new File(path+"/"+filename);
        if(file.exists()&&file.isFile()){
        	file.delete();
        }
    }
	/**
	 * 读取字符串（去除换行符）
	 * @param filename 目标文件名
	 * @return
	 */
	public static String readFile(String filename){
		ArrayList<String> result=readFilebyLines(filename);
		StringBuffer sbBuffer=new StringBuffer();
		for (String string : result) {
			sbBuffer.append(string);
			sbBuffer.append("\n");
		}
		return sbBuffer.toString().trim();
	}
	/**
	 * 按行读去字符串（去除换行符）
	 * @param filepath 目标文件所在文件夹
	 * @param filename 目标文件名
	 * @return
	 */
	public static ArrayList<String> readFilebyLines(String filepath,String filename) {
		String newfile=filepath+"/"+filename;
		return readFilebyLines(newfile);
	}
	/**
	 * 按行读取字符串（去除换行符）
	 * @param fileName 目标文件名
	 * @return
	 */
	public static ArrayList<String> readFilebyLines(String filename) {
        ArrayList<String> lines=new ArrayList<String>();
        FileInputStream fis;  
        BufferedReader reader = null;
        try {
        	fis = new FileInputStream(filename);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            reader = new BufferedReader(isr);
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
            	lines.add(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                	e1.printStackTrace();
                }
            }
        }
		return lines;
    }
	/**
	 * 读取文件，可以使用分隔符来使得返回值为一个二维数组
	 * @param filepath 目标文件所在文件夹
	 * @param filename 目标文件名
	 * @param split 分隔符
	 * @param haveHead 是否含有标题头
	 * @return
	 */
	public static String[][] readFilebySplit(String filepath,String filename,String split,Boolean haveHead){
		String newfile=filepath+"/"+filename;
		return readFilebySplit(newfile, split, haveHead);
	}
	/**
	 * 读取文件，可以使用分隔符来使得返回值为一个二维数组
	 * @param filename 目标文件名
	 * @param split 分隔符
	 * @param haveHead 是否含有标题头
	 * @return
	 */
	public static String[][] readFilebySplit(String filename,String split,Boolean haveHead){
		String[][] ret;
		int rowsize,colsize;
		ArrayList<String> lines=readFilebyLines(filename);
		if(haveHead){
			lines.remove(0);
		}
		rowsize=lines.size();
		colsize=lines.get(0).split(split).length;
		ret=new String[rowsize][colsize];
		for(int i=0;i<rowsize;i++){
			String currentline=lines.get(i);
			String[] templine=currentline.split(split);
			//如果一行数据的列数不匹配，则报错后直接返回
			if(templine.length!=colsize){
				throw new RuntimeException(String.format("第%d行的列数不匹配，结束读取。",i));
			}
			for(int j=0;j<colsize;j++){
				ret[i][j]=templine[j];
			}
		}
		return ret;
	}
	/**
	 * 写入文件
	 * @param content 要写入的内容
	 * @param filename 目标文件名
	 */
	public static void writeFile(String content,String filename,String encoder){
        try {
        	EasyFile.createFile(filename);
        	FileOutputStream fos = new FileOutputStream(filename);   
            OutputStreamWriter osw = new OutputStreamWriter(fos, encoder);   
            osw.write(content);   
            osw.flush(); 
            osw.close();
		 } catch (IOException e) {
		  e.printStackTrace();
		 }
	}
	/**
	 * 写入文件
	 * @param content 要写入的内容
	 * @param filepath 文件所在目录
	 * @param filename 文件名
	 * @param encoder 编码格式
	 */
	public static void writeFile(String content,String filepath,String filename,String encoder){
		EasyFile.createDir(filepath);
		String newfilename=filepath+"/"+filename;
        writeFile(content, newfilename, encoder);
	}
	/**
	 * 按行写入文件,按照指定的行数索引来写入。
	 * @param contents 要写入的内容（按行写入）
	 * @param targetrows 目标要写入的行的索引
	 * @param dir 目标文件目录
	 * @param filename 目标文件名
	 */
	public static void writeFilebyLines(ArrayList<String> contents,ArrayList<Integer> targetrows,String dir,String filename){
		EasyFile.createDir(dir);
		String newfilename=dir+"\\"+filename;
		writeFilebyLines(contents,targetrows,newfilename);
	} 
	/**
	 * 按行写入文件,按照指定的行数索引来写入。
	 * @param contents 要写入的内容（按行写入）
	 * @param targetrows 目标要写入的行的索引
	 * @param filename 目标文件名
	 */
	public static void writeFilebyLines(ArrayList<String> contents,ArrayList<Integer> targetrows, String filename){
        try {
        	EasyFile.createFile(filename);
        	FileOutputStream fos = new FileOutputStream(filename);   
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");   
		  for(int i=0;i<contents.size();i++){
			  if(targetrows.contains(i)){
				if(i==contents.size()-1){
					osw.write(contents.get(i));
	      		}
	      		else{
	      			//将字符串写入到指定的路径下的文件中
	      			osw.write(contents.get(i));
	      			osw.write("\n");
	      		}
			  }
		  }
		  osw.close();
		 } catch (IOException e) {
		  e.printStackTrace();
		 }
	}
	/**
	 * 按行写入文件
	 * @param contents要写入的内容（按行写入）
	 * @param dir  目标文件目录
	 * @param filename 目标文件名；
	 */
	public static void writeFilebyLines(ArrayList<String> contents,String dir,String filename){
		EasyFile.createDir(dir);
		String newfilename=dir+"\\"+filename;
		writeFilebyLines(contents,newfilename);
	} 
	/**
	 * 按行写入文件
	 * @param contents 要写入的内容（按行写入）
	 * @param filename 目标文件名；
	 */
	public static void writeFilebyLines(ArrayList<String> contents,String filename){
        try {
        	//System.out.println(filename);
        	EasyFile.createFile(filename);
        	FileOutputStream fos = new FileOutputStream(filename);   
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");   
        	for(int i=0;i<contents.size();i++){
        		if(i==contents.size()-1){
        			osw.write(contents.get(i));
        		}
        		else{
        			//将字符串写入到指定的路径下的文件中
        			osw.write(contents.get(i));
        			osw.write("\n");
        		}
        		
  		  }
        osw.close();
		 } catch (IOException e) {
		  e.printStackTrace();
		 }
	}
	/**
	 * 按行反转文件内容
	 * @param oldpath 老目录
	 * @param oldfile 老文件名
	 * @param newpath 新目录
	 * @param newfile 新文件名
	 */
	public static void reverseFile(String oldpath,String oldfile,String newpath,String newfile){
		createDir(oldpath);
		createDir(newpath);
		oldfile=oldpath+"\\"+oldfile;
		newfile=newpath+"\\"+newfile;
		reverseFile(oldfile, newfile);
	}
	/**
	 * 按行反转文件内容
	 * @param path 目录
	 * @param oldfile 目录下老文件名
	 * @param newfile 目录下新文件名
	 */
	public static void reverseFile(String path,String oldfile,String newfile){
		reverseFile(path, oldfile, path, newfile);
	}
	/**
	 * 按行反转文件内容
	 * @param oldfile 老文件名
	 * @param newfile 新文件名
	 */
	public static void reverseFile(String oldfile,String newfile){
		ArrayList<String> lines=readFilebyLines(oldfile);
		Collections.reverse(lines);
		writeFilebyLines(lines, newfile);
	}
	/**
	 * 使用分隔符写入二维数组
	 * @param contents 当前二维矩阵
	 * @param filename 目标文件名
	 * @param split 要使用的分隔符
	 * @param head 如果有列名，其列名数组
	 */
	public static void writeFilebySplit(String[][] contents,String filename,String split,String[] head){
		try {
			EasyFile.createFile(filename);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		StringBuffer currentline=new StringBuffer();
		if(head!=null){
			int i=0;
			for(;i<head.length-1;i++){
				currentline.append(head[i]);
				currentline.append(split);
			}
			currentline.append(head[i]);
			currentline.append("\n");
		}
		for(int i=0;i<contents.length;i++){
			int j=0;
			for(;j<contents[0].length-1;j++){
				currentline.append(contents[i][j]);
				currentline.append(split);
			}
			currentline.append(contents[i][j]);
			currentline.append("\n");
		}
		writeFile(currentline.toString().trim(),filename,"UTF-8");
	}
	/**
	 * 使用分隔符写入二维数组
	 * @param contents 当前二维矩阵
	 * @param filename 目标文件名
	 * @param split 要使用的分隔符
	 * @param head 如果有列名，其列名数组
	 */
	public static void writeFilebySplit(String[][] contents,String filename,String split,String[] head,String encoder){
		try {
			EasyFile.createFile(filename);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		StringBuffer currentline=new StringBuffer();
		if(head!=null){
			int i=0;
			for(;i<head.length-1;i++){
				currentline.append(head[i]);
				currentline.append(split);
			}
			currentline.append(head[i]);
			currentline.append("\n");
		}
		for(int i=0;i<contents.length;i++){
			int j=0;
			for(;j<contents[0].length-1;j++){
				currentline.append(contents[i][j]);
				currentline.append(split);
			}
			currentline.append(contents[i][j]);
			currentline.append("\n");
		}
		writeFile(currentline.toString().trim(),filename,encoder);
	}
	
	
}
