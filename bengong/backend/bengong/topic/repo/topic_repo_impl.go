package repo

import (
	"bengong/model"
	"bengong/topic"
	"fmt"
	"github.com/jinzhu/gorm"
)

type TopicRepoImpl struct {
	db *gorm.DB
}

func CreateTopicRepoImpl(db *gorm.DB) topic.TopicRepo {
	return &TopicRepoImpl{db}
}

func (t *TopicRepoImpl) Insert(topic *model.Topic) (*model.Topic, error) {
	err := t.db.Save(&topic).Error
	if err != nil {
		fmt.Errorf("[TopicRepoImpl.SignUp] Error when query insert: %v\n", err)
	}
	return topic, nil
}

func (t *TopicRepoImpl) GetAll() (*[]model.Topic, error) {
	topic := []model.Topic{}

	err := t.db.Find(&topic).Error
	if err != nil {
		return nil, fmt.Errorf("[TopicRepoImpl.GetDataByUser] Error when query to get all data: %w\n", err)
	}
	return &topic, nil
}

func (t *TopicRepoImpl) GetByID(id int) (*model.Topic, error) {
	topic := model.Topic{}

	err := t.db.First(&topic, id).Error
	if err != nil {
		return nil, fmt.Errorf("[TopicRepoImpl.GetByID] Error when query get data by ID")
	}
	return &topic, nil
}

func (t *TopicRepoImpl) GetTopicByLang(id int) (*[]model.Topic, error) {
	listTopic := []model.Topic{}
	err := t.db.Table("topics").Select("topics.id, topics.id_course, topics.topic_title, topics.topic_link, topics.topic_desc").Joins("join courses on courses.id = topics.id_course").Where("courses.id = ?", id).Scan(&listTopic).Error
	if err != nil {
		return nil, fmt.Errorf("[TopicRepoImpl.GetTopicByLang] Error when query get data by lang")
	}

	return &listTopic, nil
}

func (c *TopicRepoImpl) Update(id int, data *model.Topic) (*model.Topic, error) {
	err := c.db.Model(&data).Where("id = ?", id).Update(map[string]interface{}{
		"topic_title":    data.TopicTitle,
		"topic_link": data.TopicLink,
		"topic_desc": data.TopicDesc}).Error
	if err != nil {
		return nil, fmt.Errorf("[TopicRepoImpl.Update] Error when query update: %v\n", err)
	}
	return data, nil
}

func (c *TopicRepoImpl) Delete(id int) error {
	topic := model.Topic{}
	err := c.db.Where("id = ?", id).Delete(&topic).Error
	if err != nil {
		return fmt.Errorf("[TopicRepoImpl.Delete] Error when query delete: %v\n", err)
	}
	return nil
}