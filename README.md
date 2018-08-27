# automation-pom-framework

# Authors
1. Govardhan Sriramdasu

# Cloneable or doanloadable URL
https://github.com/govardhan8884764321/automation-pom-framework.git  

# What is this frame work?
This is POM framework where in **every individual page (such as .html/.jsp/.php/etc)** can be created as an **individual java file** which will be referred in the test case development.

# Setup steps:
* Clone the project to desired desktop location

# Creating an POM page
* Every page will have a mapping in the **PageObjectMapper.java** java file of **PageObjectManager.java** java file elements.
* Write/add/leverage reusable methods under com.auto.framework.pom.common.pages.BasePage.java  
* Create pages under com.auto.framework.pom.pages package. **For example adding SignUpPage.java page**
1. create com.auto.framework.pom.pages.SignUpPage page with all tye locators/methods (take other pages as references)
2. Add the url mapping in the **PageObjectManager.java** as suggested in this file
3. Add the java class file mapping in the **PageObjectMapper** as suggested in this file
