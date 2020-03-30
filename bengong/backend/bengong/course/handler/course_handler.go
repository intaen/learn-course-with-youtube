package handler

import (
	"bengong/course"
	"bengong/model"
	util "bengong/utils"
	"encoding/json"
	"fmt"
	"github.com/gorilla/mux"
	"net/http"
	"strconv"
)

type CourseHandler struct {
	courseUsecase course.CourseUsecase
}

func CreateCourseHandler(r *mux.Router, courseUsecase course.CourseUsecase) {
	ch := CourseHandler{courseUsecase}

	r.HandleFunc("/course", ch.Insert).Methods(http.MethodPost)
	r.HandleFunc("/courses", ch.GetAll).Methods(http.MethodGet)
	s := r.PathPrefix("/course").Subrouter()
	s.HandleFunc("/{id}", ch.GetByID).Methods(http.MethodGet)
	s.HandleFunc("/{id}", ch.Update).Methods(http.MethodPut)
	s.HandleFunc("/{id}", ch.Delete).Methods(http.MethodDelete)
}

func (c *CourseHandler) Insert(resp http.ResponseWriter, req *http.Request) {
	var course = model.Course{}
	err := json.NewDecoder(req.Body).Decode(&course)
	if err != nil {
		util.HandleError(resp, http.StatusInternalServerError, "Oops, Something went wrong")
		fmt.Printf("[CourseHandler.Insert] Error when request data to service: %v\n", err)
		return
	}

	data, err := c.courseUsecase.Insert(&course)
	if err != nil {
		util.HandleError(resp, http.StatusNoContent, "Oops, Something went wrong")
		fmt.Printf("[CourseHandler.Insert] Error when request data to service: %v\n", err)
		return
	}
	util.HandleSuccess(resp, http.StatusCreated, data)
}

func (c *CourseHandler) GetAll(resp http.ResponseWriter, req *http.Request) {
	course, err := c.courseUsecase.GetAll()
	if err != nil {
		util.HandleError(resp, http.StatusInternalServerError, "Oops, Something went wrong")
		fmt.Printf("[CourseHandler.GetDataByUser] Error when request data to service: %v\n", err)
		return
	}
	util.HandleSuccess(resp, http.StatusOK, course)
}

func (c *CourseHandler) GetByID(resp http.ResponseWriter, req *http.Request) {
	pathVar := mux.Vars(req)
	id, err := strconv.Atoi(pathVar["id"])
	if err != nil {
		util.HandleError(resp, http.StatusBadRequest, "ID must number!")
		fmt.Printf("[CourseHandler.GetByID] Error when convert path: %v\n", err)
	}

	course, err := c.courseUsecase.GetByID(id)
	if err != nil {
		util.HandleError(resp, http.StatusNoContent, "Oops, Something went wrong")
		fmt.Printf("[UserHandler.GetByID] Error when request data to service: %v\n", err)
		return
	}
	util.HandleSuccess(resp, http.StatusOK, course)
}

func (c *CourseHandler) Update(resp http.ResponseWriter, req *http.Request) {
	pathVar := mux.Vars(req)
	id, err := strconv.Atoi(pathVar["id"])
	if err != nil {
		util.HandleError(resp, http.StatusInternalServerError, "Oops, Something went wrong")
		fmt.Printf("[CourseHandler.Update] Error when convert path: %v\n", err)
		return
	}

	course := model.Course{}
	err = json.NewDecoder(req.Body).Decode(&course)
	if err != nil {
		util.HandleError(resp, http.StatusInternalServerError, "Oops, Something went wrong")
		fmt.Printf("[CourseHandler.Update] Error when decode data: %v\n", err)
		return
	}

	data, err := c.courseUsecase.Update(id, &course)
	if err != nil {
		util.HandleError(resp, http.StatusInternalServerError, "Oops, Something went wrong")
		fmt.Printf("[CourseHandler.Update] Error when request data to service: %v\n", err)
		return
	}

	util.HandleSuccess(resp, http.StatusCreated, data)
}

func (c *CourseHandler) Delete(resp http.ResponseWriter, req *http.Request) {
	pathVar := mux.Vars(req)
	id, err := strconv.Atoi(pathVar["id"])
	if err != nil {
		util.HandleError(resp, http.StatusInternalServerError, "Oops, Something went wrong")
		fmt.Printf("[CourseHandler.Delete] Error when convert path: %v\n", err)
		return
	}

	err = c.courseUsecase.Delete(id)
	if err != nil {
		util.HandleError(resp, http.StatusInternalServerError, "Oops, Something went wrong")
		fmt.Printf("[CourseHandler.Delete] Error when request data to service: %v\n", err)
		return
	}
	util.HandleSuccess(resp, http.StatusOK, nil)
}
