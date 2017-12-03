/**
 * 
 */
package easytools;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * ����һ����������Ĺ�����
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
	 * ʹ�ý������г�ʼ��
	 * @param crosstab Ŀ�꽻���
	 * @param types ��ǩ�����б�
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
	 * ��ӡ����ͬ����
	 * @param split
	 */
	public void printDifferentRows(String split){
		String[] differentrows=getDifferentRows(split);
		for (String string : differentrows) {
			System.out.println(string);
		}
	}
	/**
	 * ���治ͬ���е��ļ���
	 * @param filename
	 * @param split
	 */
	public void saveDifferentRows2file(String filename,String split){
		String[] differentrows=getDifferentRows(split);
		ArrayList<String> differentrowslist= new ArrayList<String>(Arrays.asList(differentrows));
		EasyFile.writeFilebyLines(differentrowslist, filename);
	}
	/**
	 * ��ȡ����ͬ����
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
	 * ʹ��ԭʼ���ݽ��г�ʼ��
	 * @param contents ԭʼ����
	 * @param realindex ��ʵ��
	 * @param expectindex Ԥ����
	 * @param types ��ǩ�����б�
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
			//��ȡ����һ�µ�����
			if(row!=col){
				differentindex.add(i);
			}
			crosstab[row][col]++;
			//��ȡ����Եͳ��
			crosstab[row][types.size()]++;
			crosstab[types.size()][col]++;
		}
		//��ȡ��һ����
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
	 * ���һ���ʱ���
	 * @return
	 */
	public String[] getAgreement(){
		String[] result=new String[types.size()+4];
		result[0]="����\t";
		int i0=0;
		for(;i0<types.size();i0++){
			result[0]+=types.get(i0);
			result[0]+="\t";
		}
		result[0]+="�ϼ�";
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
		result[types.size()+1]="�ϼ�\t";
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
	 * ������
	 */
	public void printAgreement(){
		String[] result=getAgreement();
		for (String string : result) {
			System.out.println(string);
		}
	}
	/**
	 * �ѽ�������Ŀ���ļ���
	 * @param filename ��������Ŀ���ļ�
	 */
	public void saveAgreement2File(String filename){
		String[] result=getAgreement();
		ArrayList<String> resultlist= new ArrayList<String>(Arrays.asList(result));
		EasyFile.writeFilebyLines(resultlist, filename);
	}
}

