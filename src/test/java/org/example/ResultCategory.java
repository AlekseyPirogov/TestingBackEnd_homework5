package org.example;

class ResultCategory {
    String result;

    ResultCategory(){
        this.result = null;
    }

    ResultCategory(String result) {
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

