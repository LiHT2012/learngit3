package com.dbcool.api.cfg.sysinitialres;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 系统预设的管理组织及空间的ID
 * @author wxy
 *
 */
@Configuration
@ConfigurationProperties(prefix = "management")
@PropertySource("classpath:sysres/management_space.properties")
//classpath：未找到定义位置或修改位置，似乎是springboot的默认路径，项目中为src/main/resources
/**
springboot源码关于classpath：
private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
        "classpath:/META-INF/resources/",
        "classpath:/resources/",
        "classpath:/static/",
        "classpath:/public/" };
其他信息，待核实正误
2、Classpath含义

    存放各种资源配置文件 eg.init.properties log4j.properties struts.xml
    存放模板文件 eg.actionerror.ftl
    存放class文件对应的是项目开发时的src目录编译文件

总结：这是一个定位资源的入口
一般java项目中 classpath存在与 WEB-INFO/目录。
当我们需要某个class时，系统会自动在CLASSPATH里面搜索，如果是jar，就自动从jar里面查找，如果是普通的目录，则在目录下面按照package进行查找。
但与PATH不同的是，默认的CLASSPATH是不包含当前目录的，这也是CLASSPATH里面要包含一个点的道理了。

Tomcat下的Web应用有两个预置的classpath : WEB-INF/classes 和WEB-INF/lib启动项目，项目就会加载这两个目录里的数据。这是war包的规范.要改变预置的classpath比较麻烦，在Tomcat的配置文件里没有发现类似的配置，要实现自己的classloader才能达到目的。

一个在tomcat中运行的web应用.它的classpath都包括如下目录:
我知道的有:
%tomcat%/lib
web-inf/lib
web-inf/classes
环境变量里的classpath

总结：classpath这是一个定位资源的入口.classpath下 lib的优先级大于classes;
=====

java项目中Classpath路径到底指的是哪里？
1、src不是classpath, WEB-INF/classes,lib才是classpath，WEB-INF/ 是资源目录, 客户端不能直接访问。

2、WEB-INF/classes目录存放src目录java文件编译之后的class文件，xml、properties等资源配置文件，这是一个定位资源的入口。

3、引用classpath路径下的文件，只需在文件名前加classpath:
-------------------------------------
<param-value>classpath:applicationContext-*.xml</param-value>
<!-- 引用其子目录下的文件,如 -->
<param-value>classpath:context/conf/controller.xml</param-value>
-------------------------------------

4、lib和classes同属classpath，两者的访问优先级为: lib>classes。

5、classpath 和 classpath* 区别：

classpath：只会到你的class路径中查找找文件;
classpath*：不仅包含class路径，还包括jar文件中(class路径)进行查找。
**/
public class ManagementSpace {
	private String spaceId;
	private String spaceGroupId;

	private String spaceTempBaseFolderId;
	private String spaceTempSetFolderId;
	private String spaceHotBaseFolderId;
	private String spaceHotDirFolderId;
	private String spaceHotDocFolderId;
	private String initialBaseFolderId;

	private String encyclopediaWebTheme;
	private String encyclopediaWebHotBase;
	private String encyclopediaWebHotDoc;
	private String encyclopediaWechatTheme;

	private String encyclopediaRollingRankingList;
	private String encyclopediaHotRankingList;
	private String encyclopediaThemeClassifyRank;

	public String getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(String spaceId) {
		this.spaceId = spaceId;
	}

	public String getSpaceGroupId() {
        return spaceGroupId;
    }

    public void setSpaceGroupId(String spaceGroupId) {
        this.spaceGroupId = spaceGroupId;
    }

    public String getSpaceTempBaseFolderId() {
		return spaceTempBaseFolderId;
	}

	public void setSpaceTempBaseFolderId(String spaceTempBaseFolderId) {
		this.spaceTempBaseFolderId = spaceTempBaseFolderId;
	}

	public String getSpaceTempSetFolderId() {
		return spaceTempSetFolderId;
	}

	public void setSpaceTempSetFolderId(String spaceTempSetFolderId) {
		this.spaceTempSetFolderId = spaceTempSetFolderId;
	}

	public String getSpaceHotBaseFolderId() {
		return spaceHotBaseFolderId;
	}

	public void setSpaceHotBaseFolderId(String spaceHotBaseFolderId) {
		this.spaceHotBaseFolderId = spaceHotBaseFolderId;
	}

	public String getSpaceHotDirFolderId() {
		return spaceHotDirFolderId;
	}

	public void setSpaceHotDirFolderId(String spaceHotDirFolderId) {
		this.spaceHotDirFolderId = spaceHotDirFolderId;
	}

	public String getSpaceHotDocFolderId() {
		return spaceHotDocFolderId;
	}

	public void setSpaceHotDocFolderId(String spaceHotDocFolderId) {
		this.spaceHotDocFolderId = spaceHotDocFolderId;
	}

    public String getInitialBaseFolderId() {
        return initialBaseFolderId;
    }

    public void setInitialBaseFolderId(String initialBaseFolderId) {
        this.initialBaseFolderId = initialBaseFolderId;
    }
	public String getEncyclopediaWebTheme() {
		return encyclopediaWebTheme;
	}

	public void setEncyclopediaWebTheme(String encyclopediaWebTheme) {
		this.encyclopediaWebTheme = encyclopediaWebTheme;
	}

	public String getEncyclopediaWebHotBase() {
		return encyclopediaWebHotBase;
	}

	public void setEncyclopediaWebHotBase(String encyclopediaWebHotBase) {
		this.encyclopediaWebHotBase = encyclopediaWebHotBase;
	}

	public String getEncyclopediaWebHotDoc() {
		return encyclopediaWebHotDoc;
	}

	public void setEncyclopediaWebHotDoc(String encyclopediaWebHotDoc) {
		this.encyclopediaWebHotDoc = encyclopediaWebHotDoc;
	}

	public String getEncyclopediaWechatTheme() {
		return encyclopediaWechatTheme;
	}

	public void setEncyclopediaWechatTheme(String encyclopediaWechatTheme) {
		this.encyclopediaWechatTheme = encyclopediaWechatTheme;
	}


	public String getEncyclopediaHotRankingList() {
		return encyclopediaHotRankingList;
	}

	public void setEncyclopediaHotRankingList(String encyclopediaHotRankingList) {
		this.encyclopediaHotRankingList = encyclopediaHotRankingList;
	}

	public String getEncyclopediaRollingRankingList() {
		return encyclopediaRollingRankingList;
	}

	public void setEncyclopediaRollingRankingList(String encyclopediaRollingRankingList) {
		this.encyclopediaRollingRankingList = encyclopediaRollingRankingList;
	}

	public String getEncyclopediaThemeClassifyRank() {
		return encyclopediaThemeClassifyRank;
	}

	public void setEncyclopediaThemeClassifyRank(String encyclopediaThemeClassifyRank) {
		this.encyclopediaThemeClassifyRank = encyclopediaThemeClassifyRank;
	}

}
