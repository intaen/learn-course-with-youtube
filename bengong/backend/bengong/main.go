package main

import (
	"fmt"
	"log"
	"net/http"
	"os"

	"github.com/gorilla/mux"
	"github.com/jinzhu/gorm"
	"github.com/subosito/gotenv"

	"bengong/driver"
	"bengong/middleware"
	uh "bengong/user/handler"
	ur "bengong/user/repo"
	us "bengong/user/usecase"

	ch "bengong/course/handler"
	cr "bengong/course/repo"
	cs "bengong/course/usecase"

	th "bengong/topic/handler"
	tr "bengong/topic/repo"
	ts "bengong/topic/usecase"

	dh "bengong/data/handler"
	dr "bengong/data/repo"
	ds "bengong/data/usecase"
)

var db *gorm.DB

func init() {
	gotenv.Load()
}

func main() {
	db = driver.ConnectDB()
	defer db.Close()
	driver.InitTable(db)

	r := mux.NewRouter().StrictSlash(true)

	userRepo := ur.CreateUserRepoImpl(db)
	userUsecase := us.CreateUserUsecaseImpl(userRepo)
	uh.CreateUserHandler(r, userUsecase)

	courseRepo := cr.CreateCourseRepoImpl(db)
	courseUsecase := cs.CreateCourseUsecaseImpl(courseRepo)
	ch.CreateCourseHandler(r, courseUsecase)

	topicRepo := tr.CreateTopicRepoImpl(db)
	topicUsecase := ts.CreateTopicUsecaseImpl(topicRepo)
	th.CreateTopicHandler(r, topicUsecase)

	dataRepo := dr.CreateDataRepoImpl(db)
	dataUsecase := ds.CreateDataUsecaseImpl(dataRepo)
	dh.CreateDataHandler(r, dataUsecase)

	r.Use(middleware.Logger)

	fmt.Println("Starting Web Server at Port: " + os.Getenv("PORT"))
	err := http.ListenAndServe(":"+os.Getenv("PORT"), r)
	if err != nil {
		log.Fatal(err)
	}
}