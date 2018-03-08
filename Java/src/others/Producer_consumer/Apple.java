package others.Producer_consumer;

/**
 * Created by treee on -2018/3/7-
 */
//实现一个模拟过程，有个人不断往苹果箱子里放苹果，一个人不断从苹果箱子里取苹果，苹果箱子的容量为5.
import java.util.LinkedList;

public class Apple{
    private LinkedList<Object> box = new LinkedList<Object>();

    public Apple(){

    }

    public void start(){

    }

    class producer extends Thread{
        public void run(){
            while(true){
                synchronized (box){
                    try {
                        if(box.size()==5){
                            System.out.println("");
                        }
                    }
                }
            }
        }
    }

    class consumer extends Thread{

    }

    public static void main(String[] args) {
        Apple apple = new Apple();
        apple.start();
    }

}