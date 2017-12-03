/**
 * 
 */
package easytools;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 获得评估结果的工具类
 * @author jf320
 *
 */
public class EasyEvaluation extends EasyAgreement{
	public Double[] p,r,f1;
	public Double macroP=new Double(0);
	public Double macroR=new Double(0);
	public Double macroF1=new Double(0);
	public Double microP=new Double(0);
	public Double microR=new Double(0);
	public Double microF1=new Double(0);
	public Double accuracy=new Double(0);
	/**
	 * 使用交叉表直接计算
	 * @param cross
	 * @param types
	 */
	public EasyEvaluation(int[][] cross,ArrayList<String> types){
		super(cross, types);
		p=new Double[types.size()];
		r=new Double[types.size()];
		f1=new Double[types.size()];
		double averageTp=0;
		double averageFn=0;
		double averageFp=0;
		double averageTn=0;
		for(int i=0;i<types.size();i++){
			double tp=crosstab[i][i];
			double fn=crosstab[i][types.size()]-crosstab[i][i];
			double fp=crosstab[types.size()][i]-crosstab[i][i];
			double tn=crosstab[types.size()][types.size()]-tp-fn-fp;
			if(tp+fp==0){
				p[i]=0.0;
			}
			else{
				p[i]=tp/(tp+fp);	
			}
			if(tp+fn==0){
				r[i]=0.0;
			}
			else{
				r[i]=tp/(tp+fn);
			}
			if(p[i]+r[i]==0){
				f1[i]=0.0;
			}
			else{
				f1[i]=2*p[i]*r[i]/(p[i]+r[i]);	
			}
			macroP+=p[i];
			macroR+=r[i];
			averageTp+=tp;
			averageFn+=fn;
			averageFp+=fp;
			averageTn+=tn;
		}
		averageTp/=types.size();
		averageFn/=types.size();
		averageFp/=types.size();
		averageTn=averageTn/types.size();
		macroP=macroP/types.size();
		macroR=macroR/types.size();
		macroF1=2*macroP*macroR/(macroP+macroR);
		microP=averageTp/(averageTp+averageFp);
		microR=averageTp/(averageTp+averageFn);
		microF1=2*microP*microR/(microP+microR);
		accuracy=agreement;
	}
	public EasyEvaluation(String[][] contents, int realindex, int expectindex, ArrayList<String> types) {
		super(contents, realindex, expectindex, types);
		if(realindex<0){
			realindex=contents[0].length+realindex;
		}
		if(expectindex<0){
			expectindex=contents[0].length+expectindex;
		}
		p=new Double[types.size()];
		r=new Double[types.size()];
		f1=new Double[types.size()];
		double averageTp=0;
		double averageFn=0;
		double averageFp=0;
		double averageTn=0;
		for(int i=0;i<types.size();i++){
			double tp=crosstab[i][i];
			double fn=crosstab[i][types.size()]-crosstab[i][i];
			double fp=crosstab[types.size()][i]-crosstab[i][i];
			double tn=crosstab[types.size()][types.size()]-tp-fn-fp;
			p[i]=tp/(tp+fp);
			r[i]=tp/(tp+fn);
			f1[i]=2*p[i]*r[i]/(p[i]+r[i]);
			macroP+=p[i];
			macroR+=r[i];
			averageTp+=tp;
			averageFn+=fn;
			averageFp+=fp;
			averageTn+=tn;
		}
		averageTp/=types.size();
		averageFn/=types.size();
		averageFp/=types.size();
		averageTn=averageTn/types.size();
		macroP=macroP/types.size();
		macroR=macroR/types.size();
		macroF1=2*macroP*macroR/(macroP+macroR);
		microP=averageTp/(averageTp+averageFp);
		microR=averageTp/(averageTp+averageFn);
		microF1=2*microP*microR/(microP+microR);
		accuracy=agreement;
	}
	/**
	 * 获得评估结果
	 * @return
	 */
	public String[] getEvaluation(){
		String[] result=new String[types.size()+4];
		result[0]="类型\tP\tR\tF1\tSupport";
		for(int i=1;i<types.size()+1;i++){
			result[i]=types.get(i-1);
			result[i]+="\t";
			result[i]+=String.format("%6.5f\t%6.5f\t%6.5f", p[i-1],r[i-1],f1[i-1]);
			result[i]+="\t";
			result[i]+=crosstab[i-1][types.size()];
		}
		result[types.size()+1]="微观指标\t"+getMicroEvaluation()+"\t";
		result[types.size()+1]+=crosstab[types.size()][types.size()];
		result[types.size()+2]="宏观指标\t"+getMacroEvaluation()+"\t";
		result[types.size()+2]+=crosstab[types.size()][types.size()];
		result[types.size()+3]=String.format("正确率\t%6.5f", accuracy);
		return result;
	}
	/**
	 * 获得宏观评估结果
	 * @return
	 */
	public String getMacroEvaluation(){
		String result=String.format("%6.5f\t%6.5f\t%6.5f", macroP,macroR,macroF1);
		return result;
	}
	/**
	 * 获得微观评估结果
	 * @return
	 */
	public String getMicroEvaluation(){
		String result=String.format("%6.5f\t%6.5f\t%6.5f", microP,microR,microF1);
		return result;
	}
	/**
	 * 输出评估结果
	 */
	public void printEvaluation(){
		String[] result=getEvaluation();
		for (String string : result) {
			System.out.println(string);
		}
	}
	/**
	 * 保存评估结果到文件
	 * @param filename 目标文件名
	 */
	public void saveEvaluation2file(String filename){
		String[] result=getEvaluation();
		ArrayList<String> resultlist= new ArrayList<String>(Arrays.asList(result));
		EasyFile.writeFilebyLines(resultlist, filename);
	}
}
