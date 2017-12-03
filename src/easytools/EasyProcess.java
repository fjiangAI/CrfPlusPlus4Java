/**
 * 
 */
package easytools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ����ִ��CMD����Ĺ�����
 * @author jf320
 *
 */
public class EasyProcess {
	/**
	 * ִ������
	 * @param cmd cmd�ļ���
	 * @param directory ��������
	 * @return ִ�н�����
	 */
    private static String executeBatCmd(String cmd,String directory) 
    { 
        StringBuffer reSb = new StringBuffer(); 
        try 
        { 
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe ", "/c ", cmd); 
            //���ù�������
            processBuilder.directory(new File(directory));
            // ��������Ϣ�ϲ�����׼�����Ϣ�У�����ʹ��getInputStream����ȡ���е������Ϣ 
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
     * ִ��bat������
     * @param batName bat���ļ�
     * @param direction ���л���
     * @return
     */
	public String runbat(String batName,String direction) {
		String result=executeBatCmd(batName, direction);
		return result;
    }
}
