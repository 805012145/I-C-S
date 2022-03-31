package com.antl.ics.entity;

import java.util.ArrayList;

public class TableEntity {
    private Data data = new Data();

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        /**
         * source
         */
        private Object source = new ArrayList<>();

        public Object getSource() {
            return source;
        }

        public void setSource(Object source) {
            this.source = source;
        }
    }
}
