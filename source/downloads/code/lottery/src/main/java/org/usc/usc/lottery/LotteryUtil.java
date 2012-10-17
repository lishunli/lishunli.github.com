package org.usc.usc.lottery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 不同概率抽奖工具包
 *
 * @author Shunli
 */
public class LotteryUtil {
    private static final Random random = new Random();

    /**
     * 抽奖
     *
     * @param orignalRates
     *            原始的概率列表，保证顺序和实际物品对应
     * @return
     *         物品的索引
     */
    public static int lottery(List<Double> orignalRates) {
        if (orignalRates == null || orignalRates.isEmpty()) {
            return -1;
        }

        int size = orignalRates.size();

        List<Double> sortOrignalRates = new ArrayList<Double>(size);
        sortOrignalRates.addAll(orignalRates);
        Collections.sort(sortOrignalRates);

        List<Double> rates = new ArrayList<Double>(size);
        // 计算总概率，这样可以保证不一定总概率是1
        double sumRate = 0d;
        for (double rate : sortOrignalRates) {
            sumRate += rate;
        }

        // 计算每个物品在总概率的基础下的概率情况
        Double tempSumRate = 0d;
        for (double rate : sortOrignalRates) {
            tempSumRate += rate;
            rates.add(tempSumRate / sumRate);
        }

        double result = rates.get(0);
        double nextDouble = random.nextDouble();

        for (double rate : rates) {
            if (nextDouble >= rate) {
                continue;
            }
            result = rate;
            break;
        }

        // 根据区块值来获取抽取到的物品索引
        int index = rates.indexOf(result);
        int orignalIndex = orignalRates.indexOf(sortOrignalRates.get(index));

        return orignalIndex;
    }
}
