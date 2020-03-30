package repo

import (
	"bengong/course"
	"bengong/model"
	"fmt"
	"github.com/jinzhu/gorm"
)

type CourseRepoImpl struct {
	db *gorm.DB
}

func CreateCourseRepoImpl(db *gorm.DB) course.CourseRepo {
	return &CourseRepoImpl{db}
}

func (c *CourseRepoImpl) Insert(course *model.Course) (*model.Course, error) {
	err := c.db.Save(&course).Error
	if err != nil {
		fmt.Errorf("[CourseRepoImpl.SignUp] Error when query insert: %v\n", err)
	}
	return course, nil
}

func (c *CourseRepoImpl) GetAll() (*[]model.Course, error) {
	course := []model.Course{}

	err := c.db.Find(&course).Error
	if err != nil {
		return nil, fmt.Errorf("[CourseRepoImpl.GetDataByUser] Error when query to get all data: %w\n", err)
	}
	return &course, nil
}

func (c *CourseRepoImpl) GetByID(id int) (*model.Course, error) {
	user := model.Course{}

	err := c.db.First(&user, id).Error
	if err != nil {
		return nil, fmt.Errorf("[CourseRepoImpl.GetByID] Error when query get data by ID")
	}
	return &user, nil
}

func (c *CourseRepoImpl) Update(id int, data *model.Course) (*model.Course, error) {
	err := c.db.Model(&data).Where("id = ?", id).Update(map[string]interface{}{
		"lang":    data.Lang,
		"lang_img": data.LangImg}).Error
	if err != nil {
		return nil, fmt.Errorf("[CourseRepoImpl.Update] Error when query update: %v\n", err)
	}
	return data, nil
}

func (c *CourseRepoImpl) Delete(id int) error {
	course := model.Course{}
	err := c.db.Where("id = ?", id).Delete(&course).Error
	if err != nil {
		return fmt.Errorf("[CourseRepoImpl.Delete] Error when query delete: %v\n", err)
	}
	return nil
}