## 传统方式

## jQuery OCUpload
### 引入
>OCUpload为jQuery的插件(One Click Upload)对于传统的文件上，只能通过form表单，将enctype设置为multipart/form-data，选中文件后还需在页面点击submit提交按钮，提交表单，才能在后台接收上传的文件并进行相关字段解析，上传成功后，页面还要刷新，这样并不符合我们的某些实际需求。而ajax又不支持文件上传的功能

### 解决方案 
```html
<iframe id="iframefile" style="display:none" ></iframe>
<form target="text" action="xxx" method="post" target="iframefile" enctype="multipart/form-data">
  <input type="file" name="myFile"/>
  <input type="submit" value="upload"/>         
</form>
```
通过这种方式上传文件，刷新的页面就变成了这个iframe，而且设置的隐藏我们看不到，
而我们自己所用的页面就不会刷新，通过这种方式达到了一个不刷新页面上传文件的效果。
  而OCUpload就是采用了这种方式，只是进行了封装我们看不到.下面介绍使用步骤
### OCUpload使用步骤
1.导包,OCUpload是基于jQuey的一个插件,所以必须导入jQuery和OCUpload的js包,如下
```html
<script src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery.ocupload-1.1.2.js"></script>
```
2.在页面中任意的位置提供一个元素
`<input id="myButton" type="button" value="上传"/>`

3.调用插件提供的upload方法，动态修改HTML页面元素。
```html
<script type="text/javascript">
jQuery(function($) {
  $("#myButton").upload({
    action: 'xxx',			//你所要向服务器请求的的路径，必填
    name: 'myFile',			//上传组件的name的值,不写默认是file即<input type='file' name='myFile'/>
    enctype: 'multipart/form-data',	//mime类型，使用默认就好
    params: {},			//请求时额外传递的参数，默认是为空的   
    onSelect: function(self, element) {	//当用户选择了一个文件后触发事件
      this.autoSubmit = false;		//当选择了文件后，关闭自动提交
    },
    onSubmit: function(self, element) {},	//提交表单之前触发事件
    autoSubmit: true, 		//是否自动提交，即当选择了文件，自动关闭了选择窗口后，是否自动提交请求。
    onComplete: function(data, self, element) {} //提交表单完成后触发的事件
  });
});
</script>
```
