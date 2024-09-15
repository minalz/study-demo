package cn.minalz.example;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.RandomUtils.nextDouble;

/**
 * 微信抢红包的算法
 */
public class RedEnvelopeTest {

    public static void main(String[] args) {
        doubleMeanMethod(100, 10);
    }


    /**
     * 二倍均值法
     * @param money 总金额
     * @param number 红包数
     * @return 总金额
     */
    public static List<Double> doubleMeanMethod(double money,int number){
        List<Double> result = new ArrayList<Double>();
        if(money<0&&number<1)
            return null;
        double amount,sum=0;
        int remainingNumber=number;
        int i=1;
        while(remainingNumber>1){
            amount= nextDouble(0.01,2*(money/remainingNumber));
            sum+=amount;
            System.out.println("第"+i+"个人领取的红包金额为："+String.format("%.2f", amount));
            money -= amount;
            remainingNumber--;
            result.add(amount);
            i++;
        }
        result.add(money);
        System.out.println("第"+i+"个人领取的红包金额为："+String.format("%.2f", money));
        sum+=money;
        System.out.println("验证发出的红包总金额为："+String.format("%.2f", sum));

        return result;
    }
}
