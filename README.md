# CLARIN/pws
Implementation and deployment of the ProfilerWebService

Dependencies:

* [git](http://www.git-scm.com/)
* [GNU make](https://www.gnu.org/software/make/)
* [gcc](https://gcc.gnu.org/)
* [cppunit](http://freedesktop.org/wiki/Software/cppunit/)
* [xerces-c](https://xerces.apache.org/xerces-c/)
* [java](http://java.com)
* [apache-ant](http://ant.apache.org)
* [apache-tomcat](tomcat.apache.org)
* [axis2](http://ws.apache.org/axis2)

# Building and starting the webservice
1. Initialize all git submodules using `$ git submodule update --init --recursive`.
This will checkout all required submodules:
   * `gsm/lexicon`
   * `gsm/lexicon/gsm/ocrcxx`
   * `gsm/lexicon/gsm/ocrcxx/gsm/csl`
2. Make shure that [apache-tomcat](https://tomcat.apache.org/) is installed and working
3. Set the environment variable `JAVA_HOME` to point to your java installation eg:
`export JAVA_HOME=/usr/lib/jvm/java-7-openjdk`
4. Find the base directory of your tomcat installation eg: `/srv/www/tomcat/apache-8.0.0/`
and set the environment variable `APACHE_HOME` accordingly:
`export APACHE_HOME=/srv/www/tomcat/apache-8.0.0/`
5. The language backend directory, where the language resources for the profiler are stored,
defaults to `$APACHE_HOME/../backend`.
If you want to change this value you can set the environment variable `PROFILER_BACKEND` accordingly.
6. Change into the `pws` base directory and type `$ make`.
The make process
   * downloads `axis2-1.6.2` into the `pws/vars` directory
   * generates the ant buildfile `build.xml` and the WSDL files
   * generates the `ProfilerWebService.aar` from the java soures
   * builds the profiler
   * generates the language resources
   * installs the language ressources and the profiler into the language backend
   * installs the both the `axis2` service and the `ProfilerWebService`
   * restarts apache (*Note: Im not shure if this step is required*)
7. After the build process has finished succesfully,
you can check the ProfilerWebService using `$ make test`.
You can further tune the url of the Webservice using the variables `HOST`, `PORT` and `PWS_URL`.
[*WSDL]: Web Service Descritpion Language

# Versions
This project is dependend on different intermingled technologies.
It was tested with the following tools:
* java-7-openjdk, ant-1.9.4, apache-tomcat-8.0.0-RC5, axis2-1.6.2

# APACHE TOMCAT
1. Download Apache Tomcat 6.0.18 or higher from [here](http://tomcat.apache.org/download-60.cgi)
2. Unzip the `apache-tomcat-6.0.18.zip` to `C:\Program Files\apache-tomcat-6.0.18`
3. Enable the Apache Tomcat Manager Application for direct deployment.
   In order to do so, you will have to assign the manager role to one
   user in the user configuration file `C:\Programme\apache-tomcat-6.0.18\conf\tomcat-users.xml`
   ```Xml
      <?xml version='1.0' encoding='utf-8'?>
      <tomcat-users>
        <role rolename="manager"/>
        <user username="impact" password="yourpasswod" roles="manager"/>
        </tomcat-users>
   ```
   This user is used in the deployment ant tasks. Additionally, you can
   access the Tomcat Manager Application at: http://localhost:8080/manager/html
4. Execute the `C:\Program Files\apache-tomcat-6.0.18\bin\startup.bat` file
   from the command line in order to see whether the server starts correctly.
   Open the URL http://localhost:8080 in your browser in order to verify
   if the Apache Tomcat Welcome page is displayed.

# APACHE ANT
1. Download Apache Ant Version 1.7.1 or higher from http://ant.apache.org/bindownload.cgi
2. Install apache ant following the instructions here
   http://ant.apache.org/manual/install.html
   The main steps:
   * Unzip the apache-ant-1.7.0.zip to C:\Program Files\apache-ant-1.7.0
   * Add the bin directory to your path (see point 1 for details on how
     to set the environment variable), so that you would have the following
     path for example:
     %SystemRoot%\system32;%SystemRoot%;
     `C:\Program Files\Java\jdk1.6.0_12\bin;C:\Program Files\apache-ant-1.7.0\bin`
   * Set the ANT_HOME environment variable to the directory where you installed Ant:
     Variable: `ANT_HOME` Value: `C:\Program Files\apache-ant-1.7.0`
