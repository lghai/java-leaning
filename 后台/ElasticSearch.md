# ElasticSearch

## 简介
>ElasticSearch是一个基于Lucene的搜索服务器。它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。Elasticsearch是用Java开发的，并作为Apache许可条款下的开放源码发布，是当前流行的企业级搜索引擎。设计用于云计算中，能够达到实时搜索，稳定，可靠，快速，安装使用方便。

>我们建立一个网站或应用程序，并要添加搜索功能，令我们受打击的是：搜索工作是很难的。我们希望我们的搜索解决方案要快，我们希望有一个零配置和一个完全免费的搜索模式，我们希望能够简单地使用JSON通过HTTP的索引数据，我们希望我们的搜索服务器始终可用，我们希望能够一台开始并扩展到数百，我们要实时搜索，我们要简单的多租户，我们希望建立一个云的解决方案。Elasticsearch旨在解决所有这些问题和更多的问题。

## ElasticSearch 安装配置使用入门

官网: [https://www.elastic.co/products/elasticsearch](https://www.elastic.co/products/elasticsearch) 

![altText](images/elasticsearch_index.png ) 

官网已经更新到6.1的版本.本文档使用2.4.0版本的ElasticSearch,下载后的目录结构如下: 

![altText](images/elasticsearch.png) 

运行elasticSearch/bin/elasticsearch.bat 文件,需要JAVA_HOME的环境变量支持.浏览器访问 [http://127.0.0.1:9200](http://127.0.0.1:9200)

![](images/elasticsearch_access.png) 

安装elasticSearch成功

注意：
elasticsearch需要安装jdk的版本在1.7.0.72以上，如果使用jdk在1.7.0.52版本会导致
jdk版本过低，出现启动elasticsearch.bat时闪退现象。
方法一：在环境变量中添加JAVA_OPTS ：-XX:-UseSuperWord；
方法二：在ElasticSearch/bin/elasticsearch.in.bat文件中添加一行 set JAVA_OPTS=-XX:-UseSuperWord；

