package handler

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"strconv"

	"github.com/gorilla/mux"
	"github.com/jinzhu/gorm"
	"golang.org/x/crypto/bcrypt"

	"bengong/model"
	"bengong/user"
	util "bengong/utils"
)

var db *gorm.DB

type UserHandler struct {
	userUsecase user.UserUsecase
}

func CreateUserHandler(r *mux.Router, userUsecase user.UserUsecase) {
	userHandler := UserHandler{userUsecase}

	s := r.PathPrefix("/user").Subrouter()
	s.HandleFunc("/signup", userHandler.SignUp).Methods(http.MethodPost)
	s.HandleFunc("/login", userHandler.Login).Methods(http.MethodPost)
	r.HandleFunc("/users", userHandler.GetAll).Methods(http.MethodGet)
	s.HandleFunc("/{id}", userHandler.GetByID).Methods(http.MethodGet)
	s.HandleFunc("/{id}", userHandler.Update).Methods(http.MethodPut)
	s.HandleFunc("/{id}", userHandler.Delete).Methods(http.MethodDelete)
}

func (u *UserHandler) SignUp(resp http.ResponseWriter, req *http.Request) {

	var user = model.User{}

	resp.Header().Set("Content-type", "application/json")
	json.NewDecoder(req.Body).Decode(&user)

	if user.Email == "" {
		util.HandleError(resp, http.StatusBadRequest, "Email is needed!")
		return
	}
	if user.Password == "" {
		util.HandleError(resp, http.StatusBadRequest, "Password is needed!")
		return
	}

	hash, err := bcrypt.GenerateFromPassword([]byte(user.Password), 10)
	if err != nil {
		fmt.Printf("[Hash Password] Error: %v", err)
		log.Fatal(err)
	}

	user.Password = string(hash)

	u.userUsecase.SignUp(&user)
	util.HandleSuccess(resp, http.StatusCreated, user)
}

func (u *UserHandler) Login(resp http.ResponseWriter, req *http.Request) {
	var user = model.User{}

	resp.Header().Set("Content-type", "application/json")
	json.NewDecoder(req.Body).Decode(&user)

	if user.Email == "" {
		util.HandleError(resp, http.StatusBadRequest, "Email is needed!")
		return
	}
	if user.Password == "" {
		util.HandleError(resp, http.StatusBadRequest, "Password is needed!")
		return
	}

	password := user.Password
	hashedPassword, err := u.userUsecase.Login(&user)
	if err != nil {
		util.HandleError(resp, http.StatusBadRequest, "User doesn't exist")
		return
	}

	isValidPassword := util.ComparePassword(hashedPassword.Password, []byte(password))
	if isValidPassword {
		util.HandleSuccess(resp, http.StatusOK, user)
	} else {
		util.HandleError(resp, http.StatusUnauthorized, "Access Denied!")
		return
	}
}

func (u *UserHandler) GetAll(resp http.ResponseWriter, req *http.Request) {
	user, err := u.userUsecase.GetAll()
	if err != nil {
		util.HandleError(resp, http.StatusInternalServerError, "Oops, Something went wrong")
		fmt.Printf("[UserHandler.GetDataByUser] Error when request data to service: %v\n", err)
		return
	}
	util.HandleSuccess(resp, http.StatusOK, user)
}

func (u *UserHandler) GetByID(resp http.ResponseWriter, req *http.Request) {
	pathVar := mux.Vars(req)
	id, err := strconv.Atoi(pathVar["id"])
	if err != nil {
		util.HandleError(resp, http.StatusBadRequest, "ID must number!")
		fmt.Printf("[UserHandler.GetByID] Error when convert path: %v\n", err)
		return
	}

	user, err := u.userUsecase.GetByID(id)
	if err != nil {
		util.HandleError(resp, http.StatusNoContent, "Oops, Something went wrong")
		fmt.Printf("[UserHandler.GetByID] Error when request data to service: %v\n", err)
		return
	}
	util.HandleSuccess(resp, http.StatusOK, user)
}

func (u *UserHandler) Update(resp http.ResponseWriter, req *http.Request) {
	pathVar := mux.Vars(req)
	id, err := strconv.Atoi(pathVar["id"])
	if err != nil {
		util.HandleError(resp, http.StatusInternalServerError, "Oops, Something went wrong")
		fmt.Printf("[UserHandler.Update] Error when convert path: %v\n", err)
		return
	}

	user := model.User{}
	json.NewDecoder(req.Body).Decode(&user)

	if user.Email == "" {
		util.HandleError(resp, http.StatusBadRequest, "Email is needed!")
		return
	}
	if user.Password == "" {
		util.HandleError(resp, http.StatusBadRequest, "Password is needed!")
		return
	}

	hash, err := bcrypt.GenerateFromPassword([]byte(user.Password), 10)
	if err != nil {
		fmt.Printf("[Hash Password] Error: %v", err)
		log.Fatal(err)
	}

	user.Password = string(hash)

	data, err := u.userUsecase.Update(id, &user)
	if err != nil {
		util.HandleError(resp, http.StatusInternalServerError, "Oops, Something went wrong")
		fmt.Printf("[UserHandler.Update] Error when request data to service: %v\n", err)
		return
	}

	util.HandleSuccess(resp, http.StatusCreated, data)
}

func (u *UserHandler) Delete(resp http.ResponseWriter, req *http.Request) {
	pathVar := mux.Vars(req)
	id, err := strconv.Atoi(pathVar["id"])
	if err != nil {
		util.HandleError(resp, http.StatusInternalServerError, "Oops, Something went wrong")
		fmt.Printf("[UserHandler.Delete] Error when convert path: %v\n", err)
		return
	}

	err = u.userUsecase.Delete(id)
	if err != nil {
		util.HandleError(resp, http.StatusInternalServerError, "Oops, Something went wrong")
		fmt.Printf("[UserHandler.Delete] Error when request data to service: %v\n", err)
		return
	}
	util.HandleSuccess(resp, http.StatusOK, "Data deleted!")
}

func (u *UserHandler) HashPassword(user *model.User) string {
	hash, err := bcrypt.GenerateFromPassword([]byte(user.Password), 10)
	if err != nil {
		fmt.Printf("[Hash Password] Error: %v", err)
		log.Fatal(err)
	}
	user.Password = string(hash)
	hashPassword := user.Password

	return hashPassword
}
