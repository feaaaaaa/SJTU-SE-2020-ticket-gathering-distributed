### 前端单元测试  
使用工具：jest+enzyme
使用依赖：  
+ enzyme
+ enzyme-to-json
+ jest
+ enzyme-adapter-react-16  

### 具体配置  
1. package.json:
```
"scripts": {
    "test": "jest",
    "test:watch": "jest --watch",
    "test:coverage": "jest --coverage",
  },
```

```
"jest": {
    "setupFiles": [
      "./_tests_/jestsetup.js"
    ],
    "snapshotSerializers": [
      "enzyme-to-json/serializer"
    ],
    "moduleNameMapper": {
      "^.+\\.(css|scss)$": "identity-obj-proxy"
    },
    "moduleFileExtensions": ["js", "jsx", "json"]
  }
```

2. 新建.babelrc文件
```
{
  "presets": [
      "@babel/preset-env",
    "@babel/preset-react"
  ],
  "plugins": [ "@babel/plugin-transform-arrow-functions", "@babel/plugin-proposal-class-properties" ]
}
```

3. 编辑配置：
jest:选择jest package 其他保持默认

4. 新建文件夹：_tests_ jest  默认测试位置
文件夹下创建jestsetup.js文件
```
import Enzyme, { shallow, render, mount,  } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';

// React 16 Enzyme adapter
Enzyme.configure({ adapter: new Adapter() });
// Make Enzyme functions available in all test files without importing
global.shallow = shallow;
global.render = render;
global.mount = mount;
```

### 测试步骤
1. 新建xxx.test.js文件
2. 无参数普通组件测试实例:
```
import React from 'react'
import {SuccessView} from "../src/view/SuccessView";

describe("test",()=>{
    it("test one",()=>{
        const wrapper=mount(<SuccessView/>);
        expect(wrapper).toMatchSnapshot();
    })
});
```
3. 逻辑测试  
使用jest的api，具体如下：
toBe: expect(2 + 2).toBe(4);
toEqual: expect(data).toEqual({one: 1, two: 2});(用于测试对象)
toBeNull  
具体：  
https://www.jianshu.com/p/c1b5676c1edd

4. 组件测试
工具：enzyme
api有点多，参考官方文件  
https://enzymejs.github.io/enzyme/

5. 生成测试报告
配置中有："test:coverage": "jest --coverage"
所以用 jest --coverage或者 npm test:coverage

## 踩坑之旅
jest默认不支持的东西:
+ ES6    
额外装依赖 babel-jest babel-core babel-preset-env
比如import 如果出现... is not a function 基本上是这个问题，要额外导入babel然后进行配置引入，上面已经引入了
+ fetch以及fetch里的response  
react支持fetch，但是jest不支持 要导入'node-fetch'
+ callback  这个是最容易易出错的地方  
当jest执行callback的时候，由于callback是异步调用，所以以jest会先不执行callback内容，直接到下一行代码，于是上一行代码就在还没完全结束之前就已经测完了  
解决办法:用异步的处理办法，见文档 async  

jest-mock:
简而言之，就是用自己写的函数替代原本的函数，起到测试的效果，比如设置函数返回值：
fetch.mockReturnValue
下面为mock掉fetch函数的导入库:
```
jest.mock('node-fetch');
import fetch from 'node-fetch';
const {Response} = jest.requireActual('node-fetch');
```






