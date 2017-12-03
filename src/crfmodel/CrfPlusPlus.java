package crfmodel;
import java.io.IOException;
import java.util.ArrayList;

import easytools.EasyEvaluation;
import easytools.EasyFile;
import easytools.EasyProcess;

/**
 * CRF++�㷨�İ�װ��
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
	 * CrfPlusPlus��ʼ��
	 * @param workspace ����CRF++����Ĺ���Ŀ¼
	 */
	public CrfPlusPlus(String workspace) {
		//����Ŀ¼
		this.workspace = workspace;
		//Ĭ�ϲ��Խ���ļ���
		this.testinfofilename="result.txt";
		//Ĭ��ѵ����Ϣ�ļ���
		this.traininfofilename="traininfo.txt";
		//Ĭ�ϲ����ļ��ļ���
		this.testfilename="test.txt";
		//Ĭ��ѵ���ļ��ļ���
		this.trainfilename="train.txt";
		//Ĭ��ģ���ļ���
		this.templatefilename="template";
		//Ĭ�ϲ����ļ�ִ�нű��ļ���
		this.exectestfilename="exectest.bat";
		//Ĭ��ѵ���ļ�ִ�нű��ļ���
		this.exectrainfilename="exectrain.bat";
		//Ĭ��ѵ������
		this.trainparameter="-c 4.0";
		//Ĭ�ϲ��Բ���
		this.testparameter="-m";
		//Ĭ��ģ���ļ��ļ���
		this.modelfilename="model";
		//Ĭ�������ļ��ļ���
		this.evaluationfilename="evaluation.txt";
	}
	/**
	 * ��������ļ�
	 */
	public void claerEvaluation(){
		EasyFile.delFile(workspace, evaluationfilename);
	}
	/**
	 * ���ģ��
	 */
	public void claerTemplate(){
		EasyFile.delFile(workspace, templatefilename);
	}
	/**
	 * ��������
	 */
	public void clearCmd(){
		EasyFile.delFile(workspace, exectrainfilename);
		EasyFile.delFile(workspace, exectestfilename);
	}
	/**
	 * ����ģ��
	 */
	public void clearModel(){
		EasyFile.delFile(workspace, modelfilename);
	}
	/**
	 * ��������ļ�
	 */
	public void clearTest()
	{
		EasyFile.delFile(workspace, testfilename);
		EasyFile.delFile(workspace, testinfofilename);
	}
	/**
	 * ����ѵ���ļ�
	 */
	public void clearTrain()
	{
		EasyFile.delFile(workspace, trainfilename);
		EasyFile.delFile(workspace, traininfofilename);
	}
	/**
	 * ����ѵ������
	 */
	public void createTestCmd(){
		//���������ļ�
		String cmd="..\\crf_test";
		String content=String.format("%s %s %s %s >> %s", cmd,testparameter,modelfilename,testfilename,testinfofilename);
		try {
			EasyFile.createFile(workspace,exectestfilename);
			EasyFile.writeFile(content, workspace,exectestfilename,"GB2312");
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	/**
	 * ����ѵ������
	 */
	public void createTrainCmd(){
		//���������ļ�
		String cmd="..\\crf_learn";
		String content=String.format("%s %s %s %s %s >> %s", cmd,trainparameter,templatefilename,trainfilename,modelfilename,traininfofilename);
		try {
			EasyFile.createFile(workspace,exectrainfilename);
			EasyFile.writeFile(content, workspace,exectrainfilename,"GB2312");
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	/**
	 * ����������
	 * @param evaluationfilename ��������ļ���
	 * @param types ��ǩ���ͼ���
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		EasyFile.writeFilebyLines(newcontent, tempfilename);
		String[][] contents=EasyFile.readFilebySplit(tempfilename, "\t", false);
		EasyEvaluation ee=new EasyEvaluation(contents, -2, -1, types);
		ee.saveEvaluation2file(workspace+"/"+evaluationfilename);
		EasyFile.delFile(tempfilename);
	}
	/**
	 * ��ʼ������
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
	 * ��ģ�ʹӴ洢λ�ø��Ƶ�����Ŀ¼��
	 * @param targetmodel ���Ǵ��ԭģ�͵�Ŀ¼���ļ���
	 */
	public void loadModel(String targetmodel){
		EasyFile.copyFile(targetmodel, workspace+"/"+modelfilename);
	}
	/**
	 * ��ģ��Ӵ洢λ�ø��Ƶ�����Ŀ¼��
	 * @param targettemplate ���Ǵ��ԭģ���Ŀ¼���ļ���
	 */
	public void loadTemplate(String targettemplate){
		EasyFile.copyFile(targettemplate, workspace+"/"+templatefilename);
	}
	/**
	 * �Ѳ����ļ��Ӵ洢λ�ø��Ƶ�����Ŀ¼��
	 * @param targettestfilename ���Ǵ��ԭ�����ļ���Ŀ¼���ļ���
	 */
	public void loadTestData(String targettestfilename){
		EasyFile.copyFile(targettestfilename, workspace+"/"+testfilename);
	}
	/**
	 * ��ѵ���ļ��Ӵ洢λ�ø��Ƶ�����Ŀ¼��
	 * @param targettrainfilename ���Ǵ��ԭѵ���ļ���Ŀ¼���ļ���
	 */
	public void loadTrainData(String targettrainfilename){
		//��Ŀ��ѵ���ļ��ŵ�����Ŀ¼��
		EasyFile.copyFile(targettrainfilename, workspace+"/"+trainfilename);
	}
	/**
	 * Ĭ��һ������ʵ��(����ѵ�������Լ���������)
	 * @param targettrainfilename ѵ���ļ���Ŀ¼���ļ���
	 * @param targettestfilename �����ļ���Ŀ¼���ļ���
	 * @param targettemplate ģ���ļ���Ŀ¼���ļ���
	 * @param types Ԥ���ǩ����
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
	 * ��������ʵ����
	 * @param model ����ģ�͵�Ŀ��Ŀ¼���ļ���
	 * @param result ��������Ŀ��Ŀ¼���ļ���
	 * @param evaluation ����������Ŀ��Ŀ¼���ļ���
	 */
	public void saveAll(String model,String result,String evaluation){
		saveModel(model);
		saveResult(result);
		saveEvaluation(evaluation);
	}
	/**
	 * �����������
	 * @param evaluationdes ����������Ŀ��Ŀ¼���ļ���
	 */
	public void saveEvaluation(String evaluationdes){
		EasyFile.copyFile(workspace+"/"+evaluationfilename, evaluationdes);
	}
	/**
	 * ����ģ�͵�Ŀ���ļ�
	 * @param modeldes ����ģ�͵�Ŀ��Ŀ¼���ļ���
	 */
	public void saveModel(String modeldes){
		EasyFile.copyFile(workspace+"/"+modelfilename, modeldes);
	}
	/**
	 * ����ѵ�������Ŀ���ļ�
	 * @param resultdes ��������Ŀ��Ŀ¼���ļ���
	 */
	public void saveResult(String resultdes){
		EasyFile.copyFile(workspace+"/"+testinfofilename, resultdes);
	}
	/**
	 * ����ѵ������
	 * @param parameter
	 */
	public void setTrainParameter(String parameter){
		this.trainparameter=parameter;
	}
	/**
	 * ����
	 */
	public void test(){
		//ִ�������ļ�
		EasyProcess ep=new EasyProcess();
		ep.runbat(workspace+"/"+exectestfilename,workspace);
	}
	/**
	 * ѵ��ģ��
	 */
	public void trainModel(){
		//ִ�������ļ�
		EasyProcess ep=new EasyProcess();
		ep.runbat(workspace+"/"+exectrainfilename,workspace);
	}
}
