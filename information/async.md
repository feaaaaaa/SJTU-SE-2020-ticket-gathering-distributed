解决异步调用的问题：  
### callback
function(x,callback);  
x为自己运行需要的参数，callback为函数执行完调用的回调函数  
而function(x)本身是一个异步函数，于是这样写然callback函数一定在function之后运行。  
缺点：多重的callback的嵌套，可读性可维护性很差  
jest会不管callback函数往下运行，解决方案:
```
test('the data is peanut butter', done => {
  function callback(data) {
    try {
      expect(data).toBe('peanut butter');
      done();
    } catch (error) {
      done(error);
    }
  }

  fetchData(callback);
});
```
加一个done参数，函数一定要运行到done被调用过才会结束，否则就会报错，也不是很好用，jest还是会不管异步问题，进攻参靠

### promise
比较好用，fetch的返回值就是就是promise封装成的，所以写法和fetch之后的操作很像
代码：
```
let promise = new Promise(function(resolve, reject){
            //先要执行的异步函数
            resolve()
        });
promise.then(() =>{} ); //异步函数结束后的调用的函数
```
resolve表示状态发生改变，若状态从pending转为success就会调用resolve，否则使用reject

### async await  
使用最频繁的方法 
``` 
const functionName = async () => {
    await function1;
    function2;
};
```
async相当于声明，这个函数里面存在异步函数，
而await则表示下一行函数要等该行执行完才可以继续

简单用法就是这样  
ps:我们的项目里面同时用到了三种异步的处理方法，所以前端测试有点复杂，在用到ajax的地方就经常出现await里面套promise，promise里面套callback的情况
