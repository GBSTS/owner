# SpringBoot项目生成Image并上传到个人Docker Repository
1. 在`pom.xml`文件中添加`docker.maven.plugin.version`的版本以及`Docker Repository`的`URL`
    ```xml
   <?xml version="1.0" encoding="UTF-8"?>
    <project ···>
        ···
        <properties>
            ···
            <docker.maven.plugin.version>1.2.2</docker.maven.plugin.version>
            <docker.host>http://192.168.17.130:2375</docker.host>
            ···
        </properties>
        ···
    </project>
    ```
2. 在`pom.xml`文件中添加`docker-maven-plugin`插件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
    ···
    <build>
        <finalName>${project.artifactId}</finalName>
        <plugins>
            ···
            <!--使用docker-maven-plugin插件-->
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>${docker.maven.plugin.version}</version>
                <!--将插件绑定在某个phase执行-->
                <executions>
                    <execution>
                        <id>build-image</id>
                        <!--用户只需执行mvn package ，就会自动执行mvn docker:build-->
                        <phase>package</phase>
                        <goals>
                            <goal>build</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <!--指定生成的镜像名-->
                    <imageName>gbsts/${project.artifactId}</imageName>
                    <!--指定标签-->
                    <imageTags>
                        <imageTag>${project.version}</imageTag>
                    </imageTags>
                    <!--指定基础镜像jdk1.8-->
                    <baseImage>java:8</baseImage>
                    <!--镜像制作人本人信息-->
                    <maintainer>3350861305@qq.com</maintainer>
                    <!--切换到ROOT目录-->
                    <workdir>/ROOT</workdir>
                    <cmd>["java", "-version"]</cmd>
                    <entryPoint>["java", "-jar", "/${project.build.finalName}.jar"]</entryPoint>
                    <!--指定远程 docker api地址-->
                    <dockerHost>${docker.host}</dockerHost>
                    <!-- 这里是复制 jar 包到 docker 容器指定目录配置 -->
                    <resources>
                        <resource>
                            <targetPath>/</targetPath>
                            <!--jar 包所在的路径  此处配置的 即对应 target 目录-->
                            <directory>${project.build.directory}</directory>
                            <!--用于指定需要复制的文件 需要包含的 jar包 ，这里对应的是 Dockerfile中添加的文件名 -->
                            <include>${project.build.finalName}.jar</include>
                        </resource>
                    </resources>
                </configuration>
            </plugin>
            ···
        </plugins>
    </build>
    ···
</project>

```