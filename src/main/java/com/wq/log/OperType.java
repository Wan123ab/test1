package com.wq.log;

public enum OperType {

    CREATE {
        public String getType() {

            return "创建";
        }

    },
    DELETE {
        public String getType() {

            return "删除";
        }

    },
    UPDATE {
        public String getType() {

            return "修改";
        }

    },
    READ {
        public String getType() {

            return "查询";
        }

    }


}
