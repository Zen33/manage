package fitfame.common.util;

import java.util.Random;

/**
 * 根据概率计算结果
 * @author treetree
 *
 */
public class Probability {
    public static boolean isSuccesfulWithProbability(double pro){
    	Random random = new Random();
    	double res = random.nextDouble();
    	pro = pro / 100;
    	if (res > pro)
    		return false;
    	else
    		return true;
    }
}
