package com.explore.wang.test;

import java.util.ArrayList;

/**
 * 我想实例化一个对象 ， 遍历赋不同值 ， 存储到list中
 * 与  list中存储不同对象，赋值不同值 得区别
 * Created by 王兆琦  on 2016/12/15 12.06.
 */
class Data {

    int val;

}
public class ListAddObjTest {
    public static void main(String args[]) {
        Data data = new Data();
        ArrayList<Data> list = new ArrayList<Data>();

        for (int i = 100; i < 103; i++) {
            data.val = i;
            list.add(data);
        }

        for (Data d : list) {
            System.out.println(d.val);
        }

        System.out.println(list);
    }
}


/*
运行结果是 ： 120  120   120
分析：
 1, 新建了一个Data类型的对象，新建了一个ArrayList对象
 2, 在for循环中依次给data.val赋值，并把它添加到list中去，其中需要注意的是， list中添加的是同一个data对象
  	1）在第一次循环中 data.val = 100 , 把他添加到list中 ， 此时 list.get(0).val = 100 ;
  	2)在第二次循环中，data.val = 101 , 把他添加到list中，由于list添加的是data对象，
  		list中的元素中存放的是data的地址，当访问 list.get(0)时， 就是访问 data.val , 而data.val = 101 ,
  		list.get(1).val = data.val = 101
  	3）同理，第三次循环的时候，list中三个元素的地址是data,而其中的数据变为了第三次循环时的数据 102

总结：
	1）题目中list中存放的是同一个地址值，所以元素的值就是就是最后一次循环赋值的值
	2）如果变为
	 for (int i = 100; i < 103; i++) {
	 		Data data = new Data();
            data.val = i;
            list.add(data);
        }
	这就是存放的是不同的地址，访问的是不同的数据

*/
