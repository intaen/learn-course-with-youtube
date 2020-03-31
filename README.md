# Course With Youtube
##### @intanmarsjaf
-----------------------
### Mobile Apps Course with Youtube using Kotlin and Golang
So, in this project I made mobile apps about learn coding using youtube video. I name this apps with Bengong because it stands for "BElajar NGodiNg Gratis" or "Learn Code for Free".
I use two programming language to build this, golang for backend and kotlin for frontend. Stack in this apps are:
#### Golang:
+ Gorm
+ Postgre
+ Env
+ Bycrypt
+ Gorilla Mux
#### Kotlin:
+ Retrofit
+ Custom Array Adapter
+ Shared Preferences
+ View Model
+ PierfrancescoSoffritti/android-youtube-player
#### Assets:
+ flaticon.com
+ fonts.google.com

#### To run this apps, all you have to do:
+ Create database (I set up my database with name bengong), because im using gorm, so you dont have to create table
+ Run main.go file and the server will start in localhost:5050
```sh
go run main.go
```
+ Then, open android studio and start emulator, klik l>, wait for awhile...
+ Because the database is empty, we have to add the course
+ So, this is API for BengongApps:
#### GetAll User
```sh
http://localhost:5050/users
```
#### GetByID User
```sh
http://localhost:5050/user/{id}
```
#### SignUp User
```sh
http://localhost:5050/user/signup
```
```sh
{
    "name": "example",
    "username": "example",
    "email": "example@gmail.com",
    "password": "123"
}
```
#### Login User
```sh
http://localhost:5050/user/login
```
```sh
{
    "email": "example@gmail.com",
    "password": "123"
}
```
#### Update User (Put)
```sh
http://localhost:5050/user/{id}
```
#### Delete User (Delete)
```sh
http://localhost:5050/user/{id}
```
----------------------------------
#### GetAll Course
```sh
http://localhost:5050/courses
```
#### GetByID Course
```sh
http://localhost:5050/course/{id}
```
#### Insert Course (Post)
```sh
http://localhost:5050/course
```
```sh
{
    "lang": "programming_language_name",
    "lang_img": "http://link_logo_of_prog_lang.png",
}
```
#### Update Course (Put)
```sh
http://localhost:5050/course/{id}
```
#### Delete Course (Delete)
```sh
http://localhost:5050/course/{id}
```
----------------------------------
#### GetAll Topic
```sh
http://localhost:5050/topics
```
#### GetByID Topic
```sh
http://localhost:5050/topic/{id}
```
#### Insert Topic (Post)
```sh
http://localhost:5050/topic
```
```sh
{
    "id_course": 1,
	"topic_title": "topic_of_prog_lang, ex: variables",
	"topic_link": "link_id_youtube_that_talks_about_variables",
	"topic_desc": "description_about_the_topic"
}
```
#### Update Topic (Put)
```sh
http://localhost:5050/topic/{id}
```
#### Delete Topic (Delete)
```sh
http://localhost:5050/topic/{id}
```
Ps. Actually I want to add features like tag some course that user choose, but I still cant make it, but I already prepare the API (GetDataByUser and Insert Data)
#### GetDataByUser
```sh
http://localhost:5050/data/user/{id}
```
#### Insert Data (Post)
```sh
http://localhost:5050/data
```
```sh
{
	"id_user": 1,
	"id_course": 2
}
```

