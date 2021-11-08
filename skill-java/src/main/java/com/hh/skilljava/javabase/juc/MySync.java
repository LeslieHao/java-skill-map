package com.hh.skilljava.javabase.juc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

/**
 * 提供一个框架，用于实现依赖先进先出(FIFO)等待队列的阻塞锁和相关同步器(信号量、事件等)。
 * 这个类被设计为大多数依赖单个原子int值来表示状态的同步器的有用基础。
 * 子类必须定义受保护的方法来改变这种状态，并定义这种状态对于被获取或释放的对象意味着什么。
 * 有了这些条件，该类中的其他方法将执行所有排队和阻塞机制。
 * 子类可以维护其他状态字段，但只有使用getState、setState和compareAndSetState方法操作的原子更新的int值才会跟踪同步
 * <p>
 * 子类应该定义为用于实现其外围类的同步属性的非公共内部助手类。
 * 类AbstractQueuedSynchronizer没有实现任何同步接口。
 * 相反，它定义了像acquireinterruptible这样的方法，具体的锁和相关的同步器可以适当地调用这些方法来实现它们的公共方法。
 * <p>
 * 这个类支持默认独占模式和共享模式中的一个或两个。
 * 当以独占模式获取时，其他线程尝试获取的请求不能成功。
 * 共享模式由多个线程获得可能(但不需要)成功。
 * 这个类不能“理解”这些差异，除非在机械意义上，即当一个共享模式acquire成功时，下一个等待的线程(如果存在的话)也必须确定它是否可以acquire。
 * 在不同模式下等待的线程共享同一个FIFO队列。
 * 通常，实现子类只支持其中一种模式，但这两种模式都可以发挥作用，例如在ReadWriteLock中。
 * 只支持独占模式或仅支持共享模式的子类不需要定义支持未使用mod的方法
 * <p>
 * 该类定义了一个嵌套的AbstractQueuedSynchronizer。
 * ConditionObject类,可以用作条件由子类实现支持独占模式的方法isHeldExclusively报告同步是否只对当前线程持有,方法释放与当前调用getState值完全释放该对象,并获得,给定这个保存的状态值，最终将该对象恢复到它以前获得的状态。
 * 否则没有AbstractQueuedSynchronizer方法创建这样的条件，因此如果不能满足该约束，就不要使用它。
 * AbstractQueuedSynchronizer的行为。条件对象当然取决于它的同步器实现的语义
 * <p>
 * 这个类为内部队列提供了检查、检测和监视方法，也为条件对象提供了类似的方法。
 * 可以根据需要将它们导出到类中，使用AbstractQueuedSynchronizer作为它们的同步机制
 * <p>
 * 该类的序列化只存储维持状态的底层原子整数，因此反序列化对象具有空线程队列。
 * 需要序列化的典型子类将定义一个readObject方法，该方法在反序列化时将其恢复到已知的初始状态
 * <p>
 * 如何使用:
 * <p>
 * 要使用这个类作为同步器的基础，可以通过使用getState, setState和/或compareAndSetState检查和/或修改同步状态来重新定义以下方法，如果适用的话:
 * tryAcquire
 * tryRelease
 * tryAcquireShared
 * tryReleaseShared
 * isHeldExclusively
 * 默认情况下，这些方法都会抛出UnsupportedOperationException。
 * 这些方法的实现必须在内部是线程安全的，并且通常应该简短而不阻塞。
 * 定义这些方法是使用该类所支持的唯一方法。
 * 所有其他方法都声明为final，因为它们不能独立变化。
 * <p>
 * 您可能还会发现从AbstractOwnableSynchronizer继承的方法对于跟踪拥有独占同步器的线程非常有用。
 * 我们鼓励您使用它们——这使得监视和诊断工具能够帮助用户确定哪些线程持有锁。
 * <p>
 * 即使这个类基于内部FIFO队列，它也不会自动执行FIFO获取策略。独占同步的核心采用如下形式:
 * Acquire:
 * while (!tryAcquire(arg)) {
 * enqueue thread if it is not already queued;
 * possibly block current thread;
 * }
 * <p>
 * Release:
 * if (tryRelease(arg))
 * unblock the first queued thread;
 * (共享模式类似，但可能涉及级联信号。)
 * <p>
 * 因为在进入队列之前调用了在acquire中的签入，所以一个新的获取线程可能会比其他被阻塞和排队的线程抢先。
 * 但是，如果需要，您可以定义tryAcquire和/或tryacquirered来通过内部调用一个或多个检查方法来禁用倒换，从而提供一个公平的FIFO获取顺序。
 * 特别是，大多数公平同步器都可以定义tryAcquire，如果hasqueuedformer(一个专门为公平同步器设计的方法)返回true，则tryAcquire返回false。
 * 还有其他可能的变化
 * <p>
 * 对于默认的驳船(也称为贪婪、放弃和护航避免)策略，吞吐量和可伸缩性通常最高。
 * 虽然这不能保证公平或无饥饿，但允许较早的队列线程在较晚的队列线程之前重新竞争，并且每次重新竞争都有一个无偏的机会在进入的线程中成功。
 * 另外，虽然acquire不会在通常意义上“旋转”，但在阻塞之前，它们可能会执行对tryAcquire的多次调用，并穿插其他计算。
 * 当独占同步只是短暂进行时，这就提供了自旋的大部分好处，而在非独占同步进行时，则不需要承担大部分责任。
 * 如果需要，您可以通过前面的调用来增强这种功能，通过“快速路径”检查来获取方法，可能会预先检查hasContended和/或hasQueuedThreads，以便只在同步器可能不会被竞争时才这样做。
 * <p>
 * 这个类通过将其使用范围专门化为可以依赖int状态、acquire和release参数以及内部FIFO等待队列的同步器，为同步提供了一个有效的、可伸缩的基础。
 * 当这还不够时，您可以使用原子类、您自己的自定义java.util.Queue类和LockSupport阻塞支持从较低级别构建同步器。用法示例
 * <p>
 * 这里有一个不可重入的互斥锁类，它使用值0表示无锁状态，1表示锁定状态。
 * 虽然不可重入锁并不严格要求记录当前所有者线程，但这个类无论如何都要这样做，以使使用更容易监控。它还支持条件，并公开了一种测量方法:
 * <pre> {@code
 *  class Mutex implements Lock, java.io.Serializable {
 *
 *    // Our internal helper class
 *    private static class Sync extends AbstractQueuedSynchronizer {
 *      // Reports whether in locked state
 *      protected boolean isHeldExclusively() {
 *        return getState() == 1;
 *      }
 *
 *      // Acquires the lock if state is zero
 *      public boolean tryAcquire(int acquires) {
 *        assert acquires == 1; // Otherwise unused
 *        if (compareAndSetState(0, 1)) {
 *          setExclusiveOwnerThread(Thread.currentThread());
 *          return true;
 *        }
 *        return false;
 *      }
 *
 *      // Releases the lock by setting state to zero
 *      protected boolean tryRelease(int releases) {
 *        assert releases == 1; // Otherwise unused
 *        if (getState() == 0) throw new IllegalMonitorStateException();
 *        setExclusiveOwnerThread(null);
 *        setState(0);
 *        return true;
 *      }
 *
 *      // Provides a Condition
 *      Condition newCondition() { return new ConditionObject(); }
 *
 *      // Deserializes properly
 *      private void readObject(ObjectInputStream s)
 *          throws IOException, ClassNotFoundException {
 *        s.defaultReadObject();
 *        setState(0); // reset to unlocked state
 *      }
 *    }
 *
 *    // The sync object does all the hard work. We just forward to it.
 *    private final Sync sync = new Sync();
 *
 *    public void lock()                { sync.acquire(1); }
 *    public boolean tryLock()          { return sync.tryAcquire(1); }
 *    public void unlock()              { sync.release(1); }
 *    public Condition newCondition()   { return sync.newCondition(); }
 *    public boolean isLocked()         { return sync.isHeldExclusively(); }
 *    public boolean hasQueuedThreads() { return sync.hasQueuedThreads(); }
 *    public void lockInterruptibly() throws InterruptedException {
 *      sync.acquireInterruptibly(1);
 *    }
 *    public boolean tryLock(long timeout, TimeUnit unit)
 *        throws InterruptedException {
 *      return sync.tryAcquireNanos(1, unit.toNanos(timeout));
 *    }
 *  }
 * }</pre>
 * 这里有一个锁存类，它类似于CountDownLatch，只不过它只需要一个信号来触发。
 * 因为锁存器是非独占的，所以它使用共享的获取和释放方法
 *
 * <pre>{
 *
 *  class BooleanLatch {
 *
 *    private static class Sync extends AbstractQueuedSynchronizer {
 *      boolean isSignalled() { return getState() != 0; }
 *
 *      protected int tryAcquireShared(int ignore) {
 *        return isSignalled() ? 1 : -1;
 *      }
 *
 *      protected boolean tryReleaseShared(int ignore) {
 *        setState(1);
 *        return true;
 *      }
 *    }
 *
 *    private final Sync sync = new Sync();
 *    public boolean isSignalled() { return sync.isSignalled(); }
 *    public void signal()         { sync.releaseShared(1); }
 *    public void await() throws InterruptedException {
 *      sync.acquireSharedInterruptibly(1);
 *    }
 *  }
 *  }</pre>
 *
 * @author HaoHao
 * @date 2021/10/29 3:35 下午
 */
public class MySync {


    // Our internal helper class
    private static class Sync extends AbstractQueuedSynchronizer {

        private static final long serialVersionUID = 4067011600269973494L;

        // Reports whether in locked state
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        // Acquires the lock if state is zero
        @Override
        public boolean tryAcquire(int acquires) {
            assert acquires == 1; // Otherwise unused
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        // Releases the lock by setting state to zero
        @Override
        protected boolean tryRelease(int releases) {
            assert releases == 1; // Otherwise unused
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        // Provides a Condition
        Condition newCondition() {
            return new ConditionObject();
        }

        // Deserializes properly
        private void readObject(ObjectInputStream s)
                throws IOException, ClassNotFoundException {
            s.defaultReadObject();
            setState(0); // reset to unlocked state
        }
    }

    // The sync object does all the hard work. We just forward to it.
    private final Sync sync = new Sync();

    public void lock() {
        sync.acquire(1);
    }

    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    public void unlock() {
        sync.release(1);
    }

    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean isLocked() {
        return sync.isHeldExclusively();
    }

    public boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    public boolean tryLock(long timeout, TimeUnit unit)
            throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }

}
