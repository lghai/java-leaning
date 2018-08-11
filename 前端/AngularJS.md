# AngularJS

## Angular简介

> AngularJS[1] 诞生于2009年，由Misko Hevery 等人创建，后为Google所收购。是一款优秀的前端JS框架，已经被用于Google的多款产品当中。AngularJS有着诸多特性，最为核心的是：MVC、模块化、自动化双向数据绑定、语义化标签、依赖注入等等。 AngularJS 是一个 JavaScript框架。它是一个以 JavaScript 编写的库。它可通过 `<script>`标签添加到HTML 页面。 AngularJS 通过 指令 扩展了 HTML，且通过 表达式 绑定数据到 HTML。 AngularJS 是以一个 JavaScript 文件形式发布的，可通过 script 标签添加到网页中。
> Angular是一个强大的前端框架，其强大之处主要是可以把静态页面与动态数据绑定起来。

## Angular入门

**问题:** 平时我们看到的网页界面上面的数据都是固定，但如果我们要变化这些数据，例如我在一个文本框输入，要实时改动一个文本.这时应该怎么做呢?<br>
很容易应该想到的是通过js方式改变dom文件,这时页面上应该是通过一个定时器,来监控文本框的变化,然后通过js或者jquery改变固定区域的文本.例如

```html
<div>
  <input type="text" name="name" id="name"></div>
<div>
    Hello,<span id="spanId"></span>
</div>
<script>
  var elementInput = document.getElementById("name");
  setInterval(function () {
    document.getElementById("spanId").innerText=elementInput.value;
  },50)
</script>
```

这种方式对于简单的小工程来说也够了.但是对于比较大的工程,考虑的不仅是功能的实现,还包括可维护可扩展,这就需要MVC模式了.如果只用jquery，view的逻辑会和controller，model的逻辑混在一起，不便于维护，例如你在文本框里数据了一个东西，你得用写代码去获取这个值，然后做处理，或者你的某个值改变了，你还得写代码去更新一下view，而angular就是提供这样一个解决方案的框架。

Angular里面的html文件就是view，叫模板（template），当你的数据变化需要改变模板的时候，不用再js代码里面去改变，你可以什么都不做，因为angular神奇的地方就是把模板与数据绑定（data binding），当数据改变的时候模板自动就变了，你的view变了（在文本框输入东西了）也会自动反应到你的数据上面，这就是双向绑定。在angular的理念里面，模板就是一副素描画，数据就是颜色，你想做完这幅画，只需要向模板填充你想要颜色就行了（也就是填充你的数据），例如下面这个例子

```html
<script src="./js/angular.min.js" ></script>
<!-- ng-app：是angular编程入口，是模块作用域  -->
<body ng-app>
<div>
  <!-- ng-model：模型作用  -->
  <input type="text" name="name" ng-model="name"/>
</div>
<div>
  <!-- {{}}AngularJS 表达式： 等价于 ng-bind： 视图上用于数据显示 .
  AngularJS 表达式 很像 JavaScript 表达式：它们可以包含文字、运算符和变量-->
  Hello, {{name}} <br>
  <!--<span ng-bind="name"></span> -->
</div>
```

使用AngularJS 你只需要专注你的数据和模板就够了，他们之间怎么填充，angular把这些做好了，也就是剥离了view层对contorller，mdoel层的影响，简单来说就是你用angular了数据和view自动双向绑定，不用你再代码中去更新，不用angular你还要自己写代码在view变了时候去更新数据，在数据变了的时候去更新view。 ![](./image/angular001.png)

_Angular常用属性_

- `ng-app`:AngularJS的程序入口,对该标签内的元素进行初始化
- `ng-contorller`:在当前元素范围内绑定指定的控制器(controller)
- `ng-model`: 用于绑定应用程序数据到 HTML 控制器(input, select, textarea)的值
- `ng-repeat`:定义集合中每项数据的模板
- `{{}}`:AngularJS 数据绑定表达式,将数据绑定到视图,`{{name}}` 是通过 `ng-model="name"` 进行同步。
- `filter,orderBy`:过滤器.`filter`可以根据指定的属性过滤数据.`orderBy`是排序过滤器,这两个过滤器都是内置的.过滤器是可以自定义的

## Angular基于模块化

**需求:** 在后台获取数据,并对页面中的模型和视图进行动态更新

```html
<!--
    ng-app="myapp" 定义了一个模块
    ng-controller="myctrl" 定义了一个控制器
-->
<div ng-app="myapp" ng-controller="myctrl">
    <div>
        <!--ng-model:模型作用-->
        <input type="text" name="name" ng-model="name"/>
    </div>
    <div>
        <!--视图-->
        Hello,{{name}}
    </div>
</div>
<script>
    var myapp = angular.module("myapp", []);
    //$scope是行内注入声明
    myapp.controller("myctrl", ["$scope", function ($scope) {
        // 对模型进行初始化赋值,使用$scope的方式将数据遍历到页面上
        $scope.name = "ketty";//ketty设想是 使用ajax的方式,将数据查询出来
    }]);
    // 简单的写法是 不声明直接使用
    myapp.controller("myctrl", function ($scope) {
        $scope.name = "ketty";
    });
</script>
```

**Scope(作用域)** 是应用在 HTML (视图) 和 JavaScript (控制器)之间的纽带。<br>
Scope 是一个对象，有可用的方法和属性。<br>
Scope 可应用在视图和控制器上。<br>

## AngularJS 事件绑定

**需求**:设置一个按钮对上面的示例中的模型和视图进行清空

```html
<div ng-app="myapp" ng-controller="myctrl">
    <div>
        <!--ng-model:模型作用-->
        <input type="text" name="name" ng-model="name"/><br>
        <input type="button" value="清空" ng-click="clearVal()">
    </div>
    <div>
        <!--视图-->
        Hello,{{name}}
    </div>
</div>
<script>
    var myapp = angular.module("myapp", []);
    myapp.controller("myctrl", function ($scope) {
        // 对模型进行初始化赋值,使用$scope的方式将数据遍历到页面上
        //调用属性
        $scope.name = "ketty";//ketty设想是 使用ajax的方式,将数据查询出来
        //调用方法
        $scope.clearVal = function () {
            $scope.name='';//模型设置为空
        }
    });
</script>
```

上面的清空操作 代码较少,也可以写入行内,例如 `<input type="button" value="清空2" ng-click="name=''" />`,注意双引号与单引号.

## AngularJS 集合数据遍历

通过`ng-repeat`可以将数组或对象数据迭代到视图模板中

```html
<body ng-app="myapp" ng-controller="myctrl">
<table width="100%" border="1">
    <tr>
        <th>序号</th>
        <th>商品编号</th>
        <th>商品名称</th>
        <th>价格</th>
    </tr>
    <!--
        products：是循环模块名称
        product：是每次循环的名称，用来获取属性的值
    -->
    <tr ng-repeat="product in products | orderBy:'id'"><!--orderBy排序-->
        <td>{{$index+1}}</td><!-- index为ng-repeat中的索引从0开始 -->
        <td>{{product.id}}</td>
        <td>{{product.name}}</td>
        <td>
            <div ng-bind="product.price"></div>
        </td>
    </tr>
</table>
</body>
<script type="text/javascript">
    var myapp = angular.module("myapp", []);
    myapp.controller("myctrl", ["$scope", function ($scope) {
        $scope.products = [
            {id: 1001,name: '数码相机',price: 3000},
            {id: 1002,name: '苹果手机',price: 7000}
        ];
    }]);
</script>
```

`ng-repeat`遍历表格的`tr`,同理,也可以遍历`ul`中的`li`例如:

```html
<ul><!--names需要用$scope初始化-->
    <li ng-repeat="x in names">{{x}} {{lastname}}</li>
</ul>
```

## AngularJS 路由使用(页面架构布局)

使用

```html
<!--ng-app程序入口-->
<body ng-app="myapp">
  <h2>图片标题（头）</h2>
  <ul>
    <li><a href="#/">首页</a></li>
    <li><a href="#/product">商品管理</a></li>
    <li><a href="#/user">用户管理</a></li>
    <li><a href="#/else">其他</a></li>
  </ul>
  <!-- 规定显示路由的位置 -->
  <div ng-view></div>
  <h2>公司版权信息（尾巴）</h2>
</body>
<script>
// 模块化 1.2版本之后，需要添加ngRoute，表示路由
var myapp = angular.module("myapp", ['ngRoute']);
myapp.config(['$routeProvider', function($routeProvider) {
  $routeProvider
    .when('/', {
      templateUrl: './05.route1.html'
    })
    .when('/product', {
      templateUrl: './05.route2.html'
    })
    .when('/user', {
      templateUrl: './05.route3.html'
    })
    .otherwise({
      redirectTo: '/'
    });
}]);
</script>
```

页面中的布局可以使用路由的功能来完成,一般一个网站的头和尾是固定的,路由设置对象参数规则：

```javascript
$routeProvider.when(url,{
    template:string, //在ng-view中插入简单的html内容
    templateUrl:string, //在ng-view中插入html模版文件
    controller:string,function / array, //在当前模版上执行的controller函数
    controllerAs:string, //为controller指定别名
    redirectTo:string,function, //重定向的地址
    resolve:object<key,function> //指定当前controller所依赖的其他模块
});
```
