package com.lzf.http.entity;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/11/8.
 */
public class FaceResultModel {
    private boolean can_door_open;
    private int error;
    private PersonBean person;

    public boolean isCan_door_open() {
        return can_door_open;
    }

    public void setCan_door_open(boolean can_door_open) {
        this.can_door_open = can_door_open;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public PersonBean getPerson() {
        return person;
    }

    public void setPerson(PersonBean person) {
        this.person = person;
    }

    public static class PersonBean{
        private double confidence;
        private int id;
        private int photo_id;
        private int subject_id;
        private String tag;

        public double getConfidence() {
            return confidence;
        }

        public void setConfidence(double confidence) {
            this.confidence = confidence;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPhoto_id() {
            return photo_id;
        }

        public void setPhoto_id(int photo_id) {
            this.photo_id = photo_id;
        }

        public int getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(int subject_id) {
            this.subject_id = subject_id;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
    }
}
