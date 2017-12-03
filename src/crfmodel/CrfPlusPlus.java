package crfmodel;
import java.io.IOException;
import java.util.ArrayList;

import easytools.EasyEvaluation;
import easytools.EasyFile;
import easytools.EasyProcess;

/**
 * CRF++算法的包装类
 * @author jf320
 * @date: 2017/10/22
 */
public class CrfPlusPlus {
	public String workspace="";
	public String trainfilename="";
	public String modelfilename="";
	public String testfilename="";
	public String trainparameter="";
	public String testparameter="";
	public String traininfofilename="";
	public String testinfofilename="";
	public String templatefilename="";
	public String exectrainfilename="";
	public String exectestfilename="";
	public String evaluationfilename="";
	/**
	 * CrfPlusPlus初始化
	 * @param workspace 这是CRF++程序的工作目录
	 */
	public CrfPlusPlus(String workspace) {
		//工作目录
		this.workspace = workspace;
		//默认测试结果文件名
		this.testinfofilename="result.txt";
		//默认训练信息文件名
		this.traininfofilename="traininfo.txt";
		//默认测试文件文件名
		this.testfilename="test.txt";
		//默认训练文件文件名
		this.trainfilename="train.txt";
		//默认模板文件名
		this.templatefilename="template";
		//默认测试文件执行脚本文件名
		this.exectestfilename="exectest.bat";
		//默认训练文件执行脚本文件名
		this.exectrainfilename="exectrain.bat";
		//默认训练参数
		this.trainparameter="-c 4.0";
		//默认测试参数
		this.testparameter="-m";
		//默认模型文件文件名
		this.modelfilename="model";
		//默认评估文件文件名
		this.evaluationfilename="evaluation.txt";
	}
	/**
	 * 清除评估文件
	 */
	public void claerEvaluation(){
		EasyFile.delFile(workspace, evaluationfilename);
	}
	/**
	 * 清除模板
	 */
	public void claerTemplate(){
		EasyFile.delFile(workspace, templatefilename);
	}
	/**
	 * 清理命令
	 */
	public void clearCmd(){
		EasyFile.delFile(workspace, exectrainfilename);
		EasyFile.delFile(workspace, exectestfilename);
	}
	/**
	 * 清理模型
	 */
	public void clearModel(){
		EasyFile.delFile(workspace, modelfilename);
	}
	/**
	 * 清理测试文件
	 */
	public void clearTest()
	{
		EasyFile.delFile(workspace, testfilename);
		EasyFile.delFile(workspace, testinfofilename);
	}
	/**
	 * 清理训练文件
	 */
	public void clearTrain()
	{
		EasyFile.delFile(workspace, trainfilename);
		EasyFile.delFile(workspace, traininfofilename);
	}
	/**
	 * 创建训练命令
	 */
	public void createTestCmd(){
		//创建命令文件
		String cmd="..\\crf_test";
		String content=String.format("%s %s %s %s >> %s", cmd,testparameter,modelfilename,testfilename,testinfofilename);
		try {
			EasyFile.createFile(workspace,exectestfilename);
			EasyFile.writeFile(content, workspace,exectestfilename,"GB2312");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	/**
	 * 创建训练命令
	 */
	public void createTrainCmd(){
		//创建命令文件
		String cmd="..\\crf_learn";
		String content=String.format("%s %s %s %s %s >> %s", cmd,trainparameter,templatefilename,trainfilename,modelfilename,traininfofilename);
		try {
			EasyFile.createFile(workspace,exectrainfilename);
			EasyFile.writeFile(content, workspace,exectrainfilename,"GB2312");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	/**
	 * 输出评估结果
	 * @param evaluationfilename 评估结果文件名
	 * @param types 标签类型集合
	 */
	public void evaluation(String evaluationfilename,ArrayList<String> types){
		ArrayList<String> tempcontent=EasyFile.readFilebyLines(workspace,testinfofilename);
		ArrayList<String> newcontent=new ArrayList<String>();
		for(String line : tempcontent){
			if(!"".equals(line)){
				newcontent.add(line);
			}
		}
		String tempfilename=workspace+"/"+"tempresult.txt";
		try {
			EasyFile.createFile(tempfilename);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		EasyFile.writeFilebyLines(newcontent, tempfilename);
		String[][] contents=EasyFile.readFilebySplit(tempfilename, "\t", false);
		EasyEvaluation ee=new EasyEvaluation(contents, -2, -1, types);
		ee.saveEvaluation2file(workspace+"/"+evaluationfilename);
		EasyFile.delFile(tempfilename);
	}
	/**
	 * 初始化环境
	 */
	public void init(){
		clearTrain();
		clearTest();
		clearModel();
		clearCmd();
		claerTemplate();
		claerEvaluation();
	}
	/**
	 * 把模型从存储位置复制到工作目录中
	 * @param targetmodel 这是存放原模型的目录及文件名
	 */
	public void loadModel(String targetmodel){
		EasyFile.copyFile(targetmodel, workspace+"/"+modelfilename);
	}
	/**
	 * 把模板从存储位置复制到工作目录中
	 * @param targettemplate 这是存放原模板的目录及文件名
	 */
	public void loadTemplate(String targettemplate){
		EasyFile.copyFile(targettemplate, workspace+"/"+templatefilename);
	}
	/**
	 * 把测试文件从存储位置复制到工作目录中
	 * @param targettestfilename 这是存放原测试文件的目录及文件名
	 */
	public void loadTestData(String targettestfilename){
		EasyFile.copyFile(targettestfilename, workspace+"/"+testfilename);
	}
	/**
	 * 把训练文件从存储位置复制到工作目录中
	 * @param targettrainfilename 这是存放原训练文件的目录及文件名
	 */
	public void loadTrainData(String targettrainfilename){
		//把目标训练文件放到工作目录下
		EasyFile.copyFile(targettrainfilename, workspace+"/"+trainfilename);
	}
	/**
	 * 默认一次完整实验(包括训练、测试及评估过程)
	 * @param targettrainfilename 训练文件的目录及文件名
	 * @param targettestfilename 测试文件的目录及文件名
	 * @param targettemplate 模板文件的目录及文件名
	 * @param types 预测标签集合
	 */
	public void pipeline(String targettrainfilename,String targettestfilename,String targettemplate,ArrayList<String> types){
		loadTestData(targettestfilename);
		loadTrainData(targettrainfilename);
		loadTemplate(targettemplate);
		createTrainCmd();
		createTestCmd();
		trainModel();
		test();
		evaluation(evaluationfilename,types);
	}
	/**
	 * 保存所有实验结果
	 * @param model 保存模型的目的目录及文件名
	 * @param result 保存结果的目的目录及文件名
	 * @param evaluation 保存评估的目的目录及文件名
	 */
	public void saveAll(String model,String result,String evaluation){
		saveModel(model);
		saveResult(result);
		saveEvaluation(evaluation);
	}
	/**
	 * 保存评估结果
	 * @param evaluationdes 保存评估的目的目录及文件名
	 */
	public void saveEvaluation(String evaluationdes){
		EasyFile.copyFile(workspace+"/"+evaluationfilename, evaluationdes);
	}
	/**
	 * 保存模型到目标文件
	 * @param modeldes 保存模型的目的目录及文件名
	 */
	public void saveModel(String modeldes){
		EasyFile.copyFile(workspace+"/"+modelfilename, modeldes);
	}
	/**
	 * 保存训练结果到目标文件
	 * @param resultdes 保存结果的目的目录及文件名
	 */
	public void saveResult(String resultdes){
		EasyFile.copyFile(workspace+"/"+testinfofilename, resultdes);
	}
	/**
	 * 设置训练参数
	 * @param parameter
	 */
	public void setTrainParameter(String parameter){
		this.trainparameter=parameter;
	}
	/**
	 * 测试
	 */
	public void test(){
		//执行命令文件
		EasyProcess ep=new EasyProcess();
		ep.runbat(workspace+"/"+exectestfilename,workspace);
	}
	/**
	 * 训练模型
	 */
	public void trainModel(){
		//执行命令文件
		EasyProcess ep=new EasyProcess();
		ep.runbat(workspace+"/"+exectrainfilename,workspace);
	}
}
