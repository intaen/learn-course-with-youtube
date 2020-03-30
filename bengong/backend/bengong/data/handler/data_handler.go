package handler

import (
	"bengong/data"
	"bengong/model"
	util "bengong/utils"
	"encoding/json"
	"fmt"
	"github.com/gorilla/mux"
	"net/http"
	"strconv"
)

type DataHandler struct {
	dataUsecase data.DataUsecase
}

func CreateDataHandler(r *mux.Router, dataUsecase data.DataUsecase) {
	dh := DataHandler{dataUsecase}

	r.HandleFunc("/data/user/{id}", dh.GetDataByUser).Methods(http.MethodGet)
	r.HandleFunc("/data", dh.Insert).Methods(http.MethodPost)
}

func (d *DataHandler) GetDataByUser(resp http.ResponseWriter, req *http.Request) {
	pathVar := mux.Vars(req)
	id, err := strconv.Atoi(pathVar["id"])
	if err != nil {
		util.HandleError(resp, http.StatusBadRequest, "ID must number!")
		fmt.Printf("[DataHandler.GetDataByUser] Error when convert path: %v\n", err)
	}

	data, err := d.dataUsecase.GetDataByUser(id)
	if err != nil {
		util.HandleError(resp, http.StatusInternalServerError, "Oops, Something went wrong")
		fmt.Printf("[DataHandler.GetDataByUser] Error when request data to service: %v\n", err)
		return
	}
	util.HandleSuccess(resp, http.StatusOK, data)
}

func (d *DataHandler) Insert(resp http.ResponseWriter, req *http.Request) {
	var data = model.Data{}
	err := json.NewDecoder(req.Body).Decode(&data)
	if err != nil {
		util.HandleError(resp, http.StatusInternalServerError, "Oops, Something went wrong")
		fmt.Printf("[DataHandler.Insert] Error when request data to service: %v\n", err)
		return
	}

	listData, err := d.dataUsecase.Insert(&data)
	if err != nil {
		util.HandleError(resp, http.StatusNoContent, "Oops, Something went wrong")
		fmt.Printf("[DataHandler.Insert] Error when request data to service: %v\n", err)
		return
	}
	util.HandleSuccess(resp, http.StatusCreated, listData)
}