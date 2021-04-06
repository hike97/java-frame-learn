package collection;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName collection.ArraySubListDEMO
 * @Description TODO
 * @Author hike97
 * @Date 2021/3/29 9:48
 * @Version 1.0
 **/
public class ArraySubListDEMO{
    public static void main(String[] args) {
        List<String> bookList = new ArrayList<>();
        bookList.add("遥远的救世主");
        bookList.add("背叛");
        bookList.add("天幕红尘");
        bookList.add("人生");
        bookList.add("平凡的世界");

        List<String> luyaoBookList = bookList.subList(3, 5);

        System.out.println(bookList);
        System.out.println(luyaoBookList);
        /**
         * 从运行结果可以看出，subList返回的是bookList中索引从fromIndex（包含）到toIndex（不包含）的元素集合。
         *
         * 使用起来很简单，也很好理解，不过还是有以下几点要注意，否则会造成程序错误或者异常：
         *
         * 修改原集合元素的值，会影响子集合
         * 修改原集合的结构，会引起ConcurrentModificationException异常
         * 修改子集合元素的值，会影响原集合
         * 修改子集合的结构，会影响原集合
         */
        // 修改原集合的值 会改变subList 的值
//        bookList.set(3,"路遥-人生");
//
//        System.out.println(bookList);
//        System.out.println(luyaoBookList);

        // 往原集合中添加元素
//        bookList.add("早晨从中午开始");
//
//        System.out.println(bookList);
//        System.out.println("###########修改bookList的结构导致 ConcurrentModificationException");
//        System.out.println(luyaoBookList);
        System.out.println("2.3 修改子集合的值，会影响原集合");
        luyaoBookList.set(1,"路遥-平凡的世界");
        System.out.println("修改子集合的结构，会影响原集合");
        luyaoBookList.add("赘婿");
        System.out.println(bookList);
        System.out.println(luyaoBookList);
        /**
         * 结论 SubList类是ArrayList的内部类，
         * 该构造函数中也并没有重新创建一个新的ArrayList，
         * 所以修改原集合或者子集合的元素的值，是会相互影响的。
         */
    }
}
