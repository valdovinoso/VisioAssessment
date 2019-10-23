Extract: Visio_Valdovinos_Assessment.zip
Folder structure:
	Assessment
	External_Jars

How to set up an environment for test and run.
:Open Eclipse if not avaiable, download Eclipse @https://www.eclipse.org/downloads/

:Create a new Java Project
	right-click inside Eclipse Package Explorer window, select "New" -> "Java Project"
	Enter a name
	Java version: 1.8
	click "Finish"
	
:After the Java Project is created:
	right-click the project, select "Import" -> "File System"
	Browse for the Assessment folder
	Select "Assessment" checkbox
	NOTE: Verify all files from the project will be imported.
	click "Finish"

:Add external Jar
	right-click the project and select "Build Path" -> "Configure Build Path"
	under the Libraries table. Add external jars.
	highlight and add both jars provided in the "External_Jars" folder (gson-2.6.2 and junit-4.10)
	apply and close
	
Run the J-Unit Test
:Navigate to com.visiolending.test
	right-click the package and select "Run As" -> "J-Unit Test"
	
Run the requirement
:Navigate to com.visiolending
	right-click the package and select "Run As" -> "Java Application"