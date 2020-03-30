package handler

import (
	"bengong/model"
	"bengong/topic"
	util "bengong/utils"
	"encoding/json"
	"fmt"
	"github.com/gorilla/mux"
	"net/http"
	"strconv"
)

type TopicHandler struct {
	topicUsecase topic.TopicUsecase
}

func CreateTopicHandler(r *mux.Router, topicUsecase topic.TopicUsecase) {
	th := TopicHandler{topicUsecase}

	r.HandleFunc("/topic", th.Insert).Methods(http.MethodPost)
	r.HandleFunc("/topics", th.GetAll).Methods(http.MethodGet)
	s := r.PathPrefix("/topic").Subrouter()
	s.HandleFunc("/{id}", th.GetByID).Methods(http.MethodGet)
	s.HandleFunc("/lang/{id}", th.GetTopicByLang).Methods(http.MethodGet)
	s.HandleFunc("/{id}", th.Update).Methods(http.MethodPut)
	s.HandleFunc("/{id}", th.Delete).Methods(http.MethodDelete)
}

func (t *TopicHandler) Insert(resp http.ResponseWriter, req *http.Request) {
	var topic = model.Topic{}
	err := json.NewDecoder(req.Body).Decode(&topic)
	if err != nil {
		util.HandleError(resp, http.StatusInternalServerError, "Oops, Something went wrong")
		fmt.Printf("[TopicHandler.GetDataByUser] Error when request data to service: %v\n", err)
		return
	}

	data, err := t.topicUsecase.Insert(&topic)
	if err != nil {
		util.HandleError(resp, http.StatusNoContent, "Oops, Something went wrong")
		fmt.Printf("[TopicHandler.GetByID] Error when request data to service: %v\n", err)
		return
	}
	util.HandleSuccess(resp, http.StatusCreated, data)
}

func (t *TopicHandler) GetAll(resp http.ResponseWriter, req *http.Request) {
	topic, err := t.topicUsecase.GetAll()
	if err != nil {
		util.HandleError(resp, http.StatusInternalServerError, "Oops, Something went wrong")
		fmt.Printf("[TopicHandler.GetDataByUser] Error when request data to service: %v\n", err)
		return
	}
	util.HandleSuccess(resp, http.StatusOK, topic)
}

func (t *TopicHandler) GetByID(resp http.ResponseWriter, req *http.Request) {
	pathVar := mux.Vars(req)
	id, err := strconv.Atoi(pathVar["id"])
	if err != nil {
		util.HandleError(resp, http.StatusBadRequest, "ID must number!")
		fmt.Printf("[TopicHandler.GetByID] Error when convert path: %v\n", err)
	}

	topic, err := t.topicUsecase.GetByID(id)
	if err != nil {
		util.HandleError(resp, http.StatusNoContent, "Oops, Something went wrong")
		fmt.Printf("[UserHandler.GetByID] Error when request data to service: %v\n", err)
		return
	}
	util.HandleSuccess(resp, http.StatusOK, topic)
}

func (t *TopicHandler) GetTopicByLang(resp http.ResponseWriter, req *http.Request) {
	pathVar := mux.Vars(req)
	id, err := strconv.Atoi(pathVar["id"])
	if err != nil {
		util.HandleError(resp, http.StatusBadRequest, "ID must number!")
		fmt.Printf("[TopicHandler.GetTopicByLang] Error when convert path: %v\n", err)
	}

	topic, err := t.topicUsecase.GetTopicByLang(id)
	if err != nil {
		util.HandleError(resp, http.StatusNoContent, "Oops, Something went wrong")
		fmt.Printf("[TopicHandler.GetTopicByLang] Error when request data to service: %v\n", err)
		return
	}
	util.HandleSuccess(resp, http.StatusOK, topic)
}

func (t *TopicHandler) Update(resp http.ResponseWriter, req *http.Request) {
	pathVar := mux.Vars(req)
	id, err := strconv.Atoi(pathVar["id"])
	if err != nil {
		util.HandleError(resp, http.StatusInternalServerError, "Oops, Something went wrong")
		fmt.Printf("[TopicHandler.Update] Error when convert path: %v\n", err)
		return
	}

	topic := model.Topic{}
	err = json.NewDecoder(req.Body).Decode(&topic)
	if err != nil {
		util.HandleError(resp, http.StatusInternalServerError, "Oops, Something went wrong")
		fmt.Printf("[TopicHandler.Update] Error when decode data: %v\n", err)
		return
	}

	data, err := t.topicUsecase.Update(id, &topic)
	if err != nil {
		util.HandleError(resp, http.StatusInternalServerError, "Oops, Something went wrong")
		fmt.Printf("[TopicHandler.Update] Error when request data to service: %v\n", err)
		return
	}

	util.HandleSuccess(resp, http.StatusCreated, data)
}

func (t *TopicHandler) Delete(resp http.ResponseWriter, req *http.Request) {
	pathVar := mux.Vars(req)
	id, err := strconv.Atoi(pathVar["id"])
	if err != nil {
		util.HandleError(resp, http.StatusInternalServerError, "Oops, Something went wrong")
		fmt.Printf("[TopicHandler.Delete] Error when convert path: %v\n", err)
		return
	}

	err = t.topicUsecase.Delete(id)
	if err != nil {
		util.HandleError(resp, http.StatusInternalServerError, "Oops, Something went wrong")
		fmt.Printf("[TopicHandler.Delete] Error when request data to service: %v\n", err)
		return
	}
	util.HandleSuccess(resp, http.StatusOK, nil)
}
