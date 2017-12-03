package crfmodel;

import java.util.ArrayList;

import easytools.EasyFile;
/**
 * ����CRF++��һ����������
 * @author jf
 *
 */
public class Demo {

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		//System.out.println(System.getProperty("user.dir"));//user.dirָ���˵�ǰ��·�� 
		String workspace=System.getProperty("user.dir")+"\\crf\\workspace";
		CrfPlusPlus crf=new CrfPlusPlus(workspace);
		crf.setTrainParameter("-c 4.0");
		String targetroot=System.getProperty("user.dir")+"\\example\\seg";
		String targettrainfilename=targetroot+"\\train.data";
		String targettestfilename=targetroot+"\\test.data";
		String targettemplate=targetroot+"\\template";
		ArrayList<String> types=new ArrayList<String>();
		types.add("B");
		types.add("I");
		//��ʼ������
		crf.init();
		//ִ��һ��ѵ��
		crf.pipeline(targettrainfilename, targettestfilename, targettemplate, types); 
		//����ѵ�����
		String expresult=targetroot+"/expresult";
		EasyFile.createDir(expresult);
		String model=expresult+"/model";
		String result=expresult+"/result.txt";
		String evaluation=expresult+"/evaluation.txt";
		crf.saveAll(model, result, evaluation);
		//��������ļ�
		crf.init();
	}

}
