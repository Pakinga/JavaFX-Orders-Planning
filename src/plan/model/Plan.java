package plan.model;

public class Plan {
    private int id;
    private int orderNum;
    private String productType;
    private String worker;
    private int plannedTime;
    private int actualTime;
    private String orderStatus;
    private int userId;

    public Plan(int id, int orderNum, String productType, String worker, int plannedTime, int actualTime, String orderStatus, int userId) {
        this.id = id;
        this.orderNum = orderNum;
        this.productType = productType;
        this.worker = worker;
        this.plannedTime = plannedTime;
        this.actualTime = actualTime;
        this.orderStatus = orderStatus;
        this.userId = userId;
    }

    public Plan(int orderNum, String productType, String worker, int plannedTime, int actualTime, String orderStatus, int userId) {
        this.orderNum = orderNum;
        this.productType = productType;
        this.worker = worker;
        this.plannedTime = plannedTime;
        this.actualTime = actualTime;
        this.orderStatus = orderStatus;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public int getPlannedTime() {
        return plannedTime;
    }

    public void setPlannedTime(int plannedTime) {
        this.plannedTime = plannedTime;
    }

    public int getActualTime() {
        return actualTime;
    }

    public void setActualTime(int actualTime) {
        this.actualTime = actualTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "\nPlan{" +
                "id=" + id +
                ", orderNum=" + orderNum +
                ", productType='" + productType + '\'' +
                ", worker='" + worker + '\'' +
                ", plannedTime=" + plannedTime +
                ", actualTime=" + actualTime +
                ", orderStatus='" + orderStatus + '\'' +
                ", userId=" + userId +
                '}';
    }
}
