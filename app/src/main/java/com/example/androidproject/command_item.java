package com.example.androidproject;

public class command_item {
        private String name;
        private String price;
        private String hot;
        private int icon;


        public command_item(String name, String price,String hot,int icon) {
            this.name = name;
            this.price = price;
            this.hot = hot;
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }
        public String getHot(){
            return hot;
        }

        public int getIcon() {
            return icon;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(String price) {
            this.price = price;
        }
        public void setHot(String hot){
            this.hot=hot;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }
    }
