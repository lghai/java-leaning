### 背景
>在一次实际编码过程中,需要获取本机的ip地址. 因而使用InetAddress这个类的方法  `InetAddress.getLocalHost().getHostAddress()` 可以获取本机以太网网卡ip地址
### InetAddress简介
>InetAddress的实例对象包含以数字形式保存的IP地址，同时还可能包含主机名（如果使用主机名来获取InetAddress的实例，或者使用数字来构造，并启用了反向主机名解析的功能）。InetAddress类提供了将主机名解析为IP地址（或反之）的方法  <br/>

>InetAddress对域名进行解析是使用本地机器配置或者网络命名服务（如域名系统（Domain Name System，DNS）和网络信息服务（Network Information Service，NIS））来实现。对于DNS来说，本地需要向DNS服务器发送查询的请求，然后服务器根据一系列的操作，返回对应的IP地址，为了提高效率，通常本地会缓存一些主机名与IP地址的映射，这样访问相同的地址，就不需要重复发送DNS请求了。在java.net.InetAddress类同样采用了这种策略。在默认情况下，会缓存一段有限时间的映射，对于主机名解析不成功的结果，会缓存非常短的时间（10秒）来提高性能。

### InetAddress对象的获取
InetAddress的构造函数不是公开的（public），所以需要通过它提供的静态方法来获取
```java
static InetAddress[] getAllByName(String host)
static InetAddress getByAddress(byte[] addr)
static InetAddress getByAddress(String host,byte[] addr)
static InetAddress getByName(String host)
static InetAddress getLocalHost()
```
### InetAddress的主要方法
1. `public String getHostName()` 返回的是本机名
2. `public String  getCanonicalHostName()` 该方法和getHostName方法一样，也是得到远程主机的域名。区别是，该方法得到的是主机名，getHostName得到的是主机别名。
3. `public String  getHostAddress()` 该方法用来得到主机的IP地址，这个IP地址可以是IPv4也可以是IPv6的
4. `public byte[] getAddress()` 该方法和getHostAddress方法唯一区别是，getHostAddress返回字符形式的IP地址，getAddress返回byte数组形式的IP地址。

### 示例
```java
@Test
public void testName() throws Exception {
  System.out.println(InetAddress.getByName("www.oracle.com"));
	System.out.println(InetAddress.getLocalHost().getHostName());
	System.out.println(InetAddress.getLocalHost().getCanonicalHostName());
	System.out.println(InetAddress.getLocalHost().getHostAddress());
	System.out.println(Arrays.toString(InetAddress.getLocalHost().getAddress()));
}
```
### 结果
```
www.oracle.com/223.119.154.27
hai-PC
hai-PC
192.168.56.1
[-64, -88, 56, 1]
```
