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
 * ���Ƕ��ļ������Ĺ�����
 * @author jf320
 * @date 2017/10/20
 * 
 */
public class EasyFile {
	/**
	 * �����ļ�
	 * @param src ԭ�ļ�
	 * @param dest Ŀ���ļ�
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
        
    }
	/**
	 * ��ȡĿ��Ŀ¼�µ��ļ�������
	 * @param path Ŀ��Ŀ¼ 
	 * @param isrecursive �Ƿ�ݹ�
	 * @return �ļ�������
	 */
	public static int getchildfoldnumber(String path,boolean isrecursive){
		int foldnumber=0;
		File f = new File(path);
		File[] fl = null;
		//�ж��ǲ���Ŀ¼
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
	 * �����ļ���
	 * @param path �ļ���
	 */
	public static void createDir(String path){
        File dir=new File(path);
        if(!dir.exists()){
        	dir.mkdir();
        }
    }
	/**
	 * ��ȡĿ��Ŀ¼�������ļ���(������dir)
	 * @param dir Ŀ��Ŀ¼
	 * @return
	 */
	public static ArrayList<String> getAllFile(String dir){
		ArrayList<String> result=new ArrayList<String>();
		//·��
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
	 * �������ļ�
	 * @param filename ��������·�����ļ���
	 * @throws IOException
	 */
	public static void createFile(String filename) throws IOException{
        File file=new File(filename);
        if(!file.exists()){
        	file.createNewFile();
        }
    }
	/**
	 * �������ļ�
	 * @param path �ļ�����Ŀ¼
	 * @param filename �����ļ���
	 * @throws IOException
	 */
	public static void createFile(String path,String filename) throws IOException{
        File file=new File(path+"/"+filename);
        if(!file.exists()){
        	file.createNewFile();
        }
    }
	/**
     * �ݹ�ɾ��Ŀ¼�µ������ļ�����Ŀ¼�������ļ�
     * @param dir ��Ҫɾ�����ļ�Ŀ¼
     * @return �Ƿ񷵻سɹ�
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //�ݹ�ɾ��Ŀ¼�е���Ŀ¼��
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // Ŀ¼��ʱΪ�գ�����ɾ��
        return dir.delete();
    }
    /**
     * �ݹ�ɾ��Ŀ¼�µ������ļ�����Ŀ¼�������ļ�
     * @param dir ��Ҫɾ�����ļ�Ŀ¼
     * @return �Ƿ񷵻سɹ�
     */
    public static boolean deleteDir(String dir) {
    	return deleteDir(new File(dir));
    }
	/**
	 * ɾ���ļ�
	 * @param filename ����·�����ļ���
	 */
	public static void delFile(String filename){
        File file=new File(filename);
        if(file.exists()&&file.isFile()){
        	file.delete();
        }
    }
	/**
	 * ɾ���ļ�
	 * @param path �ļ�����Ŀ¼
	 * @param filename �����ļ���
	 */
	public static void delFile(String path,String filename){
        File file=new File(path+"/"+filename);
        if(file.exists()&&file.isFile()){
        	file.delete();
        }
    }
	/**
	 * ��ȡ�ַ�����ȥ�����з���
	 * @param filename Ŀ���ļ���
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
	 * ���ж�ȥ�ַ�����ȥ�����з���
	 * @param filepath Ŀ���ļ������ļ���
	 * @param filename Ŀ���ļ���
	 * @return
	 */
	public static ArrayList<String> readFilebyLines(String filepath,String filename) {
		String newfile=filepath+"/"+filename;
		return readFilebyLines(newfile);
	}
	/**
	 * ���ж�ȡ�ַ�����ȥ�����з���
	 * @param fileName Ŀ���ļ���
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
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
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
	 * ��ȡ�ļ�������ʹ�÷ָ�����ʹ�÷���ֵΪһ����ά����
	 * @param filepath Ŀ���ļ������ļ���
	 * @param filename Ŀ���ļ���
	 * @param split �ָ���
	 * @param haveHead �Ƿ��б���ͷ
	 * @return
	 */
	public static String[][] readFilebySplit(String filepath,String filename,String split,Boolean haveHead){
		String newfile=filepath+"/"+filename;
		return readFilebySplit(newfile, split, haveHead);
	}
	/**
	 * ��ȡ�ļ�������ʹ�÷ָ�����ʹ�÷���ֵΪһ����ά����
	 * @param filename Ŀ���ļ���
	 * @param split �ָ���
	 * @param haveHead �Ƿ��б���ͷ
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
			//���һ�����ݵ�������ƥ�䣬�򱨴��ֱ�ӷ���
			if(templine.length!=colsize){
				throw new RuntimeException(String.format("��%d�е�������ƥ�䣬������ȡ��",i));
			}
			for(int j=0;j<colsize;j++){
				ret[i][j]=templine[j];
			}
		}
		return ret;
	}
	/**
	 * д���ļ�
	 * @param content Ҫд�������
	 * @param filename Ŀ���ļ���
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
	 * д���ļ�
	 * @param content Ҫд�������
	 * @param filepath �ļ�����Ŀ¼
	 * @param filename �ļ���
	 * @param encoder �����ʽ
	 */
	public static void writeFile(String content,String filepath,String filename,String encoder){
		EasyFile.createDir(filepath);
		String newfilename=filepath+"/"+filename;
        writeFile(content, newfilename, encoder);
	}
	/**
	 * ����д���ļ�,����ָ��������������д�롣
	 * @param contents Ҫд������ݣ�����д�룩
	 * @param targetrows Ŀ��Ҫд����е�����
	 * @param dir Ŀ���ļ�Ŀ¼
	 * @param filename Ŀ���ļ���
	 */
	public static void writeFilebyLines(ArrayList<String> contents,ArrayList<Integer> targetrows,String dir,String filename){
		EasyFile.createDir(dir);
		String newfilename=dir+"\\"+filename;
		writeFilebyLines(contents,targetrows,newfilename);
	} 
	/**
	 * ����д���ļ�,����ָ��������������д�롣
	 * @param contents Ҫд������ݣ�����д�룩
	 * @param targetrows Ŀ��Ҫд����е�����
	 * @param filename Ŀ���ļ���
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
	      			//���ַ���д�뵽ָ����·���µ��ļ���
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
	 * ����д���ļ�
	 * @param contentsҪд������ݣ�����д�룩
	 * @param dir  Ŀ���ļ�Ŀ¼
	 * @param filename Ŀ���ļ�����
	 */
	public static void writeFilebyLines(ArrayList<String> contents,String dir,String filename){
		EasyFile.createDir(dir);
		String newfilename=dir+"\\"+filename;
		writeFilebyLines(contents,newfilename);
	} 
	/**
	 * ����д���ļ�
	 * @param contents Ҫд������ݣ�����д�룩
	 * @param filename Ŀ���ļ�����
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
        			//���ַ���д�뵽ָ����·���µ��ļ���
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
	 * ���з�ת�ļ�����
	 * @param oldpath ��Ŀ¼
	 * @param oldfile ���ļ���
	 * @param newpath ��Ŀ¼
	 * @param newfile ���ļ���
	 */
	public static void reverseFile(String oldpath,String oldfile,String newpath,String newfile){
		createDir(oldpath);
		createDir(newpath);
		oldfile=oldpath+"\\"+oldfile;
		newfile=newpath+"\\"+newfile;
		reverseFile(oldfile, newfile);
	}
	/**
	 * ���з�ת�ļ�����
	 * @param path Ŀ¼
	 * @param oldfile Ŀ¼�����ļ���
	 * @param newfile Ŀ¼�����ļ���
	 */
	public static void reverseFile(String path,String oldfile,String newfile){
		reverseFile(path, oldfile, path, newfile);
	}
	/**
	 * ���з�ת�ļ�����
	 * @param oldfile ���ļ���
	 * @param newfile ���ļ���
	 */
	public static void reverseFile(String oldfile,String newfile){
		ArrayList<String> lines=readFilebyLines(oldfile);
		Collections.reverse(lines);
		writeFilebyLines(lines, newfile);
	}
	/**
	 * ʹ�÷ָ���д���ά����
	 * @param contents ��ǰ��ά����
	 * @param filename Ŀ���ļ���
	 * @param split Ҫʹ�õķָ���
	 * @param head ���������������������
	 */
	public static void writeFilebySplit(String[][] contents,String filename,String split,String[] head){
		try {
			EasyFile.createFile(filename);
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
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
	 * ʹ�÷ָ���д���ά����
	 * @param contents ��ǰ��ά����
	 * @param filename Ŀ���ļ���
	 * @param split Ҫʹ�õķָ���
	 * @param head ���������������������
	 */
	public static void writeFilebySplit(String[][] contents,String filename,String split,String[] head,String encoder){
		try {
			EasyFile.createFile(filename);
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
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
