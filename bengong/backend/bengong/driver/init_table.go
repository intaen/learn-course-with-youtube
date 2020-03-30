package driver

import (
	"bengong/model"

	"github.com/jinzhu/gorm"
)

//InitTable representaion
func InitTable(db *gorm.DB) {
	db.AutoMigrate(&model.User{}, &model.Course{}, &model.Topic{}, &model.Data{})
	db.Model(&model.Topic{}).AddForeignKey("id_course", "courses(id)", "CASCADE", "CASCADE")
}
