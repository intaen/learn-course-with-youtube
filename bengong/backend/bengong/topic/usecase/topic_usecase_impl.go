package usecase

import (
	"bengong/model"
	"bengong/topic"
)

type TopicUsecaseImpl struct {
	topicRepo topic.TopicRepo
}

func CreateTopicUsecaseImpl(topicRepo topic.TopicRepo) topic.TopicUsecase {
	return &TopicUsecaseImpl{topicRepo}
}

func (t *TopicUsecaseImpl) Insert(topic *model.Topic) (*model.Topic, error) {
	return t.topicRepo.Insert(topic)
}

func (t *TopicUsecaseImpl) GetAll() (*[]model.Topic, error) {
	return t.topicRepo.GetAll()
}

func (t *TopicUsecaseImpl) GetByID(id int) (*model.Topic, error) {
	return t.topicRepo.GetByID(id)
}

func (t *TopicUsecaseImpl) GetTopicByLang(id int) (*[]model.Topic, error) {
	return t.topicRepo.GetTopicByLang(id)
}

func (t *TopicUsecaseImpl) Update(id int, data *model.Topic) (*model.Topic, error) {
	return t.topicRepo.Update(id, data)
}

func (t *TopicUsecaseImpl) Delete(id int) error {
	return t.topicRepo.Delete(id)
}