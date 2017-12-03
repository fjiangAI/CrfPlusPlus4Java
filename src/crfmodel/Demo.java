package crfmodel;

import java.util.ArrayList;

import easytools.EasyFile;
/**
 * 这是CRF++的一个测试例子
 * @author jf
 *
 */
public class Demo {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		//System.out.println(System.getProperty("user.dir"));//user.dir指定了当前的路径 
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
		//初始化环境
		crf.init();
		//执行一次训练
		crf.pipeline(targettrainfilename, targettestfilename, targettemplate, types); 
		//保存训练结果
		String expresult=targetroot+"/expresult";
		EasyFile.createDir(expresult);
		String model=expresult+"/model";
		String result=expresult+"/result.txt";
		String evaluation=expresult+"/evaluation.txt";
		crf.saveAll(model, result, evaluation);
		//清除所有文件
		crf.init();
	}

}
