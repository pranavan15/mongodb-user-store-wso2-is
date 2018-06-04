# MongoDB User Store Extension for WSO2 IS

## Introduction
This is an extension, which consists of a user store implemented using MongoDB (A NoSQL Database) for WSO2 Product-IS. This MongoDB user store extension can be used as both primary and secondary user store for product-IS. This extension is compatible with IS version 5.5.0. 

## Prerequisites
- [MongoDB user store extension](https://github.com/pranavan15/mongodb-user-store-wso2-is/archive/master.zip)
- [WSO2 IS version 5.5.0](https://wso2.com/identity-and-access-management/install)
- [MongoDB](https://www.mongodb.com/download-center?jmp=nav#community)
- [MongoDB-Java-driver](https://oss.sonatype.org/content/repositories/releases/org/mongodb/mongo-java-driver/3.7.0/mongo-java-driver-3.7.0.jar)

## Steps to Configure
- First, build the `MongoDB user store extension` using maven by executing the following command from the root folder of this extension
```bash
   mvn clean install    
```

- Copy the extension jar file created inside the `target` folder and add it into the `repository/components/dropins` folder of product-IS 

- Copy the MongoDB-Java-driver jar into the `repository/components/lib` folder of product-IS

- start the MongoDB server using the following command
```bash
   sudo service mongod start  
```

- Start a Mongo shell using the below command
```bash
   mongo --host 127.0.0.1:27017
```

- Create a database named `wso2_carbon_db` by entering the following command in the Mongo shell
```bash
   use wso2_carbon_db
```

- Create the necessary collections by running the MongoDB script file [user_mgt_collections.js](/dbscripts/user_mgt_collections.js) provided by executing the following command in the Mongo shell
```bash
   load(<PATH_TO_THE_SCRIPT_FILE>)
```

- Finally, open a terminal, navigate to the `bin` folder of product-IS and start the IS server by executing the following command
```bash
   ./wso2server.sh
```

Now you have successfully added the mongoDB user store extension to the product-IS. You should see MongoDB user store listed along with other user stores using which you can create a MonogDB secondary user store and started using it for your user management operations. 


### Configuring MongoDB as the Primary User Store
