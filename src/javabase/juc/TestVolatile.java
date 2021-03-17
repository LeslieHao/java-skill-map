package javabase.juc;

/**
 * @author HaoHao
 * @date 2021/3/16 4:21 下午
 */
public class TestVolatile {

    private volatile String abc;

    private void test(){
        abc = "1";
        System.out.println(abc);
        abc = "2";
    }
}
