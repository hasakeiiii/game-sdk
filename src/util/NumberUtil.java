package util;

import java.util.Random;

public class NumberUtil {

//根据输入值取得一个随机数,在范围参数为附近上下浮动
public static int getRadom(int key,int size){
	Random random = new Random();
	int result=random.nextInt(size)*(random.nextInt(2)==0?-1:1) + key;
	return result;
}

public static int getRandom(int max) {
	return new Random().nextInt(max);
}

public static int getRandom(int min, int max) {
	int r = getRandom(max - min);
	return r + min;
}

//根据输入值取得一个随机数,在范围参数为附近上下浮动,必须是大于0的
public static int getPosRadom(int key,int size){
	Random random = new Random();
	int result=-1;
	while(result<0){
		result=random.nextInt(size)*(random.nextInt(2)==0?-1:1) + key;
	}
	return result;
}

//判断随机数字是否大于输入数字
public static int isOk(double key){
	Random random = new Random();
    double keyRate=random.nextDouble();
    if(keyRate<key){
    	return 0;
    }
	return 1;
}
public static void main(String[] args) {
	for (int i = 0; i < 100; i++) {
		System.out.println(getPosRadom(50,100));
	}
	
}
}
