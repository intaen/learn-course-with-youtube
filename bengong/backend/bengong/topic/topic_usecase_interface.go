package topic

import "bengong/model"

type TopicUsecase interface {
	Insert(topic *model.Topic) (*model.Topic, error)
	GetAll() (*[]model.Topic, error)
	GetByID(id int) (*model.Topic, error)
	GetTopicByLang(id int) (*[]model.Topic, error)
	Update(id int, data *model.Topic) (*model.Topic, error)
	Delete(id int) error
}
