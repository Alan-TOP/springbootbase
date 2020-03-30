package com.alan.springbootbase.threads;

/**
 * @author Alan
 * @Description
 * @date 2020年03月24日 15:46
 */
public class CreateThread1 extends Thread{


    /**
     *创建线程的方案有两种：继承Thread类和实现 Runnable 接口
     * 方法一：继承 Thread 类，重写方法 run（），我们在创建的 Thread 类的子类中重写 run（） ，
     *    加入线程所要执行的代码即可。下面是一个例子：CreateThread1
     *    这种方法简单明了，符合大家的习惯，但是，它也有一个很大的缺点，那就是如果我们的类已经从一个类继承，则无法再继承 Thread 类。

     *  方法二：实现 Runnable 接口
     * Runnable 接口只有一个方法 run（），我们声明自己的类实现 Runnable 接口并提供这一方法，将我们的线程代码写入其中，就完成了这一部分的任务。
     *  但是 Runnable 接口并没有任何对线程的支持，我们还必须创建 Thread 类的实例，这一点通过 Thread 类的构造函数public Thread（Runnable target）；
     *  来实现。下面是一个例子：CreateThread2
     *  使用 Runnable 接口来实现多线程使得我们能够在一个类中包容所有的代码，有利于封装
     */
    public void run() {
        for ( int i = 0; i < 10; i++ ) {
            System.out.println("New thread");
        }
    }
    public static void main(String[] args) {
        CreateThread1 tt = new CreateThread1();
        tt.start();
        for ( int i = 0; i < 100; i++ ) {
            System.out.println("Main thread");
        }
    }


    /**
     * 三、线程的四种状态
     *     1、新状态：线程已被创建但尚未执行（start（） 尚未被调用）。
     *     2、可执行状态：线程可以执行，虽然不一定正在执行。CPU 时间随时可能被分配给该线程，从而使得它执行。
     *     3、阻塞状态：线程不会被分配 CPU 时间，无法执行；可能阻塞于I/O，或者阻塞于同步锁。
     *     4、死亡状态：正常情况下run（） 返回使得线程死亡。调用 stop（）或 destroy（） 亦有同样效果，但是不被推荐，前者会产生异常，后者是强制终止，不会释放锁。
     *     四、线程的优先级
     *          线程的优先级代表该线程的重要程度，当有多个线程同时处于可执行状态并等待获得 CPU 时间时，线程调度系统根据各个线程的优先级来决定给谁分配 CPU 时间，
     *     优先级高的线程有更大的机会获得 CPU 时间，优先级低的线程也不是没有机会，只是机会要小一些罢了。
     *          你可以调用 Thread 类的方法 getPriority（） 和 setPriority（）来存取线程的优先级，线程的优先级界于1（MIN_PRIORITY）和10（MAX_PRIORITY）之间，缺省是5（NORM_PRIORITY）。
     *
     *     五、线程的同步
     *     由于同一进程的多个线程共享同一片存储空间，在带来方便的同时，也带来了访问冲突这个严重的问题。Java语言提供了专门机制以解决这种冲突，有效避免了同一个数据对象被多个线程同时访问。
     *     我们只需针对方法提出一套机制，这套机制就是 synchronized 关键字，它包括两种用法：synchronized 方法和 synchronized 块。
     *     1. synchronized 方法：通过在方法声明中加入synchronized关键字来声明 synchronized 方法。synchronized 方法控制对
     *          类成员变量的访问：每个类实例对应一把锁，每个 synchronized 方法都必须获得调用该方法的类实例的锁方能执行，否则所属线程阻塞，
     *          方法一旦执行，就独占该锁，直到从该方法返回时才将锁释放，此后被阻塞的线程方能获得该锁，重新进入可执行状态。这种机制确保了同一时刻对于每一个类实例，
     *          其所有声明为 synchronized 的成员函数中至多只有一个处于可执行状态（因为至多只有一个能够获得该类实例对应的锁），从而有效避免
     *          了类成员变量的访问冲突（只要所有可能访问类成员变量的方法均被声明为 synchronized）。
     *     在 Java 中，不光是类实例，每一个类也对应一把锁，这样我们也可将类的静态成员函数声明为 synchronized，以控制其对类的静态成员变量的访问。
     *          synchronized 方法的缺陷：若将一个大的方法声明为synchronized 将会大大影响效率，典型地，若将线程类的方法run（）声明为synchronized ，
     *     由于在线程的整个生命期内它一直在运行，因此将导致它对本类任何synchronized方法的调用都永远不会成功。
     *     2. synchronized 块：通过 synchronized关键字来声明synchronized 块。语法如下：
     *  synchronized(syncObject) {
     *      //允许访问控制的代码
     *  }
     *     synchronized 块是这样一个代码块，其中的代码必须获得对象 syncObject 的锁方能执行，具体机制同前所述。由于可以针对任意代码块，且可任意指定上锁的对象，故灵活性较高。
     */

}
