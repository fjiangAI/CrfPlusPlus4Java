/**
 * 
 */
package easytools;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 这是一个分析结果的工具类
 * @author jf320
 * @date 2017/10/21
 */
public class EasyAgreement {
	public Double agreement;
	public Double kappa;
	public int[][] crosstab;
	public ArrayList<String> types=new ArrayList<String>();
	public String[][] copycontents;
	public ArrayList<Integer> differentindex=new ArrayList<Integer>();
	/**
	 * 使用交叉表进行初始化
	 * @param crosstab 目标交叉表
	 * @param types 标签类型列表
	 */
	public EasyAgreement(int[][] cross,ArrayList<String> types){
		this.crosstab=new int[cross.length+1][cross[0].length+1];
		this.types=types;
		
		for(int i=0;i<types.size();i++){
			for(int j=0;j<types.size();j++){
				crosstab[i][j]=cross[i][j];
				crosstab[j][i]=cross[j][i];
				crosstab[i][types.size()]+=cross[i][j];
				crosstab[types.size()][i]+=cross[j][i];
			}
		}
		for(int i=0;i<types.size();i++){
			crosstab[types.size()][types.size()]+=crosstab[i][types.size()];
		}
		double ka=0;
		double ke=0;
		for(int i=0;i<types.size();i++){
			ka+=crosstab[i][i];
			ke+=(crosstab[types.size()][i]*1.0/crosstab[types.size()][types.size()])*(crosstab[i][types.size()]*1.0/crosstab[types.size()][types.size()]);
		}
		ka=ka/crosstab[types.size()][types.size()];
		kappa=(ka-ke)/(1-ke);
		agreement=ka;
	}
	/**
	 * 打印出不同的行
	 * @param split
	 */
	public void printDifferentRows(String split){
		String[] differentrows=getDifferentRows(split);
		for (String string : differentrows) {
			System.out.println(string);
		}
	}
	/**
	 * 保存不同的行到文件中
	 * @param filename
	 * @param split
	 */
	public void saveDifferentRows2file(String filename,String split){
		String[] differentrows=getDifferentRows(split);
		ArrayList<String> differentrowslist= new ArrayList<String>(Arrays.asList(differentrows));
		EasyFile.writeFilebyLines(differentrowslist, filename);
	}
	/**
	 * 获取到不同的行
	 * @param split
	 * @return
	 */
	public String[] getDifferentRows(String split){
		String[] result=new String[differentindex.size()];
		for (int i = 0; i < differentindex.size(); i++) {
			String[] temp=copycontents[differentindex.get(i)];
			result[i]="";
			int j=0;
			for(;j<temp.length-1;j++){
				result[i]+=temp[j];
				result[i]+="\t";
			}
			result[i]+=temp[j];
		}
		return result;
	}
	/**
	 * 使用原始数据进行初始化
	 * @param contents 原始数据
	 * @param realindex 真实列
	 * @param expectindex 预测列
	 * @param types 标签类型列表
	 */
	public EasyAgreement(String[][] contents,int realindex,int expectindex,ArrayList<String> types){
		crosstab=new int[types.size()+1][types.size()+1];
		copycontents=contents;
		this.types=types;
		if(realindex<0){
			realindex=contents[0].length+realindex;
		}
		if(expectindex<0){
			expectindex=contents[0].length+expectindex;
		}
		for(int i=0;i<contents.length;i++){
			//System.out.println(contents[i][realindex]);
			int row=types.indexOf(contents[i][realindex]);
			int col=types.indexOf(contents[i][expectindex]);
			//获取到不一致的行数
			if(row!=col){
				differentindex.add(i);
			}
			crosstab[row][col]++;
			//获取到边缘统计
			crosstab[row][types.size()]++;
			crosstab[types.size()][col]++;
		}
		//获取到一致数
		crosstab[types.size()][types.size()]=contents.length;
		double ka=0;
		double ke=0;
		for(int i=0;i<types.size();i++){
			ka+=crosstab[i][i];
			ke+=(crosstab[types.size()][i]*1.0/crosstab[types.size()][types.size()])*(crosstab[i][types.size()]*1.0/crosstab[types.size()][types.size()]);
		}
		ka=ka/crosstab[types.size()][types.size()];
		kappa=(ka-ke)/(1-ke);
		agreement=ka;
	}
	/**
	 * 获得一致率报告
	 * @return
	 */
	public String[] getAgreement(){
		String[] result=new String[types.size()+4];
		result[0]="类型\t";
		int i0=0;
		for(;i0<types.size();i0++){
			result[0]+=types.get(i0);
			result[0]+="\t";
		}
		result[0]+="合计";
		for(int i=1;i<types.size()+1;i++){
			result[i]="";
			result[i]+=types.get(i-1);
			result[i]+="\t";
			int j=0;
			for(;j<types.size();j++){
				result[i]+=crosstab[i-1][j];
				result[i]+="\t";
			}
			result[i]+=crosstab[i-1][j];
		}
		result[types.size()+1]="合计\t";
		for(int i=0;i<types.size();i++){
			result[types.size()+1]+=crosstab[types.size()][i];
			result[types.size()+1]+="\t";
		}
		result[types.size()+1]+=crosstab[types.size()][types.size()];
		result[types.size()+2]=String.format("Agreement:%6.5f", agreement);
		result[types.size()+3]=String.format("Kappa:%6.5f", kappa);
		return result;
	}
	/**
	 * 输出结果
	 */
	public void printAgreement(){
		String[] result=getAgreement();
		for (String string : result) {
			System.out.println(string);
		}
	}
	/**
	 * 把结果输出到目标文件中
	 * @param filename 输出结果到目标文件
	 */
	public void saveAgreement2File(String filename){
		String[] result=getAgreement();
		ArrayList<String> resultlist= new ArrayList<String>(Arrays.asList(result));
		EasyFile.writeFilebyLines(resultlist, filename);
	}
}

