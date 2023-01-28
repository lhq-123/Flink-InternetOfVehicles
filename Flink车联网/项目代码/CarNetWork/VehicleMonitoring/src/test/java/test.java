import java.util.Random;

/**
 * @author Alex_liu
 * @create 2023-01-28 18:06
 * @Description
 */
public class test {
    public static void main(String[] args) {
        Random r = new Random();
        double d1 = r.nextDouble()*100;
        int i1 = r.nextInt(10);
        int i2 = r.nextInt(20);
        long l1 = r.nextLong();
        boolean b1 = r.nextBoolean();
        float f1 = r.nextFloat();
        System.out.println("d1="+d1);
        System.out.println("i1="+i1);
        System.out.println("i2="+i2);
        System.out.println("l1="+l1);
        System.out.println("b1="+b1);
        System.out.println("f1="+f1);
        for (int i3=1; i3<=100;i3++){
            int num = r.nextInt(10);
            System.out.println("num="+num);
        }
    }
}
