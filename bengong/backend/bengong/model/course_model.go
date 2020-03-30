package model

type Course struct {
	ID int `gorm:"primary_key;auto_increment" json:"id,omitempty"`
	Lang string `gorm:"column:lang;not null" json:"lang"`
	LangImg string `gorm:"column:lang_img;not null" json:"lang_img"`
}