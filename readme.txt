
Steps to run the application:
1. Install .apk file available in outputs folder of this project
2. Make sure you are connected to the internet
3. Enter the keyword in the text box to see viral images related to it
4. You will see grid view with all retrieved images
5. Click on any image to see it in full size
6. Now you can add comments on that image and can also see previous comments on it (if any)

Description of code:

MainActivity.java 
1. Here parseJava is the main function which makes an API call to Imgur to fetch JSON data related to keyword.
2. It extracts links and image title from JSON data received and adds it to the arraylist.
3. These ArrayList are used to set images in gridview
4. We have onclick listener for gridview which calls new activity and shows that particular image in full size.

FullImageActivity.java
1. It gets position of image in gridview from ImageAdapter and retrieves corresponding link and title from arraylist.
2. It has two other features of adding new comments on that image and viewing previously added.
3. It makes function call to DBHandler to add and retrieve comments from SQLite database. 

DBHandler.java
1. Here I have implemented database functions like to create database, add comments and view comments related to particular image.
2. We can use libraries like Sugar ORM to simplify the interaction with SQLite database in Android. It provides simple readily available APIs for db operations so that we don't need to write SQL queries.

ImageAdapter.java
Here I am creating customized adapter to add images in gridview

