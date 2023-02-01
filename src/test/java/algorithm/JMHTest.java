package algorithm;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

import static algorithm.Gcd.gcd;

/**
 * @BenchmarkMode
 * 微基准测试类型。JMH 提供了以下几种类型进行支持：
 *
 * 类型	描述
 * Throughput	每段时间执行的次数，一般是秒
 * AverageTime	平均时间，每次操作的平均耗时
 * SampleTime	在测试中，随机进行采样执行的时间
 * SingleShotTime	在每次执行中计算耗时
 * All	所有模式
 *
 *
 * @Warmup
 * 这个单词的意思就是预热，iterations = 3就是指预热轮数。
 *
 * @Measurement
 * 正式度量计算的轮数。
 *
 * iterations 进行测试的轮次
 * time 每轮进行的时长
 * timeUnit时长单位
 * @Threads
 * 每个进程中的测试线程。
 *
 * @Fork
 * 进行 fork 的次数。如果 fork 数是3的话，则 JMH 会 fork 出3个进程来进行测试。
 *
 * @OutputTimeUnit
 * 基准测试结果的时间类型。一般选择秒、毫秒、微秒。
 *
 * @Benchmark
 * 方法级注解，表示该方法是需要进行 benchmark 的对象，用法和 JUnit 的 @Test 类似。
 *
 * @Param
 * 属性级注解，@Param 可以用来指定某项参数的多种情况。特别适合用来测试一个函数在不同的参数输入的情况下的性能。
 *
 * @Setup
 * 方法级注解，这个注解的作用就是我们需要在测试之前进行一些准备工作，比如对一些数据的初始化之类的。
 *
 * @TearDown
 * 方法级注解，这个注解的作用就是我们需要在测试之后进行一些结束工作，比如关闭线程池，数据库连接等的，主要用于资源的回收等。
 *
 * @State
 * 当使用@Setup参数的时候，必须在类上加这个参数，不然会提示无法运行。
 *
 * 就比如我上面的例子中，就必须设置state。
 *
 * State 用于声明某个类是一个“状态”，然后接受一个 Scope 参数用来表示该状态的共享范围。因为很多 benchmark 会需要一些表示状态的类，JMH 允许你把这些类以依赖注入的方式注入到 benchmark 函数里。Scope 主要分为三种。
 *
 * Thread: 该状态为每个线程独享。
 * Group: 该状态为同一个组里面所有线程共享。
 * Benchmark: 该状态在所有线程间共享。
 *
 * @author ：li zhen
 * @description:
 * @date ：2023/2/1 9:41
 */
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.SECONDS)
@Threads(Threads.MAX)
public class JMHTest {

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void testEGcd(){
        gcd(10,25);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void testWGcd(){
        gcd(10L,25L);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHTest.class.getSimpleName()) // 可以用方法名，也可以用XXX.class.getSimpleName()
                .forks(3)
                .warmupIterations(2)// 预热2轮
                .measurementIterations(2)// 代表正式计量测试做2轮，而每次都是先执行完预热再执行正式计量， 内容都是调用标注了@Benchmark的代码。
                .output("C:\\Users\\86181\\Desktop")
                .build();

        new Runner(opt).run();
    }

}
