
##### Banking application
Created in Eclipse using javafx-sdk-21.0.9
<br>
--------------------------------
##### Development environment

Create library to hold javafx .jar files.

- IDE: Eclipse
- Java: JDK 21
- JavaFX SDK: 21.0.9
- Eclipse plugin: e(fx)clipse

<b>JavaFX configuration</b>
1. Download and install JavaFX SDK 21.0.9.

2. Install the e(fx)clipse plugin in Eclipse.

3. Create a User Library in Eclipse containing the JavaFX .jar files from the SDK's lib directory (for example, named javafx).
4. Add the JavaFX libraries to the project's Modulepath, together with:
   
    - JavaFX21<br>
    - JavaFXSDK<br>
    - JRE System Library [JavaSE-21]
    

Make sure the JavaFX library paths point to the JavaFX SDK installation on your computer.

--------------------------------

<br>
Installing JavaFX on MacOs:
<br>
https://www.youtube.com/watch?v=-W_GyGZV_ro
<br>

--------------------------------

<br>
Login (view, (controller)) is only a mock-up.
<br>

##### Central parts:
Serialize and deserialize.<br>
serialVersionUID.<br>
Transient fields.<br>
Pseudo constructor method calling to recreate the transient fields from serialized read file.<br>
Polymorphism and inheritance.<br>
File handling.<br>
MVC.<br>
Error handling.<br>
UX and GUI.<br>
<br>
Inherited account types.
Bank stored as serialized files including transient field 
and pseudo constructors used for deserializing and recreating fields in objects.
Polymorphism used for account type and for determining account type.
<br>

UI implements for example fileChooser.

<br>


UI/GUI screenshots:<br>
![Create customer, account](images/newCustomerAccount.png)

![Customer info](src/images/customerInfo.png)

![Bank customers](src/images/bankCustomers.png)

![Transaction](src/images/transaction.png)

![Save, open](src/images/saveOpen.png)

![Save, open](src/images/saveOpen2.png)

![Statement, receipt](src/images/statementReceipt.png)




