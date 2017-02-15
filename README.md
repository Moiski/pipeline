System Requirements
  -------------------
  JDK: 1.8 or above.
  SERVER: Apache Tomcat 8.0.35
  DATABASE: MySQL 5.1.39
  MAVEN: any version
  
  The application was tested at Windows 7 system
  
   Preparing actions
  --------------------
  Check that you have specified all of the path variables.
  Such as JAVA_HOME and M2_HOME.
  
  Create a database using SQL Scripts from the root folder of the project.
  pipeline_DAMP_DB_15_02_2017.sql - creates a database with the name "mkpipeline" and fills it the data.
  
  Open the \Pipelines\DAO\src\main\resources\spring-dao-config.xml
           
  You need to change the username, password and url values.
 ************************************************************************************************** 
    <property name="url" value="jdbc:mysql://localhost:3306/mkpipeline" />
    <property name="username" value="root" />
    <property name="password" value="root" />
 ************************************************************************************************** 
 
  Now you need to register a user in the tomcat-users.xml file (you can find this file in a folder conf of Tomcat server):
 **************************************************************************************************   
	 <tomcat-users>
		<role roleame="admin-gui"/>
		<role rolename="manager-gui"/>
		<role rolename="manager-script"/>
		<role rolename="manager-jmx"/>
		<role rolename="manager-status"/>
		<role rolename="manager"/>
		<user username="username" password="password" roles="admin-gui,manager-gui,manager-script,manager-jmx,manager-status,manager" />
	</tomcat-users>
 **************************************************************************************************
  
  Register the Tomcat server in a settings.xml file of Maven (you can find this file in a folder conf of Maven).
  You can use your personal values inside the <id>, <username> and <password> tags: 
 **************************************************************************************************
	<servers>
		<server>
		  <id>Tomcat-1.8-localhost</id>
		  <username>username</username>
		  <password>password</password>
		</server>
	</servers>
 **************************************************************************************************

  Open a pom.xml file from a root folder of the Pipelines project.
  Set the <server> value according that you have specified in settings.xml.
  Change the localhost and port values if you have others ones.
 **************************************************************************************************
	 <plugin>
		<groupId>org.apache.tomcat.maven</groupId>
		<artifactId>tomcat7-maven-plugin</artifactId>
		<version>2.2</version>
			<configuration>
				<!-- id from settings.xml -->
				<server>Tomcat-1.8-localhost</server>
				<url>http://localhost:8080/manager/text</url>
				<path>/${project.name}</path>
			</configuration>
	</plugin>
 **************************************************************************************************

 Deploy and start of the application.
 -------------------------------------
  Start the tomcat server.
  Go to the root folder of project.
  Build the project, using a console command: mvn clean install
  Deploy the application, using a console command: mvn  tomcat7:deploy
	
  Now you can start the web application using next url in your web browser: http://localhost:8080/pipeline/
  
  To create new pipeline you can sends to server request with pipeline config in YAML format (request.yaml).
