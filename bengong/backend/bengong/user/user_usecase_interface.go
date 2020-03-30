package user

import "bengong/model"

type UserUsecase interface {
	SignUp(user *model.User) (*model.User, error)
	Login(user *model.User) (*model.User, error)
	GetAll() (*[]model.User, error)
	GetByID(id int) (*model.User, error)
	Update(id int, data *model.User) (*model.User, error)
	Delete(id int) error
}
