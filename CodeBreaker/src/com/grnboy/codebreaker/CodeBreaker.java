package com.grnboy.codebreaker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class CodeBreaker {
	public static void main(String[] args) {
		//作成したクラスの初期化
		CBEngine cbe = new CBEngine();
		
		// 変数の初期化。
		/*
		 * 必要な変数 タイトル、ルール文、答えの格納される配列、 入力された数字の格納される配列。 ヒット数、ブロー数、チャレンジの回数
		 */

		// タイトル文
		String title = cbe.getTitle();

		// ルール説明文
		String rules = cbe.getRule();

		// 初期数字の格納される配列
//		int[] answerNum = new int[3];

		// 推理数字の格納される配列
//		int[] inputNum = new int[3];

		// 推理数字と解答数字を比較してのヒットカウンタ
//		int countHit = 0;
//		int countBlow = 0;
		int countChallenge = 0;
		
		// ゲームの初期化はここで実施
		cbe.initGame();

		// タイトルとルールの表示
		System.out.println(title);
		System.out.println(rules);

		// ランダムな答えを作成。
		// ただし、仕様通り、同じ数字がないようにする。
		cbe.setAnswerNum();

		// 入力させる。数値のチェックを行う。
		while (true) {
		countChallenge++;
		System.out.println("*******" + countChallenge + "回目の挑戦！*******");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			for (int i = 0; i <= cbe.getDefaultCardNum() - 1; i++) {
				System.out.print( (i + 1) + "個目 : ");
				try {
					cbe.inputCardNum(i,br.readLine());
				} catch (InputException e) {
					System.err.print(e.getMessage());
					i--;
				} catch (IOException e) { // 文字inputってこの辺りが必要？
					System.err.println("もう一度入力してください");
					i--;
				}
			}
			
			
//			for (int i = 0; i <= 2; i++) {
//				System.out.println(inputNum[i]);
//			}
			
			// 答え判断			
			
			// 終了判断　ヒットが3つになったら終了
			if (cbe.judgeAnswer()) {
				System.out.println("３つのカード番号を当てました！おめでとうございます！");
				System.out.println(countChallenge + "回目のチャレンジでした。");
				break;
			} else {
				System.out.println("残念！あなたのHIT数は" + cbe.getHit() + "で、BLOW数は"
						+ cbe.getBlow() + "でした。");
				System.out.println(countChallenge + "回目のチャレンジでした。");
			}
		}
	}
}
