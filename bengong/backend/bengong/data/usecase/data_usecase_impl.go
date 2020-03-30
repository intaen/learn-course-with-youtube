package usecase

import (
	"bengong/data"
	"bengong/model"
)

type DataUsecaseImpl struct {
	dataRepo data.DataRepo
}

func CreateDataUsecaseImpl(dataRepo data.DataRepo) data.DataUsecase {
	return &DataUsecaseImpl{dataRepo}
}

func (d *DataUsecaseImpl) GetDataByUser(userID int) (*[]model.Data, error) {
	return d.dataRepo.GetDataByUser(userID)
}

func (d *DataUsecaseImpl) Insert(data *model.Data) (*model.Data, error) {
	return d.dataRepo.Insert(data)
}