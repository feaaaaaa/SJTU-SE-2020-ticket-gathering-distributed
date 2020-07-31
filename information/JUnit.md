**简述**

单元测试是一个对单一实体（类或方法）的测试。

一个正式的编写好的单元测试用例的特点是：已知输入和预期输出

每一项需求至少需要两个单元测试用例：一个正检验，一个负检验。如果一个需求有子需求，每一个子需求必须至少有正检验和负检验两个测试用例。



**环境配置**

https://www.w3cschool.cn/junit/x6o71hv6.html



**框架**

-工具：

- 在所有测试调用指令发起前的 setUp() 方法。
- 在测试方法运行后的 tearDown() 方法。

-套件：

@RunWith 和 @Suite 都被用作运行测试套件。

如：

@RunWith(Suite.class) 

@Suite.SuiteClasses({ TestJunit1.class ,TestJunit2.class })

-运行器

-分类



**基本用法**：

简单例子：

对如下待测试类：

```java
public class MessageUtil {

   private String message;

   public MessageUtil(String message){
      this.message = message;
   }

   public String printMessage(){
      System.out.println(message);
      return message;
   }   
}  
```

创建测试类：

```java
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class TestJunit {

   String message = "Hello World";  
   MessageUtil messageUtil = new MessageUtil(message);

   @Test//注意添加annotation
   public void testPrintMessage() {
 	 assertEquals(message,messageUtil.printMessage());//这里使用Junit 的 assertEquals API 来检查。
   }
}
```

还需要创建test runner类来运行测试类的案例：

```java
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(TestJunit.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println(result.wasSuccessful());
   }
}   
```

运行 Test Runner,它可以运行在所提供的 Test Case 类中定义的测试案例。

结果：

```
Hello World
true
```



**API**

-Assert类：

public class Assert extends java.lang.Object

| 1    | **void assertEquals(boolean expected, boolean actual)** 检查两个变量或者等式是否平衡 |
| ---- | ------------------------------------------------------------ |
| 2    | **void assertFalse(boolean condition)** 检查条件是假的       |
| 3    | **void assertNotNull(Object object)** 检查对象不是空的       |
| 4    | **void assertNull(Object object)** 检查对象是空的            |
| 5    | **void assertTrue(boolean condition)** 检查条件为真          |
| 6    | **void fail()** 在没有报告的情况下使测试不通过               |

-TestCase 类

public abstract class TestCase extends Assert implements Test

| 1    | **int countTestCases()** 为被run(TestResult result) 执行的测试案例计数 |
| ---- | ------------------------------------------------------------ |
| 2    | **TestResult createResult()** 创建一个默认的 TestResult 对象 |
| 3    | **String getName()** 获取 TestCase 的名称                    |
| 4    | **TestResult run()** 一个运行这个测试的方便的方法，收集由TestResult 对象产生的结果 |
| 5    | **void run(TestResult result)** 在 TestResult 中运行测试案例并收集结果 |
| 6    | **void setName(String name)** 设置 TestCase 的名称           |
| 7    | **void setUp()** 创建固定装置，例如，打开一个网络连接        |
| 8    | **void tearDown()** 拆除固定装置，例如，关闭一个网络连接     |
| 9    | **String toString()** 返回测试案例的一个字符串表示           |

-TestResult 类

public class TestResult extends Object

这里区分失败和错误。失败是可以预料的并且可以通过假设来检查。错误是不可预料的问题就像 ArrayIndexOutOfBoundsException。

| 序号 | 方法和描述                                                   |
| :--- | :----------------------------------------------------------- |
| 1    | **void addError(Test test, Throwable t)** 在错误列表中加入一个错误 |
| 2    | **void addFailure(Test test, AssertionFailedError t)** 在失败列表中加入一个失败 |
| 3    | **void endTest(Test test)** 显示测试被编译的这个结果         |
| 4    | **int errorCount()** 获取被检测出错误的数量                  |
| 5    | **Enumeration errors()** 返回错误的详细信息                  |
| 6    | **int failureCount()** 获取被检测出的失败的数量              |
| 7    | **void run(TestCase test)** 运行 TestCase                    |
| 8    | **int int runCount()** 获得运行测试的数量                    |
| 9    | **void startTest(Test test)** 声明一个测试即将开始           |
| 10   | **void stop()** 标明测试必须停止                             |

-TestSuite 类

public class TestSuite extends Object implements Test

| 1    | **void addTest(Test test)** 在套中加入测试。                 |
| ---- | ------------------------------------------------------------ |
| 2    | **void addTestSuite(Class testClass)** 将已经给定的类中的测试加到套中。 |
| 3    | **int countTestCases()** 对这个测试即将运行的测试案例进行计数。 |
| 4    | **String getName()** 返回套的名称。                          |
| 5    | **void run(TestResult result)** 在 TestResult 中运行测试并收集结果。 |
| 6    | **void setName(String name)** 设置套的名称。                 |
| 7    | **Test testAt(int index)** 在给定的目录中返回测试。          |
| 8    | **int testCount()** 返回套中测试的数量。                     |
| 9    | **static Test warning(String message)** 返回会失败的测试并且记录警告信息。 |





