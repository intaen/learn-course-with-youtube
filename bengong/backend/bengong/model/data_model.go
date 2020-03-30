package model

type Data struct {
	ID int `gorm:"primary_key;auto_increment" json:"id,omitempty"`
	ID_User int `gorm:"column:id_user;not null; type:int REFERENCES users(id)" json:"id_user"`
	ID_Course int `gorm:"column:id_course;not null; type:int REFERENCES courses(id)" json:"id_course"`
}