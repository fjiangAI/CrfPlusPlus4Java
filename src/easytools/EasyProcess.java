/**
 * 
 */
package easytools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 这是执行CMD命令的工具类
 * @author jf320
 *
 */
public class EasyProcess {
	/**
	 * 执行命令
	 * @param cmd cmd文件名
	 * @param directory 工作环境
	 * @return 执行结果输出
	 */
    private static String executeBatCmd(String cmd,String directory) 
    { 
        StringBuffer reSb = new StringBuffer(); 
        try 
        { 
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe ", "/c ", cmd); 
            //设置工作区域
            processBuilder.directory(new File(directory));
            // 将错误信息合并到标准输出信息中，可以使用getInputStream来获取所有的输出信息 
            processBuilder.redirectErrorStream(true); 
            try 
            { 
                BufferedReader br = new BufferedReader(new InputStreamReader(processBuilder.start().getInputStream())); 
                String line = null; 
                while ((line = br.readLine()) != null) 
                { 
                    reSb.append(line); 
                } 
            } 
            catch (IOException e) 
            {
            	e.printStackTrace();
                           } 
        } 
        catch (Exception e) 
        { 
        	e.printStackTrace();
        } 
        return reSb.toString(); 
    } 
    /**
     * 执行bat结果输出
     * @param batName bat的文件
     * @param direction 运行环境
     * @return
     */
	public String runbat(String batName,String direction) {
		String result=executeBatCmd(batName, direction);
		return result;
    }
}
