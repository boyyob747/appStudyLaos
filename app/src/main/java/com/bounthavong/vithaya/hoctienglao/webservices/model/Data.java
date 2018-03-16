package com.bounthavong.vithaya.hoctienglao.webservices.model;

import java.util.List;

/**
 * Created by Boy- on 17/3/2561.
 */

public class Data {
    /**
     * id : 3
     * name : Người mới bắt đầu
     * categories : [{"id":6,"name":"Con số","url_img":"number.png.png","vocabularies":[{"id":4,"vb_lao":"ສູນ","vb_vn":"Không","vb_karaoke":"sun","url_mp3":"zero.mp3.mp3"},{"id":5,"vb_lao":"ໜຶ່ງ","vb_vn":"Một","vb_karaoke":"nueng","url_mp3":"one.mp3.mp3"},{"id":6,"vb_lao":"ສອງ","vb_vn":"Hai","vb_karaoke":"song","url_mp3":"two.mp3.mp3"},{"id":7,"vb_lao":"ສາມ","vb_vn":"Ba","vb_karaoke":"saam","url_mp3":"three.mp3.mp3"},{"id":8,"vb_lao":"ສີ່","vb_vn":"Bốn","vb_karaoke":"sii","url_mp3":"four.mp3.mp3"},{"id":9,"vb_lao":"ຫ້າ","vb_vn":"Năm","vb_karaoke":"haa","url_mp3":"five.mp3.mp3"},{"id":10,"vb_lao":"ຫົກ","vb_vn":"Sáu","vb_karaoke":"hok","url_mp3":"six.mp3.mp3"},{"id":11,"vb_lao":"ເຈັດ","vb_vn":"Bảy","vb_karaoke":"jet","url_mp3":"seven.mp3.mp3"},{"id":12,"vb_lao":"ແປດ","vb_vn":"Tám","vb_karaoke":"bpaaet","url_mp3":"eight.mp3.mp3"},{"id":13,"vb_lao":"ເກົ້າ\"","vb_vn":"Chín","vb_karaoke":"gow","url_mp3":"nine.mp3.mp3"},{"id":14,"vb_lao":"ສິບ","vb_vn":"Mười","vb_karaoke":"sip","url_mp3":"ten.mp3.mp3"}]}]
     */

    private int id;
    private String name;
    private List<CategoriesBean> categories;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoriesBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categories=" + categories +
                '}';
    }

    public static class CategoriesBean {
        /**
         * id : 6
         * name : Con số
         * url_img : number.png.png
         * vocabularies : [{"id":4,"vb_lao":"ສູນ","vb_vn":"Không","vb_karaoke":"sun","url_mp3":"zero.mp3.mp3"},{"id":5,"vb_lao":"ໜຶ່ງ","vb_vn":"Một","vb_karaoke":"nueng","url_mp3":"one.mp3.mp3"},{"id":6,"vb_lao":"ສອງ","vb_vn":"Hai","vb_karaoke":"song","url_mp3":"two.mp3.mp3"},{"id":7,"vb_lao":"ສາມ","vb_vn":"Ba","vb_karaoke":"saam","url_mp3":"three.mp3.mp3"},{"id":8,"vb_lao":"ສີ່","vb_vn":"Bốn","vb_karaoke":"sii","url_mp3":"four.mp3.mp3"},{"id":9,"vb_lao":"ຫ້າ","vb_vn":"Năm","vb_karaoke":"haa","url_mp3":"five.mp3.mp3"},{"id":10,"vb_lao":"ຫົກ","vb_vn":"Sáu","vb_karaoke":"hok","url_mp3":"six.mp3.mp3"},{"id":11,"vb_lao":"ເຈັດ","vb_vn":"Bảy","vb_karaoke":"jet","url_mp3":"seven.mp3.mp3"},{"id":12,"vb_lao":"ແປດ","vb_vn":"Tám","vb_karaoke":"bpaaet","url_mp3":"eight.mp3.mp3"},{"id":13,"vb_lao":"ເກົ້າ\"","vb_vn":"Chín","vb_karaoke":"gow","url_mp3":"nine.mp3.mp3"},{"id":14,"vb_lao":"ສິບ","vb_vn":"Mười","vb_karaoke":"sip","url_mp3":"ten.mp3.mp3"}]
         */

        private int id;
        private String name;
        private String url_img;
        private List<VocabulariesBean> vocabularies;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl_img() {
            return url_img;
        }

        public void setUrl_img(String url_img) {
            this.url_img = url_img;
        }

        public List<VocabulariesBean> getVocabularies() {
            return vocabularies;
        }

        public void setVocabularies(List<VocabulariesBean> vocabularies) {
            this.vocabularies = vocabularies;
        }

        @Override
        public String toString() {
            return "CategoriesBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", url_img='" + url_img + '\'' +
                    ", vocabularies=" + vocabularies +
                    '}';
        }

        public static class VocabulariesBean {
            /**
             * id : 4
             * vb_lao : ສູນ
             * vb_vn : Không
             * vb_karaoke : sun
             * url_mp3 : zero.mp3.mp3
             */

            private int id;
            private String vb_lao;
            private String vb_vn;
            private String vb_karaoke;
            private String url_mp3;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getVb_lao() {
                return vb_lao;
            }

            public void setVb_lao(String vb_lao) {
                this.vb_lao = vb_lao;
            }

            public String getVb_vn() {
                return vb_vn;
            }

            public void setVb_vn(String vb_vn) {
                this.vb_vn = vb_vn;
            }

            public String getVb_karaoke() {
                return vb_karaoke;
            }

            public void setVb_karaoke(String vb_karaoke) {
                this.vb_karaoke = vb_karaoke;
            }

            public String getUrl_mp3() {
                return url_mp3;
            }

            public void setUrl_mp3(String url_mp3) {
                this.url_mp3 = url_mp3;
            }

            @Override
            public String toString() {
                return "VocabulariesBean{" +
                        "id=" + id +
                        ", vb_lao='" + vb_lao + '\'' +
                        ", vb_vn='" + vb_vn + '\'' +
                        ", vb_karaoke='" + vb_karaoke + '\'' +
                        ", url_mp3='" + url_mp3 + '\'' +
                        '}';
            }
        }
    }
}
