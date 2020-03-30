package course

import "bengong/model"

type CourseRepo interface {
	Insert(course *model.Course) (*model.Course, error)
	GetAll() (*[]model.Course, error)
	GetByID(id int) (*model.Course, error)
	Update(id int, data *model.Course) (*model.Course, error)
	Delete(id int) error
}