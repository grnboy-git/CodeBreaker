package com.grnboy.codebreaker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class CBEngine {
	
	// タイトル文
	private String title = "Code Breaker!!";

	// ルール説明文
	private String rules = "▼ゲームの遊び方▼\n"
	// +"\n"
	// +"\n"
	// +"\n"
	// +"\n"
	// +"\n"
	// +"\n"
			+ "全てのカード番号を解読しろ！！\n";

	// 初期数字の格納される配列
	private int[] answerNum;

	// 推理数字の格納される配列
	private int[] inputNum;
	
	// ゲームのコンフィグ設定的な
	private int defaultCardNum = 3; // カード何枚でゲームを遊ぶか
	private int defaultCardRange = 6; // 使用するカード番号の範囲

	// 推理数字と解答数字を比較してのヒットカウンタ
	private int countHit = 0;
	private int countBlow = 0;
	
//	コンフィグ設定の適用（ゲーム中の使用カード枚数）
	public CBEngine(){
		answerNum = new int[defaultCardNum];
		inputNum = new int[defaultCardNum];
		setAnswerNum ();
	}
//	設定変更時の再設定適用処理（ゲーム中の使用カード枚数）
	public CBEngine(int defaultCardNum){
		this.defaultCardNum = defaultCardNum;
		answerNum = new int[defaultCardNum];
		inputNum = new int[defaultCardNum];
		setAnswerNum ();
	}
	
//	答えになる番号の設定
	public void setAnswerNum(){
		if (answerNum.length != defaultCardNum){
			answerNum = new int[defaultCardNum];
			inputNum = new int[defaultCardNum];
		}
		Random random = new Random();
		for (int i = 0; i <= 2; i++) {
			int num = random.nextInt(defaultCardRange) + 1;
			for (int j=i-1; j>=0; j--){
				while (answerNum[j]==num) {
					num = random.nextInt(defaultCardRange) + 1;
				}
			}
			answerNum[i] = num;
		}
	}
//	解答番号の入力設定処理
	public void inputCardNum(int index, int answerNum)throws InputException{
		if (index > -1 && index < defaultCardNum){
			if (answerNum > 0 && answerNum < defaultCardRange+1){
				inputNum[index] = answerNum;
			}else{
				throw new InputException("入力した数値の範囲がおかしいかも(・・;)");
			}
		}else{
			throw new InputException("入力内容がおかしいから確認しよう！");
		}
	}
//	入力した数値がString型で来たときの処理をとりあえず追加してみる
	public void inputCardNum(int index, String string)throws InputException{
		int answerNum;
        try{
        	answerNum = Integer.parseInt(string); //stringで来ちゃったら試しにint型にして渡してみる
        }
        catch(NumberFormatException e){
            throw new InputException("入力が答えの範囲外です"); //うまくいかなかったら再入力を促す
        }
        inputCardNum(index, answerNum);
	}
	
//	入力後の判定処理（解答と入力した数列のマッチ判定）
	public boolean judgeAnswer(){
		countHit = 0; //初期化する（メソッドにした方がベター？）
		countBlow = 0; //初期化する（メソッドにした方がベター？）

		for (int i = 0; i <= answerNum.length - 1; i++) { //[FIXME]冗長じゃね？
			if (answerNum[i] == inputNum[i]) {
				countHit++;
			} else {
				for (int j = 0; j
						<= answerNum.length - 1; j++) {
					if (answerNum[i] == inputNum[j]) {
						countBlow++;
					}
				}
			}
		}
		if(countHit != defaultCardNum){ //カード枚数とヒット数が同一ならtrueが返される（ゲームクリア）
			return false;
		}
		return true;
	}
	
//	//////////////////////////
//	getter setterはここから
//	//////////////////////////
	
	public String getTitle(){ //ゲームタイトルを取得
		return title;
	}
	public String getRule(){ //ルールを取得
		return rules;
	}
	public int getDefaultCardNum(){ //コンフィグ設定を取得
		return defaultCardNum;
	}
	public int getDefaultCardRange(){ //コンフィグ設定を取得
		return defaultCardRange;
	}
	public int getHit(){
		return countHit;
	}
	public int getBlow(){
		return countBlow;
	}
	public int[] getAnswer(){
		return answerNum;
	}
	public int[] getInput(){
		return inputNum;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public void setRule(String rule){
		this.rules = rule;
	}
	public void setInput(int[] input)throws InputException{
		for (int i=0; i<=input.length-1; i++){
			inputCardNum(i, input[i]);
		}
	}

}
