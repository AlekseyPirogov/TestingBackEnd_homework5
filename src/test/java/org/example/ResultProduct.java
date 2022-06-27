package org.example;

class ResultProduct {
    String result;

    ResultProduct(){
        this.result = null;
    }

    ResultProduct(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return result;
    }

}
