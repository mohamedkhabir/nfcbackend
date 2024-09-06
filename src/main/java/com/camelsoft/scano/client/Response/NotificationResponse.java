package com.camelsoft.scano.client.Response;


import com.camelsoft.scano.client.models.notification.notificationmodel;

import java.util.ArrayList;
import java.util.List;

public class NotificationResponse {

    private List<notificationmodel> result = new ArrayList<>();
    private int currentPage;
    private Long totalItems;
    private int totalPages;

    public NotificationResponse() {
    }

    public NotificationResponse(List<notificationmodel> result, int currentPage, Long totalItems, int totalPages) {
        this.result = result;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public List<notificationmodel> getResult() {
        return result;
    }

    public void setResult(List<notificationmodel> result) {
        this.result = result;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
