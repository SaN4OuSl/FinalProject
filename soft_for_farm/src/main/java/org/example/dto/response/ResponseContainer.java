package org.example.dto.response;

public class ResponseContainer<D> {

    D data;

    public ResponseContainer (){
    }

    public ResponseContainer (D data){
        this.data = data;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }
}
