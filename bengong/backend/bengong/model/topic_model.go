package model

type Topic struct {
	ID int `gorm:"primary_key;auto_increment" json:"id,omitempty"`
	ID_Course int `gorm:"column:id_course" json:"id_course"`
	TopicTitle string `gorm:"column:topic_title;not null" json:"topic_title"`
	TopicLink string `gorm:"column:topic_link;not null" json:"topic_link"`
	TopicDesc string `gorm:"column:topic_desc;not null" json:"topic_desc"`
}