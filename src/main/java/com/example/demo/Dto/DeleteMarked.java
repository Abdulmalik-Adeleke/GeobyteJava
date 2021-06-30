package com.example.demo.Dto;

import java.util.List;

public class DeleteMarked {
    private List<Integer> ids;

    public DeleteMarked() {
    }


    public DeleteMarked(List<Integer> ids) {
        this.ids = ids;
    }

    public List<Integer> getIds() {
        return this.ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    
}
