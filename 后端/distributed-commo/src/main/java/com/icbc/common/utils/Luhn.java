package com.icbc.distributed.common;

import java.util.Arrays;

public class Luhn {
    private int[] no;

    private Boolean isValidate = null;

    public Luhn(String strno)
    {
        this(convertStrToInArr(strno));
    }
    public Luhn(int[] no)
    {
        if(null!=no && no.length>0)
        {
            this.no = Arrays.copyOf(no, no.length);
            for(int i=0;i<no.length;i++)
            {
                if(no[i]<0)
                {
                    throw new IllegalArgumentException("No can not contain negtive value");
                }
            }
        }else{
            throw new IllegalArgumentException("No is null or Empty");
        }
    }
    /**
     * 校验
     * @return
     */
    public boolean check()
    {
        if(null == isValidate)
        {
            isValidate = luhnCheck(getCardNoArr());
        }
        return isValidate;
    }
    /**
     * 获取全部id
     * @return
     */
    public int[] getCardNoArr()
    {
        return Arrays.copyOf(no, no.length);
    }
    /**
     * 计算校验和的算法
     * @return
     */
    public int getCheckSum(){
        if(check())
        {
            return no[0];
        }
        int[] cardNoArr = getCardNoArr();
        for(int i=0;i<cardNoArr.length;i+=2)
        {
            cardNoArr[i] <<= 1;
            cardNoArr[i] = cardNoArr[i]/10 + cardNoArr[i]%10;
        }
        int sum = 0;
        for(int i=0;i<cardNoArr.length;i++)
        {
            sum += cardNoArr[i];
            //System.out.print(cardNoArr[i]);
        }
        return sum * 9 % 10;
    }

    private static int[] convertStrToInArr(String cardNo) {
        if(null==cardNo)throw new IllegalArgumentException();
        int index = cardNo.length();
        int[] cardNoArr = new int[cardNo.length()];
        for(char c : cardNo.toCharArray())
        {
            cardNoArr[--index] = c - '0';
        }
        return cardNoArr;
    }
    /**
     * 校验的具体算法实现
     * @param cardNoArr
     * @return
     */
    private static boolean luhnCheck(int[] cardNoArr) {
        for(int i=1;i<cardNoArr.length;i+=2)
        {
            cardNoArr[i] <<= 1;
            cardNoArr[i] = cardNoArr[i]/10 + cardNoArr[i]%10;
        }
        int sum = 0;
        for(int i=0;i<cardNoArr.length;i++)
        {
            sum += cardNoArr[i];
            //System.out.print(cardNoArr[i]);
        }

        return sum % 10 == 0;
    }
}