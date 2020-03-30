package repo

import (
	"bengong/data"
	"bengong/model"
	"fmt"
	"github.com/jinzhu/gorm"
)

type DataRepoImpl struct {
	db *gorm.DB
}

func CreateDataRepoImpl(db *gorm.DB) data.DataRepo {
	return &DataRepoImpl{db}
}

func (d *DataRepoImpl) GetDataByUser(userID int) (*[]model.Data, error) {
	data := []model.Data{}

	err := d.db.Table("data").Select("data.id, data.id_user, users.name, data.id_course, courses.lang, courses.lang_img").Joins("join users on users.id = data.id_user join courses on courses.id = data.id_course").Where("users.id = ?", userID).Scan(&data).Error
	if err != nil {
		return nil, fmt.Errorf("[DataRepoImpl.GetDataByUser] Error when query get data by lang")
	}

	return &data, nil
}

func (d *DataRepoImpl) Insert(data *model.Data) (*model.Data, error) {
	err := d.db.Save(&data).Error
	if err != nil {
		fmt.Errorf("[DataRepoImpl.Insert] Error when query insert: %v\n", err)
	}
	return data, nil
}