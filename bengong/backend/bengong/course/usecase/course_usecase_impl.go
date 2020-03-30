package usecase

import (
	"bengong/course"
	"bengong/model"
)

type CourseUsecaseImpl struct {
	courseRepo course.CourseRepo
}

func CreateCourseUsecaseImpl(courseRepo course.CourseRepo) course.CourseUsecase {
	return &CourseUsecaseImpl{courseRepo}
}

func (c *CourseUsecaseImpl) Insert(course *model.Course) (*model.Course, error) {
	return c.courseRepo.Insert(course)
}

func (c *CourseUsecaseImpl) GetAll() (*[]model.Course, error) {
	return c.courseRepo.GetAll()
}

func (c *CourseUsecaseImpl) GetByID(id int) (*model.Course, error) {
	return c.courseRepo.GetByID(id)
}

func (c *CourseUsecaseImpl) Update(id int, data *model.Course) (*model.Course, error) {
	return c.courseRepo.Update(id, data)
}

func (c *CourseUsecaseImpl) Delete(id int) error {
	return c.courseRepo.Delete(id)
}